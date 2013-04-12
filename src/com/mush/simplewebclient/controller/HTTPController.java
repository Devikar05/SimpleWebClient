package com.mush.simplewebclient.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mush.simplewebclient.model.HttpRequestUtil;

public class HTTPController {
	public final static int ADD_REQUEST_HEAD = 1;
	public final static int ADD_REQUEST_PARAM = 2;
	public final static int RESPONSE_POST = 3;
	
	public static final int RESPONS_ERROR = 0;
	public static final int RESPONS_MESSAGE = 1;
	public static final int RESPONS_HEAD = 2;
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String HEAD = "head";

	private String URL = "http://www.baidu.com:80";
	private HttpRequestUtil mHttpRequestUtil;
	private HTTPListener mHTTPListener;
	private Handler mHandler;

	private Map<String, String> requestHead;
	private Map<String, String> requestParam;
	private String responseMessage;

	public HTTPController(HTTPListener mHTTPListener) {
		this.mHttpRequestUtil = new HttpRequestUtil();
		this.mHTTPListener = mHTTPListener;
		this.requestHead = new HashMap<String, String>();
		this.requestParam = new HashMap<String, String>();
	}

	public HTTPController(Handler mHandler) {
		this.mHandler = mHandler;
		this.mHttpRequestUtil = new HttpRequestUtil();
		this.requestHead = new HashMap<String, String>();
		this.requestParam = new HashMap<String, String>();
		//this.responseHead = new HashMap<String, String>();
		this.URL = "http://www.renren.com:80";
	}

	/**
	 * 添加请求头
	 * 
	 * @param key
	 * @param value
	 */
	public void addRequestHead(String key, String value) {
		this.requestHead.put(key, value);
		mHTTPListener.onHTTPChanged(HTTPController.ADD_REQUEST_HEAD);
	}

	public Map<String, String> getRequestHead() {
		return this.requestHead;
	}

	/**
	 * 添加请求参数
	 * 
	 * @param key
	 * @param value
	 */
	public void addRequestParam(String key, String value) {
		this.requestParam.put(key, value);
		mHTTPListener.onHTTPChanged(HTTPController.ADD_REQUEST_HEAD);
	}

	public Map<String, String> getRequestParam() {
		return this.requestParam;
	}

	public void setURL(String URL){
		this.URL = URL;
	}
	
	public void doGET() {
		new Thread() {
			@Override
			public void run() {
				try {
					URLConnection conn = mHttpRequestUtil.sendPostRequest(URL,
							requestParam, requestHead);
					
					Map<String, List<String>> rh = conn.getHeaderFields();
					Set<Entry<String, List<String>>> entrys = null;
					if (rh != null && !rh.isEmpty()) {
						Map<String, String> responseHead = new LinkedHashMap<String,String>();
						entrys = rh.entrySet();
						for (Map.Entry<String, List<String>> entry : entrys) {
							responseHead.put(entry.getKey(), entry.getValue()
									.toString());
						}
						Message msg = Message.obtain();
						msg.what = HTTPController.RESPONS_HEAD;
						msg.obj = responseHead;
						mHandler.sendMessage(msg);
					}
					// 获取响应参数
					InputStream is = conn.getInputStream();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(is, "UTF-8"));
					StringBuffer buffer = new StringBuffer();
					String line = "";
					while ((line = in.readLine()) != null) {
						buffer.append(new String(line.getBytes()));
					}
					responseMessage = buffer.toString();
					Message msg = Message.obtain();
					msg.what = HTTPController.RESPONS_MESSAGE;
					msg.obj = responseMessage;
					Log.d("doget", responseMessage);
					mHandler.sendMessage(msg);
					
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					Bundle bundle = new Bundle();
					bundle.putString("error", e.toString());
					Message msg = Message.obtain();
					msg.what = HTTPController.RESPONS_ERROR;
					msg.setData(bundle);
					mHandler.sendMessage(msg);
				}
			}
		}.start();

	}

	public void doPOST() {
		Log.d("doPost","start");
		new Thread() {
			@Override
			public void run() {
				try {
					Log.d("doPost","start");
					
					
					URLConnection conn = mHttpRequestUtil.sendPostRequest(URL,
							requestParam, requestHead);
					// 获取响应头
					Map<String, List<String>> rh = conn.getHeaderFields();
					Set<Entry<String, List<String>>> entrys = null;
					if (rh != null && !rh.isEmpty()) {
						Map<String, String> responseHead = new LinkedHashMap<String,String>();
						entrys = rh.entrySet();
						for (Map.Entry<String, List<String>> entry : entrys) {
							responseHead.put(entry.getKey(), entry.getValue()
									.toString());
						}
						Message msg = Message.obtain();
						msg.what = HTTPController.RESPONS_HEAD;
						msg.obj = responseHead;
						mHandler.sendMessage(msg);
					}
					// 获取相应参数
					InputStream is = conn.getInputStream();
					BufferedReader in = new BufferedReader(
							new InputStreamReader(is, "GBK"));
					StringBuffer buffer = new StringBuffer();
					String line = "";
					while ((line = in.readLine()) != null) {
						buffer.append(new String(line.getBytes()));
					}
					responseMessage = buffer.toString();
					Bundle bundle = new Bundle();
					bundle.putString("responseMessage", responseMessage);
					Message msg = Message.obtain();
					msg.what = HTTPController.RESPONS_MESSAGE;
					msg.setData(bundle);
					mHandler.sendMessage(msg);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					Bundle bundle = new Bundle();
					bundle.putString("error", e.toString());
					Message msg = Message.obtain();
					msg.what = HTTPController.RESPONS_ERROR;
					msg.setData(bundle);
					mHandler.sendMessage(msg);
				}
			}
		}.start();

	}
}
