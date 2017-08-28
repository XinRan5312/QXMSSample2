package com.ran.qxmjsstudyone.utils;

import android.content.Context;

/**
 * Created by houqixin on 2017/8/28.
 */

public class UIUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((pxValue - 0.5f) / scale);
    }
}
