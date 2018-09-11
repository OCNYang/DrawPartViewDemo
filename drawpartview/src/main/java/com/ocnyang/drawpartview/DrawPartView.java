package com.ocnyang.drawpartview;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/8/28 10:58.
 *    *     *   *         *   * *       Email address: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

public class DrawPartView extends FrameLayout implements FingerTrackView.DrawTrackOverListener {
    private static final String TAG = "DrawPartView";

    private List<PartPointF> mPartPointFList;

    private int mBitmapWidth;
    private int mBitmapHeight;
    private int mWidth;
    private int mHeight;

    private boolean mBitmapShowStatus;
    private double mContrastFactor;

    private ImageView mImageView;
    private FingerTrackView mFingerTrackView;
    private OverlayView mOverlayView;

    private ImageLoaderInterface mImageLoaderInterface;
    private OnDrawPartResultListener mOnDrawPartResultListener;

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

        mOverlayView = new OverlayView(context);
        mOverlayView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mOverlayView);

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

        } else {
            //图片显示状态：宽度充满，高度居中
            mBitmapShowStatus = true;
            mContrastFactor = ((double) mWidth) / mBitmapWidth;
        }
    }

    /**
     * 根据图片的显示，将图片的测量坐标转换成显示坐标
     *
     * @param length 转换长度，比如: radius;
     * @return
     */
    public float getRealShowLength(float length) {
        return ((float) (length * mContrastFactor));
    }

    /**
     * 将 X 轴方向的测量坐标转换成显示坐标
     *
     * @param x X 轴方向的测量坐标
     * @return x 的实现显示时的坐标
     */
    public float getRealShowX(float x) {
        if (mBitmapShowStatus) {
            return ((float) (x * mContrastFactor));
        } else {
            double trans = (mWidth - (mContrastFactor * mBitmapWidth)) / 2;
            return ((float) (x * mContrastFactor + trans));
        }
    }

    /**
     * 将 Y 轴方向的测量坐标转换成实际显示坐标
     *
     * @param y Y 轴方向的测量坐标
     * @return y 的实现显示时的坐标
     */
    public float getRealShowY(float y) {
        if (!mBitmapShowStatus) {
            return ((float) (y * mContrastFactor));
        } else {
            double trans = (mHeight - (mContrastFactor * mBitmapHeight)) / 2;
            return ((float) (y * mContrastFactor + trans));
        }
    }

    /**
     * 将测量坐标系的矩形坐标 转换成 实际显示坐标系的矩形坐标
     *
     * @param rectF
     * @return
     */
    public RectF getRealShowRectF(RectF rectF) {
        return getRealShowRectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    public RectF getRealShowRectF(float left, float top, float right, float bottom) {
        RectF realRectF = new RectF();
        if (mBitmapShowStatus) {
            double trans = (mHeight - (mContrastFactor * mBitmapHeight)) / 2;
            realRectF.left = ((float) (left * mContrastFactor));
            realRectF.right = ((float) (right * mContrastFactor));
            realRectF.top = ((float) (top * mContrastFactor + trans));
            realRectF.bottom = ((float) (bottom * mContrastFactor + trans));
        } else {
            double trans = (mWidth - (mContrastFactor * mBitmapWidth)) / 2;
            realRectF.left = ((float) (left * mContrastFactor + trans));
            realRectF.right = ((float) (right * mContrastFactor + trans));
            realRectF.top = ((float) (top * mContrastFactor));
            realRectF.bottom = ((float) (bottom * mContrastFactor));
        }
        return realRectF;
    }

    /**
     * 画圈结束后（手指抬起时）的监听回调
     *
     * @param fingerTrackPath
     */
    @Override
    public void onDrawTrackOverListener(Path fingerTrackPath) {
        Path currPath = new Path(fingerTrackPath);

        if (mPartPointFList != null) {
            ArrayList<PartPointF> selectPartList = new ArrayList<>();

            for (PartPointF partPointF : mPartPointFList) {
                Path path = new Path(currPath);
                RectF bounds = new RectF();
                path.computeBounds(bounds, true);

                Region region = new Region();
                region.setPath(path, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));

                if (partPointF instanceof PartPathPointF && partPointF.isCheckByPath() && ((PartPathPointF) partPointF).getPath() != null) {
                    Path pointPath = new Path(((PartPathPointF) partPointF).getPath());
                    RectF boundsPoint = new RectF();
                    pointPath.computeBounds(boundsPoint, true);

                    Region regionPoint = new Region();
                    regionPoint.setPath(pointPath, new Region((int) boundsPoint.left, (int) boundsPoint.top, (int) boundsPoint.right, (int) boundsPoint.bottom));

                    if (!region.quickReject(regionPoint) && region.op(regionPoint, Region.Op.INTERSECT)) {
                        selectPartList.add(partPointF);
                    }
                } else {
                    if (region.contains(((int) partPointF.x), (int) partPointF.y)) {
                        selectPartList.add(partPointF);
                    }
                }
            }
            onFeedBackResult(selectPartList);
        } else {
            Log.e(TAG, "PartPointList is null,in other words,there is no part data.");
        }
    }

    public List<PartPointF> getPartPointFList() {
        return mPartPointFList;
    }

    public void setPartPointFList(List<PartPointF> partPointFList) {
        mPartPointFList = partPointFList;
        if (mOverlayView != null) {
            mOverlayView.setPartPointFList(partPointFList);
        }
    }

    /**
     * 设置模型图片
     *
     * @param imgID           模型图片的资源 ID
     * @param imgAnchorWidth  模型图片的测量宽度（如果测量图片和模型图片大小相同，则等同于模型图片的实际宽度像素）
     * @param imgAnchorHeight 模型图片的测量高度
     */
    public void setImageView(@DrawableRes int imgID, int imgAnchorWidth, int imgAnchorHeight) {
        mBitmapWidth = imgAnchorWidth;
        mBitmapHeight = imgAnchorHeight;
        mImageView.setImageResource(imgID);
    }

    public void setImageView(@NonNull ImageLoaderInterface imageLoaderInterface,
                             int imgAnchorWidth, int imgAnchorHeight) {
        mBitmapWidth = imgAnchorWidth;
        mBitmapHeight = imgAnchorHeight;
        imageLoaderInterface.displayImage(mImageView.getContext(), mImageView);
    }

    public ImageLoaderInterface getImageLoaderInterface() {
        return mImageLoaderInterface;
    }

    /**
     * 设置手指画圈轨迹的画笔样式
     *
     * @param paint
     */
    public void setFingerTrackPaint(Paint paint) {
        if (mFingerTrackView != null) {
            mFingerTrackView.setPaint(paint);
        }
    }

    /**
     * 设置锚点、锚点区域绘制时的画笔样式
     *
     * @param paint
     */
    public void setOverlayViewPaint(Paint paint) {
        if (mOverlayView != null) {
            mOverlayView.setPaint(paint);
        }
    }

    public void showOverlayView(boolean visible) {
        if (mOverlayView != null) {
            mOverlayView.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    private void onFeedBackResult(List<PartPointF> partPointFList) {
        if (mOnDrawPartResultListener == null) {
            return;
        }

        mOnDrawPartResultListener.onDrawPartResult(partPointFList);
    }

    public interface OnDrawPartResultListener {
        void onDrawPartResult(List<PartPointF> partPointFList);
    }

    public OnDrawPartResultListener getOnDrawPartResultListener() {
        return mOnDrawPartResultListener;
    }

    public void setOnDrawPartResultListener(OnDrawPartResultListener onDrawPartResultListener) {
        mOnDrawPartResultListener = onDrawPartResultListener;
    }
}
