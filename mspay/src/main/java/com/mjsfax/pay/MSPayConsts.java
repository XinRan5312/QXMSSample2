package com.mjsfax.pay;

/**
 * Created by lenovo on 2017/8/15.
 */

public class MSPayConsts {
    public static final String WECHAT_APPID = "wxf4103a4b13aeed8a";

    public static class WechatPayResult {
        public static final int WECHAT_PAY_SUCCESS = 0;
        public static final int WECHAT_PAY_FAILURE = -1;
        public static final int WECHAT_PAY_CANCEL = -2;
    }

    public static class AliPayResult {
        public static final int ALI_PAY_SUCCESS = 9000;//	订单支付成功
        public static final int ALI_PAY_WAITING = 8000;//正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
        public static final int ALI_PAY_FAILURE = 4000;//订单支付失败
        public static final int ALI_PAY_REPEAT_ERROR = 5000;//重复请求
        public static final int ALI_PAY_CANCEL = 6001;//用户中途取消
        public static final int ALI_PAY_NET_WORK_ERROR = 6002;//网络连接出错
        public static final int ALI_PAY_UNKNOWN = 6004; //支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
    }
}
