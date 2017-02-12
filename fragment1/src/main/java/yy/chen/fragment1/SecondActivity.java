package yy.chen.fragment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by chenrongfa on 2017/1/12
 */
public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    private Button button;
    List<String> hll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

//        button= (Button) findViewById(R.id.but12);
        Log.e(TAG, "onCreate: " );
    }
    public void click(View v){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }
}
