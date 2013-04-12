package com.mush.simplewebclient;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SimpleWebClientAPP extends Application {
	static public SimpleWebClientAPP app;

	static public List<String>history;
	@Override
	public void onCreate() {
		app = this;
		history = new ArrayList<String>();
	}

	@Override
	public void onTerminate() {
	}

	public static String get_rootURL() {
		SharedPreferences sharedPreferences = SimpleWebClientAPP.app
				.getSharedPreferences("com.mush.simplewebclient_preferences",
						Context.MODE_PRIVATE);
		return sharedPreferences.getString("root_url",
				"http://192.168.1.101:8080");
	}
	
	public static String get_encode(){
		SharedPreferences sharedPreferences = SimpleWebClientAPP.app
				.getSharedPreferences("com.mush.simplewebclient_preferences",
						Context.MODE_PRIVATE);
		return sharedPreferences.getString("encode",
				"UTF-8");
	}
}
