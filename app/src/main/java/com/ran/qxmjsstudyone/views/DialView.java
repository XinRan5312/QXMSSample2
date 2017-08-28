package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ran.qxmjsstudyone.utils.UIUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by houqixin on 2017/8/28.
 */

public class DialView extends View {
    private Paint mLinePaint;
    private int mStartAgnle;
    private int mSwipeAgnle;
    private int mSpaceAgnle;
    private int mRaidus;
    private int mRealAgnle;
    private Paint mRealPaint;
    private int mScaleLineLen;
    private OnColorChangeLisner mOnColorChangeLisner;

    public void setOnColorChangeLisner(OnColorChangeLisner onColorChangeLisner) {
        this.mOnColorChangeLisner = onColorChangeLisner;
    }

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRaidus = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec)) / 2;
        setMeasuredDimension(2 * mRaidus, 2 * mRaidus);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(new RectF(0, 0, 2 * mRaidus, 2 * mRaidus), mStartAgnle, mSwipeAgnle, false, mLinePaint);
        //开始操作坐标系 为了准确的画好刻度线
        canvas.save();

        canvas.translate(mRaidus, mRaidus);//本View从现在开始坐标原点从（0,0）到(mRaidus,mRaidus)

        canvas.rotate(30);//坐标系在原点在(mRaidus,mRaidus)基础上X和Y轴旋转30度，正好刻度线从弧度的开始开始画
        canvas.drawLine(0, mRaidus, 0, mRaidus - mScaleLineLen, mLinePaint);
        for (int hasDrawAgnle = 0; hasDrawAgnle < mSwipeAgnle; hasDrawAgnle += mSpaceAgnle) {
            canvas.rotate(mSpaceAgnle);//在圆弧上画刻度线
            if (hasDrawAgnle < mRealAgnle) {
                int red=255 - 255 * hasDrawAgnle / mRealAgnle;
                int green=255 * hasDrawAgnle / mRealAgnle;
                mRealPaint.setARGB(255, red,green, 0);
                canvas.drawLine(0, mRaidus, 0, mRaidus - mScaleLineLen, mRealPaint);
                if(mOnColorChangeLisner!=null){
                    mOnColorChangeLisner.onColorChange(red,green);
                }

            } else {
                canvas.drawLine(0, mRaidus, 0, mRaidus - mScaleLineLen, mLinePaint);
            }
        }
        canvas.restore();//回复标坐标系以前的模样  介绍变换坐标系
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        int circleRaidus=mRaidus-mScaleLineLen;
        Paint cirlePaint=new Paint();
        cirlePaint.setColor(Color.GREEN);
        cirlePaint.setAntiAlias(true);
        cirlePaint.setStrokeWidth(2f);
        cirlePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mRaidus,mRaidus,circleRaidus,cirlePaint);


        Paint textPaint=new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(2f);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(circleRaidus/2);
        String text=""+mRealAgnle;
        Rect rect=new Rect();
        textPaint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text,mRaidus,mRaidus+rect.height()/2,textPaint);

        // 画固定值分
        textPaint.setTextSize(circleRaidus/6);
        canvas.drawText("分", mRaidus+rect.width(),mRaidus-rect.height()/4, textPaint);



    }

    private boolean mIsDynamic = false;
    private int mDynamicStatus = 1;

    public void dynamicDraw(final int angle) {
        final Timer timer = new Timer();
        mRealAgnle = 0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mIsDynamic) {
                    return;
                }
                if (mDynamicStatus == 1) {//前进
                    mRealAgnle += 3;
                    if (mRealAgnle >= angle) {
                        mRealAgnle = angle;
                        mDynamicStatus=0;
                    }
                }else if(mDynamicStatus==0){//后腿
                    mRealAgnle -= 3;
                    if (mRealAgnle <= 0) {
                        mRealAgnle = 0;
                        mDynamicStatus=1;
                        mIsDynamic=true;//退出动画
                        timer.cancel();
                    }
                }
                postInvalidate();

            }
        }, 400, 30);
    }

    public interface OnColorChangeLisner{
        void onColorChange(int red,int green);
    }

    private void init() {
        mStartAgnle = 120;
        mSwipeAgnle = 300;
        mSpaceAgnle = 3;
        mRealAgnle = 200;
        mScaleLineLen=UIUtils.dip2px(getContext(),10);
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(3f);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mRealPaint = new Paint();
        mRealPaint.setColor(Color.GREEN);
        mRealPaint.setAntiAlias(true);
        mRealPaint.setStrokeWidth(3f);
        mRealPaint.setStyle(Paint.Style.STROKE);

    }

}
