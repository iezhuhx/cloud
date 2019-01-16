package com.cyb.utils.http;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import com.cyb.app.reptile.ProxyInfor;

@SuppressWarnings("deprecation")
public class HttpsClient {
	public static void main(String[] args) {
		String html = get("https://31f.cn/http-proxy/");
		System.out.println(html);
	}

	public static DefaultHttpClient getNewHttpsClient(HttpClient httpClient) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
			ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
			return new DefaultHttpClient(mgr, httpClient.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}
	public static String get(String url,ProxyInfor proxy1) {
		return get(url,proxy1,null);
	}
	public static String get(String url,ProxyInfor proxy1,Map<String, String> cookies) {
		/*HttpClient httpClient = new DefaultHttpClient();
		httpClient = HttpsClient.getNewHttpsClient(httpClient);*/
		String html = "";
		HttpGet request = new HttpGet(url);
		HttpHost proxy = new HttpHost(proxy1.getIp(),proxy1.getPort());
		HttpResponse response = null;
		RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectTimeout(1000)
                .setSocketTimeout(1000)
                .setConnectionRequestTimeout(3000)
                .build();
		//request.setConfig(requestConfig);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			if(CollectionUtils.isEmpty(cookies)){
				setCookieStore(response,cookies);
			}
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity mEntity = response.getEntity();
				html = EntityUtils.toString(mEntity);
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			//e.printStackTrace();
		}
		return html.toString();
	}
	
	 public static CookieStore setCookieStore(HttpResponse httpResponse,Map<String, String> cookies) {
		    System.out.println("----setCookieStore");
		    
		    CookieStore cookieStore = new BasicCookieStore();
		    // JSESSIONID
		    String setCookie = httpResponse.getFirstHeader("Set-Cookie")
		        .getValue();
		    String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
		        setCookie.indexOf(";"));
		    System.out.println("JSESSIONID:" + JSESSIONID);
		    // 新建一个Cookie
		    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",JSESSIONID);
		    for(String key:cookies.keySet()){
		    	cookie.setAttribute(key,cookies.get(key));
		    }
		    //cookie.setVersion(0);
		   // cookie.setDomain("127.0.0.1");
		    //cookie.setPath("/CwlProClient");
		    // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
		    // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
		    // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
		    // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
		    cookieStore.addCookie(cookie);
		    return cookieStore;
		  }
	/**
	 * 获取网页html
	 */
	@SuppressWarnings({ "resource" })
	public static String get(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient = HttpsClient.getNewHttpsClient(httpClient);
		String html = "";
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		/*HttpHost proxy = new HttpHost("58.60.255.82",8118);
		RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(3000)
                .build();
		request.setConfig(requestConfig);*/
		try {
			
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity mEntity = response.getEntity();
				html = EntityUtils.toString(mEntity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return html.toString();
	}
	
	//https://blog.csdn.net/edwin_/article/details/76738193
	public static String post(String url) {
		HttpPost post = new HttpPost(url);
		StringEntity param = new StringEntity("AAA", "UTF-8");
		post.setEntity(param);
		return "";
	}

}
