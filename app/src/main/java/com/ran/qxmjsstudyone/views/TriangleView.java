package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
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
    private int mSpace;
    private String mText;

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
        double a=mSpace*mSpace;
        double b=mScreenWith*mScreenWith;
        double c=mHeight*mHeight;
        int x= (int) ((mSpace*mScreenWith)/(2*mHeight)+Math.sqrt(a+a*b/(4*c)));
        path.moveTo(x,mHeight-mSpace);
        path.lineTo(mScreenWith/2,mSpace);
        path.lineTo(mScreenWith-x,mHeight-mSpace);
        path.close();
        canvas.drawPath(path,mRedPaint);
    }
    private void drawTexts(Canvas canvas) {
        Rect rect=new Rect();
        mTextPaint.getTextBounds(mText,0,mText.length(),rect);
        canvas.drawText(mText,mScreenWith/2-rect.width()/2,mHeight/2+rect.height(),mTextPaint);
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
        mHeight=dip2px(100);
        mSpace=dip2px(20);
        mText="广州小贷";
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
