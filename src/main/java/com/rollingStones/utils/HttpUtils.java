package com.rollingStones.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

public class HttpUtils {


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
    
    
    public static String doGet(String getURL, String charset) {

		StringBuffer buf = new StringBuffer();
		try {
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			//设置成微信访问
//			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 5.0.1; GT-I9502 Build/LRX22C; wv) "
//					+ "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.121 Mobile Safari/537.36 "
//					+ "MicroMessenger/6.1.0.78_r1129455.543 NetType/WIFI");
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));// 设置编码,否则中文乱码
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
	 * 向指定URL发送POST方法的请求
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String param) {
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "close");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible;MSIE 7.0; Windows NT 5.1; Maxthon;)"); 
			// 设置 HttpURLConnection的字符编码
	        conn.setRequestProperty("Accept-Charset", "UTF-8");
//	        conn.setRequestProperty("Referer", "http://i.aixuexi.com");

	        
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			OutputStream outputStream = conn.getOutputStream();
			// 发送请求参数
			outputStream.write(param.getBytes("utf-8"));
			// flush输出流的缓冲
			outputStream.flush();
			outputStream.close();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常：");
			e.printStackTrace();
		}finally {// 使用finally块来关闭输出流、输入流
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
	
	
	public static String doHttpsPost(String url, Map<String, String> map, String charset) {  
        HttpClient httpClient = null;  
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
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,  
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
     * 发起https请求并获取结果 (创建菜单用到)
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {  
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
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
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
        } catch (ConnectException ce) {  
            System.err.print("Weixin server connection timed out.");  
        } catch (Exception e) {  
        	System.err.print("https request error:");  
        	e.printStackTrace();
        }  
        
        return jsonObject;  
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
    
}
