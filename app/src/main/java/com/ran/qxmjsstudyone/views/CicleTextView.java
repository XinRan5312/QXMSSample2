package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.shapes.PathShape;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.ran.qxmjsstudyone.R;

/**
 * Created by houqixin on 2017/7/5.
 */

public class CicleTextView extends View {
    private Paint mBitmapPaint;
    private boolean mIsSelected;
    private boolean mIsCicle;
    private Paint mCiclePaint;
    private Paint mTextPaint;
    private String mMsgCount;

    public CicleTextView(Context context) {
        super(context);
        initPaint();
    }

    public CicleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CicleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitMap(canvas);
        drawCicle(canvas);
        drawCount(canvas);
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void drawBitMap(Canvas canvas) {
        Bitmap bitmap = null;
        if (mIsSelected) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ms_sl_msg_pressed);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ms_sl_msg_normal);
        }
        //这都是相对于父布局(0,0)点的位置
        RectF rectF = new RectF(dip2px(0), dip2px(28), dip2px(24), dip2px(52));
        canvas.drawBitmap(bitmap, null, rectF, mBitmapPaint);
    }

    private void drawCicle(Canvas canvas) {
        if (!TextUtils.isEmpty(mMsgCount)) {
            RectF rectF1 = null;
            if (mIsCicle) {
                //这都是相对于父布局(0,0)点的位置
                rectF1 = new RectF(dip2px(22), dip2px(22), dip2px(36), dip2px(36));
                canvas.drawArc(rectF1, 0, 360, true, mCiclePaint);
            } else {
                //这都是相对于父布局(0,0)点的位置
                rectF1 = new RectF(dip2px(21), dip2px(22), dip2px(40), dip2px(36));
                canvas.drawRoundRect(rectF1,dip2px(5),dip2px(5),mCiclePaint);
            }
        }
    }

    private void drawCount(Canvas canvas) {
        if (!TextUtils.isEmpty(mMsgCount)) {
            if (mIsCicle) {//有可能不一样 一样是巧合 先这么分开写
                //这都是相对于父布局(0,0)点的位置
                canvas.drawText(mMsgCount, dip2px(24), dip2px(33), mTextPaint);
            }else{
                canvas.drawText(mMsgCount, dip2px(24), dip2px(32), mTextPaint);
            }
        }
    }

    private void initPaint() {
        mIsSelected = false;
        mIsCicle = false;
        mMsgCount = "10+";
        mBitmapPaint = new Paint();
        mBitmapPaint = new Paint();
        mBitmapPaint.setColor(Color.parseColor("#CBCADD"));
        mBitmapPaint.setStrokeWidth(3);
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setStyle(Paint.Style.STROKE);

        mCiclePaint = new Paint();
        mCiclePaint.setColor(Color.RED);
        mCiclePaint.setStrokeWidth(2f);
        mCiclePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(dip2px(9));
        mTextPaint.setTypeface(Typeface.DEFAULT);
    }

    public void setIsSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
        invalidate();
    }

    public void setmMsgCount(String mMsgCount) {
        this.mMsgCount = mMsgCount;
        invalidate();
    }

    public void setIsCicle(boolean mIsCicle) {
        this.mIsCicle = mIsCicle;
        invalidate();
    }
}
