package yy.chen.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "yy.chen.service.action.FOO";
    private static final String ACTION_BAZ = "yy.chen.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "yy.chen.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "yy.chen.service.extra.PARAM2";
    private static final String TAG = "MyIntentService";
    public MyIntentService() {
        super("MyIntentService");
        Log.e(TAG, "MyIntentService: "+this.toString());
        Log.e(TAG, "MyIntentService: "+"MyIntentService" );
        MainActivity.bij.add(this);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
//    // TODO: Customize helper method
//    public static void startActionFoo(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, MyIntentService.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, MyIntentService.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e(TAG, "onHandleIntent: "+"xingle" );
        Log.e(TAG, "getName: "+Thread.currentThread().getName() );
        System.out.print("xingle" );

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(TAG, "onBind: "+"onBind" );
        return null;

    }
}
