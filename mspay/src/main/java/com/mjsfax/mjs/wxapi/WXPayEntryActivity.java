package com.mjsfax.mjs.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mjsfax.pay.MSPayConsts;
import com.mjsfax.pay.R;
import com.mjsfax.pay.model.WechatPayResponse;
import com.mjsfax.rx.broadcast.RxEventHandler;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        api = WXAPIFactory.createWXAPI(this, MSPayConsts.WECHAT_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        //...
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            WechatPayResponse response = new WechatPayResponse();
            response.resp = baseResp;
            RxEventHandler.getInstance().broadcast(response);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        api = null;
    }
}
