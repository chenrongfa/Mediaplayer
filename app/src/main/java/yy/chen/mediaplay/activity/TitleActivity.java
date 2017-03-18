package yy.chen.mediaplay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.adapter.NetSearchAdapter;
import yy.chen.mediaplay.bean.SearchTitle;
import yy.chen.mediaplay.util.ActivityCollector;
import yy.chen.mediaplay.util.Constant;
import yy.chen.mediaplay.util.JsonParser;

import static yy.chen.mediaplay.R.id.tv_audiosearch;

/**
 * Created by chenrongfa on 2017/1/9
 */
public class TitleActivity extends Activity implements View.OnClickListener {
      private static final String TAG = "TitleActivity";
      //语音合成文字对象
      com.iflytek.cloud.SpeechRecognizer mIat;
      //文字合成语音对象
      SpeechSynthesizer mTts;
      //1.创建RecognizerDialog对象
      RecognizerDialog mDialog;
      private EditText tvAudiosearch;
      private ImageView ivAudiosearch;
      private Button btnSureSearch;
      private ListView lvTitlelv;
      private ProgressBar pbAudiopb;
      private TextView tvSearchdata;
      private SearchTitle search;
      private List<SearchTitle.ItemsBean> trailer;
      private Button button1;
      private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
      private InitListener mInitListener = new InitListener() {
            @Override
            public void onInit(int i) {
                  Log.e(TAG, "onInit: 初始化" + i);
                  if (ErrorCode.SUCCESS != i) {
                        Toast.makeText(TitleActivity.this, "初始化失败" + i, Toast.LENGTH_SHORT).show();
                  }
            }
      };
      //2.设置accent、 language等参数
      private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                  Log.e(TAG, "onResult: recognizerResult123");
                  printResult(recognizerResult);
//                  et_speech.setText("");
//                  et_speech.setText(recognizerResult.getResultString());
            }

            @Override
            public void onError(SpeechError speechError) {
                  Toast.makeText(TitleActivity.this, "onError", Toast.LENGTH_SHORT).show();

            }
      };
      private RecognizerListener mRecoListener = new RecognizerListener() {

            @Override
            public void onVolumeChanged(int i, byte[] bytes) {
                  Toast.makeText(TitleActivity.this, "onVolumeChanged" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBeginOfSpeech() {
                  Toast.makeText(TitleActivity.this, "onBeginOfSpeech", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onEndOfSpeech() {
                  Toast.makeText(TitleActivity.this, "onEndOfSpeech", Toast.LENGTH_SHORT).show();
                  Log.e(TAG, "onEndOfSpeech: ");
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                  Toast.makeText(TitleActivity.this, "onResult" + recognizerResult.getResultString(), Toast.LENGTH_SHORT).show();
                  printResult(recognizerResult);
                  Log.e(TAG, "onResult: recognizerResult");
            }

            @Override
            public void onError(SpeechError speechError) {
                  Toast.makeText(TitleActivity.this, "onError", Toast.LENGTH_SHORT).show();

            }

            //扩展用接口
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            }
      };

      /**
       * Find the Views in the layout<br />
       * <br />
       * Auto-created on 2017-01-09 10:38:34 by Android Layout Finder
       * (http://www.buzzingandroid.com/tools/android-layout-finder)
       */
      private void findViews() {
            tvAudiosearch = (EditText) findViewById(tv_audiosearch);
            ivAudiosearch = (ImageView) findViewById(R.id.iv_audiosearch);
            btnSureSearch = (Button) findViewById(R.id.btn_sure_search);
            lvTitlelv = (ListView) findViewById(R.id.lv_titlelv);
            pbAudiopb = (ProgressBar) findViewById(R.id.pb_audiopb);
            tvSearchdata = (TextView) findViewById(R.id.tv_searchdata);
            btnSureSearch.setOnClickListener(this);
            ivAudiosearch.setOnClickListener(this);
            tvAudiosearch.setOnClickListener(this);
      }


      @Override
      public void onClick(View v) {
            if (v == btnSureSearch) {
                  // Handle clicks for btnSureSearch
                  String result = tvAudiosearch.getText().toString().trim();
//            String result ="hello";
                  if (!TextUtils.isEmpty(result)) {
                        Toast.makeText(this, "btnSureSearch", Toast.LENGTH_SHORT).show();
                        getDat(result);
                        tvAudiosearch.setText("");
                  } else {
                        showTip("请输入内容");
                  }

            } else if (v == ivAudiosearch) {
                 ;
                  Toast.makeText(this, "kaishi", Toast.LENGTH_SHORT).show();

                  //4.显示dialog，接收语音输入
                  mIat.startListening(mRecoListener);
                  mDialog.show();

            } else if (v == tvAudiosearch) {
                  Toast.makeText(this, "tvAudiosearch", Toast.LENGTH_SHORT).show();
                  tvAudiosearch.requestFocus();
            }
      }

      private void getDat(String result) {
            //解码
            try {
                  String res = URLDecoder.decode(result, "UTF-8");
                  getHttpData(res);
            } catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
            }
      }

      private void getHttpData(String res) {
            pbAudiopb.setVisibility(View.VISIBLE);
            RequestParams param = new RequestParams(Constant.SEARCH_URL + res);
             x.http().get(param, new Callback.CommonCallback<String>() {
                  @Override
                  public void onSuccess(String result) {
                        Log.e(TAG, "onSuccess: " + 1111 + result);
                        search = new Gson().fromJson(result, SearchTitle.class);
                        trailer = search.getItems();
                        if (trailer != null && trailer.size() > 0) {
                              lvTitlelv.setAdapter(new NetSearchAdapter(trailer, TitleActivity.this));
                              tvSearchdata.setVisibility(View.GONE);
                        } else {
                              //显示文本
                              tvSearchdata.setVisibility(View.VISIBLE);
                        }
                  }


                  @Override
                  public void onError(Throwable ex, boolean isOnCallback) {
                        pbAudiopb.setVisibility(View.GONE);
                        Log.e(TAG, "onError: ");
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
            setParam();
            setEvent();
      }

      /**
       *  设置时间的方法区
       */
      private void setEvent() {
            lvTitlelv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(trailer!=null){
                              Intent intent = new Intent(TitleActivity.this, NewActivity.class);
                              intent.putExtra("url",trailer.get(position).getDetailUrl());
                              startActivity(intent);
                        }
                  }
            });
      }

      @Override
      protected void onDestroy() {
            ActivityCollector.activityList.remove(this);

            super.onDestroy();
      }

      private void setParam() {

            mIat = com.iflytek.cloud.SpeechRecognizer.createRecognizer(this, null);

            mIat.setParameter(SpeechConstant.DOMAIN, "iat");
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
//3.开始听写
//            mIat.startListening(mRecoListener);
//听写监听器

//若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
//结果
// mDialog.setParameter("asr_sch", "1");
// mDialog.setParameter("nlp_version", "2.0");
//3.设置回调接口
            mDialog = new RecognizerDialog(this, mInitListener);
            mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
            mDialog.setListener(mRecognizerDialogListener);


            mTts = SpeechSynthesizer.createSynthesizer(this, null);
//2.合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
//设置发音人（更多在线发音人，用户可参见 附录13.2
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
            mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
            mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
//设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
//保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
//仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
            mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");


      }

      private void printResult(RecognizerResult results) {
            String text = JsonParser.parseIatResult(results.getResultString());
            String sn = "";
            // 读取json结果中的sn字段
            try {
                  JSONObject resultJson = new JSONObject(results.getResultString());
                  sn = resultJson.optString("sn");
            } catch (JSONException e) {
                  e.printStackTrace();
            }
            mIatResults.put(sn, text);
//
            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                  resultBuffer.append(mIatResults.get(key));
            }
            String s = new String(resultBuffer);
            s.endsWith("");
//           et_speech.setText(new String(resultBuffer).replaceAll("\\W",""));
            tvAudiosearch.setText(resultBuffer);
            tvAudiosearch.setSelection(resultBuffer.length());
//            et_speech.setSelection(resultBuffer.length());
//            if(mIat.isListening()){
//                  mIat.cancel();
//            }
      }
}
