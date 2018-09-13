package com.ocnyang.drawpartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/8/28 11:01.
 *    *     *   *         *   * *       Email address: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

public class FingerTrackView extends View {
    public static final String TAG = "FingerTrackView";

    private Path mPath = new Path();
    private Paint mPaint;
    private float mPreX, mPreY;
    private DrawTrackOverListener mDrawTrackOverListener;

    public FingerTrackView(Context context) {
        this(context, null);
    }

    public FingerTrackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FingerTrackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setStrokeWidth(5);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                reset();
                mPath.moveTo(event.getX(), event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX()) / 2;
                float endY = (mPreY + event.getY()) / 2;
                mPath.quadTo(mPreX, mPreY, endX, endY);
                mPreX = event.getX();
                mPreY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mDrawTrackOverListener != null) {
                    mDrawTrackOverListener.onDrawTrackOverListener(mPath);
                } else {
                    Log.w(TAG, "There is no a DrawTrackOverListener!");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void reset() {
        mPath.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    public interface DrawTrackOverListener {
        /**
         * 画圈完成后的回调
         *
         * @param fingerTrackPath 画圈的路径
         */
        void onDrawTrackOverListener(Path fingerTrackPath);
    }

    public DrawTrackOverListener getDrawTrackOverListener() {
        return mDrawTrackOverListener;
    }

    public void setDrawTrackOverListener(DrawTrackOverListener drawTrackOverListener) {
        mDrawTrackOverListener = drawTrackOverListener;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }
}
