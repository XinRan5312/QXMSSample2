package com.ran.qxmjsstudyone;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.ran.qxmjsstudyone.base.BaseActvity;
import com.ran.qxmjsstudyone.views.DialView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by houqixin on 2017/8/28.
 */

public class DialActivity extends BaseActvity {
    @BindView(R.id.dial_view)
    DialView dialView;
    @BindView(R.id.root_view_container)
    LinearLayout rootViewContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialView.setOnAngleColorListener(new DialView.OnAngleColorListener() {
            @Override
            public void onAngleColorListener(int red, int green) {
                Color color=new Color();
                int c=color.argb(150, red, green, 0);
                rootViewContainer.setBackgroundColor(c);
            }
        });
        dialView.change(200);
    }
}
