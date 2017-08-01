package com.ran.qxmjsstudyone;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import com.ran.qxmjsstudyone.base.BaseActvity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by houqixin on 2017/7/10.
 */

public class VertifyRepayCircleActivity extends BaseActvity {
    @BindView(R.id.tv_gradient_drawble)
    TextView tvGradientDrawble;
    private int mDisabledColor;
    private int mPressedColor;
    private int mSelectedColor;
    private int mFocusedColor;
    private int mDefaultColor;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_plane);
        ButterKnife.bind(this);
        mDefaultColor=R.color.text_color_red;
        mDisabledColor=R.color.text_color_999;
        mFocusedColor=R.color.text_color_fff;
        mPressedColor=R.color.text_color_theme;
        mSelectedColor=R.color.text_color_333;
        ColorStateList strokeColor=create();
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(2, strokeColor);
        drawable.setCornerRadius(8f);
        drawable.setColor(strokeColor);
        tvGradientDrawble.setBackground(drawable);
    }
    public ColorStateList create() {

        int[] colors = new int[]{mDisabledColor,mPressedColor,mDefaultColor,mSelectedColor, mDefaultColor};
        int[][] states = new int[5][];
        states[0] = new int[]{android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{android.R.attr.state_selected};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{};
        return new ColorStateList(states, colors);
    }
//    public StateListDrawable create() {
//        StateListDrawable selector = new StateListDrawable();
//        if (hasSetDisabledDrawable)
//            selector.addState(new int[]{-android.R.attr.state_enabled}, mDisabledDrawable);
//        if (hasSetPressedDrawable)
//            selector.addState(new int[]{android.R.attr.state_pressed}, mPressedDrawable);
//        if (hasSetSelectedDrawable)
//            selector.addState(new int[]{android.R.attr.state_selected}, mSelectedDrawable);
//        if (hasSetFocusedDrawable)
//            selector.addState(new int[]{android.R.attr.state_focused}, mFocusedDrawable);
//        selector.addState(new int[]{}, mDefaultDrawable);
//        return selector;
//    }
}
