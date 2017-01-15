package yy.chen.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private Button btn1;
   private Button btn2;
    private String TAG="MainActivity";
    private Itest it;
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "onServiceConnected: " );
          it= Itest.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "onServiceDisconnected: " );
            it=null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1= (Button) findViewById(R.id.btn);
        btn2= (Button) findViewById(R.id.button2);
        Intent intent=new Intent(this,MyService.class);
        bindService(intent,conn , Context.BIND_AUTO_CREATE);
    }
    public void click(View v){
        if(v==btn1){
            try {
                it.setName("陈蓉发");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else{
            try {
                Toast.makeText(this,it.getName(),Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
