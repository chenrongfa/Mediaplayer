package yy.chen.myapplication;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


public class MainActivity extends Activity {

    NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PackageManager packageManager = getPackageManager();
//        Toast toast = Toast.makeText(this, "你好", Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP,0,0);
//        toast.show();
       /* ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.boy);
        Toast toast=new Toast(this);
        toast.setView(imageView);

        toast.show();*/
        Notification notification=new Notification(R.mipmap.ic_launcher
        ,"状态通知栏",System.currentTimeMillis());
        Intent intent=new Intent(this,Hello.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getActivity(this,200,intent,0);
        notification.setLatestEventInfo(this,"通知栏","通知内容",pendingIntent);
        notificationManager.notify(0,notification);
    }
}
