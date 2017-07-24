package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import com.ran.qxmjsstudyone.R;

/**
 * Created by houqixin on 2017/7/24.
 */

public class CardAndPhoneEditText extends android.support.v7.widget.AppCompatEditText {
    private String separator = " ";
    private boolean isFirst = false;
    private String type="phone";

    public CardAndPhoneEditText(Context context) {
        super(context);
        init(null);
    }

    public CardAndPhoneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        if(attrs!=null){
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.CardAndPhoneEditText);
            type = a.getString(R.styleable.CardAndPhoneEditText_typeCard);
            a.recycle();
        }

        if (type.equals("id_card")) {
            setInputType(InputType.TYPE_CLASS_TEXT);
        }  else {
            setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFirst) {//这几句要加，不然每输入一个值都会执行两次onTextChanged()，导致堆栈溢出，原因不明
                    isFirst = false;
                    return;
                }
                isFirst = true;
                Log.i("tag", "onTextChanged()");
                String resultString = "";
                int index = 0;
                String tempString = s.toString().replace(" ", "");
                if (type.equals("id_card")) {
                    if ((index + 3) < tempString.length()) {
                        resultString += (tempString.substring(index, index + 3) + separator);
                        index += 3;
                    }
                    if ((index + 3) < tempString.length()) {
                        resultString += (tempString.substring(index, index + 3) + separator);
                        index += 3;
                    }
                    while ((index + 4) < tempString.length()) {
                        resultString += (tempString.substring(index, index + 4) + separator);
                        index += 4;
                    }
                } else if (type.equals("band_card")) {
                    while ((index + 4) < tempString.length()) {
                        resultString += (tempString.substring(index, index + 4) + separator);
                        index += 4;
                    }
                } else {

                    if ((index + 3) < tempString.length()) {
                        resultString += (tempString.substring(index, index + 3) + separator);
                        index += 3;
                    }
                    while ((index + 4) < tempString.length()) {
                        resultString += (tempString.substring(index, index + 4) + separator);
                        index += 4;
                    }
                }
                resultString += tempString.substring(index, tempString.length());
                CardAndPhoneEditText.this.setText(resultString);
                //此语句不可少，否则输入的光标会出现在最左边，不会随输入的值往右移动
                CardAndPhoneEditText.this.setSelection(resultString.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    public void setSeparator(String separator) {
        this.separator = separator;
        invalidate();
    }

    public void setType(String type) {
        this.type = type;
        invalidate();
    }
}

