package yy.chen.mediaplay.view;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.RelativeLayout;

import io.vov.vitamio.widget.VideoView;


/**
 * Created by chenrongfa on 2016/12/30
 */

public class VitamioVideoView  extends VideoView {
    public VitamioVideoView(Context context) {
        super(context);
        Log.e("erro","context");

    }

    public VitamioVideoView(Context context, AttributeSet attrs) {
       super(context, attrs);
        Log.e("erro","super(context, attrs)");
    }

    public VitamioVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }
    //设置视频的大小
    public void setVideoSize(int width,int height){
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(width,height);
        layoutParams.addRule(Gravity.CENTER);
        setZOrderMediaOverlay(true);
        setLayoutParams(layoutParams);

    }
}
