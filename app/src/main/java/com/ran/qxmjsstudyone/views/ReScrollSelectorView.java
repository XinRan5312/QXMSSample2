package com.ran.qxmjsstudyone.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by houqixin on 2017/7/6.
 */

public class ReScrollSelectorView extends View {
    private int mItemWith;
    private int mVisibleItems;
    private int mViewWith;
    private int mViewHeight;
    private ArrayList<String> mSouce = new ArrayList<>();
    private Paint mNormalPaint;
    private Paint mSelectedPaint;
    private float mOffset;
    private boolean mIsFirstDraw = true;
    private int mSelectedItem;
    private Rect mRect = new Rect();
    private float mDownX;
    private int mNormalTextW;
    private int mNormalTextH;
    private int mSmallTextW;
    private int mSmallTextH;
    private Paint mSmallPaint;

    public ReScrollSelectorView(Context context) {
        this(context, null);
    }

    public ReScrollSelectorView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public ReScrollSelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsFirstDraw) {
            mViewWith = getWidth();
            mViewHeight = getHeight();
            mItemWith = mViewWith / mVisibleItems;
            mIsFirstDraw = false;
        }
        if (mSelectedItem >= 0 && mSelectedItem < mSouce.size()) {
            drawTexts(canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float toX = event.getX();

                if (mSelectedItem > 0 && mSelectedItem < mSouce.size() - 1) {
                    mOffset = toX - mDownX;
                } else {
                    mOffset = (toX - mDownX) / 2;
                }
                if (mOffset > 0) {//向右滑动
                    if (mOffset >= mItemWith && mSelectedItem > 0) {
                        mOffset = 0;
                        mSelectedItem--;
                        mDownX = toX;
                    }

                } else {
                    if (Math.abs(mOffset) >= mItemWith && mSelectedItem < mSouce.size() - 1) {
                        mOffset = 0;
                        mSelectedItem++;
                        mDownX = toX;
                    }
                }

                invalidate();
                break;

            case MotionEvent.ACTION_UP:

                mOffset = 0;
                invalidate();

                break;
            default:
                break;


        }

        return super.onTouchEvent(event);
    }

    private void drawTexts(Canvas canvas) {

        if (mSelectedItem > 0 && mSelectedItem < mSouce.size() - 1) {
            String beforeText = mSouce.get(mSelectedItem - 1);
            String afterText = mSouce.get(mSelectedItem + 1);
            mNormalPaint.getTextBounds(beforeText, 0, beforeText.length(), mRect);
            int w1 = mRect.width();
            mNormalPaint.getTextBounds(afterText, 0, afterText.length(), mRect);
            int w2 = mRect.width();
            mNormalTextW = (w1 + w2) / 2;
            mNormalTextH = mRect.height();

        }

        if (mSelectedItem == 0 || mSelectedItem == 1) {
            for (int i = 0; i < mSouce.size() - 1; i++) {
                if (mNormalTextH > 0 && i != mSelectedItem &&
                        i != mSelectedItem + 2) {
                    canvas.drawText(mSouce.get(i), (i - mSelectedItem) * mItemWith + mViewWith / 2 - mNormalTextW / 2 + mOffset,
                            mViewHeight / 2 + mNormalTextH / 2, mNormalPaint);
                }

            }
            int n = mSelectedItem + 2;
            canvas.drawText(mSouce.get(n), (n - mSelectedItem) * mItemWith + mViewWith / 2 - mNormalTextW / 2 + mOffset,
                    mViewHeight / 2 + mNormalTextH / 2, mSmallPaint);
        } else if (mSelectedItem == mSouce.size() - 1 || mSelectedItem == mSouce.size() - 2) {
            for (int i = 0; i < mSouce.size() - 1; i++) {
                if (mNormalTextH > 0 && i != mSelectedItem && i != mSelectedItem - 2) {
                    canvas.drawText(mSouce.get(i), (i - mSelectedItem) * mItemWith + mViewWith / 2 - mNormalTextW / 2 + mOffset,
                            mViewHeight / 2 + mNormalTextH / 2, mNormalPaint);
                }

            }

            int m = mSelectedItem - 2;
            canvas.drawText(mSouce.get(m), (m - mSelectedItem) * mItemWith + mViewWith / 2 - mNormalTextW / 2 + mOffset,
                    mViewHeight / 2 + mNormalTextH / 2, mSmallPaint);
        } else {
            for (int i = 0; i < mSouce.size() - 1; i++) {
                if (mNormalTextH > 0 && i != mSelectedItem && i != mSelectedItem - 2 &&
                        i != mSelectedItem + 2) {
                    canvas.drawText(mSouce.get(i), (i - mSelectedItem) * mItemWith + mViewWith / 2 - mNormalTextW / 2 + mOffset,
                            mViewHeight / 2 + mNormalTextH / 2, mNormalPaint);
                }

            }
            int n = mSelectedItem + 2;
            int m = mSelectedItem - 2;
            canvas.drawText(mSouce.get(n), (n - mSelectedItem) * mItemWith + mViewWith / 2 - mNormalTextW / 2 + mOffset,
                    mViewHeight / 2 + mNormalTextH / 2, mSmallPaint);
            canvas.drawText(mSouce.get(m), (m - mSelectedItem) * mItemWith + mViewWith / 2 - mNormalTextW / 2 + mOffset,
                    mViewHeight / 2 + mNormalTextH / 2, mSmallPaint);
        }

        String selectedText = mSouce.get(mSelectedItem);
        mSelectedPaint.getTextBounds(mSouce.get(mSelectedItem), 0, selectedText.length(), mRect);
        int selectedTextW = mRect.width();
        int selectedTextH = mRect.height();
        canvas.drawText(selectedText, mViewWith / 2 - selectedTextW / 2 + mOffset, mViewHeight / 2 + selectedTextH / 2, mSelectedPaint);


    }

    private void init() {
        setWillNotDraw(false);
        setClickable(true);
        mVisibleItems = 5;
        for (int i = 0; i < 12; i++) {
            mSouce.add(i, "" + i + "期");
        }
        mSelectedItem = mSouce.size() / 2;
        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNormalPaint.setColor(Color.BLACK);
        mNormalPaint.setTextSize(dip2px(18));

        mSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedPaint.setColor(Color.RED);
        mSelectedPaint.setTextSize(dip2px(26));

        mSmallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallPaint.setColor(Color.GRAY);
        mSmallPaint.setTextSize(dip2px(12));
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 改变中间可见文字的数目
     *
     * @param visibleItems 可见数
     */
    public void setVisibleSize(int visibleItems) {
        if (visibleItems > 0) {
            this.mVisibleItems = visibleItems;
            invalidate();
        }

    }


    /**
     * 向左移动一个单元
     */
    public void moveLeftStep() {
        if (mSelectedItem < mSouce.size() - 1) {
            mSelectedItem++;
            invalidate();
        }

    }

    /**
     * 向右移动一个单元
     */
    public void moveRightStep() {
        if (mSelectedItem > 0) {
            mSelectedItem--;
            invalidate();
        }
    }

    /**
     * 设置个数据源
     *
     * @param strings 数据源String集合
     */
    public void setData(ArrayList<String> strings) {
        this.mSouce = strings;
        mSelectedItem = strings.size() / 2;
        invalidate();
    }

    /**
     * 获得被选中的文本
     *
     * @return 被选中的文本
     */
    public String getSelectedString() {
        if (mSouce.size() != 0) {
            return mSouce.get(mSelectedItem);
        }
        return null;
    }
}
