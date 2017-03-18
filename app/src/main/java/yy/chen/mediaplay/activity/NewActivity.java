package yy.chen.mediaplay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import yy.chen.mediaplay.R;


public class NewActivity extends Activity {

private WebView webView;
      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new);
            initWebView();
      }

      private void initWebView() {
            Intent intent = getIntent();
            if(intent!= null){
                  String url = intent.getStringExtra("url");
                  webView= (WebView) findViewById(R.id.wv_new);
                  webView.loadUrl(url);
                  WebSettings settings = webView.getSettings();
                  settings.setJavaScriptEnabled(true);
                  settings.setUseWideViewPort(true);
//                  settings.setSupportZoom(true);
//                  settings.setLoadWithOverviewMode(true);

//                  settings.setMediaPlaybackRequiresUserGesture(true);
                  settings.setSupportZoom(true);
                  settings.setBuiltInZoomControls(true);
                  settings.setDisplayZoomControls(false);
                  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                  settings.setAppCachePath(getFilesDir().getPath());
                  settings.setDatabaseEnabled(true);
                  settings.setAppCacheEnabled(true);
                  webView.setWebChromeClient(new WebChromeClient());
            }
      }
}
