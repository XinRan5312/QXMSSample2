package com.mjsfax.pay.model;

import com.mjsfax.logs.MSLogger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/8/15.
 */

public class AliPayRequest {
    public static String getOrderInfo(JSONObject jsonObject) {
        try {
            return jsonObject.getString("");
        } catch (JSONException e) {
            MSLogger.e("parseError");
        }
        return "";
    }
}
