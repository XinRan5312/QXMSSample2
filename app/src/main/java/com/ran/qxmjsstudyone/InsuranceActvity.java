package com.ran.qxmjsstudyone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ran.qxmjsstudyone.base.BaseActvity;
import com.ran.qxmjsstudyone.views.BaseSeletorPopupWindow;
import com.ran.qxmjsstudyone.views.SwitchButton;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by houqixin on 2017/8/4.
 */

public class InsuranceActvity extends BaseActvity {
    @BindView(R.id.datePicker_button)
    Button datePickerButton;
    @BindView(R.id.datePicker)
    TextView dateDisplay;
    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;
    @BindView(R.id.recycler_item_sb)
    SwitchButton recyclerItemSb;
    @BindView(R.id.popup_button)
    Button popupButton;
    private BaseSeletorPopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        ButterKnife.bind(this);
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG);

            }
        });
        recyclerItemSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("CompoundButton:", "" + isChecked);
                dateDisplay.setText("" + isChecked);
            }
        });

        LinearLayout view= (LinearLayout) getLayoutInflater().inflate(R.layout.insurance_popup_view,null);
        popupWindow= new BaseSeletorPopupWindow(this) {
            @Override
            protected void initViews(View contentView) {

            }
        };
        popupWindow.setContentView(view);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               popupWindow.showAsDropDown(popupButton,0,dip2px(8), Gravity.BOTTOM);
            }
        });
    }
    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        dateDisplay.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
}
