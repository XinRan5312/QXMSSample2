package com.ran.qxmjsstudyone;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ran.qxmjsstudyone.base.BaseActvity;
import com.ran.qxmjsstudyone.views.ReScrollSelectorView;
import com.ran.qxmjsstudyone.views.ScrollSelectorView;
import com.ran.qxmjsstudyone.views.TriangleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActvity {
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.cash_hint)
    TextView cashHint;
    @BindView(R.id.cash)
    TextView cash;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.min_cash)
    TextView minCash;
    @BindView(R.id.max_cash)
    TextView maxCash;
    int min = 100;
    int max = 5000;
    @BindView(R.id.triangle)
    TriangleView triangle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void initData() {
        minCash.setText("" + min);
        maxCash.setText("" + max);
        cash.setVisibility(View.GONE);

    }

    private void initListener() {
        triangle.setVisibility(View.GONE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                unDataCash(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                unDataCash(seekBar.getProgress());

            }
        });
    }

    public void unDataCash(int progress) {
        int selectCash = max * progress / 100;

        if (selectCash < min) {
            cash.setVisibility(View.GONE);
        } else {
            cash.setVisibility(View.VISIBLE);
            cash.setText("" + selectCash);
        }
    }

}
