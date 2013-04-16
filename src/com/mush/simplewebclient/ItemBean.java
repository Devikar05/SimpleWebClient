package com.mush.simplewebclient;

import java.util.Comparator;
import java.util.Map;

public class ItemBean  implements Comparable<ItemBean>{
	/**
	 * 请求头
	 */
	public static final int ITEM_TYPE_0 = 0;
	/**
	 * 请求参数
	 */
	public static final int ITEM_TYPE_3 = 1;
	/**
	 * 响应头
	 */
	public static final int ITEM_TYPE_2 = 2;
	
	/**
	 *响应参数
	 */
	public static final int ITEM_TYPE_1 = 3;



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


	@Override
	public int compareTo(ItemBean another) {
		// TODO 自动生成的方法存根
		return this.getItemType()>another.getItemType()?1:-1;
	}

}
