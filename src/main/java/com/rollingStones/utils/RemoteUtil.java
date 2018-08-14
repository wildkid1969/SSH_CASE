package com.rollingStones.utils;

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class RemoteUtil {


    private static final int RETRY_COUNT = 3; // 超时重试次数

    private static final int TIME_OUT = 3000; // 超时数

    private static final int MAX_PER_ROUTE = 100; // 路由最大连接数

    private static final int MAX_TOTAL = 300; // 连接池的最大连接数

    private static final String CHARSET = "UTF-8";

    private static final String EMPTY = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteUtil.class);

    private static final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(register());

    private static final CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .setRetryHandler(getRetryHandler())
            .setDefaultRequestConfig(getRequestConfig())
            .build();

    private RemoteUtil() {

    }

    static {
        initConnectionManager(connectionManager);
    }

    private static void initConnectionManager(PoolingHttpClientConnectionManager clientConnectionManager) {
        // socket 超时时间
        clientConnectionManager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(TIME_OUT).build());
        // 路由的最大连接数
        clientConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        // 连接池最大连接数
        clientConnectionManager.setMaxTotal(MAX_TOTAL);
    }

    private static Registry<ConnectionSocketFactory> register() {
        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", new SSLConnectionSocketFactory(sslContext(), new NoopHostnameVerifier()))
                .register("http", PlainConnectionSocketFactory.getSocketFactory()) // thread-safe
                .build();
    }

    private static SSLContext sslContext() {
        try {
            return SSLContexts.custom().loadTrustMaterial(null, new TrustAllStrategy()).build();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                // 分配的 socket的 soTimeOut, 后续处理过程中使用这个超时时间
                .setSocketTimeout(TIME_OUT)
                // socket 建立网络连接, 超时时间.
                .setConnectTimeout(TIME_OUT)
                // 从连接池获取连接, 等待时间.
                .setConnectionRequestTimeout(TIME_OUT)
                .build();
    }

    private static HttpRequestRetryHandler getRetryHandler() {
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                LOGGER.info("retry count: {}, exception message: {}", executionCount, exception.getMessage());
                if (executionCount >= RETRY_COUNT) {
                    return false;
                }
                if (exception instanceof SocketException) {
                    return true;
                }
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                final HttpClientContext clientContext = HttpClientContext.adapt(context);
                final HttpRequest request = clientContext.getRequest();
                boolean handleAsIdempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (handleAsIdempotent) {
                    return true;
                }
                return false;
            }
        };
    }

    private static ResponseResult requestAndResponse(HttpUriRequest request) {
        HttpEntity entity = null;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                entity = response.getEntity();
                if (entity != null) {
                    return new ResponseResult(true, status, EntityUtils.toString(entity, CHARSET));
                }
                LOGGER.error("service call normal, entity is null, url: {}", request.getURI());
            }
            LOGGER.error("service call error, url: {}, status: {}", request.getURI(), status);
            return new ResponseResult(false, status, EMPTY);
        } catch (Exception e) {
            LOGGER.error("service call exception, url: {} {}", request.getURI(), e);
        } finally {
            closeResponse(response);
            EntityUtils.consumeQuietly(entity);
        }
        return new ResponseResult(false, 0, EMPTY);
    }

    private static void closeResponse(CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                LOGGER.error("close http response error {}", e);
            }
        }
    }

    private static class ResponseResult {

        boolean success;
        int status;
        String result;

        public ResponseResult(boolean success, int status, String result) {
            this.success = success;
            this.status = status;
            this.result = result;
        }

    }

    public static String get(String url) {
        return get(url, null);
    }

    public static String get(String url, final Map<String, String> params) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        String newUrl = setParam(url, params);
        HttpGet httpGet = new HttpGet(newUrl);

        LOGGER.info("service call: {}, params: {}", url, params);
        ResponseResult result = requestAndResponse(httpGet);
        LOGGER.info("service response time: {}, status: {}, result: {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), result.status, result.result);

        if (result.success) {
            return result.result;
        }

        throw new IllegalArgumentException("service call error");
    }

    public static String post(String url, final Map<String, String> params) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        HttpPost httpPost = new HttpPost(url);
        setParam(httpPost, params);

        LOGGER.info("service call: {}, params: {}", url, params);
        ResponseResult result = requestAndResponse(httpPost);
        LOGGER.info("service response time: {}, status: {}, result: {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), result.status, result.result);

        if (result.success) {
            return result.result;
        }

        throw new IllegalArgumentException("service call error");
    }

    public static String postJson(final String url, final String jsonParam) {
        return postJson(url, jsonParam, null);
    }

    public static String postJson(final String url, final String jsonParam, final Map<String, String> headParams) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        HttpPost httpPost = new HttpPost(url);
        addHeaders(httpPost, headParams);
        StringEntity stringEntity = new StringEntity(jsonParam, CHARSET);
        stringEntity.setContentType("application/json");

        LOGGER.info("service call: {}, params: {}", url, jsonParam);
        ResponseResult result = requestAndResponse(httpPost);
        LOGGER.info("service response time: {}, status: {}, result: {}", stopwatch.elapsed(TimeUnit.MILLISECONDS), result.status, result.result);

        if (result.success) {
            return result.result;
        }

        throw new IllegalArgumentException("service call error");
    }


    private static void setParam(final HttpPost httpPost, final Map<String, String> params) {

        if (params == null || params.isEmpty()) {
            return;
        }

        List<NameValuePair> formParams = Lists.newArrayList();
        for (Map.Entry<String, String> entrySet : params.entrySet()) {
            if (StringUtils.isBlank(entrySet.getValue())) {
                continue;
            }

            formParams.add(new BasicNameValuePair(entrySet.getKey(), entrySet.getValue()));
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, CHARSET));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("setParams error {}", e);
        }

    }


    private static String setParam(final String url, final Map<String, String> params) {

        if (StringUtils.isBlank(url) || params == null || params.isEmpty()) {
            return Strings.nullToEmpty(url);
        }

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url.contains("?") ? "&" : "?");

        for (Map.Entry<String, String> entrySet : params.entrySet()) {
            if (StringUtils.isBlank(entrySet.getValue())) {
                continue;
            }

            try {
                urlBuilder.append(entrySet.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entrySet.getValue(), CHARSET))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("setParams error {}", e);
            }
        }

        // delete end '&' or '?'
        urlBuilder.substring(urlBuilder.length() - 1, urlBuilder.length());
        return urlBuilder.toString();
    }

    private static void addHeaders(final HttpPost httpPost, final Map<String, String> headParams) {

        if (headParams == null || headParams.isEmpty()) {
            return;
        }

        for (Map.Entry<String, String> entrySet : headParams.entrySet()) {
            if (StringUtils.isBlank(entrySet.getValue())) {
                continue;
            }

            httpPost.addHeader(entrySet.getKey(), entrySet.getValue());
        }
    }
}
