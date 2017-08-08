package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ran.qxmjsstudyone.R;


/**
 * Created by houqixin on 2017/8/7.
 */

public class SwithView extends LinearLayout {
    private RelativeLayout rootView;
    private TextView leftTextView;
    private TextView rightTextView;
    private TextView thumbView;

    public SwithView(Context context) {
        super(context);
        init(context);
    }

    public SwithView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwithView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View chirdView = inflate(context, R.layout.layout_switch_view, this);
        rootView = (RelativeLayout) chirdView.findViewById(R.id.root_view);
        leftTextView = (TextView) chirdView.findViewById(R.id.text_left);
        rightTextView = (TextView) chirdView.findViewById(R.id.text_right);
        thumbView = (TextView) chirdView.findViewById(R.id.thumb_view);

        rootView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Log.e("onTouch", "rootView");
                int action = event.getAction();
                float downX = 0;
                float moveOff = 0;
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float toX = event.getX();
                        moveOff = toX - downX;
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) thumbView.getLayoutParams();
                        if (moveOff > 0) {
                            if(params.leftMargin<rootView.getMeasuredWidth()-thumbView.getMeasuredWidth())
                            params.leftMargin = (int) (params.leftMargin + moveOff);
                        }
                        thumbView.setLayoutParams(params);
                        downX = toX;
                        break;

                    case MotionEvent.ACTION_UP:

                        invalidate();

                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        thumbView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("onTouch", "thumbView");
                return false;
            }
        });
        leftTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("onTouch", "leftTextView");
                return false;
            }
        });
        rightTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("onTouch", "rightTextView");
                return false;
            }
        });
    }
}
