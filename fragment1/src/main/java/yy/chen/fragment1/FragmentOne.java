package yy.chen.fragment1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by chenrongfa on 2016/12/26
 */

public class FragmentOne extends Fragment {
   public  FragmentOne(){
       Log.e("erro","FragmentOne");

   }

    @Override
    public void onInflate(Activity context, AttributeSet attrs, Bundle
            savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.e("erro","onInflate");
    }

    @Override
    public void onAttach(Activity context) {

        Log.e("erro","onAttach");
        super.onAttach(context);
    }

    private EditText tv;
    public   interface CallBack{
        public void getMessage(String msg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        Log.e("erro","onCreateView");
        View v= inflater.inflate(R.layout.fragment,null);
        tv = (EditText) getActivity().findViewById(R.id.tv_test);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("erro","onActivityCreated");

    }

    public void getData(CallBack callBack){
        Log.e("erro",tv.toString()+"1");
        String s = tv.getText().toString();
        if (s==null)
            s="ll";
        callBack.getMessage(s);
    }
}
