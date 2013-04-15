package com.mush.simplewebclient;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mush.simplewebclient.controller.HTTPController;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String tag = "MainActivity";
	public static final int DIALOG_ADDHEAD = 1;
	public static final int DIALOG_ADDPARAM = 2;

	private DetialFragment detialFragment;
	//private CompileFragment compileFragment;
	private HistoryFragment historyFragment;
	//private SendFragment sendFragment;

	public List<ItemBean> data;
	public HTTPController httpController;
	private Handler mHandler;

	Dialog dialog;
	AutoCompleteTextView autoet_key;
	AutoCompleteTextView autoet_value;
	Button btn_add;
	Button btn_drop;

	private ActionBar ab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(tag, "onCreate");

		detialFragment = (DetialFragment) Fragment.instantiate(this,
				DetialFragment.class.getName());
		historyFragment = (HistoryFragment) Fragment.instantiate(this,
				HistoryFragment.class.getName());

		ab = getActionBar();
		setListNavigation(ab);

		this.data = new ArrayList<ItemBean>();
		//this.data = new LinkedList<ItemBean>();
	}

	/* （非 Javadoc）
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	public Dialog onCreateDialog(final int id) {
		if (id == MainActivity.DIALOG_ADDHEAD
				|| id == MainActivity.DIALOG_ADDPARAM) {
			LayoutInflater inflater = getLayoutInflater();
			View dialogView = inflater.inflate(R.layout.dialog_add,
					(ViewGroup) findViewById(R.id.dialog_add));
			autoet_key = (AutoCompleteTextView) dialogView
					.findViewById(R.id.autoet_key);
			autoet_value = (AutoCompleteTextView) dialogView
					.findViewById(R.id.autoet_value);
			btn_add = (Button) dialogView.findViewById(R.id.bt_dialog_add);
			btn_drop = (Button) dialogView.findViewById(R.id.bt_dialog_drop);
			OnClickListener oncliceListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					switch (v.getId()) {
					case R.id.bt_dialog_add: {
						if (autoet_value.getText().toString().isEmpty()
								|| autoet_key.getText().toString().isEmpty()) {
							Toast.makeText(MainActivity.this, "填入正确的Key和Value",
									Toast.LENGTH_SHORT).show();
						} else {
							boolean add = true;
							for (ItemBean item : data) {
								if (((item.getItemType() == ItemBean.ITEM_TYPE_0) && (id == MainActivity.DIALOG_ADDHEAD))
										|| ((item.getItemType() == ItemBean.ITEM_TYPE_3) && (id == MainActivity.DIALOG_ADDPARAM))) {
									item.getData().put(
											autoet_key.getText().toString(),
											autoet_value.getText().toString());
									detialFragment.mAdapter.notifyDataSetInvalidated();
									add = false;
								}
							}

							if (add) {
								ItemBean item = new ItemBean();
								item.setItemType(id == MainActivity.DIALOG_ADDHEAD ? ItemBean.ITEM_TYPE_0
										: ItemBean.ITEM_TYPE_3);
								Map<String, String> heads = new LinkedHashMap<String, String>();
								heads.put(autoet_key.getText().toString(),
										autoet_value.getText().toString());
								item.setData(heads);
								if(item.getItemType() == ItemBean.ITEM_TYPE_0){
									data.add(0,item);
									detialFragment.mAdapter.notifyDataSetInvalidated();
								}else{
									data.add(data.size()>0?1:0,item);
									detialFragment.mAdapter.notifyDataSetInvalidated();
								}
								
							}
							autoet_key.setText(null);
							autoet_value.setText(null);
							dialog.dismiss();
							removeDialog(id);
						}
						break;
					}
					case R.id.bt_dialog_drop: {
						autoet_key.setText(null);
						autoet_value.setText(null);
						dialog.dismiss();
						removeDialog(id);
						break;
					}
					}
				}
			};
			btn_add.setOnClickListener(oncliceListener);
			btn_drop.setOnClickListener(oncliceListener);

			if (id == MainActivity.DIALOG_ADDHEAD) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_dropdown_item_1line, this
								.getResources().getStringArray(
										R.array.http_head_key));
				autoet_key.setAdapter(adapter);
			}

			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setView(dialogView);
			builder.setTitle("添加HTTP请求头");
			dialog = builder.create();
		}
		return dialog;
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i(tag, "onStart");
		this.mHandler = new MyHandler(MainActivity.this);
		httpController = new HTTPController(this.mHandler);
	}

	@Override
	public void onRestart() {
		super.onRestart();
		Log.i(tag, "onRestart");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.i(tag, "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret;

		switch (item.getItemId()) {
		case R.id.menu_settings: {
			startActivity(new Intent(getApplication(), SettingsActivity.class));
			ret = true;
			break;
		}
		case android.R.id.home: {
			ab.setSelectedNavigationItem(0);
			ret = true;
			break;
		}
		default: {
			ret = super.onOptionsItemSelected(item);
		}
		}

		return ret;
	}

	static class MyHandler extends Handler {
		WeakReference<MainActivity> mActivity;

		MyHandler(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			MainActivity activity = mActivity.get();
			switch (msg.what) {
			// 响应完成
			case HTTPController.RESPONS_ERROR: {
				Log.w(tag + "MyHandler", "RESPONS_ERROR" + msg.what);
				Toast.makeText(activity,
						msg.getData().getString(HTTPController.ERROR),
						Toast.LENGTH_LONG).show();
				break;
			}
			case HTTPController.RESPONS_HEAD: {
				// activity.httpController.doPOST();
				Log.w(tag + "MyHandler", "RESPONS_HEAD" + msg.what);
				
				boolean add = true;
				for (ItemBean item : activity.data) {
					if (item.getItemType() == ItemBean.ITEM_TYPE_2) {
						item.setData((Map<String,String>) msg.obj);
						add = false;
					}
				}

				if (add) {
					ItemBean item = new ItemBean();
					item.setItemType(ItemBean.ITEM_TYPE_2);
					item.setData((Map<String,String>) msg.obj);
					activity.data.add(activity.data.size()<=2?activity.data.size():2,item);
					activity.detialFragment.mAdapter.notifyDataSetChanged();
				}
				break;
			}
			case HTTPController.RESPONS_MESSAGE: {
				Log.w(tag + "MyHandler", "RESPONS_MESSAGE" + msg.what);
				boolean add = true;
				for (ItemBean item : activity.data) {
					if (item.getItemType() == ItemBean.ITEM_TYPE_1) {
						item.setMessage((String) msg.obj);
						add = false;
					}
				}

				if (add) {
					ItemBean item = new ItemBean();
					item.setItemType(ItemBean.ITEM_TYPE_1);
					item.setMessage((String) msg.obj);
					activity.data.add(activity.data.size()<=3?activity.data.size():3,item);
					activity.detialFragment.mAdapter.notifyDataSetChanged();
				}
				break;
			}
			default: {
				Log.w(tag + "MyHandler", "未知标记Handler Message:" + msg.what);
				break;
			}
			}
		}
	}

	private void setListNavigation(final ActionBar actionBar) {
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setTitle("");
		final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		// 添加“详细参数”
		map = new HashMap<String, Object>();
		map.put("title", getString(R.string.frag_detail));
		map.put("fragment", detialFragment);
		data.add(map);
		// 添加“历史记录”
		map = new HashMap<String, Object>();
		map.put("title", getString(R.string.frag_history));
		map.put("fragment", historyFragment);
		data.add(map);

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_spinner_dropdown_item,
				new String[] { "title" }, new int[] { android.R.id.text1 });
		actionBar.setListNavigationCallbacks(adapter,
				new OnNavigationListener() {

					@Override
					public boolean onNavigationItemSelected(int itemPosition,
							long itemId) {
						Log.d("ActionBar", itemPosition + "");

						// 当不是首页的时候显示左上角的返回按钮
						actionBar.setDisplayHomeAsUpEnabled(itemPosition != 0);

						Map<String, Object> map = data.get(itemPosition);
						Object o = map.get("fragment");
						if (o instanceof Fragment) {
							FragmentTransaction ft = getFragmentManager()
									.beginTransaction();
							ft.replace(android.R.id.content, (Fragment) o);
							ft.commit();
						}
						return true;
					}
				});
	}
}
