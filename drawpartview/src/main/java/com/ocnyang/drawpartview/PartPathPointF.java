package com.ocnyang.drawpartview;

import android.graphics.Path;
import android.graphics.Point;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/8/28 14:28.
 *    *     *   *         *   * *       Email address: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

public class PartPathPointF extends PartPointF {
    private Path path;

    public PartPathPointF(float x, float y) {
        super(x, y);
    }

    public PartPathPointF(Point p) {
        super(p);
    }

    public PartPathPointF(float x, float y, int partNumber) {
        super(x, y, partNumber);
    }

    public PartPathPointF(Point p, int partNumber) {
        super(p, partNumber);
    }

    public PartPathPointF(float x, float y, int partNumber, String partName) {
        super(x, y, partNumber, partName);
    }

    public PartPathPointF(Point p, int partNumber, String partName) {
        super(p, partNumber, partName);
    }

    public PartPathPointF(float x, float y, Path path) {
        super(x, y);
        setPathCheckEmtpy(path);
    }

    public PartPathPointF(Point p, Path path) {
        super(p);
        setPathCheckEmtpy(path);
    }

    public PartPathPointF(float x, float y, int partNumber, Path path) {
        super(x, y, partNumber);
        setPathCheckEmtpy(path);
    }

    public PartPathPointF(Point p, int partNumber, Path path) {
        super(p, partNumber);
        setPathCheckEmtpy(path);
    }

    public PartPathPointF(float x, float y, int partNumber, String partName, Path path) {
        super(x, y, partNumber, partName);
        setPathCheckEmtpy(path);
    }

    public PartPathPointF(Point p, int partNumber, String partName, Path path) {
        super(p, partNumber, partName);
        setPathCheckEmtpy(path);
    }

    private void setPathCheckEmtpy(Path path) {
        if (path != null) {
            this.path = path;
            setCheckByPath(true);
        }
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
        if (path != null) {
            setCheckByPath(true);
        } else {
            setCheckByPath(false);
        }
    }
}
