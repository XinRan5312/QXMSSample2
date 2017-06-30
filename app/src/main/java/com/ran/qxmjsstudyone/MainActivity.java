package com.ran.qxmjsstudyone;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ran.qxmjsstudyone.base.BaseActvity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActvity {
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

            Toast.makeText(MainActivity.this,"hello butterKnife",Toast.LENGTH_LONG).show();
        }
    }
}
