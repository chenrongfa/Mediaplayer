package yy.chen.mediaplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import yy.chen.mediaplay.bean.Lyric;
import yy.chen.mediaplay.util.DestinyUtil;

/**
 * Created by chenrongfa on 2017/1/5
 */

public class LyricView extends TextView {
    private static final String TAG = "LyricView";
    private ArrayList<Lyric> lyrics;

    public LyricView(Context context) {
        this(context, null);
    }

    private int index;
    private int width;
    private int height;
    private Paint wpaint;
    private Paint paint;
    private boolean isNotFound;
    private Context context;

    public LyricView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        wpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(DestinyUtil.getSpToPx(context, 20));
        paint.setColor(Color.GREEN);
        wpaint.setColor(Color.BLACK);
        wpaint.setTextSize(DestinyUtil.getSpToPx(context, 20));
        this.context = context;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (lyrics != null && lyrics.size() > 0) {
            String content = lyrics.get(index).getContent();
            if (content != null) {
                canvas.drawText(content, width / 4,
                        height / 2, paint);
            }
            if (index > 0) {
                float height1 = height / 2;
                for (int i = index - 1; i >= 0; i--) {
                    height1 = height1 - getTextSize()-10;
                    content = lyrics.get(i).getContent();
                    canvas.drawText(content, width / 4, height1, wpaint);
                    if (height1 < 0) break;
                }
            }
            int num = 0;
            if (index < lyrics.size() - 1) {
                float height1 = height / 2;
                for (int i = index + 1; i < lyrics.size(); i++) {
                    height1 = height1 + getTextSize()+10;
                    content = lyrics.get(i).getContent();
                    canvas.drawText(content, width / 4, height1, wpaint);
                    if (height1 > height) break;
                }
            }
        }
        //没有歌词文件
        if (isNotFound) {
            canvas.drawText("没有发现歌词", width / 2, height / 2, wpaint);
        }
        Log.e(TAG, "onDraw: index" + index);
        super.onDraw(canvas);
    }

    public ArrayList<Lyric> getLyrics() {
        return lyrics;
    }

    public void setLyrics(ArrayList<Lyric> lyrics) {
        this.lyrics = lyrics;
    }

    public synchronized void setIndex(int position) {
//        Log.e(TAG, "setIndex: " + position);
        if (lyrics != null && lyrics.size() > 0) {
            int i = 0;
            for (Lyric lyric : lyrics) {
//                Log.e(TAG, "setIndex: " + lyric.toString());
                if (lyric.getTime() == position) {
                    index = i;
                    invalidate();
                    return;
                }
                i++;
            }
        }

    }

    public void setNotFound(boolean yes) {
        isNotFound = yes;
        invalidate();
    }
}
