package yy.chen.mediaplay.util;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import static android.net.TrafficStats.UNSUPPORTED;

/**
 * Created by chenrongfa on 2016/12/31
 */

public class SpeedUtil {
    private static final String TAG = "SpeedUtil";
    private static final int SUPPORTED = 6;
    private Context context;
    private Handler handler;
    private  long lastTotle;
    private  long lasttime;

    public SpeedUtil(Context context, Handler handler){
        this.handler=handler;
        this.context=context;

    }
    public  String getSpeed(){
        int uid=context.getApplicationInfo().uid;
        if(uid== UNSUPPORTED){
            Toast.makeText(context,"不支持网速查询",Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessage(UNSUPPORTED);
            Log.e("erro","不支持网速查询");
        }else {
            Log.e("erro","支持");
            long nowTime = System.currentTimeMillis();
            long nowtotle = TrafficStats.getTotalRxBytes();
            long dtotle = nowtotle - lastTotle;
            long dtime = nowTime- lasttime;
            lasttime=nowTime;
            lastTotle = nowtotle;
            return   getString(dtotle,dtime);
        }
        return null;
    }

    private String getString(long dtotle, long dtime) {
        Log.e(TAG, "getString: dtime"+dtime);
        Log.e(TAG, "getString: dtotle"+dtotle );
        //得到秒
        long mills= dtime/(long) 1000;
        Log.e(TAG, "getString: mills"+mills );
       long kb =dtotle/(long) 1024;
        if(kb<=0||mills==0){
            Toast.makeText(context,"没有网速",Toast.LENGTH_SHORT).show();
        }else {
          String kb1 = kb/ mills+"kb/s";
            Log.e(TAG, "getString: kb"+kb1 );
            return kb1;
        }
        return null;
    }
}
