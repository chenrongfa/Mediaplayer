package yy.chen.mediaplay.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chenrongfa on 2017/1/3
 */

public class CacheUtil {
   static SharedPreferences sharedPreferences;
    public static void putStrig(Context context,String key,String value){

        sharedPreferences = context.getSharedPreferences("saveData", Context
                .MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).commit();
    }
    public static String getString(String key){
       return sharedPreferences.getString(key,"");

    }
}
