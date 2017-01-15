package yy.chen.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by KM on 2016/12/24.
 */

public class PorterDuff1 extends View {
   private PorterDuffXfermode porterDuff;
    int width=100;
    int height=100;
    int screanhw[]=new int[2];
    Bitmap bitmap1,bitmap2;
    public static PorterDuff.Mode constmode=PorterDuff.Mode.DARKEN;

    public PorterDuff1(Context context) {
        super(context);
    }


    public PorterDuff1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
   public  PorterDuff1(Context context, AttributeSet attrs){
 super(context, attrs);
       porterDuff=new PorterDuffXfermode(constmode);
       screanhw=GetScreenHw.getHW(context);
       bitmap1=makeDst(width,height);
       bitmap2=makeOval(width,height);

   }

    private Bitmap makeOval(int width, int height) {
        Paint  paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        paint.setColor(Color.GREEN);
        Canvas cs=new Canvas(bp);
        cs.drawOval(new RectF(0,0,width*3/4,height*3/4),paint);
        return  bp;

    }

    private Bitmap makeDst(int width, int height) {
        Paint  paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        paint.setColor(Color.BLUE);
        Canvas cs=new Canvas(bp);
        cs.drawRect(new RectF(width/3,height/3,width*19/20,height*19/20),paint);
        return  bp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint=new Paint();

        paint.setStyle(Paint.Style.FILL);
        canvas.drawBitmap(bitmap1, (screanhw[0] /3 - width) /2, (screanhw[1] / 2 -
                height)
                /2,
                paint);
        canvas.drawBitmap(bitmap2, (screanhw[0] /3 - width)/2 + screanhw[0]/3,
                (screanhw[1]
                / 2 -
                height) / 2, paint);
        int sc = canvas.saveLayer(0, 0, screanhw[0], screanhw[1], null, Canvas
                .MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        canvas.drawBitmap(bitmap1, (screanhw[0] / 3 - width) / 2 + screanhw[0] / 3 * 2,
                (screanhw[1] / 2 - height) / 2, paint);     //绘制i
        //设置Paint的Xfermode
        paint.setXfermode(porterDuff);
        canvas.drawBitmap(bitmap2, (screanhw[0] / 3 - width) / 2 + screanhw[0] / 3 * 2,
                (screanhw[1] / 2 - height) / 2, paint);
        paint.setXfermode(null);
        // 还原画布
        canvas.restoreToCount(sc);

    }
}
