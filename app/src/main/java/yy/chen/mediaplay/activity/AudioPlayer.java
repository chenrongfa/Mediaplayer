package yy.chen.mediaplay.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.greenrobot.event.EventBus;
import yy.chen.mediaplay.IAudioService;
import yy.chen.mediaplay.R;
import yy.chen.mediaplay.bean.Lyric;
import yy.chen.mediaplay.bean.Video;
import yy.chen.mediaplay.service.AudioService;
import yy.chen.mediaplay.util.ActivityCollector;
import yy.chen.mediaplay.util.LyricUtil;
import yy.chen.mediaplay.util.TimeUtil;
import yy.chen.mediaplay.view.BaseVisualizerView;
import yy.chen.mediaplay.view.LyricView;

import static yy.chen.mediaplay.R.id.btn_audio_mode;
import static yy.chen.mediaplay.R.id.iv_src;

public class AudioPlayer extends Activity implements View.OnClickListener {
    private static final int PREPARELYRIC = 2;//可以准备歌词标签
    private static final int NOTFOUNDLYRIC = 3;
    private LyricView tv_lyric;

    private static final int AUDIOSEEKTO = 0;
    private static final int HIDEVIEW = 1;
    private AnimationDrawable animationDrawable;
    private boolean canShow;//标识是否从状态栏进入
    private int currentPosition;
    private IAudioService stub;
    private int lastVolum;//记录上一次的音量;
    private RelativeLayout rl_hide;
    private AudioManager audioManager;
    private int audioVolume;
    private RelativeLayout rlTop;
    private LinearLayout llAudio;
    private Button btnVoice;
    private SeekBar sbVoice;
    private Button btnEverything;
    private BaseVisualizerView ivSrc;
    private TextView tvSong;
    private TextView tvAuthor;
    private TextView tvTime;
    private SeekBar sbPlay;
    private LinearLayout llBtn;
    private Button btnAudioMode;
    private Button btnPre;
    private Button btnPause;
    private Button btnNext;
    private Button btnLyrc;
    private int duration;//播放总时间
    private int maxsize;
    private Video video;
    //    private int SINGLEMODE=0;
//    private int ALLMODE=1;
//    private int ORDERMODE=2;
    private int PLAYMODE = 0;//0为顺序，1为单曲循环，2全部。
    private int currentionplayPosition;//播放位置
    private static final String TAG = "AudioPlayer";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PREPARELYRIC:
                    int posit = 0;
                    try {
                        posit = stub.getCurrPosition();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    tv_lyric.setIndex(posit);
                    removeMessages(PREPARELYRIC);
                    sendEmptyMessage(PREPARELYRIC);
                    break;
                case HIDEVIEW:
                    llBtn.setVisibility(View.INVISIBLE);
                    sbPlay.setVisibility(View.INVISIBLE);
                    llAudio.setVisibility(View.INVISIBLE);
                    isHIDED = true;
                    removeMessages(HIDEVIEW);
                    break;
                case AUDIOSEEKTO:
                    try {
                        currentionplayPosition = stub.getCurrPosition();
                        sbPlay.setProgress(currentionplayPosition);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    tvTime.setText(TimeUtil.TimeToString(currentionplayPosition) + "/"
                            + TimeUtil.TimeToString(duration));
                    removeMessages(AUDIOSEEKTO);
                    sendEmptyMessageDelayed(AUDIOSEEKTO, 1000);
                    break;
                case NOTFOUNDLYRIC:
                    tv_lyric.setNotFound(true);
                    break;
            }

            super.handleMessage(msg);
        }
    };
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            stub = IAudioService.Stub.asInterface(service);
            if (stub != null) {
                try {
                    if (!canShow) {
                        Log.e(TAG, "onServiceConnected: " + "onServiceConnected");
                        stub.openAudio(currentPosition);
                    } else {


                        getdata(video);
                        PLAYMODE = congif.getInt("PLAYMODE", 0);
                        stub.setPlayMode(PLAYMODE);
                        handleMode();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (stub != null) {
                try {
                    stub.stop();
                    stub = null;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
            Log.e(TAG, "onServiceDisconnected: " + "onServiceDisconnected");
        }
    };

    private void setNextPre() {
        Log.e(TAG, "setNextPre: "+currentPosition);
        try {
            currentPosition = stub.getPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (currentPosition == maxsize - 1) {
            btnNext.setBackgroundResource(R.drawable.btn_next_gray);
            btnNext.setEnabled(false);
            isCanNext=true;
            try {
                stub.setCanNext(isCanNext);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        if (currentPosition == 0) {
            btnPre.setBackgroundResource(R.drawable.btn_pre_gray);
            btnPre.setEnabled(false);
            isCanPre=true;
        }
    }

    private boolean isCanNext;
    private boolean isCanPre;
    private boolean isHIDED;
    private ArrayList<Lyric> lyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_audio_player);
        ActivityCollector.activityList.add(this);
        findViews();
        // TODO: 2017/1/7  
        canShow = getIntent().getBooleanExtra("canShow", false);
        if (!canShow) {
            currentPosition = getIntent().getIntExtra("position", 0);

        }

        initData();

    }

    private Visualizer mVisualizer;

    private void setupVisualizerFxAndUi() {
        try {
            int audioSessionid = stub.getAudioSessionId();
            System.out.println("audioSessionid==" + audioSessionid);
            mVisualizer = new Visualizer(audioSessionid);
            // 参数内必须是2的位数
            mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            // 设置允许波形表示，并且捕获它
            ivSrc.setVisualizer(mVisualizer);
            mVisualizer.setEnabled(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    //  private PreparedBroad prb;
    private SharedPreferences congif;

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) mVisualizer.release();
    }

    //订阅方法
//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void onEvent(Video video) {
        this.video = video;
        getdata(video);
        setupVisualizerFxAndUi();

    }
//     public void onEventThreadMain(Video video){
//         this.video = video;
//         getdata(video);
//         setupVisualizerFxAndUi();
//     }

    private void initData() {
        //注册eventbus
        EventBus.getDefault().register(this);
        //隐藏视图
        handler.sendEmptyMessageDelayed(HIDEVIEW, 6000);
        //注册广播
        // TODO: 2017/1/5 guangbo
//        prb=new PreparedBroad();
//        IntentFilter intent1 =new IntentFilter();
//        intent1.addAction(AudioService.PREPARED);
//                registerReceiver(prb, intent1);
        //得到音量
        congif = getSharedPreferences("congif", Context.MODE_PRIVATE);
        lastVolum = congif.getInt("audioLevel", 0);
        //得到播放模式

        //开启服务

        Intent intent = new Intent(this, AudioService.class);
        intent.setAction("com.yy.audio");
              bindService(intent, conn, Context.BIND_AUTO_CREATE);
//        startService(intent);

        //设置大小
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        sbVoice.setMax(audioVolume);
        sbVoice.setProgress(lastVolum);

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, lastVolum, 0);
    }

    //注册广播
//    class  PreparedBroad extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            getdata();
//        }
//    }

    private void getdata(Video video) {
        //得到列表的大小
        try {
            maxsize=stub.getListSize();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //准备歌词
        try {

            lyrics = LyricUtil.getLyric(getAssets().open("danche.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(lyrics, new Comparator<Lyric>() {
            @Override
            public int compare(Lyric lhs, Lyric rhs) {
                return lhs.getTime() - rhs.getTime();
            }
        });
        if (lyrics != null && lyrics.size() > 0) {
            tv_lyric.setLyrics(lyrics);
            handler.sendEmptyMessage(PREPARELYRIC);
        } else {
            handler.sendEmptyMessage(NOTFOUNDLYRIC);
        }
        if (video != null) {
            if (video.getAuthor() != null) tvAuthor.setText(video.getAuthor());
            if (video.getName() != null) tvSong.setText(video.getName());
        }else {
            try {
                // from notification come in
                video   =stub.getVideo();
                if (video.getAuthor() != null) tvAuthor.setText(video.getAuthor());
                if (video.getName() != null) tvSong.setText(video.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        try {
            duration = stub.getMaxDuretion();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (duration >= 0) {
            Log.e(TAG, "initData: duration" + duration);
            sbPlay.setMax(duration);
            handler.sendEmptyMessage(AUDIOSEEKTO);
        }
        //判断
        // TODO: 2017/1/8
        setNextPre();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: " + "onSaveInstanceState");
        outState.putParcelable("video", video);
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-01-04 12:18:47 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tv_lyric = (LyricView) findViewById(R.id.tv_lyric);
        rlTop = (RelativeLayout) findViewById(R.id.rl_top);
        llAudio = (LinearLayout) findViewById(R.id.ll_audio);
        btnVoice = (Button) findViewById(R.id.btn_voice);
        sbVoice = (SeekBar) findViewById(R.id.sb_voice);
        btnEverything = (Button) findViewById(R.id.btn_everything);
//        ivSrc = (ImageView)findViewById( iv_src );
        tvSong = (TextView) findViewById(R.id.tv_song);
        tvAuthor = (TextView) findViewById(R.id.tv_author);
        tvTime = (TextView) findViewById(R.id.tv_time);
        sbPlay = (SeekBar) findViewById(R.id.sb_play);
        llBtn = (LinearLayout) findViewById(R.id.ll_btn);
        btnAudioMode = (Button) findViewById(btn_audio_mode);
        btnPre = (Button) findViewById(R.id.btn_pre);
        btnPause = (Button) findViewById(R.id.btn_pause);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnLyrc = (Button) findViewById(R.id.btn_lyrc);
        ivSrc = (BaseVisualizerView) findViewById(iv_src);
//        animationDrawable= (AnimationDrawable) ivSrc.getBackground();
//        animationDrawable.start();
        btnVoice.setOnClickListener(this);
        btnEverything.setOnClickListener(this);
        btnAudioMode.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnLyrc.setOnClickListener(this);
        sbVoice.setOnSeekBarChangeListener(new AudioSeekBarChangeListener());
        sbPlay.setOnSeekBarChangeListener(new AudioSeekBarChangeListener());
        rl_hide = (RelativeLayout) findViewById(R.id.activity_audio_player);
    }

    class AudioSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar == sbVoice) {
                sbVoice.setProgress(progress);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            } else {
                if (fromUser) {
                    seekBar.setProgress(progress);
                    try {
                        stub.seekTo(progress);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDEVIEW);

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDEVIEW, 4000);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isHIDED) {
            Log.e(TAG, "onClick: " + "isHIDED");
            llBtn.setVisibility(View.VISIBLE);
            sbPlay.setVisibility(View.VISIBLE);
            llAudio.setVisibility(View.VISIBLE);
        }
        return super.onTouchEvent(event);
    }

    // TODO: 2017/1/4 onclick
    @Override
    public void onClick(View v) {

        if (v == btnVoice) {
            // Handle clicks for btnVoice
        } else if (v == btnEverything) {
            // Handle clicks for btnEverything
        } else if (v == btnAudioMode) {
            try {
                handleMode();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (v == btnPre) {
            // Handle clicks for btnPre
            handlePre();
        } else if (v == btnPause) {
            handlePause();

        } else if (v == btnNext) {
            // Handle clicks for btnNext
            //得到播放列表位置和列表的大小，进行判断
            handleNext();

        } else if (v == btnLyrc) {
            // Handle clicks for btnLyrc
        }
        handler.removeMessages(HIDEVIEW);
        handler.sendEmptyMessageDelayed(HIDEVIEW, 4000);
    }

    private void handleMode() throws RemoteException {
        try {
            PLAYMODE = stub.getPlayMode();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //如果为零 设置单曲 1 全部 2循环
        switch (PLAYMODE) {
            default:
            case 0:
                PLAYMODE++;
                btnAudioMode.setBackgroundResource(R.drawable.btn_modesingle_selector);
                break;
            case 1:
                PLAYMODE++;
                btnAudioMode.setBackgroundResource(R.drawable.btn_modeall_selector);
                break;
            case 2:
                PLAYMODE = 0;
                btnAudioMode.setBackgroundResource(R.drawable.btn_mode_selector);

                break;

        }
        try {
            stub.setPlayMode(PLAYMODE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private boolean isplaying = false;

    private void handlePause() {
        // Handle clicks for btnPause

        try {
            isplaying = stub.isPlaying();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (isplaying) {
            //如果是播放那就暂停
            handler.removeMessages(PREPARELYRIC);
            try {
                stub.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            btnPause.setBackgroundResource(R.drawable.btn_start_selector);
        } else {            //否则开始
            handler.sendEmptyMessage(PREPARELYRIC);
            try {
                stub.start();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            btnPause.setBackgroundResource(R.drawable.btn_pause_selector);
        }
    }

    private void handlePre() {
        try {
            isCanNext = stub.isCanNext();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            currentPosition = stub.getPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (currentPosition < maxsize || currentPosition >= 1) {
            currentPosition--;
            if (isplaying) {
                btnPause.setBackgroundResource(R.drawable.btn_pause_selector);
            }
            if (currentPosition == 0) {
                btnPre.setBackgroundResource(R.drawable.btn_pre_gray);
                btnPre.setEnabled(false);
            }
            try {
//                stub.setPosition(currentPosition);
                stub.openAudio(currentPosition);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (isCanNext) {
                btnNext.setEnabled(true);
                btnNext.setBackgroundResource(R.drawable.btn_next_selector);
            }
            //if可以波上一个时btnpre.setEnabled(false)那么在反回上一个时，那btnpre要变成可以操作
            isCanPre = true;

        } else if (currentPosition == 0) {
            btnPre.setBackgroundResource(R.drawable.btn_pre_gray);
            btnPre.setEnabled(false);
        }


    }

    private void handleNext() {
        try {
            currentPosition = stub.getPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (currentPosition < maxsize - 1 || currentPosition >= 0) {
            currentPosition++;
            if (currentPosition == maxsize - 1) {
                btnNext.setBackgroundResource(R.drawable.btn_next_gray);
                btnNext.setEnabled(false);
            }
            if (isplaying) {
                btnPause.setBackgroundResource(R.drawable.btn_pause_selector);
            }
            try {
                stub.setPosition(currentPosition);
                stub.openAudio(currentPosition);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (isCanPre) {
                btnPre.setEnabled(true);
                btnPre.setBackgroundResource(R.drawable.btn_next_selector);
            }
            //但是如果是从服务，顺序播放，而不改iscannext是有问题的。
            //if可以波下一个btnNext.setEnabled(false)那么在反回上一个时，那btnnext可以操作
            isCanNext = true;
            if (isCanNext) try {
                stub.setCanNext(isCanNext);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else if (currentPosition == maxsize - 1) {
            btnNext.setBackgroundResource(R.drawable.btn_next_gray);
            btnNext.setEnabled(false);
        }

    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
//        if(prb!=null){
//            unregisterReceiver(prb);
//            prb=null;
//        }
        Log.e(TAG, "onDestroy: ");
        //得到当前音量然后保存
        if (congif != null) {
            SharedPreferences.Editor edit = congif.edit();
            edit.putInt("audioLevel", sbVoice.getProgress());
            edit.putInt("PLAYMODE", PLAYMODE);
            edit.commit();
            congif = null;
        }
        if (conn != null) unbindService(conn);
        EventBus.getDefault().unregister(this);
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }


}
