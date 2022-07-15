package com.zx.hoperun.zt.base;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author ZhaoChenxu
 * @Date 2014-10-21下午1:21:58
 * @Description Post方式发送请求
 * @File HttpsPostMap.java
 */
public class HttpPostMap {
	/**
	 * 向URL发送POST请求，返回值采用UTF-8格式处理
	 * map封装了键值对
	 * @param URL  server url
	 * @param params  post varieties
	 * @return
	 */

	@SuppressWarnings("unused")
	private static final int BUFFERSIZE = 1048576;
	private static final int TIMEOUT = 100000;
	private static final String CODE_FORMAT = "UTF-8";

	private static int httpTimeOut = TIMEOUT;
	private static String codeFormat = CODE_FORMAT;


	public static int getHttpTimeOut() {
		return httpTimeOut;
	}

	public static void setHttpTimeOut(int httpTimeOut) {
		HttpPostMap.httpTimeOut = httpTimeOut;
	}

	public static String getCodeFormat() {
		return codeFormat;
	}

	public static void setCodeFormat(String codeFormat) {
		HttpPostMap.codeFormat = codeFormat;
	}

	/**
	 * 
	 * @param URL:请求地址
	 * @param params：请求参数
	 * @param pathFlat：1为http请求，2为https请求
	 * @return 返回json格式字符串
	 */
	public static String post(String URL, Map<String, String> params,int pathFlat) throws NoSuchAlgorithmException, KeyManagementException
	{
		/**
		 * 读取param中的数据，写入NameValuePair
		 */
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(params!=null){	
			Iterator iter = params.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();

				nvps.add(new BasicNameValuePair(key, value));
			}
		}
		StringBuffer result = new StringBuffer();
		HttpClient httpClient =null;
		try{
			/**
			 * httpclient并发送post数据
			 */
			if(pathFlat ==2){
				SSLContext ctx = SSLContext.getInstance("TLS");
				X509TrustManager tm = new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
					public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
				};
				ctx.init(null, new TrustManager[] { tm }, null);
				SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("https", 443, ssf));
				ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
				httpClient = new DefaultHttpClient(mgr);  
			}else if(pathFlat ==1){
				httpClient = new DefaultHttpClient(); 
			}

			//HttpClient httpClient = new DefaultHttpClient();
			HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, httpTimeOut);  //设定12秒超时，届时会弹出Exception
			HttpConnectionParams.setSoTimeout(httpParams, httpTimeOut);

			org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(URL);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, codeFormat));
			HttpResponse response = httpClient.execute(httpPost);
			URL = URLDecoder.decode(URL, "UTF-8");
			/**
			 * 获取服务器响应         */
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String line="";
			while((line=bufferedReader.readLine()) != null){
				result.append(line);
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (ClientProtocolException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return result.toString();
	}
}