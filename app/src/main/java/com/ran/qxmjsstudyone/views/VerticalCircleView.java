package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ran.qxmjsstudyone.bean.VerticalCircleParams;

import java.util.HashMap;

/**
 * Created by houqixin on 2017/7/18.
 */

public class VerticalCircleView extends View {
    private Paint mCiclePaint;
    private Paint mTextPaint;
    private Paint mLinePaint;
    private Paint mCicleFillPaint;
    private int mItemHeigt;
    private int mItemCount;
    private int mWith;
    private int mRadius;
    private int mLineLength;
    private int mFirstCirleX;
    private int mFirstCirleY;
    private int mLineDashGap;
    private float mStrokeWidth;
    private HashMap<Integer, VerticalCircleParams> mapCount;

    public void setMapCount(HashMap<Integer, VerticalCircleParams> mapCount) {
        this.mapCount = mapCount;
        invalidate();
    }

    public VerticalCircleView(Context context) {
        super(context);
        init();
    }

    public VerticalCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(mWith, mItemHeigt * mItemCount);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mItemCount; i++) {
            drawNumCircle(canvas, i + 1);
        }


    }

    private void drawNumCircle(Canvas canvas, int num) {
//        VerticalCircleParams params = null;
//        if (mapCount.containsKey(num)) {
//            params = mapCount.get(num);
//        }
        //画文字
        float y1 = mFirstCirleY + mItemHeigt * (num - 1);
        if (num <3) {
            canvas.drawCircle(mFirstCirleX, mFirstCirleY + mItemHeigt * (num - 1), mRadius, mCiclePaint);
            //画对号
            Path path = new Path();

            path.moveTo(mFirstCirleX - 2 * mRadius / 3, y1);
            path.lineTo(mFirstCirleX - 1 * mRadius / 5, y1 + 1 * mRadius / 3 + dip2px(3));
            path.lineTo(mFirstCirleX + 2 * mRadius / 3, y1 - 2 * mRadius / 3 + dip2px(5));
            canvas.drawPath(path, mCiclePaint);

        }else if(num==3){//提示逾期了
            canvas.drawCircle(mFirstCirleX, mFirstCirleY + mItemHeigt * (num - 1), mRadius, mCicleFillPaint);
            Rect rect = new Rect();
            String text = "逾期";
            mTextPaint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, mFirstCirleX - rect.width() / 2, y1 + rect.height() / 2, mTextPaint);
        }else{
            canvas.drawCircle(mFirstCirleX, mFirstCirleY + mItemHeigt * (num - 1), mRadius, mCiclePaint);
        }

        //画竖线
        if (num < mItemCount) {//画mItemCount-1个竖线就好了
            float y2 = y1 + mRadius;
            Path path1 = new Path();
            path1.moveTo(mFirstCirleX, y2);
            path1.lineTo(mFirstCirleX, y2 + mLineLength);
            canvas.drawPath(path1, mLinePaint);
        }
    }

    private void init() {
        mItemHeigt = dip2px(64);
        mItemCount = 7;
        mRadius = dip2px(18);
        mLineDashGap = dip2px(2);
        mStrokeWidth = dip2px(2);
        mLineLength = mItemHeigt - mRadius * 2;
        mFirstCirleX = mRadius + dip2px(1);
        mWith = mRadius * 2 + dip2px(2);
        mFirstCirleY = mItemHeigt / 2 + dip2px(1);


        mCiclePaint = new Paint();
        mCiclePaint.setColor(Color.parseColor("#FF6600"));
        mCiclePaint.setStrokeWidth(mStrokeWidth);
        mCiclePaint.setStyle(Paint.Style.STROKE);
        mCiclePaint.setAntiAlias(true);
        mCiclePaint.setDither(true);

        mCicleFillPaint = new Paint();
        mCicleFillPaint.setColor(Color.parseColor("#FF2020"));
        mCicleFillPaint.setStrokeWidth(mStrokeWidth);
        mCicleFillPaint.setStyle(Paint.Style.FILL);
        mCicleFillPaint.setAntiAlias(true);
        mCicleFillPaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));
        mTextPaint.setTextSize(dip2px(9));
        mTextPaint.setTypeface(Typeface.DEFAULT);


        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor("#FF6600"));
        mLinePaint.setStrokeWidth(mStrokeWidth);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        PathEffect effects = new DashPathEffect(new float[]{mLineDashGap, mLineDashGap, mLineDashGap, mLineDashGap}, 0);
        mLinePaint.setPathEffect(effects);


    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
