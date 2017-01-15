package yy.chen.fragmenttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private FragmentManager fragmentManager;
    private Button btn10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn10 = (Button) findViewById(R.id.button10);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        fragmentManager=getSupportFragmentManager();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                FragmentOne fragmentOne1=new FragmentOne();
                FragmentTransaction transaction = fragmentManager
                        .beginTransaction();
                transaction.add(R.id.fr_container,fragmentOne1,"one").addToBackStack(null)
                        .commit();
                break;
            case R.id.button2:
                FragmentTwo fragmen1=new FragmentTwo();
                FragmentTransaction transaction1 = fragmentManager
                        .beginTransaction();
                transaction1.add(R.id.fr_container,fragmen1,"two").addToBackStack(null)
                        .commit();
                break;
            case R.id.button3:
                FragmentOne fragmentOne=new FragmentOne();
                FragmentTransaction transaction2 = fragmentManager
                        .beginTransaction();
                transaction2.replace(R.id.fr_container,fragmentOne,"reone")
                       .addToBackStack(null) .commit();
                break;
            case R.id.button4:
                FragmentTwo fragmen=new FragmentTwo();
                FragmentTransaction transaction3 = fragmentManager
                        .beginTransaction();
                transaction3.replace(R.id.fr_container,fragmen,"retwo")
                        .addToBackStack(null).commit();
                break;
            case R.id.button5:
                Intent intent=new Intent(this,Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.button6:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(getSupportFragmentManager()
                        .findFragmentByTag("one"));
                break;
            case R.id.button7:
                FragmentTransaction fragmentTransaction1 = fragmentManager
                        .beginTransaction();
                fragmentTransaction1.remove(getSupportFragmentManager()
                        .findFragmentByTag("two")).commit();
                break;case R.id.button8:
                FragmentTransaction fragmentTransaction2 = fragmentManager
                        .beginTransaction();
                fragmentTransaction2.detach(getSupportFragmentManager()
                        .findFragmentByTag("one"));
                break;case R.id.button9:
                FragmentTransaction fragmentTransaction3 = fragmentManager
                        .beginTransaction();
                fragmentTransaction3.detach(getSupportFragmentManager()
                        .findFragmentByTag("retwo"));
                break;case R.id.button10:
//                Toast.makeText(this, "10", Toast.LENGTH_SHORT).show();
//                FragmentTransaction fragmentTransaction5 = fragmentManager
//                        .beginTransaction();
//                fragmentTransaction5.attach(getSupportFragmentManager()
//                        .findFragmentByTag("one")).commit();
//              if(getSupportFragmentManager()
//                      .findFragmentByTag("one").isAdded()&&getSupportFragmentManager()
//                      .findFragmentByTag("one").isResumed()){
//                  getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager()
//                          .findFragmentByTag("one")).commit();
//
//              }
//                if (getSupportFragmentManager()
//                        .findFragmentByTag("one").isHidden()){
//                    getSupportFragmentManager()
//                            .beginTransaction().show(getSupportFragmentManager()
//                            .findFragmentByTag("one")).commit();
//                }
                if(fragmentManager.getBackStackEntryCount()>1){
                    fragmentManager.popBackStack();
                    return ;
                }
                break;

        }
        if (fragmentManager.findFragmentByTag("one")!=null) {
            Toast.makeText(this, "added" + fragmentManager.findFragmentByTag("one").isAdded(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount()>0){
            fragmentManager.popBackStack();
            return ;
        }
        super.onBackPressed();
    }

}
