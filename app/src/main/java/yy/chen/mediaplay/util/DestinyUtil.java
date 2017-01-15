package yy.chen.mediaplay.util;

import android.content.Context;

/**
 * Created by chenrongfa on 2017/1/6
 */

public class DestinyUtil {
    /**
     * dp 转化px
     * @param context
     * @param dp
     * @return px
     */
    public static float getDpToPx(Context context,int dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (dp*density+0.5f);
    }

    /**
     *  px转化dp
     * @param context
     * @param px
     * @return dp
     */
    public static float getPxToDp(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        return (px/density+0.5f);
    }

    /**
     *  sp to px
     * @param context
     * @param sp
     * @return px
     */
    public static float getSpToPx(Context context,int sp){
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (sp*density+0.5f);
    }

    /**
     *  px转化 sp
     * @param context
     * @param px
     * @return sp
     */
    public static float getPxToSp(Context context,int px){
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (px/density+0.5f);
    }

}
