package com.ocnyang.drawpartviewdemo;

import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ocnyang.drawpartview.DrawPartView;
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

//        ArrayList<PartPointF> partPointFList = new ArrayList<>();
//        Path path = new Path();
//        path.addCircle(50f, 50f, 50, Path.Direction.CCW);
//        PartPointF 左上角 = new PartPathPointF(50f, 50f, 1, "左上角", path);
//        partPointFList.add(左上角);
//        Path path1 = new Path();
//        path1.addRect(433, 25, 483, 75, Path.Direction.CCW);
//        partPointFList.add(new PartPathPointF(458f, 50f, 1, "右上角", path1));
//        partPointFList.add(new PartPointF(229f, 541f, 1, "正中心"));
//        partPointFList.add(new PartPointF(0f, 950, 1, "左下角"));
//        partPointFList.add(new PartPointF(458f, 950f, 1, "右下角"));
//        partPointFList.add(new PartPointF(200f, 500f, 1, "随机点"));
//        mDrawPartView.setPartPointFList(partPointFList);
//        mDrawPartView.setImageView(R.drawable.san_xiang_si_men);

        initDragonflyView();
    }

    private void initDragonflyView() {
        ArrayList<PartPointF> partPointFList = new ArrayList<>();

        Path path1 = new Path();
        path1.addOval(new RectF(1143f, 198f, 1263f, 398f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 1, "头", path1));

        Path path2_1 = new Path();
        Path path2_2 = new Path();
        path2_1.addCircle(1142f, 311, 77, Path.Direction.CCW);
        path2_2.addCircle(1272f, 311, 77, Path.Direction.CCW);
        path2_1.op(path2_2, Path.Op.XOR);
        partPointFList.add(new PartPathPointF(0, 0, 2, "眼睛", path2_1));

        Path path3 = new Path();
        path3.addRect(new RectF(498f, 434f, 1101f, 602f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 3, "左上翼", path3));

        Path path4 = new Path();
        path4.addRect(new RectF(1294f, 433f, 1897f, 602f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 4, "右上翼", path4));

        Path path5 = new Path();
        path5.moveTo(1093f, 528);
        path5.lineTo(1112f, 596f);
        path5.lineTo(1020f, 743f);
        path5.lineTo(670f, 968f);
        path5.lineTo(630f, 938f);
        path5.lineTo(727f, 751f);
        path5.close();
        partPointFList.add(new PartPathPointF(0, 0, 5, "右上翼", path5));

        Path path6 = new Path();
        path6.moveTo(1302f, 527);
        path6.lineTo(1286f, 591f);
        path6.lineTo(1397f, 760f);
        path6.lineTo(1703f, 965f);
        path6.lineTo(1769f, 941f);
        path6.lineTo(1670f, 750f);
        path6.close();
        partPointFList.add(new PartPathPointF(0, 0, 6, "右下翼", path6));

        Path path7_1 = new Path();
        Path path7_2 = new Path();
        path7_1.addCircle(1200f, 783f, 67, Path.Direction.CCW);
        path7_2.addRect(1133f, 735f, 1267f, 832f, Path.Direction.CCW);
        path7_1.op(path7_2, Path.Op.UNION);
        partPointFList.add(new PartPathPointF(0, 0, 7, "尾节1", path7_1));

        Path path8 = new Path();
        path4.addRect(new RectF(1138f, 835f, 1263f, 970f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 8, "尾节2", path8));

        Path path9 = new Path();
        path9.addRect(new RectF(1142f, 971f, 1259f, 1100f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 9, "尾节3", path9));

        Path path10 = new Path();
        path10.addRect(new RectF(1153f, 1105f, 1251f, 1233f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 10, "尾节4", path10));

        Path path11 = new Path();
        path11.addRect(new RectF(1158f, 1240f, 1242f, 1403f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 11, "尾节5", path11));

        partPointFList.add(new PartPointF(630f, 938f, 12, "左下翼尖"));
        partPointFList.add(new PartPointF(1769f, 941f, 13, "右下翼尖"));
        partPointFList.add(new PartPointF(503f, 542f, 14, "左上翼尖"));
        partPointFList.add(new PartPointF(1897f, 536f, 15, "右上翼尖"));

        mDrawPartView.setPartPointFList(partPointFList);
        mDrawPartView.setImageView(R.drawable.dragonfly);
    }
}
