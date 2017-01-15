package yy.chen.mediaplay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import yy.chen.mediaplay.R;

public class SplashAcvivity extends Activity {
    public static String TAG=SplashAcvivity.class.getSimpleName();
    private boolean isOpenMain=false;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acvivity);
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
//两秒后执行
            startMainAvtivity();
            Log.e(TAG,Thread.currentThread().getName());
        }

        
    },2000);

    }

   //开启main并关闭所在activity
    private void startMainAvtivity() {
       if(!isOpenMain) {
           Intent intent=new Intent(this,MainActivity.class);
           startActivity(intent);
           isOpenMain=true;
           finish();}
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
         Log.e(TAG, event.getAction()+"");
        startMainAvtivity();

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
