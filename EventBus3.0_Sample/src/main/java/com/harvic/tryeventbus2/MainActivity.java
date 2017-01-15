package com.harvic.tryeventbus2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.harvic.other.FirstEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends Activity {

	private Button btn;
	private TextView tv;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//1.注册
		EventBus.getDefault().register(this);

		btn = (Button) findViewById(R.id.btn_try);

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						SecondActivity.class);
				startActivity(intent);
			}
		});
	}


	@Subscribe(threadMode = ThreadMode.MAIN,sticky = false,priority = 80)
	public void testHello(FirstEvent event) {

		Log.d("harvic", "testHello:" + event.getMsg()+",priority=80"+",sticky==false");
		Log.d("harvic","Thread-name=="+Thread.currentThread().getName());
	}

	@Subscribe(threadMode = ThreadMode.MAIN,sticky = true,priority = 71)
	public void onEventMainThread(FirstEvent event) {
		Log.d("harvic", "onEventMainThread:" + event.getMsg()+",priority=71,sticky = true");
		Log.d("harvic","Thread-name=="+Thread.currentThread().getName());
	}

//	@Subscribe(threadMode = ThreadMode.BACKGROUND)
//	public void onEventBackgroundThread(FirstEvent event){
//		for(int i=0;i<50;i++) {
//			Log.d("harvic", "onEventBackground:"+ i+ event.getMsg());
//			Log.d("harvic", "Thread-name==" + Thread.currentThread().getName());
//		}
//	}
//	@Subscribe(threadMode = ThreadMode.ASYNC)
//	public void onEventAsync(FirstEvent event){
//		for(int i=0;i<50;i++) {
//		Log.d("harvic", "onEventAsync:"+i + event.getMsg());
//		Log.d("harvic","Thread-name=="+Thread.currentThread().getName());}
//	}
//

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(FirstEvent event) {
		Log.d("harvic", "OnEvent:" + event.getMsg());
		Log.d("harvic","Thread-name=="+Thread.currentThread().getName());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//取消注册
		EventBus.getDefault().unregister(this);
	}
}