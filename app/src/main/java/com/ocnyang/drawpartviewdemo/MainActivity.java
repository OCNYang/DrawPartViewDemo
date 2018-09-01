package com.ocnyang.drawpartviewdemo;

import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        ArrayList<PartPointF> partPointFList = new ArrayList<>();
        Path path = new Path();
        path.addCircle(50f, 50f, 50, Path.Direction.CCW);
        PartPointF 左上角 = new PartPathPointF(50f, 50f, 1, "左上角", path);
        partPointFList.add(左上角);
        Path path1 = new Path();
        path1.addRect(433, 25, 483, 75, Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(458f, 50f, 1, "右上角", path1));
        partPointFList.add(new PartPointF(229f, 541f, 1, "正中心"));
        partPointFList.add(new PartPointF(0f, 950, 1, "左下角"));
        partPointFList.add(new PartPointF(458f, 950f, 1, "右下角"));
        partPointFList.add(new PartPointF(200f, 500f, 1, "随机点"));
        mDrawPartView.setPartPointFList(partPointFList);
        mDrawPartView.setImageView(R.drawable.san_xiang_si_men);
    }
}
