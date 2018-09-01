package com.ocnyang.drawpartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/8/31 16:59.
 *    *     *   *         *   * *       Email address: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

public class OverlayView extends View {
    private List<PartPointF> mPartPointFList;
    private Paint mPaint;

    public OverlayView(Context context) {
        super(context);
        initPaint();
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setStrokeWidth(5);
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.FILL);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPartPointFList != null) {
            for (PartPointF partPointF : mPartPointFList) {
//                canvas.drawCircle(partPointF.x, partPointF.y, 50, mPaint);
                if (partPointF instanceof PartPathPointF && partPointF.isCheckByPath()) {
                    canvas.drawPath(((PartPathPointF) partPointF).getPath(), mPaint);
//                    mPaint.setColor(Color.GREEN);
//                    canvas.drawCircle(partPointF.x, partPointF.y, 45, mPaint);
//                    mPaint.setColor(Color.RED);
                } else {
                    canvas.drawPoint(partPointF.x, partPointF.y, mPaint);
                }
            }
        }
    }

    public List<PartPointF> getPartPointFList() {
        return mPartPointFList;
    }

    public void setPartPointFList(List<PartPointF> partPointFList) {
        mPartPointFList = partPointFList;
        invalidate();
    }
}
