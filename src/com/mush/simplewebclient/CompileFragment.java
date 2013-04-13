package com.mush.simplewebclient;

import java.lang.ref.WeakReference;

import android.app.Fragment;
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

import com.mush.simplewebclient.services.WebClient;

public class CompileFragment extends Fragment {
	public static final String tag = "";

	private View view;
	private Button bt_post;
	private Button bt_get;
	private EditText et_url;
	private EditText et_contain;
	private TextView tv_show;

	private WebClient webClient;
	
	private MyHandler handler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.fragment_compile, container,
				false);
		this.bt_get = (Button) this.view.findViewById(R.id.bt_get);
		this.bt_get.setOnClickListener(onClinkListener);
		this.bt_post = (Button) this.view.findViewById(R.id.bt_post);
		this.bt_post.setOnClickListener(onClinkListener);
		this.et_contain = (EditText) this.view.findViewById(R.id.et_contain);
		this.et_url = (EditText) this.view.findViewById(R.id.et_url);
		this.tv_show = (TextView) this.view.findViewById(R.id.tv_show);
		
		MainActivity parent = (MainActivity) this.getActivity();
		return this.view;
		
	}

	private View.OnClickListener onClinkListener = new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			switch (v.getId()) {
			case R.id.bt_post: {
				 doPOST();
				break;
			}
			case R.id.bt_get: {
				doGET();
				break;
			}
			}
		}
	};
	
	private void doPOST() {
		final String url = SimpleWebClientAPP.get_rootURL()
				+ et_url.getText().toString();
		tv_show.setText("\n待发送：\t" + et_contain.getText().toString() + "\t");
		SimpleWebClientAPP.history.add("\n待发送：\t" + et_contain.getText().toString() + "\t");
		tv_show.append("\n操作:POST\t" + url);
		SimpleWebClientAPP.history.add("\n操作:POST\t" + url);
		new Thread() {
			String resp = "";

			@Override
			public void run() {
				try {
					resp = webClient.doPost(url, et_contain.getText().toString(),"text/text");
					Message msg = Message.obtain();
					msg.what = 0;
					msg.obj = resp;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg = Message.obtain();
					msg.what = 0;
					msg.obj = e.toString();
					handler.sendMessage(msg);
				}
			}
		}.start();
	}
	
	private void doGET() {
		final String url = SimpleWebClientAPP.get_rootURL()
				+ et_url.getText().toString();
		tv_show.append("\n操作:GET\t" + url);
		SimpleWebClientAPP.history.add("\n操作:GET\t" + url);
		new Thread() {
			String resp = "";

			@Override
			public void run() {
				try {
					if (null == et_contain.getText().toString()) {
						resp = webClient.doGet(url);
					} else {
						tv_show.setText("\n待发送：\t" + et_contain.getText().toString() + "\t");
						SimpleWebClientAPP.history.add("\n待发送：\t" + et_contain.getText().toString() + "\t");
						resp = webClient.doGet(url, tv_show.getText().toString(),null);
					}

					Message msg = Message.obtain();
					msg.what = 0;
					msg.obj = resp;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg = Message.obtain();
					msg.what = 0;
					msg.obj = e.toString();
					handler.sendMessage(msg);
				}
			}
		}.start();
	}
	
	static class MyHandler extends Handler {
		WeakReference<CompileFragment> mFragment;

		MyHandler(CompileFragment fragment) {
			mFragment = new WeakReference<CompileFragment>(fragment);
		}

		@Override
		public void handleMessage(Message msg) {
			CompileFragment fragment = mFragment.get();
			switch (msg.what) {
			// 响应完成
			case 0: {
				fragment.tv_show.append("\nresp: " + (String) msg.obj);
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
