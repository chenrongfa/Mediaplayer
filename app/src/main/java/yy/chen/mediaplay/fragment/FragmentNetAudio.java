package yy.chen.mediaplay.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.activity.MainActivity;
import yy.chen.mediaplay.adapter.NetMessage;
import yy.chen.mediaplay.bean.BaiSiBudeQiJie;
import yy.chen.mediaplay.util.Constant;
/**
 * Created by chenrongfa on 2016/12/26
 */

public class FragmentNetAudio extends Fragment implements View.OnClickListener {
    public FragmentNetAudio() {
        Log.e("erro","FragmentFour");
    }
    @ViewInject(R.id.lv_netmessage)
    private ListView lv_netmessage;
    @ViewInject(R.id.tv_mesfound)
    private TextView tv_mesfound;
    @ViewInject(R.id.pb_netPb)
    private ProgressBar pb_netPb;
    private MainActivity context;
    private BaiSiBudeQiJie baisi;
    private List<BaiSiBudeQiJie.ListBean> listBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.netmessage, null);
        x.view().inject(this,view);
        //获取网络数据
        getData();
        return view;
    }

    private void getData() {
        pb_netPb.setVisibility(View.VISIBLE);
        RequestParams parm=new RequestParams(Constant.ALL_RES_URL);
        x.http().get(parm,new Callback.CommonCallback<String>(){

            @Override
            public void onSuccess(String result) {
                pb_netPb.setVisibility(View.GONE);
                baisi=new Gson().fromJson( result,BaiSiBudeQiJie.class);
                listBean=baisi.getList();
                //逻辑判断
                isHaveData(listBean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                pb_netPb.setVisibility(View.GONE);
                tv_mesfound.setText(ex.toString());
                tv_mesfound.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                pb_netPb.setVisibility(View.GONE);
            }

            @Override
            public void onFinished() {
                pb_netPb.setVisibility(View.GONE);
            }
        });

    }

    private void isHaveData(List<BaiSiBudeQiJie.ListBean> listBean) {
      if(listBean!=null &&listBean.size()>0){
          lv_netmessage.setAdapter(new NetMessage(listBean,context));
      }else{
          tv_mesfound.setText("没有数据");
          tv_mesfound.setVisibility(View.VISIBLE);
      }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context= (MainActivity) activity;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "你好", Toast.LENGTH_SHORT).show();
        pb_netPb.setVisibility(View.VISIBLE);
    }
}
