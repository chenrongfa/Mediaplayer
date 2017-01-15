package yy.chen.mediaplay.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.activity.MainActivity;
import yy.chen.mediaplay.activity.MyPlay;
import yy.chen.mediaplay.adapter.VideoAdapter;
import yy.chen.mediaplay.bean.Video;

/**
 * Created by chenrongfa on 2016/12/26
 */

public class FragmentOne extends Fragment {
    private static final int LOADDATA = 1;
    private static final String TAG = "FragmentOne";
    private ListView lv_video;
    private TextView tv_found;
    private ProgressBar pb_progress;
    private ArrayList<Video> video = new ArrayList<Video>();
    public static final int NOTDATA = 0;
    private boolean isLoad;
    private MainActivity context;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOTDATA:
                    Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
                    tv_found.setVisibility(View.VISIBLE);
                    pb_progress.setVisibility(View.GONE);
                    break;
                case LOADDATA:
                    lv_video.setAdapter(new VideoAdapter(video, context));
                    pb_progress.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public FragmentOne() {
        Log.e("erro", "Fragmentone" + Thread.currentThread().getName());

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);       //得到上下
        Log.e("erro", "context" + context);
        this.context = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: FragmentOne" );
        View v = inflater.inflate(R.layout.test, null);
        lv_video = (ListView) v.findViewById(R.id.lv_video);
        tv_found = (TextView) v.findViewById(R.id.tv_found);
        pb_progress = (ProgressBar) v.findViewById(R.id.pb_progress);
        //设置监听
        lv_video.setOnItemClickListener(new setOnclickListener());
        intiData();

        return v;
    }

    class setOnclickListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //开发自己系统播放器
            Intent intent = new Intent(context, MyPlay.class);
            intent.setDataAndType(Uri.parse(video.get(position).getData()), "video/*");
            Bundle bundle = new Bundle();
            //且带当前数据和位置 ..全部
            bundle.putParcelableArrayList("data", video);
            bundle.putParcelable("currentdata", video.get(position));
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
    public void getDataFromProvider(){
        if(!isLoad){
            new Thread(new Runnable() {


                @Override
                public void run() {
                    String state = Environment.getExternalStorageState();
                    Log.e("erro", "run");
                    // 得到uri
                    ContentResolver resolver = context.getContentResolver();
                    Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    //名称
                    String name = MediaStore.Video.Media.DISPLAY_NAME;
                    String time = MediaStore.Video.Media.DURATION;//时间
//                String descript= MediaStore.Video.Media.DESCRIPTION;//miaoshu
                    String size = MediaStore.Video.Media.SIZE;//miaoshu
                    String data = MediaStore.Video.Media.DATA; //地址
                    String author=MediaStore.Video.Media.ARTIST;
//                   String noMusic = MediaStore.Video.Media;
                    String[] sel = {name, time, size, data,author};
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
                            video.add(new Video(name, time, size, data,author));
                        }

                    }
                    query.close();
                    if (video.size() == 0) {
                        handler.sendEmptyMessage(NOTDATA);
                    } else {
                        handler.sendEmptyMessage(LOADDATA);


                    }
                }
            }).start();
            isLoad=true;
        }else {
            handler.sendEmptyMessage(LOADDATA);
        }
    }


    public void intiData() {
        //得到视频内容1.以后缀名从sd卡读取

            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = Environment.getExternalStorageDirectory().getAbsoluteFile();
                    getFile(file);
                    if (video.size() == 0) {
                        handler.sendEmptyMessage(NOTDATA);
                    } else {
                        handler.sendEmptyMessage(LOADDATA);
                    }
                }

            }).start();
            isLoad = true;
        }
    //2.内容提供者


    private void getFile(File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                int i = pathname.getName().indexOf(".") + 1;
                String filename = pathname.getName();
                if (i != -1) {
                    String substring = pathname.getName().substring(i);
                    if ("mp3".equalsIgnoreCase(substring) || "mp4".equalsIgnoreCase(substring) || "avi".equalsIgnoreCase(substring) || "rmvb".equalsIgnoreCase(substring)) {
                        Video video2 = new Video();
                        video2.setData(pathname.getAbsolutePath());
                        video2.setName(filename);
                        video2.setSize(pathname.length()+"");
                        video2.setTime("100");
                        video.add(video2);
                        return true;
                    } else if (pathname.isDirectory()) {
                        getFile(pathname);
                    }
                }
                return false;
            }
        });

    }
}
