package com.mjsfax.pay;

import com.mjsfax.rx.obervable.MSSingle;

import org.json.JSONObject;

/**
 * Created by lenovo on 2017/8/14.
 */

public interface IMSPay {
    int PAY_ALIPAY = 1;
    int PAY_WECHAT = 2;
    int PAY_RESULT_SUCCESS = 100;
    int PAY_RESULT_FAILURE = 200;

    MSSingle<JSONObject> queryPolicyPayInfo(String orderId, int payType);
}
