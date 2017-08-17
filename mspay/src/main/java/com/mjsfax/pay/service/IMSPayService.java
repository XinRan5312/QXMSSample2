package com.mjsfax.pay.service;

import android.app.Activity;
import android.content.Context;

import com.mjsfax.rx.obervable.MSSingle;
import com.tencent.mm.opensdk.modelpay.PayReq;

/**
 * Created by lenovo on 2017/8/15.
 */

public interface IMSPayService {
    MSSingle<String> aliPay(Activity activity, String orderInfo);

    void wechatPay(Context context, PayReq req);
}
