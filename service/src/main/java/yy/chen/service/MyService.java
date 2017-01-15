package yy.chen.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    Object obj;
    public MyService() {
        super();
        Log.e(TAG, "MyService: "+"MyService");
        obj=new Object();
    }

    private static final String TAG = "MyService";


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: "+"onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e(TAG, "onCreate: "+"xingle" );
        Log.e(TAG, "myservice: "+Thread.currentThread().getName() );
        System.out.print("xingle" );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(TAG, "onBind: "+"onBind" );
        return null;

    }
}
