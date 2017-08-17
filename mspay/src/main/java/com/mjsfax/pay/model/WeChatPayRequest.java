package com.mjsfax.pay.model;

import com.mjsfax.pay.MSPayConsts;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.json.JSONObject;

/**
 * Created by lenovo on 2017/8/15.
 */

public class WeChatPayRequest {

    public static PayReq parsePayRequest(JSONObject jsonObject) {
        PayReq request = new PayReq();
        request.appId = MSPayConsts.WECHAT_APPID;
        request.partnerId = "1900000109";
        request.prepayId = "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr = "1101000000140429eb40476f8896f4c9";
        request.timeStamp = "1398746574";
        request.sign = "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        return request;
    }
}
