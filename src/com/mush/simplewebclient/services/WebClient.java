package com.mush.simplewebclient.services;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WebClient {
	private static final String TAG = "WebClient";
	private String encode;
	private HttpClient httpClient;
	private HttpParams httpParams;
	private int timeout;
	private int bufferSize;

	public WebClient() {
		Log.v(TAG, "初始化");
		timeout = 5 * 1000;
		bufferSize = 8192;
		encode = "gbk";
		httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
		HttpConnectionParams.setSoTimeout(httpParams, timeout);
		HttpConnectionParams.setSocketBufferSize(httpParams, bufferSize);
		HttpClientParams.setRedirecting(httpParams, true);
		httpClient = new DefaultHttpClient(httpParams);
	}

	public String doGet(String url) throws Exception {
		return doGet(url, null);
	}

	public String doPost(String url) throws Exception {
		return doPost(url, null);
	}
	
	public String doGet(String url, String params, String str)throws Exception {
		// 添加QueryString

		if (params == null) {

			doGet(url, null);
		}
		// 创建HttpGet对象
		HttpGet get = new HttpGet(url);
		Log.i("doGet " + "URL ", url);
		try {
			String strResp = "";
			// 发起请求
			HttpResponse resp = httpClient.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK)
				strResp = EntityUtils.toString(resp.getEntity());
			else
				// 如果返回的StatusCode不是OK则抛异常
				throw new Exception("Error Response:"
						+ resp.getStatusLine().toString());
			Log.i("doGet Response ", strResp);
			return strResp;
		} finally {
			get.abort();
		}
	}

	public String doGet(String url, Map<String, String> params)
			throws Exception {
		// 添加QueryString
		String paramStr = "";
		if (params != null) {
			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) iter
						.next();
				paramStr += "&" + entry.getKey() + "="
						+ URLEncoder.encode(entry.getValue(), encode);
			}
			if (paramStr.length() > 0)
				paramStr.replaceFirst("&", "?");
			url += paramStr;
		}
		// 创建HttpGet对象
		HttpGet get = new HttpGet(url);
		Log.i("doGet " + "URL ", url);
		try {
			String strResp = "";
			// 发起请求
			HttpResponse resp = httpClient.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK)
				strResp = EntityUtils.toString(resp.getEntity());
			else
				// 如果返回的StatusCode不是OK则抛异常
				throw new Exception("Error Response:"
						+ resp.getStatusLine().toString());
			Log.i("doGet Response ", strResp);
			return strResp;
		} finally {
			get.abort();
		}
	}

	public String doPost(String url, Map<String, String> params)
			throws Exception {
		String paramsJson = "";
		Log.v(TAG + "URL", url);
		// POST参数组装
		//List<NameValuePair> data = new ArrayList<NameValuePair>();
		if (params != null) {
			GsonBuilder builder = new GsonBuilder();   
	        // 不转换没有 @Expose 注解的字段   
	        builder.excludeFieldsWithoutExposeAnnotation();  
	        Gson gson = builder.create();   
			paramsJson = gson.toJson(params);
		}
		Log.i("doPost URL", url);
		HttpPost post = new HttpPost(url);
		try {
			// 添加请求参数到请求对象
			if (params != null) {
				//paramsJson=new String(paramsJson.getBytes("UTF-8"),"GBK");
				StringEntity se = new StringEntity(paramsJson, HTTP.UTF_8);
				se.setContentType("application/json");
				post.setEntity(se);
				Log.i("doPost params", paramsJson);
			}

			// 发起请求
			HttpResponse resp = httpClient.execute(post);
			String strResp = "";
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				strResp = EntityUtils.toString(resp.getEntity());
				Log.i("doPost response", strResp);
			}

			else{
				// 如果返回的StatusCode不是OK则抛异常
				throw new Exception("Error Response:"
						+ resp.getStatusLine().toString());
		
			}
			
			return strResp;
		} finally {
			post.abort();
		}
	}

	/**
	 * @param url
	 *            - 需要访问的address
	 * @param data
	 *            - Request的内容字符串
	 * @param contentType
	 *            - Request的ContentType
	 * @return Response的字符串
	 * @throws Exception
	 */
	public String doPost(String url, String data, String contentType)
			throws Exception {
		Log.i("doPost URL", url);
		HttpPost post = new HttpPost(url);
		try {
			// 添加请求参数到请求对象
			Log.i("doPost prams", data);
			StringEntity se = new StringEntity(data, HTTP.UTF_8);
			se.setContentType(contentType);
			post.setEntity(se);
			// 发起请求
			HttpResponse resp = httpClient.execute(post);
			String strResp = "";
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {

				strResp = EntityUtils.toString(resp.getEntity());
				Log.i("doPost response", strResp);
			} else {
				// 如果返回的StatusCode不是OK则抛异常
				throw new Exception("Error Response:"
						+ resp.getStatusLine().toString());
			}

			return strResp;
		} finally {
			post.abort();
		}
	}

	/**
	 * @param url
	 *            - 需要访问的address
	 * @param data
	 *            - 实现了Obj2Json接口的数据对象
	 * @param contentType
	 *            - 可以为空
	 * @return 将该对象转换成Json格式的String
	 * @throws Exception
	 */
	public String doPost(String url, Obj2Json data, String contentType)
			throws Exception {

		return doPost(url, data.toJSONStringer(), "application/json");

	}

}