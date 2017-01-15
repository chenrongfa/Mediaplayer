package yy.chen.mediaplay.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.base.BasePager;
import yy.chen.mediaplay.fragment.FragmentFour;
import yy.chen.mediaplay.fragment.FragmentNetVideo;
import yy.chen.mediaplay.fragment.FragmentOne;
import yy.chen.mediaplay.fragment.FragmentTwo;
import yy.chen.mediaplay.util.ActivityCollector;

//主页面
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean doubleClick;
    private Toolbar tl_tab;
    public MainActivity(){

    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    doubleClick=false;
                    removeMessages(0);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private FrameLayout fl_main;
    private RadioGroup rg_main;
    private ArrayList<BasePager> basePager ;
    private int positon ;//记录点击的位置
    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentNetVideo fragmentNetVideo;
    private FragmentFour fragmentFour;
   private  FragmentManager  fragmentManager;
    private RadioButton  btnvideo;
    private RadioButton  btnaudio;
    private RadioButton  btnnetvideo;
    private RadioButton  btnnetaudio;
    private DrawerLayout dl_test;
    private int num;
    private RelativeLayout rl_draw;
    private ActionBarDrawerToggle act;
    private Button btn_eixt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ActivityCollector.activityList.add(this);
        initData();
//        tl_tab.setLogo();
        setToolBar();
        initDrawer();
    }

    // TODO: 2017/1/14 initdrawer
    private void initDrawer() {
      dl_test.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
          @Override
          public void onDrawerOpened(View drawerView) {
              super.onDrawerOpened(drawerView);
              Toast.makeText(MainActivity.this, "open", Toast.LENGTH_SHORT).show();
              tl_tab.setTitle("close");
          }

          @Override
          public void onDrawerClosed(View drawerView) {
              super.onDrawerClosed(drawerView);
             tl_tab.setTitle("open");
              Toast.makeText(MainActivity.this, "guanbi", Toast.LENGTH_SHORT).show();
          }
      });
    }

    private void setToolBar() {
        tl_tab.setNavigationIcon(R.drawable.easyicon_net_48);
        tl_tab.setTitle("陈蓉发");
        tl_tab.inflateMenu(R.menu.main_tab);
        tl_tab.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
        tl_tab.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLeft();

            }
        });
        tl_tab.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.im_test:
                        Toast.makeText(MainActivity.this, ""+item.getTitle(), Toast.LENGTH_SHORT)
                                .show();
                        return true;
                    case R.id.im_test1:
                        Toast.makeText(MainActivity.this, ""+item.getTitle(), Toast.LENGTH_SHORT)
                                .show();
                        return true;
                }

                return false;
            }
        });
    }

    private void openLeft() {
        if(dl_test.isDrawerOpen(rl_draw)){
            dl_test.closeDrawer(rl_draw);
        }else{
            dl_test.openDrawer(rl_draw);
        }
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        tl_tab= (Toolbar) findViewById(R.id.tl_tab);
        btn_eixt= (Button) findViewById(R.id.btn_eixt);

        rl_draw= (RelativeLayout) findViewById(R.id.rl_draw);
        dl_test= (DrawerLayout) findViewById(R.id.dl_test);
        if (fragmentNetVideo == null) fragmentNetVideo = new FragmentNetVideo();
        if (fragmentTwo == null) fragmentTwo = new FragmentTwo();
        if (fragmentFour == null) fragmentFour = new FragmentFour();
        if (fragmentOne == null) fragmentOne = new FragmentOne();
        fl_main = (FrameLayout) findViewById(R.id.fl_main);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        rg_main.setOnCheckedChangeListener(new RgCheckedListener());
        btnvideo= (RadioButton) findViewById(R.id.rb_video);
        btnaudio= (RadioButton) findViewById(R.id.rb_audio);
        btnnetvideo= (RadioButton) findViewById(R.id.rb_netvideo);
        btnnetaudio= (RadioButton) findViewById(R.id.rb_netaudio);

//        rg_main.check(R.id.rb_video);

        btnvideo.setChecked(true);
        btn_eixt.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "点我干嘛", Toast.LENGTH_SHORT).show();
        if(v==btn_eixt){
            //退出所以活动
            ActivityCollector.removeAll();
        }
    }

    public class RgCheckedListener implements RadioGroup.OnCheckedChangeListener {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_video:
                        positon = 0;
                        break;
                    case R.id.rb_audio:
                        positon = 1;
                        break;
                    case R.id.rb_netvideo:
                        positon = 2;
                        break;
                    case R.id.rb_netaudio:
                        positon = 3;
                        break;
                }
                //显示到frame
                int count = fragmentManager.getBackStackEntryCount();
                Log.e(TAG, "onCheckedChanged: "+count);
                setFrame();
            }

        private static final String TAG = "RgCheckedListener";
            public void setFrame() {

                //开始事务
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (fragmentOne == null) fragmentOne = new FragmentOne();
                //替换
                if (positon == 0 &&num==0) {

                    isRun(transaction);
                    if(!fragmentOne.isAdded()){
                        Log.e(TAG, "setFrame: "+num );
                        num++;
                            //没有添加
                            transaction.add(R.id.fl_main,fragmentOne,"one");
                            transaction.addToBackStack("fragmentOne");
                        }
                    if(fragmentOne.isAdded()&&fragmentOne.isHidden()){
                        //隐藏就show
                        Log.e(TAG, "setFrame: show1");
                        transaction.show(fragmentOne);
                    }
//                    transaction.replace(R.id.fl_main, fragmentOne);
                } else if (positon == 1) {
                    if (fragmentTwo == null) fragmentTwo = new FragmentTwo();
                    Log.e(TAG, "setFrame:fragmentTwo " );
                        isRunOne(transaction);
                        if(!fragmentTwo.isAdded()){
                        //没有添加
                        transaction.add(R.id.fl_main,fragmentTwo,"two");
                        transaction.addToBackStack("fragmentTwo");
                    }if(fragmentTwo.isAdded()&&fragmentTwo.isHidden()){
                        //隐藏就show
                        transaction.show(fragmentTwo);
                    }
// transaction.replace(R.id.fl_main, fragmentTwo);
                } else if (positon == 2) {
                    if (fragmentNetVideo == null) fragmentNetVideo = new FragmentNetVideo();
                    //对其他三个有没有在运行并判断
                    isRunTwo(transaction);
                    if(!fragmentNetVideo.isAdded()){
                        //没有添加
                        transaction.add(R.id.fl_main,fragmentNetVideo,"two");
                        transaction.addToBackStack("fragmentNetVideo");

                    }if(fragmentNetVideo.isAdded()&&fragmentNetVideo.isHidden()){
                        //隐藏就show
                        transaction.show(fragmentNetVideo);
                    }
                } else if (positon == 3) {
                    if (fragmentFour == null) fragmentFour = new FragmentFour();
                    //对其他三个有没有在运行并判断
                      isRunThree(transaction);
                    if(!fragmentFour.isAdded()){
                        //没有就添加
                        transaction.add(R.id.fl_main,fragmentFour,"two");
                        transaction.addToBackStack("fragmentFour");

                    }if(fragmentFour.isAdded()&&fragmentFour.isHidden()){
                        //隐藏就show
                        transaction.show(fragmentFour);
                    }
//                    transaction.replace(R.id.fl_main, fragmentFour);
                }

            //提交事务
                transaction.commit();

            //关闭抽屉
                openLeft();

            }


        }

    private void isRun(FragmentTransaction transaction) {
        if(fragmentTwo.isAdded()&&fragmentTwo.isResumed()){
            transaction.hide(fragmentTwo);
        }
        if(fragmentNetVideo.isAdded()&&fragmentNetVideo.isResumed()){
            transaction.hide(fragmentNetVideo);
        }if(fragmentFour.isAdded()&&fragmentFour.isResumed()){
            transaction.hide(fragmentFour);
        }
    }

    private void isRunOne(FragmentTransaction transaction) {
        if(fragmentOne.isAdded()&&fragmentOne.isResumed()){
            transaction.hide(fragmentOne);
        }
        if(fragmentNetVideo.isAdded()&&fragmentNetVideo.isResumed()){
            transaction.hide(fragmentNetVideo);
        }if(fragmentFour.isAdded()&&fragmentFour.isResumed()){
            transaction.hide(fragmentFour);
        }


    }

    private void isRunTwo(FragmentTransaction transaction) {
        if(fragmentOne.isAdded()&&fragmentOne.isResumed()){
            transaction.hide(fragmentOne);
        }
        if(fragmentTwo.isAdded()&&fragmentTwo.isResumed()){
            transaction.hide(fragmentTwo);
        }if(fragmentFour.isAdded()&&fragmentFour.isResumed()){
            transaction.hide(fragmentFour);
        }

    }

    private void isRunThree(FragmentTransaction transaction) {
        if(fragmentOne.isAdded()&&fragmentOne.isResumed()){
            transaction.hide(fragmentOne);
        }
        if(fragmentTwo.isAdded()&&fragmentTwo.isResumed()){
            transaction.hide(fragmentTwo);
        }if(fragmentNetVideo.isAdded()&&fragmentNetVideo.isResumed()){
            transaction.hide(fragmentNetVideo);
        }
    }

    private static final String TAG = "MainActivity";

    // TODO: 2017/1/11
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int number=fragmentManager.getBackStackEntryCount();
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if (number>1) {
                Log.e(TAG, "onKeyDown: "+number );
                handleBack(number);
                return true;
            } else {
                if (!doubleClick) {
                    Toast.makeText(this, " two click will exit in two second" + keyCode, Toast.LENGTH_SHORT).show();
                    doubleClick = true;
                    handler.sendEmptyMessageDelayed(0, 2000);
                    return true;
                } else {
                    finish();
                }
            }
        }
        return super.onKeyDown(keyCode,event);
    }

    private void handleBack(int number) {
        FragmentManager.BackStackEntry currtent= fragmentManager
                .getBackStackEntryAt(number - 1);
        FragmentManager.BackStackEntry last= fragmentManager
                .getBackStackEntryAt(number - 2);
        fragmentManager.popBackStack();
//                if(.equals(backStackEntryAt.getName())){
        if("fragmentOne".equals(last.getName())) {
            rg_main.check(R.id.rb_video);

        }else if("fragmentTwo".equals(last.getName())){
            rg_main.check(R.id.rb_audio);
//            fragmentManager.popBackStack("fragmentTwo",
//                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if("fragmentNetVideo".equals(last.getName())){
            rg_main.check(R.id.rb_netvideo);

        }else if("fragmentFour".equals(last.getName())){
            rg_main.check(R.id.rb_netaudio);

        }


    }

    @Override
    protected void onDestroy() {
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        ActivityCollector.activityList.remove(this);
        super.onDestroy();
    }
}