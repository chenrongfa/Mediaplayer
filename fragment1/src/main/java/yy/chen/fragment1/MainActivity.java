package yy.chen.fragment1;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Button btn1;
    private Button btn2;
    private FragmentOne fragmentOne;
    BlankFragment blankFragment;
    private Toolbar tool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("erro","mainonCreate");
        setContentView(R.layout.activity_main);
        tool= (Toolbar) findViewById(R.id.tb_show);
        setSupportActionBar(tool);
        btn1= (Button) findViewById(R.id.button4);
        btn2= (Button) findViewById(R.id.button5);
        fragmentOne=new FragmentOne();
        blankFragment = new BlankFragment();
        if(savedInstanceState!=null){
            Toast.makeText(this, ""+savedInstanceState.getString("hello"), Toast
                    .LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "还没有执行 onsave", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("hello","key");
        Log.e(TAG, "onSaveInstanceState: " );
        super.onSaveInstanceState(outState);
    }

    private static final String TAG = "MainActivity";
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        Log.e(TAG, "onOptionsMenuClosed: " );
        super.onOptionsMenuClosed(menu);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.e(TAG, "onCreateOptionsMenu: " );
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menuitem,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.e(TAG, "onOptionsItemSelected: " );
//        switch (item.getItemId()){
//            case R.id.it_test:
//                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(this,SecondActivity.class);
//                startActivity(intent);
//                return true;
////                break;
//            case R.id.it_test1:
//                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;case R.id.it_inner:
//                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;case R.id.it_inner1:
//                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;case R.id.it_gp1:
//                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;case R.id.it_gp:
//                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public  void click(View  v){
            fragmentManager = getFragmentManager();
            fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {


                @Override
                public void onBackStackChanged() {
                    int num=fragmentManager.getBackStackEntryCount();

                    FragmentManager.BackStackEntry backStackEntryAt = fragmentManager
                            .getBackStackEntryAt(0);
                    String name = backStackEntryAt.getName();
                    Toast.makeText(MainActivity.this,name+"num"+num, Toast.LENGTH_SHORT)
                            .show();
                }
            });

            if(v==btn1){


//                fragmentTransaction = fragmentManager.beginTransaction();
//                if(!fragmentOne.isAdded()) {
//
//                    fragmentTransaction.add(R.id.fr_container, fragmentOne, "fr_t");
//                    fragmentTransaction.addToBackStack("chenrongfa");
//
//                }
//                if(blankFragment.isAdded()&&blankFragment.isResumed()){
//                    fragmentTransaction.hide(blankFragment);
//                }
//                if(fragmentOne.isAdded() &&fragmentOne.isHidden()){
//
//                    fragmentTransaction.show(fragmentOne);
//                }
//                fragmentTransaction.commit();
//                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,SecondActivity.class);
                startActivity(intent);
            }else{
                FragmentTransaction transaction = fragmentManager
                        .beginTransaction();
                if(fragmentOne.isAdded()){
                    transaction.hide(fragmentOne);
                }


                transaction.add(R.id.fr_container,blankFragment,"hh")
                        .addToBackStack(null).hide(fragmentOne).commit();

            }
        }
//   @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        FragmentOne fragmentOne= (FragmentOne) fragmentManager.findFragmentByTag("fr_t");
////        fragmentOne.getData(new FragmentOne.CallBack() {
////            @Override
////            public void getMessage(String msg) {
////                Log.e("erro",msg);
////            }
////        });
//
//        return true;
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy0: ");
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
