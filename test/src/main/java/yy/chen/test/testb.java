package yy.chen.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class testb extends Activity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testb);
        String name=JavaContrloller.activities.get(0).getClass().getSimpleName();
        Toast.makeText(this, "activity_testb", Toast.LENGTH_SHORT).show();
        Log.e("erro",name);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("hello","nihao");

        setResult(4,intent);

        return super.onTouchEvent(event);
    }

}
