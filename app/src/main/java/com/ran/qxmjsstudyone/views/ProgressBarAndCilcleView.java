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
import android.view.View;

/**
 * Created by houqixin on 2017/7/10.
 */

public class ProgressBarAndCilcleView extends View {
    private Paint mCiclePaint;
    private Paint mTextPaint;
    private Paint mCicleWhitePaint;
    private int mRadius;
    private int mSmallRadius;

    private int mScreenWith;
    private int mCicleSpace;
    private boolean mIsFirst = true;
    private Paint mRectPaint;
    private Paint mRectRedPaint;
    private Paint mCicleRedPaint;
    private int mMargin;
    private Paint mTextOraingePaint;
    private int mDrawStatus = 1;
    private Paint mOkPaint;

    public ProgressBarAndCilcleView(Context context) {
        super(context);
        init();
    }

    public ProgressBarAndCilcleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressBarAndCilcleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsFirst) {
            mScreenWith = getWidth();
            mMargin = dip2px(16);
            mCicleSpace = (mScreenWith - mMargin * 2) / 2 - mRadius * 2;
            mIsFirst = true;
        }
        drawByStatus(canvas);


    }

    private void drawByStatus(Canvas canvas) {
        int xy = mRadius + mMargin;
        int rectSpace = createSpace();
        Rect rect1 = new Rect(xy, xy - dip2px(4), mScreenWith - xy, xy + dip2px(4));

        canvas.drawRect(rect1, mRectPaint);
        Rect rect2 = new Rect(xy, xy - dip2px(3), rectSpace, xy + dip2px(3));
        canvas.drawRect(rect2, mRectRedPaint);

        canvas.drawCircle(xy, xy, mRadius, mCiclePaint);
        canvas.drawCircle(xy, xy, mSmallRadius, mCicleRedPaint);
        Rect r = new Rect();
        if (mDrawStatus == 0) {
            String text = "1";
            mTextPaint.getTextBounds(text, 0, text.length(), r);
            canvas.drawText(text, xy - r.width() / 2 - dip2px(1), xy + r.height() / 2, mTextPaint);
        }else if(mDrawStatus==1){
            Path path=new Path();
            path.moveTo(xy-2*mRadius/3,xy);
            path.lineTo(xy-1*mRadius/5,xy+1*mRadius/3+dip2px(3));
            path.lineTo(xy+2*mRadius/3,xy-2*mRadius/3+dip2px(5));
            canvas.drawPath(path,mOkPaint);
        }

        int x1 = mScreenWith / 2;
        canvas.drawCircle(x1, xy, mRadius, mCiclePaint);
        canvas.drawCircle(x1, xy, mSmallRadius, mDrawStatus == 0 ? mCicleWhitePaint : mCicleRedPaint);
        if (mDrawStatus == 0) {
            String text1 = "2";
            mTextOraingePaint.getTextBounds(text1, 0, text1.length(), r);
            canvas.drawText(text1, x1 - r.width() / 2, xy + r.height() / 2, mTextOraingePaint);
        }else if(mDrawStatus==1){
            Path path=new Path();
            path.moveTo(x1-2*mRadius/3,xy);
            path.lineTo(x1-1*mRadius/5,xy+1*mRadius/3+dip2px(3));
            path.lineTo(x1+2*mRadius/3,xy-2*mRadius/3+dip2px(5));
            canvas.drawPath(path,mOkPaint);
        }


        int x2 = mScreenWith - mMargin - mRadius;
        String text2 = "3";
        canvas.drawCircle(x2, xy, mRadius, mCiclePaint);
        canvas.drawCircle(x2, xy, mSmallRadius, mCicleWhitePaint);
        mTextOraingePaint.getTextBounds(text2, 0, text2.length(), r);
        canvas.drawText(text2, x2 - r.width() / 2, xy + r.height() / 2, mTextOraingePaint);
    }

    private int createSpace() {

        switch (mDrawStatus) {
            case 0:
                return mScreenWith - mMargin - mCicleSpace - mRadius * 2 - mCicleSpace / 2;
            case 1:
                return mScreenWith - mMargin - mRadius - mCicleSpace / 2;
            default:
                return mScreenWith - mMargin - mCicleSpace - mRadius * 2 - mCicleSpace / 2;
        }
    }

    public void setDrawStatus(int drawStatus) {
        this.mDrawStatus = drawStatus;
        invalidate();
    }

    private void init() {
        mRadius = dip2px(18);
        mSmallRadius = mRadius - 6;
        mCiclePaint = new Paint();
        mCiclePaint.setColor(Color.GRAY);
        mCiclePaint.setStrokeWidth(6f);

        mCicleWhitePaint = new Paint();
        mCicleWhitePaint.setColor(Color.WHITE);
        mCicleWhitePaint.setStrokeWidth(1f);
        mCicleWhitePaint.setStyle(Paint.Style.FILL);

        mCicleRedPaint = new Paint();
        mCicleRedPaint.setColor(Color.RED);
        mCicleRedPaint.setStrokeWidth(1f);
        mCicleRedPaint.setStyle(Paint.Style.FILL);

        mRectPaint = new Paint();
        mRectPaint.setColor(Color.GRAY);
        mRectPaint.setStrokeWidth(2f);
        mRectPaint.setStyle(Paint.Style.FILL);

        mRectRedPaint = new Paint();
        mRectRedPaint.setColor(Color.RED);
        mRectRedPaint.setStrokeWidth(2f);
        mRectRedPaint.setStyle(Paint.Style.FILL);


        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(dip2px(9));
        mTextPaint.setTypeface(Typeface.DEFAULT);

        mOkPaint = new Paint();
        mOkPaint.setColor(Color.WHITE);
        mOkPaint.setStrokeWidth(3f);
        mOkPaint.setStyle(Paint.Style.STROKE);
        mOkPaint.setAntiAlias(true);//去锯齿


        mTextOraingePaint = new Paint();
        mTextOraingePaint.setColor(Color.parseColor("#FF6600"));
        mTextOraingePaint.setTextSize(dip2px(9));
        mTextOraingePaint.setTypeface(Typeface.DEFAULT);
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
