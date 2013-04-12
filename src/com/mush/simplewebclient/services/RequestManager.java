package com.mush.simplewebclient.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestManager {
	
	private List<OnRequestChanged> onRequestChanged = new ArrayList<OnRequestChanged>();
	
	public Map<String, String> requestParams;
	public Map<String, String> requestHeads;
	public Map<String, String> responseHeads;
	public Map<String, String> responseParams;
	
	public RequestManager (){
		
	}
	
	public void addOnRequestChangedListener(OnRequestChanged onRequestChanged){
		this.onRequestChanged.add(onRequestChanged);
	}
	
	
	/**
	 * 添加一个HTTP请求参数
	 * @param key
	 * @param values
	 */
	public void addParam(String key, String value){
		
		this.requestParams.put(key, value);
		//
	}
	
	/**
	 * 添加一个请求头
	 * @param key
	 * @param value
	 */
	public void addHead(String key, String value){
		this.responseHeads.put(key, value);
		//
	}

}
