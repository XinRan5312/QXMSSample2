package com.ran.qxmjsstudyone.utils.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by houqixin on 2017/7/31.
 *
 * MSPopup的代理类，扩展了一些方法，便于使用
 * 另外以后万一不用MSPopup了 只需要改动这个类就好
 * 不需要改动其它的所有使用类了
 */

public abstract class MJCBasePopupWindow<T> {
    private MSPopup msPopup;
    protected Activity mContext;
    private View mCustomView;
    private T mViewData;

    public MJCBasePopupWindow(Activity context, T viewData) {
        this.mContext=context;
        this.mViewData=viewData;
        msPopup=MSPopup.create(context);
    }
    public MJCBasePopupWindow customViewId(int layId){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mCustomView = inflater.inflate(layId, null);
        initCustomViewData(mCustomView,mViewData);
        return this;
    }
    public MJCBasePopupWindow customView(View view){
        mCustomView = view;
        initCustomViewData(mCustomView,mViewData);
        return this;
    }
    public MJCBasePopupWindow setTitle(String title) {
        msPopup.setTitle(title);
        return this;
    }

    public MJCBasePopupWindow setSingleButtonText(String buttonText) {
       msPopup.setSingleButtonText(buttonText);
        return this;
    }

    public MJCBasePopupWindow setOnSingleButtonClickListener(View.OnClickListener listener) {
        msPopup.setOnSingleButtonClickListener(listener);
        return this;
    }

    public MJCBasePopupWindow setDoubleButtonText(String leftBtnText, String rightBtnText) {
     msPopup.setDoubleButtonText(leftBtnText,rightBtnText);
        return this;
    }

    public MJCBasePopupWindow setOnLeftButtonClickListener(View.OnClickListener listener) {
        msPopup.setOnLeftButtonClickListener(listener);
        return this;
    }

    public MJCBasePopupWindow setOnRightButtonClickListener(View.OnClickListener listener) {
        msPopup.setOnRightButtonClickListener(listener);
        return this;
    }

    public MJCBasePopupWindow show() {
        onWillShow();
       msPopup.show(mContext,mCustomView);
        return this;
    }

    protected  void onWillShow(){

    }

    public void dismiss() {
       msPopup.dismiss();
        hasDismiss();
    }


    protected void hasDismiss() {

    }

    protected abstract void initCustomViewData(View mCustomView, T viewData);
}
