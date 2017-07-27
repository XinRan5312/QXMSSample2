package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ran.qxmjsstudyone.R;

/**
 * Created by houqixin on 2017/7/18.
 */

public class RepayPlaneCircleView extends View {
    private Paint mCiclePaint;
    private Paint mLinePaint;
    private int mItemHeigt;
    private int mRadius;
    private int mLineDashGap;
    private float mStrokeWidth;
    private int mLineLength;
    private CircleType mCircleType = CircleType.FILE_CIRCLE;
    private boolean mIsHasUpLine = true;
    private boolean mIsHasDownLine = true;
    public void setNoUpLine() {
        mIsHasUpLine = false;
        invalidate();
    }
    public void setNoDowmLine() {
        mIsHasDownLine = false;
        invalidate();
    }
    public void setCustomParams(CircleType circleType) {
        this.mCircleType = circleType;
        invalidate();
    }

    public RepayPlaneCircleView(Context context) {
        super(context);
        init();
    }

    public RepayPlaneCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepayPlaneCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitMap(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mItemHeigt, mItemHeigt);
    }

    private void drawBitMap(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tick_icon_loandetail_default);
        if (mCircleType == CircleType.NULL_CIRCLE) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tick_icon_loandetail_default);
        } else if (mCircleType == CircleType.OK_CIRCLE) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tick_icon_loandetail_future);
        } else if (mCircleType == CircleType.FILE_CIRCLE) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.overdue_icon_loandetail_default);
        }
        int cirlemargin = dip2px(16);
        RectF rectF = new RectF(cirlemargin, cirlemargin, cirlemargin + 2 * mRadius, cirlemargin + 2 * mRadius);
        canvas.drawBitmap(bitmap, null, rectF, mCiclePaint);
        if (mIsHasUpLine) {
            Path pathUp = new Path();
            pathUp.moveTo(cirlemargin + mRadius, cirlemargin);
            pathUp.lineTo(cirlemargin + mRadius, cirlemargin - mLineLength);
            canvas.drawPath(pathUp, mLinePaint);
        }

        if (mIsHasDownLine) {
            Path pathDown = new Path();
            pathDown.moveTo(cirlemargin + mRadius, cirlemargin + mRadius * 2);
            pathDown.lineTo(cirlemargin + mRadius, cirlemargin + mRadius * 2 + mLineLength);
            canvas.drawPath(pathDown, mLinePaint);
        }
    }

    private void init() {
        mItemHeigt = dip2px(64);
        mRadius = dip2px(16);
        mLineDashGap = dip2px(2);
        mStrokeWidth = dip2px(2);
        mLineLength = (mItemHeigt - mRadius * 2) / 2;

        mCiclePaint = new Paint();
        mCiclePaint.setColor(Color.parseColor("#FF6600"));
        mCiclePaint.setStrokeWidth(mStrokeWidth);
        mCiclePaint.setStyle(Paint.Style.STROKE);
        mCiclePaint.setAntiAlias(true);
        mCiclePaint.setDither(true);

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

    public enum CircleType {

        NULL_CIRCLE, OK_CIRCLE, FILE_CIRCLE;
    }
}
