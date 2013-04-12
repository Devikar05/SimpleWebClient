package com.mush.simplewebclient;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TwoItemAdapter extends BaseAdapter {
	
	private int mRowLayout;
	private LayoutInflater mInflater;
	
	private List <String[]> list;
	
	public TwoItemAdapter(Context context, int rowLayout){
		this.mRowLayout = rowLayout;
		this.mInflater = LayoutInflater.from(context);
		this.list = new ArrayList<String[]>();
	}
	
	public void addItem(String[] item){
		this.list.add(item);
	}

	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	public String[] getItem(int position) {
		// TODO 自动生成的方法存根
		return this.list.get(position);
	}
	
	public void clear(){
		this.list.clear();
	}

	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(this.mRowLayout, null);
			holder = new ViewHolder();
			
			//初始化控件
			holder.txt1 = (TextView) convertView.findViewById(R.id.txt1);
			holder.txt2 = (TextView) convertView.findViewById(R.id.txt2);
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		//娴熟数据
		String[] txt = this.list.get(position);
		holder.txt1.setText(txt[0]);
		holder.txt2.setText(txt[1]);
		
		return convertView;
	}
	
	class ViewHolder {
		TextView txt1;
		TextView txt2;
	}

}
