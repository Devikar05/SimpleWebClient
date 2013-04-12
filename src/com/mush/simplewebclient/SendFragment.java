package com.mush.simplewebclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mush.simplewebclient.services.HttpRequestUtil;
import com.mush.simplewebclient.services.WebClient;

public class SendFragment extends Fragment {
	public static final String tag = "Fragment1";

	private View view;

	private Button bt_add;
	private Button bt_post;
	private Button bt_get;
	private Button bt_clear;
	private TextView tv_show;
	private EditText et_url;
	private EditText et_contain;
	private EditText et_key;



	private MyHandler handler;

	private GsonBuilder builder;
	private Gson gson;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(tag, "onCreateView");

		this.view = inflater.inflate(R.layout.fragment_send, container, false);

		this.bt_add = (Button) this.view.findViewById(R.id.bt_add_param);
		this.bt_add.setOnClickListener(onClinkListener);
		this.bt_post = (Button) this.view.findViewById(R.id.bt_post);
		this.bt_post.setOnClickListener(onClinkListener);
		this.bt_get = (Button) this.view.findViewById(R.id.bt_get);
		this.bt_get.setOnClickListener(onClinkListener);
		this.bt_clear = (Button) this.view.findViewById(R.id.bt_clear);
		this.bt_clear.setOnClickListener(onClinkListener);
		this.tv_show = (TextView) this.view.findViewById(R.id.tv_show);
		this.et_contain = (EditText) this.view.findViewById(R.id.et_contain);
		this.et_key = (EditText) this.view.findViewById(R.id.et_key);
		this.et_url = (EditText) this.view.findViewById(R.id.et_url);

		this.handler = new MyHandler(this);
		
		MainActivity parent = (MainActivity)this.getActivity();

		
		this.builder = new GsonBuilder();
		this.builder.excludeFieldsWithoutExposeAnnotation();
		this.gson = builder.create();
		
		return this.view;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(tag, "onResume");
	}
	
	@Override
	public void onStart(){
		super.onStart();
		SharedPreferences sharedPreferences = SimpleWebClientAPP.app
				.getSharedPreferences("com.mush.simplewebclient_preferences",
						Context.MODE_PRIVATE);
		this.et_url.setText(sharedPreferences.getString("url", "/"));
	}

	@Override
	public void onPause() {
		super.onPause();
		String url = "";
		String paramsJson = "";
		
		url = this.et_url.getText().toString();
 
		SharedPreferences sharedPreferences = SimpleWebClientAPP.app
				.getSharedPreferences("com.mush.simplewebclient_preferences",
						Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("url", url);
		editor.commit();
		  
        // 不转换没有 @Expose 注解的字段   
		
	}

	private View.OnClickListener onClinkListener = new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			switch (v.getId()) {
			case R.id.bt_add_param: {
			
				break;
			}
			case R.id.bt_post: {
				
				break;
			}
			case R.id.bt_get: {
				
				break;
			}
			case R.id.bt_clear:{
				
				break;
			}
			}
		}
	};




	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Log.i(tag, "onCreate");
	}

	static class MyHandler extends Handler {
		WeakReference<SendFragment> mActivity;

		MyHandler(SendFragment activity) {
			mActivity = new WeakReference<SendFragment>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			SendFragment activity = mActivity.get();
			switch (msg.what) {
			// 响应完成
			case 0: {
				//activity.params.clear();
				activity.tv_show.append("\nresp: " + (String) msg.obj);
				SimpleWebClientAPP.history.add("\nresp: " + (String) msg.obj);
				break;
			}
			default: {
				Log.w(tag + "MyHandler", "未知标记Handler Message:" + msg.what);
			}
			}
		}
	}
}
