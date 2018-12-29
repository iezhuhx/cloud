package com.cyb.utils.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.cyb.app.reptile.ProxyInfor;
import com.cyb.utils.file.FileUtils;

@SuppressWarnings("deprecation")
public class MyHttpClient {
	static String charset = "gbk";// "gb2312"
	static String filepath = "d:/SpiderHttpClient.htm";
	static String url_str = "http://www.czce.com.cn/portal/DFSStaticFiles/Future/2016/20160215/FutureDataWhsheet.htm";

	public static void main(String[] args) throws Exception {
		/*
		 * HttpClient hc = new DefaultHttpClient(); HttpGet hg = new
		 * HttpGet(url_str); HttpResponse response = hc.execute(hg); HttpEntity
		 * entity = response.getEntity(); InputStream htm_in = null; if(entity
		 * != null){ System.out.println("文件大小："+entity.getContentLength());
		 * htm_in = entity.getContent(); StringBuffer htm_str =
		 * InputStream2String(htm_in,charset);
		 * FileUtils.overideString2File(htm_str.toString(),filepath); //
		 * saveStr2Html(filepath,htm_str.toString());
		 * System.out.println("文件下载成功！"+filepath); }
		 */
		doPost("http://localhost:8080");
		postBody("http://localhost:8080", "body content is here!");
	}

