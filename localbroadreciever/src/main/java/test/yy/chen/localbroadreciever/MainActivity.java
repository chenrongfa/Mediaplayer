package test.yy.chen.localbroadreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LocalBroadcastManager localBroadcastManager;
    private Button btn;
    private int int2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);

        IntentFilter inte=new IntentFilter("com.yy.chen");
        localBroadcastManager.registerReceiver(new LocalBroad(),inte);

        btn= (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(int2==0){
        Intent intent2=new Intent(this,Main2Activity.class);
        startActivity(intent2);}
if(int2 ==1){
        Intent intent=new Intent("com.yy.chen");
        localBroadcastManager.sendBroadcast(intent);}
        int2++;
        }

    class LocalBroad extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "lailai", Toast.LENGTH_SHORT).show();
        }
    }
}
