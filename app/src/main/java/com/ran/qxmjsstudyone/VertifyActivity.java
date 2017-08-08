package com.ran.qxmjsstudyone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;

import com.ran.qxmjsstudyone.base.BaseActvity;
import com.ran.qxmjsstudyone.views.CardAndPhoneEditText;

import butterknife.BindView;


/**
 * Created by houqixin on 2017/7/10.
 */

public class VertifyActivity extends BaseActvity {
    @BindView(R.id.card_edit_view)
    CardAndPhoneEditText cardEditView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertify);
        cardEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
