package yy.chen.test;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chenrongfa on 2016/12/30
 */

public class JavaContrloller {
    public static List<Activity> activities=new LinkedList<Activity>();
    public static  void add(Activity activity){
        activities.add(activity);

    }
}
