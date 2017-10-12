package com.ran.qxmjsstudyone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.ran.qxmjsstudyone.base.BaseActvity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by houqixin on 2017/10/12.
 */

public class PDFActvity extends BaseActvity {

    @BindView(R.id.pdfview)
    PDFView pdfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         *1. pages is optional, it allows you to filter and order the pages of the PDF as you need
         2.onDraw is also optional, and allows you to draw something on a provided canvas, above the current page
         */
        pdfView.fromAsset("ui.pdf")
                .pages(5,3,0,1,2,4,0,0,0)
                .defaultPage(0)
                .swipeVertical(true)
                .enableSwipe(true)
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                         if(displayedPage==1){
                             Paint paint=new Paint();
                             paint.setAntiAlias(true);
                             paint.setDither(true);
                             paint.setStyle(Paint.Style.STROKE);
                             paint.setColor(Color.RED);
                             paint.setStrokeWidth(3.0f);
                             canvas.drawText("QX&WR",100,100,paint);
                         }
                    }
                })
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        Log.e("nbPages:", nbPages+"");

                    }
                })
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        Log.e("page:", page+"");
                        Log.e("pageCount:", pageCount+"");
                    }
                })
                .load();

    }
}