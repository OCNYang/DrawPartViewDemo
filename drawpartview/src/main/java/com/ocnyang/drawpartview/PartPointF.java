package com.ocnyang.drawpartview;

import android.graphics.Point;
import android.graphics.PointF;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/8/28 14:09.
 *    *     *   *         *   * *       Email address: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

public class PartPointF extends PointF {

    private int partNumber;
    private String partName;
    private boolean checkByPath = false;

    public PartPointF(float x, float y) {
        super(x, y);
    }

    public PartPointF(Point p) {
        super(p);
    }

    public PartPointF(float x, float y, int partNumber) {
        super(x, y);
        this.partNumber = partNumber;
    }

    public PartPointF(Point p, int partNumber) {
        super(p);
        this.partNumber = partNumber;
    }

    public PartPointF(float x, float y, int partNumber, String partName) {
        super(x, y);
        this.partNumber = partNumber;
        this.partName = partName;
    }

    public PartPointF(Point p, int partNumber, String partName) {
        super(p);
        this.partNumber = partNumber;
        this.partName = partName;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public boolean isCheckByPath() {
        return checkByPath;
    }

    public void setCheckByPath(boolean checkByPath) {
        this.checkByPath = checkByPath;
    }
}
