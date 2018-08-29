package com.ocnyang.drawpartview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/8/28 10:58.
 *    *     *   *         *   * *       Email address: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

public class DrawPartView extends FrameLayout implements FingerTrackView.DrawTrackOverListener {

    private List<PartPointF> mPartPointFList;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private int mWidth;
    private int mHeight;
    private boolean mBitmapShowStatus;
    private double mContrastFactor;
    private ImageView mImageView;
    private FingerTrackView mFingerTrackView;

    public DrawPartView(@NonNull Context context) {
        this(context, null);
    }

    public DrawPartView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawPartView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mImageView = new ImageView(context);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mImageView);

        mFingerTrackView = new FingerTrackView(context);
        mFingerTrackView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mFingerTrackView.setDrawTrackOverListener(this);
        addView(mFingerTrackView);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        computeTranslation();
        Log.e("on size", "w:" + w + "h:" + h);
    }

    /**
     * 判断图片的显示状态，然后调整坐标系
     */
    private void computeTranslation() {
        double i = ((double) mWidth) / mHeight;
        double j = ((double) mBitmapWidth) / mBitmapHeight;

        if ((i > j)) {
            //图片显示状态：高度充满，宽度居中
            mBitmapShowStatus = false;
            mContrastFactor = ((double) mHeight / mBitmapHeight);
            resetPartPointFListByHeight();

        } else {
            //图片显示状态：宽度充满，高度居中
            mBitmapShowStatus = true;
            mContrastFactor = ((double) mWidth) / mBitmapWidth;
            resetPartPointFListByWidth();
        }
    }

    /**
     * 根据图片的显示，调整图片的相对于整个 DrawPartView 坐标系的坐标
     */
    private void resetPartPointFListByWidth() {
        if (mPartPointFList != null) {
            double trans = (mHeight - (mContrastFactor * mBitmapHeight)) / 2;

            for (PartPointF partPointF : mPartPointFList) {
                float realX = (float) (partPointF.x * mContrastFactor);
                float realY = (float) (partPointF.y * mContrastFactor + trans);
                partPointF.set(realX, realY);
            }
        }
    }

    private void resetPartPointFListByHeight() {
        if (mPartPointFList != null) {
            double trans = (mWidth - (mContrastFactor * mBitmapWidth)) / 2;

            for (PartPointF partPointF : mPartPointFList) {
                float realX = ((float) (partPointF.x * mContrastFactor + trans));
                float realY = ((float) (partPointF.y * mContrastFactor));
                partPointF.set(realX, realY);
            }
        }
    }

    /**
     * 画圈结束后（手指抬起时）的监听回调
     *
     * @param fingerTrackPath
     */
    @Override
    public void onDrawTrackOverListener(Path fingerTrackPath) {
        if (mBitmapShowStatus) {
            Toast.makeText(this.getContext(), "无处理", Toast.LENGTH_SHORT).show();
        } else {
            Path currPath = new Path(fingerTrackPath);
            StringBuilder stringBuilder = new StringBuilder("");

            if (mPartPointFList != null) {
                for (PartPointF partPointF : mPartPointFList) {
                    Path path = new Path(currPath);
                    RectF bounds = new RectF();
                    path.computeBounds(bounds, true);

                    Region region = new Region();
                    region.setPath(path, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));

                    Log.e("reat", "" + bounds.left + "," + bounds.top + "," + bounds.right + "," + bounds.bottom);

                    if (region.contains(((int) partPointF.x), (int) partPointF.y)) {
                        stringBuilder.append(partPointF.getPartName()).append(" ");
                    }
                }
                Toast.makeText(this.getContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getContext(), "无", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public List<PartPointF> getPartPointFList() {
        return mPartPointFList;
    }

    public void setPartPointFList(List<PartPointF> partPointFList) {
        mPartPointFList = partPointFList;
//        computeTranslation();
    }

    public void setImageView(int imgID) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgID, options);
        mBitmapHeight = options.outHeight;
        mBitmapWidth = options.outWidth;
        mImageView.setImageBitmap(bitmap);
        Log.e("bitmap", "width:" + mBitmapWidth + " height:" + mBitmapHeight);
    }
}
