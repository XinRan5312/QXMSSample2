package com.ran.qxmssampletwo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ran.qxmssampletwo.base.QXBaseActvity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends QXBaseActvity {
    @BindView(R.id.tv)
     TextView mToasTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @OnClick(R.id.tv)
    public void viewsOnClick(View view){
        if(view.getId()==R.id.tv){

            Toast.makeText(mContext,"hello butterKnife",Toast.LENGTH_LONG).show();
        }
    }
}
