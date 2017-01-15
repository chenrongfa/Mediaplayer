package yy.chen.mediaplay.viewpager;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import yy.chen.mediaplay.base.BasePager;

/**
 * Created by chenrongfa on 2016/12/26
 */

public class VideoPager extends BasePager {
//    private Context context;
    private TextView textView;
    @Override
    public View initView() {
        Log.e("erro","text初始化");
        textView=new TextView(context);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        Log.e("erro","VideoPager数据初始化");
        textView.setText("初始化");
    }

    public VideoPager(Context context) {
        super(context);
//        this.context=context;
    }
}
