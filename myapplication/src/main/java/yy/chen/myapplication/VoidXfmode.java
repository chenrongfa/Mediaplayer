package yy.chen.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelXorXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by KM on 2016/12/24.
 */

public class VoidXfmode extends View {
//    private AvoidXfermode voidXfmode;
    private Paint paint;
    private Bitmap bitmap;
     PixelXorXfermode pixelXorXfermode;
    public VoidXfmode(Context context) {
        super(context);
        pixelXorXfermode=new PixelXorXfermode(0xFF00BEA4);
      bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.boy);
       paint=new Paint();
    }

    public VoidXfmode(Context context, AttributeSet attrs) {
        super(context, attrs);
//        voidXfmode=new AvoidXfermode(0Xff00beae,0,AvoidXfermode.Mode.TARGET);
//        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.boy);
//        paint=new Paint();
        pixelXorXfermode=new PixelXorXfermode(0xFF00BEA4);
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.boy);
        paint=new Paint();
    }

    public VoidXfmode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,paint);
        paint.setXfermode(pixelXorXfermode);
        paint.setARGB(255, 222, 83, 71);
        canvas.drawRect(0,0,getWidth(),getHeight(), paint);
    }
}
