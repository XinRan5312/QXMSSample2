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

import java.util.ArrayList;

/**
 * Created by houqixin on 2017/9/5.
 */

public class InsuranceInvestProgressView extends View {
    private Paint mTopTextPaint;
    private Paint mMiddleTextPaint;
    private Paint mBottomTextPaint;
    private Paint mFilleCiclePaint;
    private Paint mCiclePaint;
    private Paint mLinePaint;

    private int mWith;
    private int mFirstLineLen;
    private int mSecondLineLen;
    private int mThirdLineLen;
    private int mFourthLineLen;
    private int mRadius;
    private int mSmallRadius;
    private boolean mIsFirstDraw = true;

    private ArrayList<String> mListTopText;
    private ArrayList<String> mListMiddleText;
    private ArrayList<String> mListBottomText;


    public InsuranceInvestProgressView(Context context) {
        super(context);
        init();
    }

    public InsuranceInvestProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InsuranceInvestProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), dip2px(96));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initData();
        drawTopText(canvas);
        drawMiddleText(canvas);
        drawBottomText(canvas);
        drawLineAndCircle(canvas);
    }

    private void drawLineAndCircle(Canvas canvas) {
        int cenrterY = dip2px(24);
        int centerX = mRadius;
        Path path=new Path();
        path.moveTo(centerX+mRadius,cenrterY);

        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                centerX += mFirstLineLen;
                canvas.drawCircle(centerX, cenrterY, mRadius, mCiclePaint);
                canvas.drawCircle(centerX, cenrterY, mSmallRadius, mFilleCiclePaint);
                path.moveTo(centerX+mRadius,cenrterY);
                path.lineTo(centerX+mSecondLineLen-mSmallRadius,cenrterY);
            } else if (i == 2) {
                centerX += mSecondLineLen;
                canvas.drawCircle(centerX, cenrterY, mSmallRadius, mCiclePaint);
                path.moveTo(centerX+mSmallRadius,cenrterY);
                path.lineTo(centerX+mThirdLineLen-mRadius*2-mSmallRadius-dip2px(1),cenrterY);

            } else if (i == 3) {
                centerX += mSecondLineLen;
                canvas.drawCircle(centerX, cenrterY, mRadius, mCiclePaint);
                canvas.drawCircle(centerX, cenrterY, mSmallRadius, mFilleCiclePaint);
                path.moveTo(centerX+mRadius,cenrterY);
                path.lineTo(centerX+mFourthLineLen+mRadius,cenrterY);
            }else if(i==0){
                canvas.drawCircle(centerX, cenrterY, mRadius, mCiclePaint);
                path.lineTo(mFirstLineLen,cenrterY);
            }
        }
        canvas.drawPath(path,mLinePaint);

    }

    private void drawTopText(Canvas canvas) {
        int y = dip2px(16);
        int centerX = (mFirstLineLen + dip2px(6)) / 2;
        for (int i = 0; i < mListTopText.size(); i++) {
            String text = mListTopText.get(i);
            Rect rect = new Rect();
            mTopTextPaint.getTextBounds(text, 0, text.length(), rect);
            if (i == 1) {
                centerX = mFirstLineLen + dip2px(6) + mSecondLineLen / 2;
            } else if (i == 2) {
                centerX = mFirstLineLen + mSecondLineLen + mThirdLineLen / 2;
            } else if (i == 3) {
                centerX = mFirstLineLen + dip2px(6) + mSecondLineLen + mThirdLineLen + mFourthLineLen / 2;
            }
            canvas.drawText(text, centerX - rect.width() / 2, y, mTopTextPaint);
        }

    }

    private void drawMiddleText(Canvas canvas) {
        int y = dip2px(50);
        int centerX = 0;
        for (int i = 0; i < mListMiddleText.size(); i++) {
            String text = mListMiddleText.get(i);
            Rect rect = new Rect();
            mMiddleTextPaint.getTextBounds(text, 0, text.length(), rect);
            if (i == 1) {
                centerX = mFirstLineLen + dip2px(6);
            } else if (i == 2) {
                centerX = mWith - mFourthLineLen;
            }
            canvas.drawText(text, centerX, y, mMiddleTextPaint);
        }

    }

    private void drawBottomText(Canvas canvas) {
        int y = dip2px(66);
        int centerX = 0;
        for (int i = 0; i < mListBottomText.size(); i++) {
            String text = mListBottomText.get(i);
            Rect rect = new Rect();
            mBottomTextPaint.getTextBounds(text, 0, text.length(), rect);
            if (i == 1) {
                centerX = mFirstLineLen + dip2px(6);
            } else if (i == 2) {
                centerX = mWith - mFourthLineLen;
            }
            canvas.drawText(text, centerX, y, mBottomTextPaint);
        }

    }

    private void initData() {
        if (mIsFirstDraw) {
            mIsFirstDraw = false;
            mWith = getMeasuredWidth();
            mFirstLineLen = 62 * mWith / 330;
            mSecondLineLen = 80 * mWith / 330;
            mThirdLineLen = 90 * mWith / 330;
            mFourthLineLen = 93 * mWith / 330;
        }
    }

    private void init() {
        mRadius = dip2px(6);
        mSmallRadius = dip2px(3);

        mTopTextPaint = new Paint();
        mTopTextPaint.setColor(Color.parseColor("#CCCCCC"));
        mTopTextPaint.setTextSize(dip2px(10));
        mTopTextPaint.setTypeface(Typeface.DEFAULT);
        mTopTextPaint.setAntiAlias(true);
        mTopTextPaint.setDither(true);

        mMiddleTextPaint = new Paint();
        mMiddleTextPaint.setColor(Color.parseColor("#4229B3"));
        mMiddleTextPaint.setTextSize(dip2px(12));
        mMiddleTextPaint.setTypeface(Typeface.DEFAULT);
        mMiddleTextPaint.setAntiAlias(true);
        mMiddleTextPaint.setDither(true);

        mBottomTextPaint = new Paint();
        mBottomTextPaint.setColor(Color.parseColor("#4229B3"));
        mBottomTextPaint.setTextSize(dip2px(10));
        mBottomTextPaint.setTypeface(Typeface.DEFAULT);
        mBottomTextPaint.setAntiAlias(true);
        mBottomTextPaint.setDither(true);

        mFilleCiclePaint = new Paint();
        mFilleCiclePaint.setColor(Color.parseColor("#4229B3"));
        mFilleCiclePaint.setStrokeWidth(1f);
        mFilleCiclePaint.setAntiAlias(true);
        mFilleCiclePaint.setDither(true);
        mFilleCiclePaint.setStyle(Paint.Style.FILL);

        mCiclePaint = new Paint();
        mCiclePaint.setColor(Color.parseColor("#4229B3"));
        mCiclePaint.setStrokeWidth(1f);
        mCiclePaint.setAntiAlias(true);
        mCiclePaint.setStyle(Paint.Style.STROKE);
        mCiclePaint.setDither(true);


        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor("#4229B3"));
        mLinePaint.setStrokeWidth(2f);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setDither(true);

        mListTopText = new ArrayList<>();
        mListTopText.add("1天");
        mListTopText.add("犹豫期15天");
        mListTopText.add("固定期5年");
        mListTopText.add("持续收益 随时退保");


        mListMiddleText = new ArrayList<>();
        mListMiddleText.add("购买");
        mListMiddleText.add("计算收益");
        mListMiddleText.add("免费退保");

        mListBottomText = new ArrayList<>();
        mListBottomText.add("2018.12.19");
        mListBottomText.add("2018.12.20");
        mListBottomText.add("2023.12.18");


    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
