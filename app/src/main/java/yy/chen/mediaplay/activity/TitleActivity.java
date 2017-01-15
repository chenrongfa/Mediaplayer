package yy.chen.mediaplay.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.adapter.NetSearchAdapter;
import yy.chen.mediaplay.bean.SearchTitle;
import yy.chen.mediaplay.util.ActivityCollector;
import yy.chen.mediaplay.util.Constant;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static yy.chen.mediaplay.R.id.tv_audiosearch;

/**
 * Created by chenrongfa on 2017/1/9
 */
public class TitleActivity extends Activity implements View.OnClickListener {

    private EditText tvAudiosearch;
    private ImageView ivAudiosearch;
    private Button btnSureSearch;
    private ListView lvTitlelv;
    private ProgressBar pbAudiopb;
    private TextView tvSearchdata;
    private SearchTitle search;
    private List<SearchTitle.ItemsBean> trailer;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-01-09 10:38:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvAudiosearch = (EditText)findViewById( tv_audiosearch );
        ivAudiosearch = (ImageView)findViewById( R.id.iv_audiosearch );
        btnSureSearch = (Button)findViewById( R.id.btn_sure_search );
        lvTitlelv = (ListView)findViewById( R.id.lv_titlelv );
        pbAudiopb = (ProgressBar)findViewById( R.id.pb_audiopb );
        tvSearchdata = (TextView)findViewById( R.id.tv_searchdata );

        btnSureSearch.setOnClickListener( this );
        ivAudiosearch.setOnClickListener(this);
        tvAudiosearch.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-01-09 10:38:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnSureSearch ) {
            // Handle clicks for btnSureSearch

            String result = tvAudiosearch.getText().toString();
//            String result ="hello";
            if(result!=null&&result.length()!=0){
                Toast.makeText(this, "btnSureSearch", Toast.LENGTH_SHORT).show();
                  getDat(result);
           }else{
                showTip("请输入内容");
            }

        }else if(v==ivAudiosearch){
            Toast.makeText(this, "ivAudiosearch", Toast.LENGTH_SHORT).show();

        }else if(v==tvAudiosearch){
            Toast.makeText(this, "tvAudiosearch", Toast.LENGTH_SHORT).show();
            tvAudiosearch.requestFocus();
        }
    }

    private void getDat(String result) {
        //解码
        try {
            String  res= URLDecoder.decode(result,"UTF-8");
            
            getHttpData(res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getHttpData(String res) {
        pbAudiopb.setVisibility(View.VISIBLE);
        RequestParams param=new RequestParams(Constant.SEARCH_URL+res);
        Callback.Cancelable post = x.http().get(param, new Callback
                .CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "onSuccess: " +1111 +result);
                search= new Gson().fromJson(result, SearchTitle.class);
                trailer =  search.getItems();
                if(trailer!=null&&trailer.size()>0){
                    lvTitlelv.setAdapter(new NetSearchAdapter(trailer,TitleActivity.this));
                }else{
                    //显示文本
                    tvSearchdata.setVisibility(View.VISIBLE);
                }
            }



            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                pbAudiopb.setVisibility(View.GONE);
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onCancelled(CancelledException cex) {
                pbAudiopb.setVisibility(View.GONE);
            }

            @Override
            public void onFinished() {
                pbAudiopb.setVisibility(View.GONE);
            }
        });
    }

    //提示
    private void showTip(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_audio);
        ActivityCollector.activityList.add(this);
        findViews();
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.activityList.remove(this);

        super.onDestroy();
    }
}
