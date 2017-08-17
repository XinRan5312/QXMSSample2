package com.mjsfax.utils;

import android.text.TextUtils;

/**
 * Created by lenovo on 2016/12/22.
 */

public class MSBankCardUtils {
    public static boolean verifyBankCard(String bankCard) {
        if (TextUtils.isEmpty(bankCard)) {
            return false;
        }
        int count = bankCard.length();
        int sum = 0;
        for (int i = count - 1, j = 1; i >= 0; --i, j++) {
            int chars = bankCard.charAt(i) - 48;
            if (chars > 9 || chars < 0) {
                //判断输入的是否是整数
                return false;
            }
            if (j % 2 == 0) {
                //倒叙遍历,偶数位乘以2
                int result = chars * 2;
                if (result >= 10) {
                    //大于等于,包含10
                    //将个位和十位相加
                    result -= 9;
                }
                sum += result;
            } else {
                //奇数位直接相加
                sum += chars;
            }
        }

        return sum % 10 == 0;
    }
}
