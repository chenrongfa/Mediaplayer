package yy.chen.autocomplemtext;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class MainActivity extends Activity {
private AutoCompleteTextView actv_test;
    private MultiAutoCompleteTextView mctv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actv_test= (AutoCompleteTextView) findViewById(R.id.actv_test);
        mctv_test= (MultiAutoCompleteTextView) findViewById(R.id.mctv_test);
        final String[] data={"小猪猪","小妹妹","大妹妹","kk","77","tt"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.item,
                data);
        actv_test.setAdapter(adapter);
        mctv_test.setAdapter(adapter);
        actv_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView view1 = (TextView) view;
                Log.e("erro",view1.getText().toString());
            }
        });
    }
}
