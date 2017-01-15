package yy.chen.fragmenttest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chenrongfa on 2016/12/26
 */

public class FragmentTwo extends Fragment {
    private static final String TAG = "FragmentTwo";
   public FragmentTwo(){
        Log.e(TAG, "FragmentTwo: " );
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: FragmentTwo" );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.e(TAG, "onCreateView: FragmentTwo" );
        return   inflater.inflate(R.layout.fragment1,null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: two" );
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart:FragmentTwo " );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop:FragmentTwo " );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: FragmentTwo" );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach:FragmentTwo " );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach:FragmentTwo " );

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: FragmentTwo" );
        }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause:FragmentTwo " );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView:FragmentTwo " );
    }

}
