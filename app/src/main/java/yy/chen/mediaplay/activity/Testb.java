package yy.chen.mediaplay.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import yy.chen.mediaplay.R;

public class Testb extends Activity {


    @Override
    protected void onStop() {
        super.onStop();
        Log.e("erro","onStop2");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent=new Intent();
        intent.setDataAndType(Uri.parse("http://192.168.2.100:8080/pp.mp4"),"video/*");
        startActivity(intent);

        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testb);
        Log.e("erro","ONCREATE");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("erro","onStart2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("erro","onDestroy2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("erro","onRestart2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("erro","resume2");
    }
}
