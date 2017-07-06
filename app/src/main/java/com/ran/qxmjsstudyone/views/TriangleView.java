package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by houqixin on 2017/7/6.
 */

public class TriangleView extends View {

    private int mScreenWith;
    private Paint mWhitPaint;
    private Paint mRedPaint;
    private Paint mTextPaint;
    private int mHeight;

    public TriangleView(Context context) {
        super(context);
        initPaint();
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawWhiteTriangle(canvas);
        drawRedTriangle(canvas);
        drawTexts(canvas);


    }

    private void drawWhiteTriangle(Canvas canvas) {
        Path path=new Path();
        path.moveTo(0,mHeight);
        path.lineTo(mScreenWith/2,0);
        path.lineTo(mScreenWith,mHeight);
        path.close();
        canvas.drawPath(path,mWhitPaint);
    }

    private void drawRedTriangle(Canvas canvas) {
        Path path=new Path();
        path.moveTo(dip2px(50),mHeight-dip2px(8));
        path.lineTo(mScreenWith/2,dip2px(8));
        path.lineTo(mScreenWith-dip2px(50),mHeight-dip2px(8));
        path.close();
        canvas.drawPath(path,mRedPaint);
    }
    private void drawTexts(Canvas canvas) {
        canvas.drawText("你们好啊",mScreenWith/2-dip2px(28),dip2px(30),mTextPaint);
    }
    private void getScreenWith(){
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mScreenWith = wm.getDefaultDisplay().getWidth();

    }
    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    private void initPaint() {
        getScreenWith();
        mHeight=dip2px(50);
        mWhitPaint = new Paint();
        mWhitPaint.setColor(Color.WHITE);
        mWhitPaint.setStrokeWidth(2f);
        mWhitPaint.setStyle(Paint.Style.FILL);

        mRedPaint = new Paint();
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStrokeWidth(2f);
        mRedPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(dip2px(14));
        mTextPaint.setTypeface(Typeface.DEFAULT);
    }
}
