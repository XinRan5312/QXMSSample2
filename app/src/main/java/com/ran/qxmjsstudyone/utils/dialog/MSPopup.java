package com.ran.qxmjsstudyone.utils.dialog;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ran.qxmjsstudyone.R;


public class MSPopup {
    private PopupWindow mPopupWindow;
    private View mRootView;
    private TextView mTvTitle;
    private FrameLayout mFlContainer;
    private TextView mTvLeftButton;
    private View mButtonDivider;
    private TextView mTvRightButton;

    public static MSPopup create(Context context) {
        MSPopup popup = new MSPopup(context);
        return popup;
    }

    private MSPopup(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mRootView = inflater.inflate(R.layout.popup_base, null);
        mTvTitle = (TextView)mRootView.findViewById(R.id.tv_title);
        mTvTitle.setVisibility(View.GONE);
        mFlContainer = (FrameLayout)mRootView.findViewById(R.id.fl_container);
        mTvLeftButton = (TextView)mRootView.findViewById(R.id.tv_left_button);
        mTvLeftButton.setVisibility(View.GONE);
        mButtonDivider = mRootView.findViewById(R.id.tv_button_divider);
        mButtonDivider.setVisibility(View.GONE);
        mTvRightButton = (TextView)mRootView.findViewById(R.id.tv_right_button);
    }

    public MSPopup setTitle(String title) {
        mTvTitle.setText(title);
        mTvTitle.setVisibility(View.VISIBLE);
        return this;
    }

    public MSPopup setSingleButtonText(String buttonText) {
        mTvRightButton.setText(buttonText);
        mTvRightButton.setBackgroundResource(R.drawable.shape_popup_button);
        return this;
    }

    public MSPopup setOnSingleButtonClickListener(View.OnClickListener listener) {
        mTvRightButton.setOnClickListener(listener);
        return this;
    }

    public MSPopup setDoubleButtonText(String leftBtnText, String rightBtnText) {
        mTvLeftButton.setText(leftBtnText);
        mTvLeftButton.setVisibility(View.VISIBLE);
        mButtonDivider.setVisibility(View.VISIBLE);
        mTvRightButton.setText(rightBtnText);
        return this;
    }

    public MSPopup setOnLeftButtonClickListener(View.OnClickListener listener) {
        mTvLeftButton.setOnClickListener(listener);
        return this;
    }

    public MSPopup setOnRightButtonClickListener(View.OnClickListener listener) {
        mTvRightButton.setOnClickListener(listener);
        return this;
    }

    public MSPopup show(Activity parent, View contentView) {
        mFlContainer.addView(contentView);
        View decorView = parent.getWindow().getDecorView();
        mPopupWindow = new PopupWindow(mRootView, decorView.getMeasuredWidth(), decorView.getMeasuredHeight(), true);
        mPopupWindow.showAtLocation(decorView, Gravity.CENTER, 0, 0);
        return this;
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }
}
