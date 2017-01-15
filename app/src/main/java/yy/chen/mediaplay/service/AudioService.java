package yy.chen.mediaplay.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import yy.chen.mediaplay.IAudioService;
import yy.chen.mediaplay.R;
import yy.chen.mediaplay.activity.AudioPlayer;
import yy.chen.mediaplay.bean.Video;
import yy.chen.mediaplay.util.ActivityCollector;

/**
 * Created by chenrongfa on 2017/1/4
 */

public class AudioService extends Service {
    public static final String PREPARED ="com.yy.prepared" ;
    private int durationd;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");

        return stub;
    }
  private AudioService audioService;
    private int currPosition; //当前列表当前位置
    private int currPlayPosition ;//播放进度
    private  boolean isLoad;
    private ArrayList<Video> video;
    private MediaPlayer media;
    private AudioManager audioManager;
    private int audioVolume;
    private NotificationManager notificationManager;
    private int maxsize;
    private boolean isCanNext;
    private int PLAYMODE=0;//0为顺序，1为单曲循环，2全部。
   public AudioService(){
       Log.e(TAG, "AudioService: "+"AudioService");
   }
    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: " );
        super.onCreate();
//        Vitamio.isInitialized(this);
//        audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);
//        audioVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioVolume/2,0);
        video=new ArrayList<>();
        intiData();
        maxsize=video.size();

        audioService=this;
    }

    private static final String TAG = "AudioService";
    //服务与aidl绑定
    private IAudioService.Stub stub=new IAudioService.Stub() {
        @Override
        public int getAudioSessionId() throws RemoteException {
            return media.getAudioSessionId();
        }

        @Override
        public boolean isCanNext() throws RemoteException {
            return isCanNext;
        }

        @Override
        public void setPosition(int position) throws RemoteException {
            currPosition=position;
        }

        @Override
        public void setCanNext(boolean can) throws RemoteException {
            isCanNext=can;
        }

        @Override
        public Video getVideo() throws RemoteException {
            return video.get(currPosition);
        }

        @Override
        public int getPosition() throws RemoteException {
            return audioService.getPosition();
        }

        @Override
        public int getListSize() throws RemoteException {
            return video.size();
        }

        @Override
       public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                              double aDouble, String aString) throws RemoteException {

       }

        @Override
        public void openAudio(int position) throws RemoteException {
            try {
                audioService.openAudio(position);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
       public void stop() throws RemoteException {
             audioService.stop();
       }

        @Override
        public boolean isPlaying() throws RemoteException {
            return audioService.isPlaying();
        }

        @Override
        public void seekTo(int position) throws RemoteException {
            audioService.seekTo(position);
        }

        @Override
        public int getCurrPosition() throws RemoteException {
            return audioService.getCurrPosition();
        }

        @Override
        public int getMaxDuretion() throws RemoteException {
            return audioService.getMaxDuretion();
        }

        @Override
       public void start() throws RemoteException {
           audioService.start();
       }

       @Override
       public void pause() throws RemoteException {
             audioService.pause();
       }

       @Override
       public void release() throws RemoteException {
              audioService.release();
       }

       @Override
       public void setPlayMode(int playMode) throws RemoteException {
               audioService.setPlayMode(playMode);
       }

       @Override
       public int getPlayMode() throws RemoteException {
           return audioService.getPlayMode();
       }

       @Override
       public void next() throws RemoteException {

       }
   };
    private void stop(){
        if(media!=null)
            media.stop();

    }  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void start(){
            if(media!=null)
                media.start();

        notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        ActivityCollector.manager=notificationManager;
        Intent intent=new Intent(this, AudioPlayer.class);
        intent.putExtra("position",currPosition);
        intent.putExtra("canShow",true);

        PendingIntent pending=PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        notification = new Notification.Builder(this)
                .setContentTitle(video.get(currPosition).getAuthor())
                .setContentText((video.get(currPosition).getName()))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.login_icon))
                .setSmallIcon(R.drawable.notification_music_playing)
                .setShowWhen(true)
                .setContentIntent(pending)

                .build();
        notificationManager.notify(1,notification);

    }

    /**
     * 播放位置
     * @return
     */
    private int getCurrPosition(){

       return  media.getCurrentPosition();
    }

    /**
     * 设置播放位置
     * @param position
     */
    private void seekTo(int position){
        if(media!=null){
            media.seekTo(position);
        }
    }

    /**
     *
     * @return 反回播放最大值
     */
    private int getMaxDuretion(){
        return media.getDuration();
    }
    private void pause(){
        if(media!=null)
            media.pause();

    }
    private void release(){
        if(media!= null){
            media.release();
            media.reset();
            media=null;
        }

    }

    private void setPlayMode( int playMode){
        PLAYMODE=playMode;
    }
    private int getPlayMode (){
        return PLAYMODE;

    }
    private void next(){

    }
    private void pre(){

    }
    private boolean isPlaying(){

       return media.isPlaying();
    }

    /**
     *
     * @return 得到当前列表所在的位置
     */
   private int getPosition(){
       return currPosition;
   }

    /**
     *   初始化音乐
     * @param position
     * @throws IOException
     */
    private void openAudio(int position) throws IOException {
        currPosition=position;
        if(media!= null){
            media.reset();
            media.release();
            media=null;
        }
            media=new MediaPlayer();
        ActivityCollector.mediaPlayer=media;
        if(video!=null&&video.size()>0){
            Log.e(TAG, "openAudio: "+video.get(currPosition).toString() );
            Log.e(TAG, "openAudio: "+currPosition);
            String data = video.get(currPosition).getData();
            if(data!=null) {
                media.setDataSource(data);
                media.setOnPreparedListener(new PrepareListener());
                media.setOnErrorListener(new AudioErrorListener());
                media.setOnCompletionListener(new AudioCompletionListener());
                media.prepareAsync();
//                media.setAudioAmplify(10);
            }
        }
    }
    class AudioCompletionListener implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.e(TAG, "onPrepared: "+"onCompletion");

            Toast.makeText(audioService,"wanbi",Toast.LENGTH_SHORT).show();
            switch (PLAYMODE){
                case 0:
                    handleMode();
                    break;
                case 1:
                    try {
                        audioService.openAudio(currPosition);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        //乱序
                        audioService.openAudio((int)Math.random()*maxsize);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void handleMode() {
        if(currPosition<video.size()-1){
            currPosition++;
            isCanNext=true;
            try {
                audioService.openAudio(currPosition);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(currPosition==video.size()-1){
                Toast.makeText(audioService,"播放完毕",Toast.LENGTH_SHORT).show();
        }
    }

    class AudioErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Toast.makeText(audioService,"出错",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    class PrepareListener implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.e(TAG, "onPrepared: "+"onPrepared");
              audioService.start();
            //发送广播我准备好了
//            sendMessage(PREPARED);
            //发布
            EventBus.getDefault().post(video.get(currPosition));

        }
    }

    private void sendMessage(String prepared) {
        Intent intent=new Intent();
        intent.setAction(PREPARED);
        sendBroadcast(intent);
    }

    public void intiData() {
        //得到视频内容1.以后缀名从sd卡读取2.内容提供者
        if (!isLoad) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String state = Environment.getExternalStorageState();
                    Log.e("erro", "run");
                    // 得到uri
                    ContentResolver resolver = getContentResolver();
                    Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    //名称
                    String name = MediaStore.Audio.Media.DISPLAY_NAME;
                    String time = MediaStore.Audio.Media.DURATION;//时间
//                String descript= MediaStore.Video.Media.DESCRIPTION;//miaoshu
                    String size = MediaStore.Audio.Media.SIZE;//miaoshu
                    String data = MediaStore.Audio.Media.DATA; //地址
                    String author = MediaStore.Audio.Media.ARTIST;
//                   String noMusic = MediaStore.Video.Media;
                    String[] sel = {name, time, size, data, author};
                    //得到游标
                    Cursor query = resolver.query(uri, sel, null, null, null);
                    if (query != null) {
                        while (query.moveToNext()) {
                            name = query.getString(0);
                            time = query.getString(1);//遍历游标
//                        descript=query.getString(2);
                            size = query.getString(2);
                            data = query.getString(3);
                            author = query.getString(4);
                            Log.e("erro", time + "time");
                            //存到集合
                            video.add(new Video(name, time, size, data, author));
                        }

                    }
                    query.close();

                }
            }).start();
                isLoad=true;
        }
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.e(TAG, "unbindService: "+"unbindService");
        notificationManager.cancel(1);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: ");
        return super.onUnbind(intent);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e(TAG, "onStart: ");
        super.onStart(intent, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind: " );
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: " );
        super.onDestroy();
    }
}
