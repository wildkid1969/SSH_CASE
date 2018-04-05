package com.rollingStones.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

public class HttpClientUtils {
    
    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    
    /**
     * 获取UserAgent
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        String ua = "";
        if (!StringUtils.isEmpty(request.getHeader("user-agent"))) {
            ua = request.getHeader("user-agent");
        } else if (!StringUtils.isEmpty(request.getHeader("User-Agent"))) {
            ua = request.getHeader("User-Agent");
        } else if (!StringUtils.isEmpty(request.getHeader("user-Agent"))) {
            ua = request.getHeader("user-Agent");
        }
        return ua;
    }
    

    /**
     * 发送get请求
     * @param url
     * @param charset
     * @return
     */
    public static String doGet(String url, String charset) {

        StringBuffer buf = new StringBuffer();
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl
                    .openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
//          connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 5.0.1; GT-I9502 Build/LRX22C; wv) "
//                  + "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.121 Mobile Safari/537.36 "
//                  + "MicroMessenger/6.1.0.78_r1129455.543 NetType/WIFI");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), charset));// 设置编码,否则中文乱码
            String lines;
            while ((lines = reader.readLine()) != null) {
                buf.append(lines);
            }
            connection.disconnect();
            reader.close();
        } catch (Exception e) {
        }
        return buf.toString();
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url + "?" + param;

            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setReadTimeout(10000);
//          conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            conn.getHeaderFields();
            // 遍历所有的响应头字段
            // for (String key : map.keySet()) {
            // System.out.println(key + "--->" + map.get(key));
            // }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 向指定URL发送POST方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            // conn.setRequestProperty("connection", "Keep-Alive");
            conn.setReadTimeout(10000);
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    
    public static void onlyPostUrl(String url) {
        java.net.HttpURLConnection l_connection = null;
        java.io.InputStream l_urlStream = null;
        try {
            java.net.URL l_url = new java.net.URL(url);
            l_connection = (java.net.HttpURLConnection) l_url.openConnection();
            l_connection.connect();
            l_urlStream = l_connection.getInputStream();
            l_urlStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l_connection.disconnect();
        }
    }
    
    public static String doHttpsPost(String url, Map<String, String> map, String charset) {  
        org.apache.http.client.HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try {  
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url);  
            // 设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while (iterator.hasNext()) {  
                Entry<String, String> elem = (Entry<String, String>) iterator  
                        .next();  
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));  
            }  
            if (list.size() > 0) {  
                org.apache.http.client.entity.UrlEncodedFormEntity entity = new org.apache.http.client.entity.UrlEncodedFormEntity(list,  
                        charset);  
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(httpPost);  
            if (response != null) {  
                HttpEntity resEntity = response.getEntity();  
                if (resEntity != null) {  
                    result = EntityUtils.toString(resEntity, charset);  
                }  
            }  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return result;  
    }  
    
     /** 
     * 发起https请求并获取net.sf.json.JSONObject结果
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param submitStr 提交的数据 
     * @return net.sf.json.JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String submitStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
//            TrustManager[] tm = { new MyX509TrustManager() };  
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
//            sslContext.init(null, tm, new java.security.SecureRandom());  
//            // 从上述SSLContext对象中得到SSLSocketFactory对象  
//            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
//            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != submitStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(submitStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException e) {  
            System.err.println("Weixin server connection timed out.");  
            e.printStackTrace();
        } catch (Exception e) {  
            System.err.println("https request error:{}");  
            e.printStackTrace();
        }  
        
        return jsonObject;  
    }
    
    public static void downloadNetPic(String urlString, String filename,String savePath){  
        // 构造URL  
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  
        // 打开连接  
        URLConnection con = null;
        try {
            con = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }  
        //设置请求超时为5s  
        con.setConnectTimeout(5*1000);  
        // 输入流  
        InputStream is = null;
        try {
            is = con.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }  
      
        // 1K的数据缓冲  
        byte[] bs = new byte[1024];  
        // 读取到的数据长度  
        int len;  
        // 输出的文件流  
       File sf=new File(savePath);  
       if(!sf.exists()){  
           sf.mkdirs();  
       }  
       
        OutputStream os;
        try {
            os = new FileOutputStream(sf.getPath()+"\\"+filename);
         // 开始读取  
            while ((len = is.read(bs)) != -1) {  
              os.write(bs, 0, len);  
            }  
            // 完毕，关闭所有链接  
            os.close();  
            is.close(); 
        } catch (FileNotFoundException e) {
            System.err.println("cannot get this file:"+sf.getPath()+"\\"+filename);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  
         
    }  
    
}
