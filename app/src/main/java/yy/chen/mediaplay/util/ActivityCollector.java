package yy.chen.mediaplay.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenrongfa on 2017/1/14
 */

public class ActivityCollector {
    public static List<Activity> activityList=new ArrayList<>();
    public static MediaPlayer mediaPlayer;
    public static NotificationManager manager;

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

  public static void removeActivity(Activity activity){
      activityList.remove(activity);

  }

    public static void removeAll(){
        if(activityList.size()>0&&activityList!=null) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
        if(mediaPlayer!=null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        if(manager!=null){
            manager.cancel(1);
        }
    }
}
