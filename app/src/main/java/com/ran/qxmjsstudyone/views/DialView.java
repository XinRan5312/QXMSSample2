package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    private int color;

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
        firstWaterLine = new float[mRaidus*2];
        secondWaterLine = new float[mRaidus*2];

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
                color = mRealPaint.getColor();
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
        cirlePaint.setColor(Color.BLUE);
        cirlePaint.setAntiAlias(true);
        cirlePaint.setStrokeWidth(2f);
        cirlePaint.setStyle(Paint.Style.FILL);

        drawWaterView(canvas);

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
    /**
     * 画水球的功能
     *
     * @param canvas
     */
    private void drawWaterView(Canvas canvas) {
        // y = Asin(wx+b)+h ，这个公式里：w影响周期，A影响振幅，h影响y位置，b为初相；
        // 将周期定为view总宽度
        int len=mRaidus*2;
        float mCycleFactorW = (float) (2 * Math.PI / len);

        // 得到第一条波的y值
        for (int i = 0; i < len; i++) {
            firstWaterLine[i] = (float) (10 * Math
                    .sin(mCycleFactorW * i + move) - up);
        }
        // 得到第一条波的y值
        for (int i = 0; i < len; i++) {
            secondWaterLine[i] = (float) (15 * Math.sin(mCycleFactorW * i
                    + move + 10) - up);

        }

        canvas.save();

        // 裁剪成圆形区域
        Path path = new Path();
        waterPaint.setColor(color);
        path.reset();
        canvas.clipPath(path);

        path.addCircle(len / 2, len / 2, clipRadius, Path.Direction.CCW);
        canvas.clipPath(path, android.graphics.Region.Op.REPLACE);
        // 将坐标系移到底部
        canvas.translate(0, len / 2 + clipRadius);

        for (int i = 0; i < len; i++) {
            canvas.drawLine(i, firstWaterLine[i], i, len, waterPaint);
        }
        for (int i = 0; i < len; i++) {
            canvas.drawLine(i, secondWaterLine[i], i, len, waterPaint);
        }
        canvas.restore();
    }
    // 存放第一条水波Y值
    private float[] firstWaterLine;
    // 第二条
    private float[] secondWaterLine;
    // 画水球的画笔
    private Paint waterPaint;
    // 影响三角函数的初相
    private float move;
    // 剪切圆的半径
    private int clipRadius;
    // 水球的增长值
    int up = 0;

    private boolean mIsDynamic = false;
    private int mDynamicStatus = 1;
    // 每次后退时的值，实现越来越快的效果
    private int[] back = {2, 2, 4, 4, 6, 6, 8, 8, 10};
    // 每次前进时的值，实现越来越慢的效果
    private int[] go = {10, 10, 8, 8, 6, 6, 4, 4, 2};
    // 前进的下标
    private int go_index = 0;
    // 后退的下标
    private int back_index = 0;
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
                    mRealAgnle += go[go_index];
                    go_index++;
                    if(go_index==go.length){
                        go_index--;
                    }
                    if (mRealAgnle >= angle) {
                        mRealAgnle = angle;
                        mDynamicStatus=0;
                    }
                }else if(mDynamicStatus==0){//后腿
                    mRealAgnle -= back[back_index];
                    back_index++;
                    if(back_index==back.length){
                        back_index--;
                    }
                    if (mRealAgnle <= 0) {
                        mRealAgnle = 0;
                        mDynamicStatus=1;
                        mIsDynamic=true;//退出动画
                        timer.cancel();
                    }
                }
                postInvalidate();

            }
        }, 500, 30);
    }

    public interface OnColorChangeLisner{
        void onColorChange(int red,int green);
    }
    public void moveWaterLine() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                move += 1;
                if (move == 100) {
                    timer.cancel();
                }
                postInvalidate();
            }
        }, 500, 200);
    }
    private void init() {
        mStartAgnle = 120;
        mSwipeAgnle = 300;
        mSpaceAgnle = 3;
        mRealAgnle = 200;
        clipRadius = mRaidus - 45;

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
        waterPaint = new Paint();
        waterPaint.setAntiAlias(true);
        moveWaterLine();
    }

}