	public static void downLoadContent(String url, String toDir) {
		try {
			System.out.println(url);
			HttpClient hc = new DefaultHttpClient();
			HttpGet hg = new HttpGet(url);
			HttpResponse response = hc.execute(hg);
			HttpEntity entity = response.getEntity();
			InputStream htm_in = null;
			if (entity != null) {
				System.out.println("文件大小：" + entity.getContentLength());
				htm_in = entity.getContent();
				StringBuffer htm_str = InputStream2String(htm_in, charset);
				FileUtils.overideString2File(htm_str.toString(), toDir);
				System.out.println("文件下载成功！" + toDir);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String doGet(String url,ProxyInfor proxy1){
		HttpHost proxy = new HttpHost(proxy1.getIp(),proxy1.getPort());
		RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectTimeout(1000)
                .setSocketTimeout(1000)
                .setConnectionRequestTimeout(3000)
                .build();
		
		String result = "";
		HttpGet httpRequst = new HttpGet(url);
		//httpRequst.setConfig(requestConfig);
		//设置期望服务端返回的编码
		httpRequst.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
		   //实例化CloseableHttpClient对象
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

		// new DefaultHttpClient().execute(HttpUriRequst requst);
		try {
			// 使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
			HttpResponse httpResponse = httpclient.execute(httpRequst);// 其中HttpGet是HttpUriRequst的子类
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
				// 一般来说都要删除多余的字符
				result.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
			} else {

			}
			httpRequst.abort();
			httpRequst.releaseConnection();
		} catch (ClientProtocolException e) {
			result = e.getMessage();
		} catch (IOException e) {
			result = e.getMessage();
		}
		return result;
	}
	
	@SuppressWarnings("resource")
	public static String doGet(String uri) {
		String result = "";
		// HttpGet httpRequst = new HttpGet(String uri);
		// 创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象。
		HttpGet httpRequst = new HttpGet(uri);
		//设置期望服务端返回的编码
		httpRequst.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
		try {
			// 使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);// 其中HttpGet是HttpUriRequst的子类
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
				// 一般来说都要删除多余的字符
				result.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
			} else {

			}
			httpRequst.abort();
			httpRequst.releaseConnection();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		return result;
	}

	public static String doPost(String url) {
		String result = "";
		HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("realname", "1234567890"));
		params.add(new BasicNameValuePair("company", "1234567890"));
		params.add(new BasicNameValuePair("mobile", "13832623546"));
		params.add(new BasicNameValuePair("email", "123@qq.com"));
		params.add(new BasicNameValuePair("mintro", "1234567890"));
		try {
			httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpRequst.setHeader("self_define", "value");
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
			}
			httpRequst.abort();
			httpRequst.releaseConnection();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public static String doPost(String url,Map<String,String> para) {
		String result = "";
		HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		;
		for(String key :para.keySet()){
			params.add(new BasicNameValuePair(key, para.get(key)));
		}
		try {
			httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			@SuppressWarnings("resource")
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
			}
			httpRequst.abort();
			httpRequst.releaseConnection();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		return result;
	}

	public static void doPostWithFile(String url_, String filePath) {
		try {

			// 换行符
			final String newLine = "\r\n";
			final String boundaryPrefix = "--";
			// 定义数据分隔线
			String BOUNDARY = "========7d4a6d158c9";
			// 服务器的域名
			URL url = new URL(url_);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置为POST情
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求头参数
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			// 上传文件
			File file = new File("C:/Users/j/Pictures/more.jpg");
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("创建新文件！");
			}
			StringBuilder sb = new StringBuilder();
			sb.append(boundaryPrefix);
			sb.append(BOUNDARY);
			sb.append(newLine);
			// 文件参数,photo参数名可以随意修改
			sb.append("Content-Disposition: form-data;name=\"photo\";filename=\"" + filePath + "\"" + newLine);
			sb.append("Content-Type:application/octet-stream");
			// 参数头设置完以后需要两个换行，然后才是参数内容
			sb.append(newLine);
			sb.append(newLine);

			// 将参数头的数据写入到输出流中
			out.write(sb.toString().getBytes());

			// 数据输入流,用于读取文件数据
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			byte[] bufferOut = new byte[1024];
			int bytes = 0;
			// 每次读1KB数据,并且将文件数据写入到输出流中
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			// 最后添加换行
			out.write(newLine.getBytes());
			in.close();

			// 定义最后数据分隔线，即--加上BOUNDARY再加上--。
			byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();
			// 写上结尾标识
			out.write(end_data);
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			// BufferedReader reader = new BufferedReader(new InputStreamReader(
			// conn.getInputStream()));
			// String line = null;
			// while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			// }

		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
	}

	public static int postBody(String urlPath, String json) throws Exception {
		try {
			// Configure and open a connection to the site you will send the
			// request
			URL url = new URL(urlPath);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// 设置doOutput属性为true表示将使用此urlConnection写入数据
			urlConnection.setDoOutput(true);
			// 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
			urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 得到请求的输出流对象
			OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
			// 把数据写入请求的Body
			out.write(json);
			out.flush();
			out.close();

			// 从服务器读取响应
			InputStream inputStream = urlConnection.getInputStream();
			String encoding = urlConnection.getContentEncoding();
			String body = IOUtils.toString(inputStream, encoding);
			if (urlConnection.getResponseCode() == 200) {
				return 200;
			} else {
				throw new Exception(body);
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Method: saveHtml Description: save String to file
	 * 
	 * @param filepath
	 *            file path which need to be saved
	 * @param str
	 *            string saved
	 */
	public static void saveStr2Html(String filepath, String str) {
		try {
			/*
			 * @SuppressWarnings("resource") FileWriter fw = new
			 * FileWriter(filepath); fw.write(str); fw.flush();
			 */
			OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(filepath, true));
			outs.write(str);
			outs.close();
		} catch (IOException e) {
			System.out.println("Error at save html...");
			e.printStackTrace();
		}
	}

	/**
	 * Method: InputStream2String Description: make InputStream to String
	 * 
	 * @param in_st
	 *            inputstream which need to be converted
	 * @param charset
	 *            encoder of value
	 * @throws IOException
	 *             if an error occurred
	 */
	public static StringBuffer InputStream2String(InputStream in_st, String charset) throws IOException {
		BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
		StringBuffer res = new StringBuffer();
		String line = "";
		while ((line = buff.readLine()) != null) {
			System.out.println(line);
			res.append(line + "\n");
		}
		return res;
	}

}
