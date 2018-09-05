package com.ocnyang.drawpartviewdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ocnyang.drawpartview.DrawPartView;
import com.ocnyang.drawpartview.ImageLoaderInterface;
import com.ocnyang.drawpartview.PartPathPointF;
import com.ocnyang.drawpartview.PartPointF;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawPartView mDrawPartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawPartView = ((DrawPartView) findViewById(R.id.draw_part_view));

        //加载图片方法一:原生加载，注意当图片过大时，有可能造成内存溢出；
        //mDrawPartView.setImageView(R.drawable.dragonfly, 2400, 1600);

        //加载图片方法二：使用框架加载
        mDrawPartView.setImageView(new ImageLoaderInterface() {
            @Override
            public void displayImage(Context context, ImageView imageView) {
                Glide.with(context)
                        .load(R.drawable.dragonflyw)
                        .into(imageView);
            }
        },2400,1600);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        mDrawPartView.setFingerTrackPaint(paint);

        Paint paint1 = new Paint();
        paint1.setColor(0x99ee1111);
        paint1.setStrokeWidth(2);
        paint1.setStyle(Paint.Style.STROKE);
        mDrawPartView.setOverlayViewPaint(paint1);

        mDrawPartView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    mDrawPartView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mDrawPartView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                initDragonflyView();
            }
        });
    }

    private void initDragonflyView() {
        ArrayList<PartPointF> partPointFList = new ArrayList<>();

        Path path1 = new Path();
        path1.addOval(mDrawPartView.getRealShowRectF(1143f, 198f, 1263f, 398f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 1, "头", path1));

        Path path2_1 = new Path();
        Path path2_2 = new Path();
        path2_1.addCircle(mDrawPartView.getRealShowX(1142f), mDrawPartView.getRealShowY(311), mDrawPartView.getRealLength(77), Path.Direction.CCW);
        path2_2.addCircle(mDrawPartView.getRealShowX(1272f), mDrawPartView.getRealShowY(311), mDrawPartView.getRealLength(77), Path.Direction.CCW);
        path2_1.op(path2_2, Path.Op.XOR);
        partPointFList.add(new PartPathPointF(0, 0, 2, "眼睛", path2_1));

        Path path3 = new Path();
        path3.addRect(mDrawPartView.getRealShowRectF(498f, 434f, 1101f, 602f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 3, "左上翼", path3));

        Path path4 = new Path();
        path4.addRect(mDrawPartView.getRealShowRectF(1294f, 433f, 1897f, 602f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 4, "右上翼", path4));

        Path path5 = new Path();
        path5.moveTo(mDrawPartView.getRealShowX(1093f), mDrawPartView.getRealShowY(528));
        path5.lineTo(mDrawPartView.getRealShowX(1112f), mDrawPartView.getRealShowY(596f));
        path5.lineTo(mDrawPartView.getRealShowX(1020f), mDrawPartView.getRealShowY(743f));
        path5.lineTo(mDrawPartView.getRealShowX(670f), mDrawPartView.getRealShowY(968f));
        path5.lineTo(mDrawPartView.getRealShowX(630f), mDrawPartView.getRealShowY(938f));
        path5.lineTo(mDrawPartView.getRealShowX(727f), mDrawPartView.getRealShowY(751f));
        path5.close();
        partPointFList.add(new PartPathPointF(0, 0, 5, "右上翼", path5));

        Path path6 = new Path();
        path6.moveTo(mDrawPartView.getRealShowX(1302f), mDrawPartView.getRealShowY(527));
        path6.lineTo(mDrawPartView.getRealShowX(1286f), mDrawPartView.getRealShowY(591f));
        path6.lineTo(mDrawPartView.getRealShowX(1397f), mDrawPartView.getRealShowY(760f));
        path6.lineTo(mDrawPartView.getRealShowX(1703f), mDrawPartView.getRealShowY(965f));
        path6.lineTo(mDrawPartView.getRealShowX(1769f), mDrawPartView.getRealShowY(941f));
        path6.lineTo(mDrawPartView.getRealShowX(1670f), mDrawPartView.getRealShowY(750f));
        path6.close();
        partPointFList.add(new PartPathPointF(0, 0, 6, "右下翼", path6));

        Path path7_1 = new Path();
        Path path7_2 = new Path();
        path7_1.addCircle(mDrawPartView.getRealShowX(1200f), mDrawPartView.getRealShowY(783f), mDrawPartView.getRealLength(67), Path.Direction.CCW);
        path7_2.addRect(mDrawPartView.getRealShowRectF(1133f, 735f, 1267f, 832f), Path.Direction.CCW);
        path7_1.op(path7_2, Path.Op.UNION);
        partPointFList.add(new PartPathPointF(0, 0, 7, "尾节1", path7_1));

        Path path8 = new Path();
        path4.addRect(mDrawPartView.getRealShowRectF(1138f, 835f, 1263f, 970f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 8, "尾节2", path8));

        Path path9 = new Path();
        path9.addRect(mDrawPartView.getRealShowRectF(1142f, 971f, 1259f, 1100f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 9, "尾节3", path9));

        Path path10 = new Path();
        path10.addRect(mDrawPartView.getRealShowRectF(1153f, 1105f, 1251f, 1233f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 10, "尾节4", path10));

        Path path11 = new Path();
        path11.addRect(mDrawPartView.getRealShowRectF(1158f, 1240f, 1242f, 1403f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 11, "尾节5", path11));

        partPointFList.add(new PartPointF(mDrawPartView.getRealShowX(630f), mDrawPartView.getRealShowY(938f), 12, "左下翼尖"));
        partPointFList.add(new PartPointF(mDrawPartView.getRealShowX(1769f), mDrawPartView.getRealShowY(941f), 13, "右下翼尖"));
        partPointFList.add(new PartPointF(mDrawPartView.getRealShowX(503f), mDrawPartView.getRealShowY(542f), 14, "左上翼尖"));
        partPointFList.add(new PartPointF(mDrawPartView.getRealShowX(1897f), mDrawPartView.getRealShowY(536f), 15, "右上翼尖"));

        Path path16 = new Path();
        path16.addOval(mDrawPartView.getRealShowRectF(1104, 405, 1294, 666), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 16, "身体", path16));

        mDrawPartView.setPartPointFList(partPointFList);
    }
}
