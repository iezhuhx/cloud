package com.cyb.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cyb.app.reptile.ProxyInfor;

@SuppressWarnings("deprecation")
public class HttpsClient {
	public static void main(String[] args) {
		String html = "";//get("https://31f.cn/http-proxy/");
		html = getDocByJsoup("https://blog.csdn.net/zzuchenyb/article/details/76583444");
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

	public static String getJsoup(String url,ProxyInfor proxy1) {
		return getJsoup1(url,proxy1);
	}
	public static String getDocByJsoup(String href){
		String ip = "117.191.11.107";
		int port = 8080;
		try {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
			URL url = new URL(href);
			HttpsURLConnection urlcon = (HttpsURLConnection)url.openConnection(proxy);
			urlcon.connect(); //获取连接  
			InputStream is = urlcon.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
			StringBuffer bs = new StringBuffer();
			String l = null;
			while((l=buffer.readLine())!=null){
				bs.append(l);
			}
			System.out.println(bs.toString());
			Document doc= Jsoup.parse(bs.toString());
			return bs.toString();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getJsoup1(String url,ProxyInfor proxy){
		System.setProperty("https.proxySet", "true");
		System.getProperties().put("https.proxyHost", proxy.getIp());
		System.getProperties().put("https.proxyPort", proxy.getPort());
		Document doc = null;
		String  agent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
				+ "  Chrome/56.0.2924.87 Safari/537.36" ;
		try {
			doc = Jsoup.connect(url).ignoreContentType(true)
					.userAgent(agent)
					// ignoreHttpErrors
					//这个很重要 否则会报HTTP error fetching URL. Status=404
					.ignoreHttpErrors(true)  //这个很重要
					.timeout(3000).get();
		} catch (IOException e) {
			System.out.println(e.getMessage()+"  **************** get");
		}
		if (doc!=null) {
			return doc.body().text();
		}
		return null;
	}
	public static String get(String url,ProxyInfor proxy1,Map<String, String> cookies) {
		/*HttpClient httpClient = new DefaultHttpClient();
		httpClient = HttpsClient.getNewHttpsClient(httpClient);*/
		String html = "";
		HttpGet request = new HttpGet(url);
		HttpHost proxy = new HttpHost(proxy1.getIp(),proxy1.getPort());
		HttpResponse response = null;
		String  agent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
				+ "  Chrome/56.0.2924.87 Safari/537.36" ;
		RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectTimeout(1000)
                .setSocketTimeout(1000)
                .setConnectionRequestTimeout(3000)
                .build();
		//request.setConfig(requestConfig);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			/*if(CollectionUtils.isEmpty(cookies)){
				setCookieStore(response,cookies);
			}*/
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity mEntity = response.getEntity();
				html = EntityUtils.toString(mEntity);
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			//e.printStackTrace();
		}
		Document doc = Jsoup.parse(html.toString());
		System.out.println("title="+doc.title()+",html="+html.toString());
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
