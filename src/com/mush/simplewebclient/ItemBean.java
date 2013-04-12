package com.mush.simplewebclient;

import java.util.Map;

public class ItemBean {
	/**
	 * 请求头
	 */
	public static final int ITEM_TYPE_0 = 0;
	
	/**
	 *只有一个TextView
	 */
	public static final int ITEM_TYPE_1 = 1;
	/**
	 * 相应头
	 */
	public static final int ITEM_TYPE_2 = 2;
	/**
	 * 请求参数
	 */
	public static final int ITEM_TYPE_3 = 3;
	public static final int ITEM_TYPE_4 = 4;
	public static final int ITEM_TYPE_5 = 5;

	private int itemType;
	private Map<String, String> data;
	private String message;

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}