package com.mush.simplewebclient.controller;

public interface HTTPListener {
	public void onHTTPChanged(int event);
	public void onHTTPChanged(int event, Object[] obj);
}
