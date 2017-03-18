package yy.chen.mediaplay.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import yy.chen.mediaplay.R;
import yy.chen.mediaplay.bean.Video;
import yy.chen.mediaplay.util.ActivityCollector;
import yy.chen.mediaplay.util.SpeedUtil;
import yy.chen.mediaplay.util.TimeUtil;
import yy.chen.mediaplay.view.VitamioVideoView;

import static yy.chen.mediaplay.activity.SplashAcvivity.TAG;

/**
 * Created by chenrongfa on 2016/12/28
 */

public class VitamioPlay extends Activity implements View.OnClickListener {
    private static final int UPDATA_PLAY = 0;//播放进度what标识
    private static final int UPDATA_VOICE = 1;//声音what 标识
    private static final int UPDATA_TIME = 2;//时间标识
    private static final int SHOWVIEW = 3;//显示视图标识
    private AudioManager audiomanager;//音量
    private RelativeLayout rl_visible;
    private VitamioVideoView vv_myplay;
    private LinearLayout llStatu;
    private TextView tvMedianame;
    private ImageView ivBattery;
    private TextView tvTime1;
    private LinearLayout llAudio;
    private Button btnVoice;
    private SeekBar sbVoice;
    private LinearLayout llPlay;
    private TextView tvPlaytime;
    private SeekBar sbPlay;
    private TextView tvTotaltime;
    private LinearLayout llBtn;
    private Button btnEixt;
    private Button btnPre;
    private Button btnPause;
    private Button btnNext;
    private Button btnScreen;
    private Bundle bundle;
    private ArrayList<Video> data;
    private int position;
    private Video currentdata;
    private int longtime=0;//隐藏视图
    private TextView tv_speed;
    private LinearLayout sb_douwload;
    private boolean isHide;
    private Battery battery;
    private int screenwidth;//屏幕宽
    private int defaultwidth;//默认宽
    private int defaultheight;//默认高
    private int screeheight;//屏幕高
    private int min;   //上面两者相减的最小值
    private int audioLevel;//getshare那大上次;ondestory保存
    private boolean isvoice;//标识上次 是不是大于0
    private int lastvoice;//上次
    private int currentvoice; //记录当前音量;
    private String speed ;//网速
    private  final static int UNSUPPORTED=-1;
    private  final static int SUPPORTED=6;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // TODO: 2016/12/29 gengxin
                case UNSUPPORTED:
                    removeMessages(UNSUPPORTED);
                    break;
                case SUPPORTED:

                    if(speed!=null){
                        tv_speed.setText(speed);
                    }else {
                        tv_speed.setText("0kb/s");
                    }
                    removeMessages(SUPPORTED);
                    sendEmptyMessageDelayed(SUPPORTED,1000);
                    speed=stl.getSpeed();
                      break;
                case UPDATA_PLAY:
                    //得到当前位置，设置当前进度 更新ui
                    int currentPosition = (int) vv_myplay.getCurrentPosition();
                    sbPlay.setProgress(currentPosition);
                    tvPlaytime.setText(TimeUtil.TimeToString(currentPosition));
                    //移除上一个消息 循环
                    removeMessages(UPDATA_PLAY);
                    sendEmptyMessageDelayed(UPDATA_PLAY, 1000);
                    break;
                case UPDATA_VOICE:
                    //设置为0 更新ui  flags 0，时看不到系统的seekbar

