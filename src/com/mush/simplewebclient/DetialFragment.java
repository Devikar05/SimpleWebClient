package com.mush.simplewebclient;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class DetialFragment extends Fragment {
	public static final String tag = "DetialFragment";

	private View mView;
	private Button btn_head, btn_param, btn_post, btn_get, btn_clean;
	private ListView mListView;
	private NestListAdapter mAdapter;
	//private Handler myHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mView = inflater.inflate(R.layout.fragment_detail, container,
				false);
		this.findViews();
		return this.mView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	private void findViews() {
		this.btn_param = (Button) this.mView.findViewById(R.id.bt_add_param);
		this.btn_param.setOnClickListener(onClinkListener);
		this.btn_head = (Button) this.mView.findViewById(R.id.bt_add_head);
		this.btn_head.setOnClickListener(onClinkListener);
		this.btn_clean = (Button) this.mView.findViewById(R.id.bt_clear);
		this.btn_clean.setOnClickListener(onClinkListener);
		this.btn_get = (Button) this.mView.findViewById(R.id.bt_get);
		this.btn_get.setOnClickListener(onClinkListener);
		this.btn_post = (Button) this.mView.findViewById(R.id.bt_post);
		this.btn_post.setOnClickListener(onClinkListener);

		this.mListView = (ListView) this.mView.findViewById(R.id.list_contain);
		mAdapter = new NestListAdapter(this.mView.getContext());
		mAdapter.setDataSource(((MainActivity) this.getActivity()).data);
		this.mListView.setAdapter(mAdapter);
	}

	private View.OnClickListener onClinkListener = new View.OnClickListener() {
		@SuppressWarnings("deprecation")
		public void onClick(View v) {
			Log.d(tag, "onClick");
			// Perform action on click
			switch (v.getId()) {
			case R.id.bt_add_head: {
				getActivity().showDialog(MainActivity.DIALOG_ADDHEAD);
				break;
			}
			case R.id.bt_add_param: {
				getActivity().showDialog(MainActivity.DIALOG_ADDPARAM);
				break;
			}
			case R.id.bt_post: {
				Log.d(tag, "onClick \tbt_post");
				((MainActivity)getActivity()).httpController.setURL(SimpleWebClientAPP.get_rootURL());
				((MainActivity)getActivity()).httpController.doPOST();
				break;
			}
			case R.id.bt_get: {
				((MainActivity)getActivity()).httpController.setURL(SimpleWebClientAPP.get_rootURL());
				((MainActivity)getActivity()).httpController.doGET();
				break;
			}
			case R.id.bt_clear: {
				break;
			}

			}
		}
	};
/*
	private String doCheck(String id, String name) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("id", id);
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("User-Agent",
				"Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:19.0) Gecko/20100101 Firefox/19.0");
		heads.put("Referer", "http://cet.99sushe.com/");
		heads.put("Host", "cet.99sushe.com");
		heads.put("Connection", "keep-alive");
		heads.put("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		heads.put("Accept-Encoding", "gzip, deflate");
		heads.put("Accept", "http://cet.99sushe.com/");
		HttpURLConnection conn = (HttpURLConnection) HttpRequestUtil
				.sendPostRequest("http://cet.99sushe.com/s", params, heads);
		// int code = conn.getResponseCode();
		InputStream is = conn.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "GBK"));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(new String(line.getBytes()));
		}
		System.out.print(buffer.toString());
		return buffer.toString();
	}*/

	static class MyHandler extends Handler {
		WeakReference<DetialFragment> mFragment;

		MyHandler(DetialFragment fragment) {
			mFragment = new WeakReference<DetialFragment>(fragment);
		}

		@Override
		public void handleMessage(Message msg) {
			DetialFragment fragment = mFragment.get();
			switch (msg.what) {
			// 响应完成
			case 0: {
				String str = (String) msg.obj;
				// fragment.txt_show.setText(str);
				break;
			}
			default: {

			}
			}
		}
	}

}
