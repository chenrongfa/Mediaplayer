package test.yy.chen.localbroadreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        setContentView(R.layout.activity_main2);
        IntentFilter inte=new IntentFilter("com.yy.chen");
        localBroadcastManager.registerReceiver(new LocalBroad(),inte);
    }
  static  class LocalBroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "koko", Toast.LENGTH_SHORT).show();
        }
    }
}
