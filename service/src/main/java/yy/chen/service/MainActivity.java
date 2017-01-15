package yy.chen.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
   private Button btn_service;
   private Button btn_itservice;
    ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    public static ArrayList<MyIntentService> bij;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bij=new ArrayList<>();
        btn_service=(Button) findViewById(R.id.btn_service);
        btn_itservice=(Button) findViewById(R.id.btn_itservice);
    }
    public void click(View view){
        if(bij.size()>2){
            boolean b = bij.get(0) == bij.get(1);
            Log.e(TAG, "click: "+b);
        }
        if(view==btn_service){
            Intent intent=new Intent(this,MyService.class);

            bindService(intent,conn , Context.BIND_AUTO_CREATE);
            System.out.print("halo");
        }else{
            Intent intent=new Intent(this,MyIntentService.class);
            bindService(intent,conn , Context.BIND_AUTO_CREATE);
            System.out.print("halo");
        }
    }
}
