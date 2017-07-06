package com.ran.qxmjsstudyone;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ran.qxmjsstudyone.base.BaseActvity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActvity {
    @BindView(R.id.tv)
    TextView mToasTv;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    @OnClick(R.id.tv)
    public void viewsOnClick(View view) {
        if (view.getId() == R.id.tv) {

            Toast.makeText(MainActivity.this, "hello butterKnife", Toast.LENGTH_LONG).show();
        }
    }
}
