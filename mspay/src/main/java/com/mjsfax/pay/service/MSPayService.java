package com.mjsfax.pay.service;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.mjsfax.logs.MSLogger;
import com.mjsfax.pay.IMSPay;
import com.mjsfax.pay.MSPayConsts;
import com.mjsfax.rx.obervable.MSSingle;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lenovo on 2017/8/14.
 */

public class MSPayService implements IMSPay, IMSPayService {
    private static MSPayService mPayService;
    private IMSPay mPayImpl;
    private MSPayService(){

    }

    public static MSPayService getInstance(){
        if (mPayService == null){
            mPayService = new MSPayService();
        }
        return mPayService;
    }

    public void initialize(IMSPay pay) {
        mPayImpl = pay;
    }

    @Override
    public MSSingle<JSONObject> queryPolicyPayInfo(String orderId, int payType) {
        if (mPayImpl != null) {
            return mPayImpl.queryPolicyPayInfo(orderId, payType);
        }
        Single<JSONObject> single = Single.create(new SingleOnSubscribe<JSONObject>() {
            @Override
            public void subscribe(SingleEmitter<JSONObject> e) throws Exception {
                Throwable throwable = new Throwable("");
                e.onError(throwable);
            }
        });
        return new MSSingle<>(single);
    }


    @Override
    public MSSingle<String> aliPay(Activity activity, final String orderInfo) {
        final WeakReference<Activity> weakReference = new WeakReference<Activity>(activity);
        Single<String> single = Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                if (weakReference.get() == null) {
                    Throwable throwable = new Throwable("未知错误");
                    emitter.onError(throwable);
                    return;
                }
                PayTask alipay = new PayTask(weakReference.get());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                String resultStauts = result.get("resultStatus");
                if (TextUtils.isEmpty(resultStauts)) {
                    MSLogger.e("resultStauts is empty");
                    Throwable throwable = new Throwable("未知错误");
                    emitter.onError(throwable);
                    return;
                }
                int responseCode;
                try {
                    responseCode = Integer.parseInt(resultStauts);
                } catch (Exception e) {
                    emitter.onError(e);
                    return;
                }
                if (responseCode == MSPayConsts.AliPayResult.ALI_PAY_SUCCESS) {
                    emitter.onSuccess("订单支付成功");
                } else if (responseCode == MSPayConsts.AliPayResult.ALI_PAY_WAITING) {
                    emitter.onSuccess("正在处理中");
                } else if (responseCode == MSPayConsts.AliPayResult.ALI_PAY_FAILURE) {
                    Throwable throwable = new Throwable("订单支付失败");
                    emitter.onError(throwable);
                } else if (responseCode == MSPayConsts.AliPayResult.ALI_PAY_REPEAT_ERROR) {
                    Throwable throwable = new Throwable("重复请求");
                    emitter.onError(throwable);
                } else if (responseCode == MSPayConsts.AliPayResult.ALI_PAY_CANCEL) {
                    Throwable throwable = new Throwable("支付取消");
                    emitter.onError(throwable);
                } else if (responseCode == MSPayConsts.AliPayResult.ALI_PAY_NET_WORK_ERROR) {
                    Throwable throwable = new Throwable("网络连接出错");
                    emitter.onError(throwable);
                } else if (responseCode == MSPayConsts.AliPayResult.ALI_PAY_UNKNOWN) {
                    emitter.onSuccess("正在处理中,请稍后");
                } else {
                    String msg = result.get("result");
                    JSONObject object = new JSONObject(msg);
                    Throwable throwable = new Throwable(object.optString("msg", "未知错误"));
                    emitter.onError(throwable);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return new MSSingle<>(single);
    }

    @Override
    public void wechatPay(Context context, PayReq req) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        msgApi.registerApp(MSPayConsts.WECHAT_APPID);
        msgApi.sendReq(req);
    }
}
