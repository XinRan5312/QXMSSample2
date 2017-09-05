package com.ran.qxmjsstudyone;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ran.qxmjsstudyone.base.BaseActvity;
import com.ran.qxmjsstudyone.views.DialView;
import com.ran.qxmjsstudyone.views.InsuranceAndInvestPopupWindow;
import com.ran.qxmjsstudyone.views.InsuranceInvestProgressView;

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
    @BindView(R.id.insurance_view)
    InsuranceInvestProgressView insuranceView;
    private InsuranceAndInvestPopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        ButterKnife.bind(this);
        popupWindow = new InsuranceAndInvestPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DialActivity.this, "Popuo", Toast.LENGTH_LONG).show();
            }
        });
        insuranceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAtLocation(DialActivity.this.findViewById(R.id.root_view_container),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        dialView.setOnAngleColorListener(new DialView.OnAngleColorListener() {
            @Override
            public void onAngleColorListener(int red, int green) {
                Color color = new Color();
                int c = color.argb(150, red, green, 0);
                rootViewContainer.setBackgroundColor(c);
            }
        });
        dialView.change(200);
    }
}
