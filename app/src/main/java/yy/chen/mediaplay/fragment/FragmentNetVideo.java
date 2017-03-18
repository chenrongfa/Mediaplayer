package yy.chen.mediaplay.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.activity.MainActivity;
import yy.chen.mediaplay.activity.MyPlay;
import yy.chen.mediaplay.adapter.NetVideoAdapter;
import yy.chen.mediaplay.bean.NetVideo;
import yy.chen.mediaplay.util.CacheUtil;
import yy.chen.mediaplay.util.Constant;
import yy.chen.mediaplay.view.XListView;

import static yy.chen.mediaplay.R.layout.netvideo;

/**
 * Created by chenrongfa on 2016/12/26
 */

public class FragmentNetVideo extends Fragment {
    private static final String TAG = "FragmentNetVideo";
    @ViewInject(R.id.lv_netvideo)
    private XListView lv_netvideo;
    @ViewInject(R.id.tv_found)
    private TextView tv_found;
    @ViewInject(R.id.pb_progress)
    private ProgressBar pb_progress;
    private MainActivity context;
    private ArrayList <NetVideo> data;
    private  RequestParams entiry;
    public FragmentNetVideo() {
        Log.e("erro","Fragmentthree");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context= (MainActivity) activity;
    }
    private boolean isloaded;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//      View inflate=View.inflate(context,R.layout.netvideo,null);
        View inflate = inflater.inflate(netvideo,null);
        lv_netvideo= (XListView) inflate.findViewById(R.id.lv_netvideo);
        lv_netvideo.setPullLoadEnable(true);
        lv_netvideo.setXListViewListener(new XlistViewListener());
        x.view().inject(this,inflate);

        //加载布局
       x.view().inject(this,inflate);
        data=new ArrayList<>();
        entiry =new RequestParams(Constant.Url);
        getData(entiry);

        lv_netvideo.setOnItemClickListener(new setOnclickListener());
        return inflate;
    }
    //oncreate
    private void getData(RequestParams entiry) {
        x.http().post(entiry, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "onSuccess: "+"onSuccess"+result);
                if(result!=null) {
                    CacheUtil.putStrig(context, "saveData1", result);
                    showData(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onSuccess: "+"onError"+ex.toString() );
                String result=CacheUtil.getString("saveData1");
                if(!TextUtils.isEmpty(result)) {

                    showData(result);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG, "onSuccess: "+"CancelledException" );
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onSuccess: "+"onFinished" );
            }
        });
    }
    private boolean isloadMore;

    private void onLoad() {
        lv_netvideo.stopRefresh();
        lv_netvideo.stopLoadMore();
        lv_netvideo.setRefreshTime("刚刚");
    }
    ArrayList<NetVideo>  netvideos=new ArrayList<>();
    class XlistViewListener implements  XListView.IXListViewListener{

        @Override
        public void onRefresh() {
           //删除数据从新联网
//            if(data!=null)
//            data.clear();
            getData(entiry);
            NetVideoAdapter netVideoAdapter = new NetVideoAdapter(data, context);
            lv_netvideo.setAdapter(netVideoAdapter);
            netVideoAdapter.notifyDataSetChanged();
            onLoad();

        }

        @Override
        public void onLoadMore() {

            x.http().post(entiry, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e(TAG, "onSuccess: "+Thread.currentThread().getName() );
                    isloadMore=true;
                    Log.e(TAG, "onSuccess: "+"onSuccess"+result);
                    showData(result);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e(TAG, "onSuccess: "+Thread.currentThread().getName() );
                    Log.e(TAG, "onSuccess: "+"onError"+ex.toString() );

                    isloadMore=false;
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.e(TAG, "onSuccess: "+Thread.currentThread().getName() );
                    Log.e(TAG, "onSuccess: "+"CancelledException" );
                    isloadMore=false;
                }

                @Override
                public void onFinished() {
                    Log.e(TAG, "onSuccess: "+Thread.currentThread().getName() );
                    isloadMore=false;
                    Log.e(TAG, "onSuccess: "+"onFinished" );
                }
            });
            data.addAll(netvideos);
            for(NetVideo s:data){
            Log.e(TAG, "onLoadMore: " +s);}
            NetVideoAdapter netVideoAdapter=new NetVideoAdapter(data,context);
