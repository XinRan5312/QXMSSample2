package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ran.qxmjsstudyone.R;

/**
 * Created by houqixin on 2017/7/18.
 */

public class RectangleAddView extends View {
    private Paint mPaint;
    private int mRadius;
    private int mRound;
    private boolean mIsAdd;

    public RectangleAddView(Context context) {
        super(context);
        init(null);
    }

    public RectangleAddView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RectangleAddView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0, 0, mRadius * 2, mRadius * 2);
        canvas.drawRoundRect(rectF, mRound, mRound, mPaint);
        canvas.drawLine(mRadius / 2, mRadius, mRadius * 2 - mRadius / 2, mRadius, mPaint);
        canvas.drawLine(mRadius, mRadius / 2, mRadius, mRadius * 2 - mRadius / 2, mPaint);
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.RectangleAddView);
            mIsAdd = a.getBoolean(R.styleable.RectangleAddView_isAdd, false);
            a.recycle();
        }
        mRadius = dip2px(12);
        mRound = dip2px(2);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3f);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

}
