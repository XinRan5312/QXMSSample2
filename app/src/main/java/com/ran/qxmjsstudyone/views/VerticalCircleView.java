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
        canvas.drawCircle(mFirstCirleX, mFirstCirleY + mItemHeigt * (num - 1), mRadius, mCiclePaint);
        float y1 = mFirstCirleY + mItemHeigt * (num - 1);

        //画对号
//        Path path = new Path();
//
//        path.moveTo(mFirstCirleX-2*mRadius/3, y1);
//        path.lineTo(mFirstCirleX - 1 * mRadius / 5, y1 + 1 * mRadius / 3 + dip2px(3));
//        path.lineTo(mFirstCirleX + 2 * mRadius / 3, y1 - 2 * mRadius / 3 + dip2px(5));
//        canvas.drawPath(path, mCiclePaint);

        //画文字
        Rect rect = new Rect();
        String text = "逾期";
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, mFirstCirleX - rect.width() / 2, y1 + rect.height() / 2, mTextPaint);

        //画竖线
        if (num < mItemCount) {//画mItemCount-1个竖线就好了
            float y2 = y1 + mRadius;
            Path path = new Path();
            path.moveTo(mFirstCirleX, y2);
            path.lineTo(mFirstCirleX, y2 + mLineLength);
            canvas.drawPath(path, mLinePaint);
        }
    }

    private void init() {
        mItemHeigt = dip2px(64);
        mItemCount = 7;
        mWith = dip2px(26);
        mRadius = dip2px(12);
        mLineLength = mItemHeigt - mRadius * 2;
        mFirstCirleX = dip2px(13);
        mFirstCirleY = dip2px(33);
        mLineDashGap = dip2px(8);

        mCiclePaint = new Paint();
        mCiclePaint.setColor(Color.RED);
        mCiclePaint.setStrokeWidth(3f);
        mCiclePaint.setStyle(Paint.Style.STROKE);
        mCiclePaint.setAntiAlias(true);
        mCiclePaint.setDither(true);

        mCicleFillPaint = new Paint();
        mCicleFillPaint.setColor(Color.RED);
        mCicleFillPaint.setStrokeWidth(1f);
        mCicleFillPaint.setStyle(Paint.Style.FILL);
        mCicleFillPaint.setAntiAlias(true);
        mCicleFillPaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(dip2px(9));
        mTextPaint.setTypeface(Typeface.DEFAULT);


        mLinePaint = new Paint();
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(3f);
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
