package yy.chen.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
    private MyService myService;
    private final static String TAG="MyService";
    public MyService() {
        myService=this;
        Log.e(TAG, "MyService: ");
    }
private  String name;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return he communication channel to the service.
        return hell;
//        throw new UnsupportedOperationException("Not yet implemented");
    }
    private Itest.Stub hell=new Itest.Stub(){
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                               double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getName() throws RemoteException {
            return name;
        }

        @Override
        public void setName(String name) throws RemoteException {
                myService.name=name;
        }
    };
}
