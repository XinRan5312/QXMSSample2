package com.ran.qxmjsstudyone.adapter;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ran.qxmjsstudyone.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by houqixin on 2017/7/17.
 */

public class WebListAdapter extends BaseAdapter {
    private ArrayList<String> mList;
    private Context mContext;
    private HashMap<Integer, Boolean> mapArraws = new HashMap<>();


    public WebListAdapter(ArrayList<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_view, null);
        }
        TextView cash = (TextView) view.findViewById(R.id.cash);
        final TextView hit = (TextView) view.findViewById(R.id.cash_hint);
        final ImageView imageView = (ImageView) view.findViewById(R.id.icon_spinder);
        imageView.setTag(i);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getTag().equals(i)) {
                    // 0f -> 180f,也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。

                    ObjectAnimator anim = null;
                    if (!mapArraws.containsKey(i)) {
                        mapArraws.put(i, false);
                    }
                    if (!mapArraws.get(i)) {
                        mapArraws.put(i, true);
                        anim = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 180f);
                    } else {
                        mapArraws.put(i, false);
                        anim = ObjectAnimator.ofFloat(imageView, "rotation", 180f, 360f);
                    }
                    // 动画的持续时间，执行多久？
                    anim.setDuration(500);
                    anim.start();
                }
            }
        });

        cash.setText(mList.get(i));
        view.setTag(i);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(view.getTag().equals(i)){
//                    if(hit.getVisibility()==View.VISIBLE){
//                        hit.setVisibility(View.GONE);
//                    }else{
//                        hit.setVisibility(View.VISIBLE);
//                    }
//
//                }
//            }
//        });


        return view;
    }

}
