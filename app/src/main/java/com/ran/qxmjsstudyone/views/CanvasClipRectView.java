package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by houqixin on 2017/10/9.
 * 主要是为了练习canvas.clipRect()方法
 * 该方法用于裁剪画布，也就是设置画布的显示区域
 调用clipRect()方法后，只会显示被裁剪的区域，之外的区域将不会显示
 该方法最后有一个参数Region.Op，表示与之前区域的区域间运算种类，如果没有这个参数，则默认为Region.Op.INTERSECT
 这几个参数的意义为：

 DIFFERENCE是第一次不同于第二次的部分显示出来
 REPLACE是显示第二次的
 REVERSE_DIFFERENCE 是第二次不同于第一次的部分显示
 INTERSECT交集显示
 UNION全部显示
 XOR补集 就是全集的减去交集生育部分显示
 */

public class CanvasClipRectView extends View {
    private Paint paint;
    public CanvasClipRectView(Context context) {
        super(context);
    }

    public CanvasClipRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasClipRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
         if(paint==null)paint=new Paint();
        paint.setAntiAlias(true);                           //设置画笔为无锯齿
        paint.setColor(Color.BLACK);                        //设置画笔颜色
        canvas.clipRect(100,100,350,600, Region.Op.INTERSECT);
        canvas.drawColor(Color.RED);
        canvas.drawCircle(100,100,100,paint);
    }
}
