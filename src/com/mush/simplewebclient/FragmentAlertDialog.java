package com.mush.simplewebclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FragmentAlertDialog extends Activity {

	/** 当Activity被首次创建时，系统会调用这个方法 */

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// 把布局文件填充到Activity中

		setContentView(R.layout.dialog_add);

		// 查找布局中的text元素，并设置要显示的文本。

		View tv = findViewById(R.id.autoet_key);

		// ((TextView)tv).setText("Example of displaying an alert dialog with a DialogFragment");

		// 查找布局中的show按钮，并设置点击事件监听器。用户点击时该按钮时，显示一个Fragment对话框。

		Button button = (Button) findViewById(R.id.autoet_key);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				showDialog();

			}

		});

	}

	/**
	 * 
	 * 显示Fragment对话框。
	 */

	void showDialog() {

		// 获取Fragment对话框实例，在获取实例的时候，设置这个对话框的标题。

		DialogFragment newFragment = MyAlertDialogFragment.newInstance(

		R.string.app_name);

		/**
		 * 
		 * 显示Fragment对话框。
		 * 
		 * show(FragmentManager,
		 * String)方法：本方法把Fragment添加到给定的Fragment管理器中，并显示对话框。
		 * 
		 * 对于创建明确的事务、给Fragment对象添加给定的标签，并提交这个事务，这个方法是非常方便的。
		 * 
		 * 这个方法不会把事务添加到回退堆栈中。当这个Fragment被关闭时，要执行一个新的从Activity中
		 * 
		 * 删除这个Fragment对象的事务。
		 * 
		 * FragmentManager:指定Fragment对象要被添加到Fragment管理器对象。
		 * 
		 * String：指定Fragment对象的标签。
		 */

		newFragment.show(getFragmentManager(), "dialog");

	}

	/**
	 * 
	 * 定义Fragment对话框对象ok按钮点击时，要执行的方法。
	 */

	public void doPositiveClick() {

		Log.i("FragmentAlertDialog", "Positive click!");

	}

	/**
	 * 
	 * 定义Fragment对话框对象cancel按钮点击时，要执行的方法。
	 */

	public void doNegativieClick() {

		Log.i("FragmentAlertDialog", "Negative click");

	}

	/**
	 * 
	 * 定义Fragment对话框的静态类，继承DialogFragment类。
	 */

	public static class MyAlertDialogFragment extends DialogFragment {

		/**
		 * 
		 * 创建Fragment对话框实例
		 * 
		 * @param title
		 *            ：指定对话框的标题。
		 * 
		 * @return：Fragment对话框实例。
		 */

		public static MyAlertDialogFragment newInstance(int title) {

			MyAlertDialogFragment frag = new MyAlertDialogFragment();

			Bundle args = new Bundle();

			args.putInt("title", title);

			frag.setArguments(args);

			return frag;

		}

		/**
		 * 
		 * 覆写Fragment类的onCreateDialog方法，在FragmentDialog的show方法执行之后，
		 * 
		 * 系统会调用这个回调方法。
		 */

		@Override
		public Dialog onCreateDialog(Bundle saveInstanceState) {

			// 获取对象实例化时传入的窗口标题。

			int title = getArguments().getInt("title");

			// 返回提醒对话框。

			return new AlertDialog.Builder(getActivity())

			// 设置标题图标

					.setIcon(R.drawable.ic_launcher)

					// 设置标题

					.setTitle(title)

					// 设置确定按钮的标题和点击事件监听器。

					.setPositiveButton(R.string.app_name,

					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							((FragmentAlertDialog) getActivity())
									.doPositiveClick();

						}

					}

					)

					// 设置取消按钮的标题和点击事件监听器。

					.setNegativeButton(R.string.app_name,

					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							((FragmentAlertDialog) getActivity())
									.doNegativieClick();

						}

					}

					)

					// 创建对话框

					.create();

		}

	}

}