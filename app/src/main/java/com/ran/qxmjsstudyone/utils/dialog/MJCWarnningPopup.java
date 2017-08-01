package com.ran.qxmjsstudyone.utils.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;



/**
 * Created by houqixin on 2017/7/31.
 * 一个按钮或者两个按钮 内容就是一个TextView的都可以直接使用这个类
 */

public class MJCWarnningPopup extends MJCBasePopupWindow<String>{

    public MJCWarnningPopup(Activity context, String viewData) {
        super(context, viewData);
        TextView textView=new TextView(context);
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(-2,-2);
        params.leftMargin= dip2px(16f);
        params.rightMargin=dip2px(16f);
        params.topMargin=dip2px(32f);
        params.bottomMargin=dip2px(32f);
        textView.setLayoutParams(params);
        textView.setLineSpacing(2,1.6f);
        textView.setTextColor(Color.parseColor("#666666"));
        textView.setTextSize(14);
        customView(textView);
    }

    @Override
    protected void onWillShow() {
        setTitle("提醒").setSingleButtonText("确定");
    }

    @Override
    protected void initCustomViewData(View mCustomView, java.lang.String viewData) {
           if(mCustomView instanceof TextView){
               TextView tv= (TextView) mCustomView;
               tv.setText(viewData);
           }
    }
    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
