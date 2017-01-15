package yy.chen.mediaplay.base;

import android.content.Context;
import android.view.View;

/**
 * Created by chenrongfa on 2016/12/26
 */

public abstract class BasePager {
    public static Context context;
    public View view;
    public BasePager(Context context){
        this.context=context;
        view=initView();
    }
    //强制实现
    public abstract View initView();

   public void initData(){}


}
