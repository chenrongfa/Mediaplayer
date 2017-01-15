package yy.chen.test;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {
    private AlarmManager al;
private int k=0;
    private ListView lv_simple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("erro", "onCreate");
        JavaContrloller.add(this);
        al= (AlarmManager) getSystemService(ALARM_SERVICE);
        lv_simple= (ListView) findViewById(R.id.lv_simple);
        List<Map<String,String>> list=new ArrayList<>();
        String []strings = {"tv_item"};

        for(int i=0;i<20;i++){
            HashMap<String,String> hashMap=new HashMap<>();
           hashMap.put("tv_item","h"+i);
            hashMap.put("tv_item1","22"+i);
            list.add(hashMap);
        }

        lv_simple.setAdapter(new SimpleAdapter(this,
               list,R.layout.item,new String[]{"tv_item0","tv_item1"},
               new int[]{R.id.tv_item,R.id.tv_item1} ));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("kk", "66");
        Log.e("erro", "hhhh");

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e("erro", "onRestoreInstanceState");
        if (savedInstanceState.getString("kk") != null)
            Log.e("erro", "onRestoreInstanceState" + savedInstanceState.getInt("kk"));

        super.onRestoreInstanceState(savedInstanceState);
    }

    private static final String TAG = "MainActivity";

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Intent intent=new Intent();
////        intent.setDataAndType(Uri.parse("http://vfx.mtime" +
////                ".cn/Video/2016/12/27/mp4/161227204134063682_480.mp4"),"video/*");
////        intent.setDataAndType(Uri.parse("http://192.168.2.100:8080//t6.avi"),
////                "video/*");
//       intent.setAction("yy.chen.test.HELLO");
//
//          /*intent.setAction(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("smsto:10086"));
//        intent.putExtra("sms_body","hello");*/
//
////        startActivity(intent);
//            if(intent.resolveActivity(getPackageManager())!=null) {
////              Intent chooge=  Intent.createChooser(intent,"hello");
////                startActivity(chooge);
//                startActivityForResult(intent,5);
//            }
//        return super.onTouchEvent(event);
//    }

    private void test(int i) {
        if (i == 8) {
            Log.e(TAG, "test: " + i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==4){
            Toast.makeText(this, data.getStringExtra("hello"), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View v) {

    }
}
