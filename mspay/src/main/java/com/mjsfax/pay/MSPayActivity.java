package com.mjsfax.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mjsfax.logs.MSLogger;
import com.mjsfax.pay.model.AliPayRequest;
import com.mjsfax.pay.model.MSPayResult;
import com.mjsfax.pay.model.WeChatPayRequest;
import com.mjsfax.pay.model.WechatPayResponse;
import com.mjsfax.pay.service.IMSPayService;
import com.mjsfax.pay.service.MSPayService;
import com.mjsfax.rx.broadcast.RxEventHandler;
import com.mjsfax.rx.obervable.MSDisposable;
import com.mjsfax.utils.MSToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;


public class MSPayActivity extends Activity implements View.OnClickListener {
    private static final String ORDER_ID = "order_id";
    private String mOrderId;
    private LinearLayout mLlAlipay;
    private LinearLayout mLlWechat;
    private MSDisposable mDisposable = MSDisposable.empty();
    private MSDisposable mWechatDisposable = MSDisposable.empty();
    private MSDisposable mAliPayDisposable = MSDisposable.empty();
    private IMSPayService mPayService;
    private Context mContext;
    private ImageView mIvClose;

    public static void pay(Context context, String order) {
        if (context == null) {
            MSLogger.e("Context is null");
            return;
        }
        if (TextUtils.isEmpty(order)) {
            MSLogger.e("order is empty");
            return;
        }
        Intent intent = new Intent(context, MSPayActivity.class);
        intent.putExtra(ORDER_ID, order);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mspay);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            MSLogger.e("intent is null");
            finish();
            return;
        }
        mOrderId = intent.getStringExtra(ORDER_ID);
        if (TextUtils.isEmpty(mOrderId)) {
            MSLogger.e("orderId is Empty");
            finish();
            return;
        }
        mPayService = MSPayService.getInstance();
        mContext = this;
    }
    private void initView() {
        mLlAlipay = ((LinearLayout) findViewById(R.id.ll_alipay));
        mLlWechat = ((LinearLayout) findViewById(R.id.ll_wechat_pay));
        mIvClose = ((ImageView) findViewById(R.id.iv_close));
    }

    private void initListener() {
        mLlAlipay.setOnClickListener(this);
        mLlWechat.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mWechatDisposable.clear();
        mWechatDisposable = RxEventHandler.getInstance().toBroadcast(WechatPayResponse.class).subscribe(new Consumer<WechatPayResponse>() {
            @Override
            public void accept(WechatPayResponse wechatPayResponse) throws Exception {
                BaseResp resp = wechatPayResponse.resp;
                int errorCode = resp.errCode;
                if (errorCode == MSPayConsts.WechatPayResult.WECHAT_PAY_SUCCESS) {
                    onPayResult(IMSPay.PAY_WECHAT, IMSPay.PAY_RESULT_SUCCESS, "支付成功");
                } else if (errorCode == MSPayConsts.WechatPayResult.WECHAT_PAY_CANCEL) {
                    onPayResult(IMSPay.PAY_WECHAT, IMSPay.PAY_RESULT_FAILURE, "支付取消");
                } else if (errorCode == MSPayConsts.WechatPayResult.WECHAT_PAY_FAILURE) {
                    onPayResult(IMSPay.PAY_WECHAT, IMSPay.PAY_RESULT_FAILURE, "支付失败");
                } else {
                    MSLogger.e("errorCode = " + errorCode + " , message = " + resp.errStr);
                    onPayResult(IMSPay.PAY_WECHAT, IMSPay.PAY_RESULT_FAILURE, String.valueOf(resp.errStr));
                }
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (R.id.ll_alipay == vId) {
            //支付宝支付
            queryPolicyPayInfo(IMSPay.PAY_ALIPAY);
        } else if (R.id.ll_wechat_pay == vId) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(MSPayActivity.this, null);
            // 将该app注册到微信
            msgApi.registerApp(MSPayConsts.WECHAT_APPID);
            if (!msgApi.isWXAppInstalled()) {
                MSToastUtils.showShort(mContext, "您尚未安装微信客户端");
                return;
            }
            //微信支付;
            queryPolicyPayInfo(IMSPay.PAY_WECHAT);
        } else if (R.id.iv_close == vId) {
            finish();
        } else {
            MSLogger.e("UnKnown OnClick listener ");
            return;
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_mspay_page_hidden);
    }

    private void queryPolicyPayInfo(final int id) {
        mDisposable.clear();
        mDisposable = MSPayService.getInstance().queryPolicyPayInfo(mOrderId, id).subscribeWith(new DisposableSingleObserver<JSONObject>() {
            @Override
            public void onSuccess(JSONObject value) {
                if (id == IMSPay.PAY_ALIPAY) {
                    aliPay(AliPayRequest.getOrderInfo(value));
                    return;
                }
                if (id == IMSPay.PAY_WECHAT) {
                    wechatPay(WeChatPayRequest.parsePayRequest(value));
                }
            }

            @Override
            public void onError(Throwable e) {
                String result = e.getMessage();
                MSLogger.e("queryPolicyPayInfo error = " + result);
                onPayResult(id, IMSPay.PAY_RESULT_FAILURE, e.getMessage());
                finish();
            }
        });
    }

    private void aliPay(String orderInfo) {
        mAliPayDisposable.clear();
        mAliPayDisposable = mPayService.aliPay(MSPayActivity.this, orderInfo).subscribeWith(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String value) {
                onPayResult(IMSPay.PAY_ALIPAY, IMSPay.PAY_RESULT_SUCCESS, value);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                MSLogger.e("aliPay onError = " + e.getMessage());
                onPayResult(IMSPay.PAY_ALIPAY, IMSPay.PAY_RESULT_FAILURE, e.getMessage());
                finish();
            }
        });
    }

    public void onPayResult(int payType, int payResultCode, String message) {
        MSPayResult result = new MSPayResult();
        result.payType = payType;
        result.payResultCode = payResultCode;
        result.payResultMessage = message;
        RxEventHandler.getInstance().broadcast(result);
    }
    private void wechatPay(PayReq req) {
        mPayService.wechatPay(mContext, req);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.clear();
            mDisposable = null;
        }
        if (mWechatDisposable != null) {
            mWechatDisposable.clear();
            mWechatDisposable = null;
        }
        if (mAliPayDisposable != null) {
            mAliPayDisposable.clear();
            mAliPayDisposable = null;
        }
    }
}
