package yy.chen.mediaplay.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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
import java.util.List;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.base.BasePager;
import yy.chen.mediaplay.fragment.FragmentNetAudio;
import yy.chen.mediaplay.fragment.FragmentNetVideo;
import yy.chen.mediaplay.fragment.VideoFragment;
import yy.chen.mediaplay.fragment.AudioFragment;
import yy.chen.mediaplay.util.ActivityCollector;

//主页面
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
      private static final String TAG = "MainActivity";
      private boolean doubleClick;
      private Toolbar tl_tab;
      private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                  switch (msg.what) {
                        case 0:
                              doubleClick = false;
                              removeMessages(0);
                              break;
                  }
                  super.handleMessage(msg);
            }
      };
      private FrameLayout fl_main;
      private RadioGroup rg_main;
      private ArrayList<BasePager> basePager;
      private int positon;//记录点击的位置
      private VideoFragment videoFragment;
      private AudioFragment audioFragment;
      private FragmentNetVideo fragmentNetVideo;
      private FragmentNetAudio fragmentNetAudio;
      private List<Fragment> fragments;
      private FragmentManager fragmentManager;
      private RadioButton btnvideo;
      private RadioButton btnaudio;
      private RadioButton btnnetvideo;
      private RadioButton btnnetaudio;
      private DrawerLayout dl_test;
      private RelativeLayout rl_draw;
      private ActionBarDrawerToggle act;
      private Button btn_eixt;
      private Fragment currentFragment;
      private Fragment nextFragment;

      public MainActivity() {

      }

      @Override
      protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
            initFragment();
            setContentView(R.layout.activity_main);
            ActivityCollector.activityList.add(this);
            initData();
//        tl_tab.setLogo();
            setToolBar();
            initDrawer();
            openLeft();
      }

      private void initFragment() {
            fragments = new ArrayList<>();
            fragments.add(new VideoFragment());
            fragments.add(new AudioFragment());
            fragments.add(new FragmentNetVideo());
            fragments.add(new FragmentNetAudio());
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
                        switch (item.getItemId()) {
                              case R.id.im_test:
                                    Toast.makeText(MainActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                                    return true;
                              case R.id.im_test1:
                                    Toast.makeText(MainActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                                    return true;
                        }
                        return false;
                  }
            });
      }

      /**
       *  开则关 关则开
       */
      private void openLeft() {
            if (dl_test.isDrawerOpen(rl_draw)) {
                  dl_test.closeDrawer(rl_draw);
            } else {
                  dl_test.openDrawer(rl_draw);
            }
      }

      private void initData() {
            fragmentManager = getSupportFragmentManager();
            tl_tab = (Toolbar) findViewById(R.id.tl_tab);
            btn_eixt = (Button) findViewById(R.id.btn_eixt);

            rl_draw = (RelativeLayout) findViewById(R.id.rl_draw);
            dl_test = (DrawerLayout) findViewById(R.id.dl_test);
            if (fragmentNetVideo == null) fragmentNetVideo = new FragmentNetVideo();
            if (audioFragment == null) audioFragment = new AudioFragment();
            if (fragmentNetAudio == null) fragmentNetAudio = new FragmentNetAudio();
            if (videoFragment == null) videoFragment = new VideoFragment();
            fl_main = (FrameLayout) findViewById(R.id.fl_main);
            rg_main = (RadioGroup) findViewById(R.id.rg_main);
            rg_main.setOnCheckedChangeListener(new RgCheckedListener());
            btnvideo = (RadioButton) findViewById(R.id.rb_video);
            btnaudio = (RadioButton) findViewById(R.id.rb_audio);
            btnnetvideo = (RadioButton) findViewById(R.id.rb_netvideo);
            btnnetaudio = (RadioButton) findViewById(R.id.rb_netaudio);
//        rg_main.check(R.id.rb_video);
            btnvideo.setChecked(true);
            btn_eixt.setOnClickListener(this);


      }

      @Override
      public void onClick(View v) {
            Toast.makeText(this, "点我干嘛", Toast.LENGTH_SHORT).show();
            if (v == btn_eixt) {
                  //退出所以活动
                  ActivityCollector.removeAll();
            }
      }

      /**
       *  得到下一个fragment
       * @param positon
       * @return
       */

      private Fragment getNextFragment(int positon) {
            if (fragments != null && fragments.size() >= 0) {
                  return fragments.get(positon);
            }
            return null;

      }

      /**
       *   切换
       * @param currentFragment1
       * @param nextFragment
       */
      private void switchFragment(Fragment currentFragment1, Fragment nextFragment) {
            if (currentFragment1 != nextFragment) {
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                  if (nextFragment != null) {
                        if (nextFragment.isAdded() && nextFragment.isHidden()) {
                              fragmentTransaction.show(nextFragment);
                        } else {
                              fragmentTransaction.add(R.id.fl_main, nextFragment);
                        }
                  }
                  if (currentFragment1 != null) {
                        fragmentTransaction.hide(currentFragment1);
                  }
                  fragmentTransaction.commit();
                  currentFragment = nextFragment;


            }
            openLeft();
      }

      // TODO: 2017/1/11
      @Override
      public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (!doubleClick) {
                  Toast.makeText(this, " two click will exit in two second" + keyCode, Toast.LENGTH_SHORT).show();
                  doubleClick = true;
                  handler.sendEmptyMessageDelayed(0, 2000);
                  return true;
            } else {
                  return super.onKeyDown(keyCode, event);
            }

      }

      @Override
      protected void onDestroy() {
            //移除所有消息队列
            if (handler != null) {
                  handler.removeCallbacksAndMessages(null);
            }
            ActivityCollector.activityList.remove(this);
            super.onDestroy();
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
                  Log.e(TAG, "onCheckedChanged: " + count);
//                setFrame();
                  nextFragment = getNextFragment(positon);
                  switchFragment(currentFragment, nextFragment);
            }


      }
}