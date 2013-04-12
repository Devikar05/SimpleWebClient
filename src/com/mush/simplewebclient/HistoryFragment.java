package com.mush.simplewebclient;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HistoryFragment extends Fragment {
	public static final String tag = "HistoryFragment";
	
	private View view;
	private TextView tv_history;
	private Button bt_clear;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.fragment_history, container,
				false);
		this.tv_history = (TextView) this.view.findViewById(R.id.tv_history);
		this.tv_history.setMovementMethod(ScrollingMovementMethod.getInstance()); 
		this.bt_clear = (Button) this.view.findViewById(R.id.bt_clear);
		this.bt_clear.setOnClickListener(onClinkListener);
		
		return this.view;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.i(tag, "onResume");
		for(String str:SimpleWebClientAPP.history){
			Log.i(tag+"增加", str);
			tv_history.append(str);
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}
	
	@Override
	public void onStart(){
		super.onStart();
		Log.i(tag, "onStart");
	}

	private View.OnClickListener onClinkListener = new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on click
			switch(v.getId()){
			case R.id.bt_clear:{
				SimpleWebClientAPP.history.clear();
				tv_history.setText("");
				break;
			}
			}
		}
	};
}
