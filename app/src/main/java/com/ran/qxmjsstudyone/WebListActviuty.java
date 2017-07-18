package com.ran.qxmjsstudyone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ran.qxmjsstudyone.adapter.WebListAdapter;
import com.ran.qxmjsstudyone.base.BaseActvity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by houqixin on 2017/7/17.
 */

public class WebListActviuty extends BaseActvity {
    @BindView(R.id.list_view)
    ListView listView;
    private WebListAdapter mDapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<30;i++){
            list.add("hello"+i);
        }
        mDapter=new WebListAdapter(list,this);
        listView.setAdapter(mDapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.e("setOnItemClickListener",+view.getId()+"id:"+i);
//               mDapter.onItemClick(i);
//            }
//        });
    }
}
