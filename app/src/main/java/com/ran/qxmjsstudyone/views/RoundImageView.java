package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by houqixin on 2017/10/12.
 */

public class RoundImageView extends ImageView {
    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /*圆角的半径，依次为左上角xy半径10，右上角10，右下角0，左下角0*/
    private float[] rids = {10.0f,10.0f,10.0f,10.0f,0.0f,0.0f,0.0f,0.0f,};
    /**
     * 通过自定人为的修改（clip）draw的时候的path
     * 这样就认为的把ImageView变成了一个左上角和右上角有倒角，左下角和右下角无倒角的图片显示出来
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight(); /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