//            lv_netvideo.setAdapter(netVideoAdapter);
            netVideoAdapter.notifyDataSetChanged();
            onLoad();
            isloadMore=false;
        }
    }

    private void showData(String result) {
//        if(result!=null){
            netvideos= parseSring(result);
            if(data.size()!=0||data!=null){
                tv_found.setVisibility(View.GONE);
                data=netvideos;
                NetVideoAdapter netVideoAdapter = new NetVideoAdapter(data, context);
                lv_netvideo.setAdapter(netVideoAdapter);
                Log.e(TAG, "onSuccess: "+"data.size()!=0" );
                pb_progress.setVisibility(View.GONE);
                if(!isloaded) {
                    Log.e(TAG, "onSuccess: " + "!isloaded");
                    for(int num=0;num<data.size();num++){
                        Log.e(TAG, "onSuccess: "+data.get(num).toString() );
                    }
                    isloaded=true;
                }
            }else{
//                pb_progress.setVisibility(View.VISIBLE);
                tv_found.setVisibility(View.VISIBLE);

            }
      /*  }
    else{
//            pb_progress.setVisibility(View.VISIBLE);
            tv_found.setVisibility(View.VISIBLE);
            pb_progress.setVisibility(View.GONE);

        }*/
    }

    class  setOnclickListener implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          Toast.makeText(context,video.get(position).toString(),Toast.LENGTH_SHORT).show();
            //开启播放器  选择一个适合的播放器
//          Intent intent=new Intent();
//          intent.setDataAndType(Uri.parse(video.get(position).getData()),"video/*");
//          startActivity(intent);
            //开发自己播放器
            Intent intent=new Intent(context,MyPlay.class);
            intent.setDataAndType(Uri.parse(data.get(position).getHightUrl()),"video/*");
            Bundle bundle=new Bundle();
            intent.setAction("my.com.action");
            //且带当前数据和位置 ..全部
            bundle.putParcelableArrayList("data",data);
//            bundle.putSerializable();
            bundle.putParcelable("currentdata",data.get(position));
            bundle.putInt("position",position-1);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
    //解析json
    private ArrayList<NetVideo> parseSring(String result) {
        ArrayList<NetVideo> netVideos=new ArrayList<>();
        //用系统的
        JSONObject js= null;
        try {
            js = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = js.optJSONArray("trailers");
        if(jsonArray!=null){
           int length= jsonArray.length();
            if(isloadMore){
                isloadMore=true;
                //模拟下拉缓存刷新
                for(int num=length/2;num<length;num++){
                    JSONObject opt = (JSONObject) jsonArray.opt(num);

                    try {
                        String moveName=opt.getString("movieName");
                        String videoTitle=opt.getString("videoTitle");
                        String hightUrl=opt.getString("hightUrl");
                        String coverImg=opt.getString("coverImg");
                        netVideos.add(new NetVideo(moveName,videoTitle, hightUrl, coverImg));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }else {
                for (int num = 0; num < length / 2; num++) {
                    JSONObject opt = (JSONObject) jsonArray.opt(num);

                    try {
                        String moveName = opt.getString("movieName");
                        String videoTitle = opt.getString("videoTitle");
                        String hightUrl = opt.getString("hightUrl");
                        String coverImg = opt.getString("coverImg");
                        netVideos.add(new NetVideo(moveName,videoTitle,  hightUrl, coverImg));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        //gson
//        Gson gson=new Gson();
//        netVideos = gson.fromJson(result, new TypeToken<ArrayList<trailers>>()
//        {}.getType());
        return netVideos;
    }
}
