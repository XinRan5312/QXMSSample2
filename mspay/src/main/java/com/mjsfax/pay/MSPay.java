package com.mjsfax.pay;

import android.content.Context;

import com.mjsfax.pay.service.MSPayService;

/**
 * Created by lenovo on 2017/8/14.
 */

public class MSPay {
    public static void init(IMSPay payCallBack){
        MSPayService.getInstance().initialize(payCallBack);
    }

    public static void pay(Context context, String orderId) {
        MSPayActivity.pay(context, orderId);
    }
}
