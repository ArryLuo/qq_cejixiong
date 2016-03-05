package com.example.qq;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText qq;
	private TextView name, content;
	private Handler handler = new Handler();
	private boolean flg = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		qq = (EditText) findViewById(R.id.input);
		name = (TextView) findViewById(R.id.name);
		content = (TextView) findViewById(R.id.top);
		findViewById(R.id.send).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("温馨提示");
				builder.setMessage("你想换字体吗？");

				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// 改变字体
								name.setTypeface(Typeface.createFromAsset(
										getAssets(), "fonts/test.ttf"));
								show();
								flg = false;
							}
						});

				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								show();
								//flg = false;
							}
						});
				if (flg) {
					builder.create();
					builder.show();
				} else {
					show();
				}
			}
		});

	}

	private void show() {
		String d = qq.getText().toString();
		if (d.length() > 11) {
			Toast.makeText(MainActivity.this, "请正确输入你的qq号", 0).show();
			String trime=d="";
			qq.setText(trime);
			return ;
		}
		else{
		if (!TextUtils.isEmpty(d)) {

			new HttpQQThread(d, name, content, handler).start();
			content.setVisibility(View.VISIBLE);
		} else {
			Toast.makeText(MainActivity.this, "qq号不能为空!", 0).show();
		}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
