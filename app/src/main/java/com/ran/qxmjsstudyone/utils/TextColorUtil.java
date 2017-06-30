package com.ran.qxmjsstudyone.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by houqixin on 2017/6/30.
 */

public class TextColorUtil {
    private SpannableStringBuilder stringBuilder;
    private TextView textView;

    public TextColorUtil(TextView textView) {
        stringBuilder = new SpannableStringBuilder();
        this.textView = textView;

    }

    public TextColorUtil addStringAndCoror(String text, String color) {
        stringBuilder.append(createOneCommonStr(text, color));
        return this;

    }

    public TextColorUtil addStringAndCororAndUrl(String text, String color, final SpanStringOnClickListener listener) {
        stringBuilder.append(createOneUrlStr(text, color, listener));
        return this;

    }

    private SpannableString createOneCommonStr(String text, String color) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));

        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private SpannableString createOneUrlStr(final String text, final String color, final SpanStringOnClickListener listener) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (listener != null) {
                    listener.onSpanURLClick(text);

                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor(color));
                ds.setUnderlineText(false);
            }
        }, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));

        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    /**
     * 自定义字体大小，有链接的
     */
    public void setTextWithSizeAndLink(int textSize) {
        if (stringBuilder.length() > 0&&textSize>6) {

            stringBuilder.setSpan(new AbsoluteSizeSpan(textSize, true), 0, stringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(stringBuilder);

            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }

    }
    /**
     * 代表是默认字体大小，无链接的
     */
    public void setTextCommon() {
        if (stringBuilder.length() > 0) {

            stringBuilder.setSpan(new AbsoluteSizeSpan(12, true), 0, stringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(stringBuilder);

        }
    }

    /**
     * 代表是默认字体大小，有链接的
     */
    public void setTextWithLink() {
        if (stringBuilder.length() > 0) {

            stringBuilder.setSpan(new AbsoluteSizeSpan(12, true), 0, stringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(stringBuilder);

            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public interface SpanStringOnClickListener {
        void onSpanURLClick(String title);
    }
}