                    audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                    //移除上一个消息
                    sbVoice.setProgress(0);
                    removeMessages(UPDATA_VOICE);
                    break;
                case UPDATA_TIME:
                    //获得时间 更新ui
                    Date date = new Date();
                    SimpleDateFormat simplDate = new SimpleDateFormat("hh:mm");
                    String format = simplDate.format(date);
                    tvTime1.setText(format);
                    // 移除上一次 然后循环
                    removeMessages(UPDATA_TIME);
                    sendEmptyMessageDelayed(UPDATA_TIME, 1000);
                    break;
            case SHOWVIEW:
                    if (!isHide){
                        isHide=true;
                        rl_visible.setVisibility(View.GONE);
                    }
                    break;
            }

            super.handleMessage(msg);
        }
    };
    private boolean isscreenfull;
    private boolean isHideSpeed;

    private void findViews() {
        tv_speed= (TextView) findViewById(R.id.tv_speed);
        sb_douwload= (LinearLayout) findViewById(R.id.sb_douwload);
        vv_myplay = (VitamioVideoView) findViewById(R.id.vv_vitamioplay);
        rl_visible= (RelativeLayout) findViewById(R.id.rl_visible);
        llStatu = (LinearLayout) findViewById(R.id.ll_statu);
        tvMedianame = (TextView) findViewById(R.id.tv_medianame);
        ivBattery = (ImageView) findViewById(R.id.iv_battery);
        tvTime1 = (TextView) findViewById(R.id.tv_time1);
        llAudio = (LinearLayout) findViewById(R.id.ll_audio);
        btnVoice = (Button) findViewById(R.id.btn_voice);
        sbVoice = (SeekBar) findViewById(R.id.sb_voice);
        llPlay = (LinearLayout) findViewById(R.id.ll_play);
        tvPlaytime = (TextView) findViewById(R.id.tv_playtime);
        sbPlay = (SeekBar) findViewById(R.id.sb_play);
        tvTotaltime = (TextView) findViewById(R.id.tv_totaltime);
        llBtn = (LinearLayout) findViewById(R.id.ll_btn);
        btnEixt = (Button) findViewById(R.id.btn_eixt);
        btnPre = (Button) findViewById(R.id.btn_pre);
        btnPause = (Button) findViewById(R.id.btn_pause);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnScreen = (Button) findViewById(R.id.btn_screen);

        btnVoice.setOnClickListener(this);
        btnEixt.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnScreen.setOnClickListener(this);
        vv_myplay.setOnClickListener(this);
        //监听视频 卡屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            vv_myplay.setOnInfoListener((io.vov.vitamio.MediaPlayer.OnInfoListener) new VideoOnInfoListener());
        }
    }
    class  VideoOnInfoListener implements MediaPlayer.OnInfoListener{

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
             switch (what){
                 case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                     showSpeed();
                     break; case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                         Toast.makeText(VitamioPlay.this, "不卡", Toast.LENGTH_SHORT).show();
                         hideSpeed();
                         break;

                     }
            return true;
        }
    }

    private void showSpeed() {
        if (isHideSpeed) {
         sb_douwload.setVisibility(View.VISIBLE);
         Toast.makeText(VitamioPlay.this, "ka", Toast.LENGTH_SHORT).show();
          isHideSpeed=false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnVoice) {
            // TODO: 2016/12/29 voice  获取当前音量 如果大于1 设置为零
            //并记录下位置再次再次按时 反回那时位置
            currentvoice = audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC);

            if (currentvoice > 0) {
                handler.sendEmptyMessage(UPDATA_VOICE);
                lastvoice = currentvoice;
                isvoice = true;
            }
            if (isvoice) {
                //反回上次的位置
                sbVoice.setProgress(lastvoice);
            }

        } else if(v==rl_visible){
            Log.e("erro","rl_visible");
            if(!isHide){
            hideView();
            }else {
                showView();
            }
        }
        else if (v == btnEixt) {
            // Handle clicks for btnEixt
            finish();
        } else if (v == btnPre) {
            //如果是第一个视频 toast
         setBtnPreStatu();
            // Handle clicks for btnPre
        } else if (v == btnPause) {
            setPause();
            // Handle clicks for btnPause
        } else if (v == btnNext) {
            // Handle clicks for btnNext
            setBtnNext();
        } else if (v == btnScreen) {
            setScreenSize();

        }
        handler.removeMessages(SHOWVIEW);
        Log.e("erro","SHOWVIEW");
        handler.sendEmptyMessageDelayed(SHOWVIEW, 4000);
    }

    private void setPause() {
        if (vv_myplay.isPlaying()) {
            //如果是播放那就暂停
            vv_myplay.pause();
            btnPause.setBackgroundResource(R.drawable.btn_start_selector);
        } else {
            //否则开始
            vv_myplay.start();
            btnPause.setBackgroundResource(R.drawable.btn_pause_selector);
        }
    }

    private void setScreenSize() {
        if(!isscreenfull) {
            isscreenfull=true;
            vv_myplay.setVideoSize(screenwidth,screeheight);
            btnScreen.setBackgroundResource(R.drawable.btn_default_full_selector);
        }else {
            isscreenfull=false;
            vv_myplay.setVideoSize(defaultwidth+min,defaultheight+min);
            btnScreen.setBackgroundResource(R.drawable.btn_screen_selector);

        }
    }

    private void hideView() {
            isHide=true;
            rl_visible.setVisibility(View.GONE);
    }
    private void showView() {
            isHide=false;
            rl_visible.setVisibility(View.VISIBLE);

    }

    private void setBtnNext() {
            ++position;
            if (position == data.size()-1) {
                Toast.makeText(this, "已经是最后一个", Toast.LENGTH_SHORT).show();
                setNextdisable();

            }
            if(isbtnPre){
                setPreable();
            }
                //切换后
//            Log.e("erro",position+"position");
            String path=data.get(position ).getData();
            if (path!=null){

                    vv_myplay.setVideoPath(path);
                    tvMedianame.setText(data.get(position).getName());
                    btnNext.setEnabled(true);
                    btnNext.setBackgroundResource(R.drawable.btn_next_selector);
                 }}

    private void setPreable() {
        isbtnPre=false;
        Log.e("erro","isbtnPre");
        btnPre.setEnabled(true);
        btnPre.setBackgroundResource(R.drawable.btn_pre_selector);
    }

    //    }
    private boolean isbtnNext;
    private boolean isbtnPre;
    private void setBtnPreStatu() {
        /*if (position == 0) {
            Toast.makeText(this, "不能在后退", Toast.LENGTH_SHORT).show();
            btnPre.setEnabled(false);
            isbtnPre=true;
            btnPre.setBackgroundResource(R.drawable.btn_pre_gray);
        } else {*/
            position--;
            if (position <= 0) {
                Toast.makeText(this, "不能在后退", Toast.LENGTH_SHORT).show();
                btnPre.setEnabled(false);
                isbtnPre=true;
                btnPre.setBackgroundResource(R.drawable.btn_pre_gray);
            }else{
            String path=data.get(position ).getData();
            //判断是否 最后一个然后，使按钮能用
            if(isbtnNext){
                isbtnNext=false;//别再加载
                Log.e("erro","isbtnNext");
                btnNext.setEnabled(true);
                btnNext.setBackgroundResource(R.drawable.btn_next_selector);
            }
            if (path!=null){
                vv_myplay.setVideoPath(path);
                btnPre.setEnabled(true);
                btnPre.setBackgroundResource(R.drawable.btn_pre_selector);
                tvMedianame.setText(data.get(position).getName());
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2016/12/29 oncreate
        super.onCreate(savedInstanceState);
        if(!Vitamio.isInitialized(this))
            return;
        ActivityCollector.activityList.add(this);
        setContentView(R.layout.vitamiovideoview);

        //没有初始化退出

        //初始化
        findViews();
        initdata();
    }
   private SpeedUtil stl;//网速处理器
    private void initdata() {
        data1 = getIntent().getData();

        //接收会传过来的数据
        recieverInit();
        //网速
        stl=new SpeedUtil(this,handler);
        //得到屏幕的宽高


        getScreenwh();
        vv_myplay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //隐藏seek——download
                // TODO: 2016/12/31 chuli
                hideSpeed();
                //得到声音 ，设置音量
                audioLevel= congif.getInt("audioLevel",0);
                sbVoice.setProgress(audioLevel);
//                isscreenfull=true;
                //得到播放视图的大小
                defaultheight=mp.getVideoHeight();
                defaultwidth=mp.getVideoWidth();
                min=Math.min(screeheight-defaultheight,screenwidth-defaultwidth);
//                设置大小

                setScreenSize();
//                vv_myplay.setLayoutParams(la);
                //隐藏视图
                if(!isHide){
                    hideView();
                }else {
                    showView();
                }
                handler.removeMessages(SHOWVIEW);
                handler.sendEmptyMessageDelayed(SHOWVIEW,4000);
                //获得进度条最大值，1从 带过来的数据，2，vv_play gengxin

                int duration = (int)vv_myplay.getDuration();
                tvTotaltime.setText(TimeUtil.TimeToString(duration));
                sbPlay.setMax(duration);
                vv_myplay.start();
                //发送handler
                handler.sendEmptyMessage(UPDATA_PLAY);
                //获得最大值，更新 sb_voice
                maxVoice = audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                sbVoice.setMax(maxVoice);
                int currentvoic = audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC);
                sbVoice.setProgress(currentvoic);
                handler.sendEmptyMessage(UPDATA_TIME);
                //按钮状态
                // TODO: 2016/12/30 处理
                if(TimeUtil.isInternetRe(data1.toString())){
                    speed= stl.getSpeed();
                    handler.sendEmptyMessage(SUPPORTED);
                    setNextdisable();
                    setPreable();
                    // TODO: 2016/12/31  监听缓冲
                    mp.setOnBufferingUpdateListener(new VideoOnbufferingListner());
              }else if(position==0){
                        sbPlay.setSecondaryProgress(0);
                    if(position==data.size()-1){
                        setNextdisable();
                    }
                    if (position==0){
                        setPreable();
                    }
                }else{
                    setNextdisable();
                    setPreable();
                }

            }
        });
        //出错
        vv_myplay.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                Toast.makeText(VitamioPlay.this,"setOnErrorListener出错",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onError: "+"setOnErrorListener出错");
                return true;
            }
        });
        vv_myplay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //是否是网络视频是直接退出
                if (TimeUtil.isInternetRe(data1.toString())){
                    finish();
                }
                //自动播放下一个
                else {
                if(position==data.size()-1){
                    Toast.makeText(VitamioPlay.this, "播放wanbi", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    setBtnNext();
                }}
            }
        });
        rl_visible.setOnClickListener(this);


        //更新组件

        tvTime1.setText("用线程每秒革新");
        audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //设置sb_play 监听
        sbPlay.setOnSeekBarChangeListener(new SbOnSeekBarListner());
        //设置sb_vocie监听
        sbVoice.setOnSeekBarChangeListener(new SbOnSeekBarListner());
        //注册电量
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        battery=  new Battery();
        registerReceiver(battery,intentFilter);
        //初始化手势
        gest=new GestureDetector(this,new GestOnGesturelistner()
        );
        //得到getshare
        congif = getSharedPreferences("congif", Context.MODE_PRIVATE);
    }

    private void recieverInit() {
        bundle = getIntent().getExtras();
        if(TimeUtil.isInternetRe(data1.toString())) {
            vv_myplay.setVideoURI(getIntent().getData());
        }else if(bundle!=null){
        data = bundle.getParcelableArrayList("data");
        currentdata = bundle.getParcelable("currentdata");
        position = bundle.getInt("position");
        vv_myplay.setVideoPath(data.get(position).getData());
            tvMedianame.setText(currentdata.getName());
        }else{
            vv_myplay.setVideoURI(getIntent().getData());
        }
    }

    private void hideSpeed() {
        if(!isHideSpeed) {
            isHideSpeed=true;
            sb_douwload.setVisibility(View.GONE);
        }
    }

    //缓冲监听类
    class VideoOnbufferingListner implements MediaPlayer.OnBufferingUpdateListener{
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            Log.e("erro",percent+"percent");
            int tottlesize=sbPlay.getMax()*percent;
            int perent1=tottlesize/100;
            //关闭网络监控
            if(percent==100){
                handler.removeMessages(SUPPORTED);
            }
            sbPlay.setSecondaryProgress(perent1);
        }
    }
    private void setNextdisable() {
        isbtnNext=true;
        btnNext.setEnabled(false);
        btnNext.setBackgroundResource(R.drawable.btn_next_gray);
    }

    private Uri data1;//获得数据
     private  SharedPreferences congif;
    private void getScreenwh() {
        DisplayMetrics diplay=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(diplay);
        screenwidth=diplay.widthPixels;
        screeheight=diplay.heightPixels;
    }
    private int voicelevel;
    private int maxVoice;

    class  GestOnGesturelistner extends GestureDetector.SimpleOnGestureListener{
       @Override
       public boolean onDown(MotionEvent e) {
           voicelevel = sbVoice.getProgress();
           if(!isHide){
               hideView();
           }else {
               showView();
           }
           handler.removeMessages(SHOWVIEW);
           handler.sendEmptyMessageDelayed(SHOWVIEW,4000);
           return true;
       }

       @Override
       public boolean onDoubleTap(MotionEvent e) {
           setScreenSize();
           return true;
       }

       @Override
       public void onLongPress(MotionEvent e) {
           setPause();
           super.onLongPress(e);
       }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float
                distanceY) {

            Log.e("erro","x"+distanceX);
            Log.e("erro","y"+distanceY);
//          int min= (int)distanceY/Math.min(screeheight,screenwidth);

            if(distanceY>0.6||distanceY<-0.6)
            sbVoice.setProgress(Math.max(Math.min(voicelevel+(int)distanceY,maxVoice),0));
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float
                velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    class SbOnSeekBarListner implements SeekBar.OnSeekBarChangeListener {
        // fromuser 用户拖动的是true
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(sbVoice==seekBar) {
                audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 2);
            }else{
                if (fromUser) {
                    vv_myplay.seekTo(progress);
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
          //触碰时移除
            handler.removeMessages(SHOWVIEW);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //离开发送
            handler.sendEmptyMessageDelayed(SHOWVIEW,4000);
        }
    }



    /**
     * 定义手势
     */
    private GestureDetector gest;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return  gest.onTouchEvent(event);
    }

    //监听电量的变化
    class Battery extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
          int level=intent.getIntExtra("level",0);
            //设置图片
            setBattery(level);
        }
    }

    private void setBattery(int level) {
        if(level<0){
            ivBattery.setImageResource(R.drawable.ic_battery_0);
        }else if(level<10){
            ivBattery.setImageResource(R.drawable.ic_battery_10);
        }else if(level<10){
            ivBattery.setImageResource(R.drawable.ic_battery_10);
        }else if(level<20){
            ivBattery.setImageResource(R.drawable.ic_battery_20);
        }else if(level<40){
            ivBattery.setImageResource(R.drawable.ic_battery_40);
        }else if(level<60){
            ivBattery.setImageResource(R.drawable.ic_battery_60);
        }else if(level<80){
            ivBattery.setImageResource(R.drawable.ic_battery_80);
        }else if(level<100){
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        }else {
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        }
    }

    @Override
    protected void onDestroy() {
        //退出判断是否在播放
        if(vv_myplay.isPlaying()){
            vv_myplay.stopPlayback();
        }
        //取消广播
        if(battery!=null){
            unregisterReceiver(battery);
        }
        //得到当前音量然后保存
        if (congif!=null){
        SharedPreferences.Editor edit = congif.edit();
        edit.putInt("audioLevel",sbVoice.getProgress());
        edit.commit();
        congif=null;
        }
        //删除线程
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler=null;
        }
        ActivityCollector.activityList.remove(this);
        super.onDestroy();
    }
}
