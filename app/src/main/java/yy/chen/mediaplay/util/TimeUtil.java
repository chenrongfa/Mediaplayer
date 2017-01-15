package yy.chen.mediaplay.util;

/**
 * Created by chenrongfa on 2016/12/28
 */

public class TimeUtil {
    //前缀
   static int prix=0,houzui=0;
    public static String TimeToString(int i){
        final int t=i;

                int h=t/1000;
                prix=h/60;
                houzui=h%60;
        return  prix+":"+houzui;
        }
    public  static boolean isInternetRe(String url){
        if(url.toLowerCase().startsWith("http")||url.toLowerCase().startsWith("http"))
        {
            return true;
        }
        return false;
    }

}
