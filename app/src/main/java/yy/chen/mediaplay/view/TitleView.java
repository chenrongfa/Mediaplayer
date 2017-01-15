package yy.chen.mediaplay.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.activity.TitleActivity;

/**
 * Created by chenrongfa on 2016/12/27
 */

public class TitleView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private View tv_search;
    private View rl_game;
    private View tv_histroy;

    public TitleView(Context context) {
       this(context,null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;


    }
//布局加载完成调用
    @Override
    protected void onFinishInflate() {
        tv_search=  getChildAt(0);
        rl_game=getChildAt(1);
        tv_histroy=getChildAt(2);
        tv_search.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        tv_histroy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
//                Toast.makeText(context,"sousuo",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,TitleActivity.class);
                context.startActivity(intent);
            break;
            case R.id.tv_histroy:
                Toast.makeText(context,"lishi",Toast.LENGTH_SHORT).show();
            break;
            case R.id.rl_game:
                Toast.makeText(context,"youxi",Toast.LENGTH_SHORT).show();
            break;
        }
    }
}
