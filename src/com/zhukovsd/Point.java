package com.zhukovsd;

/**
 * Created by ZhukovSD on 22.10.2016.
 */
public class Point {
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point shift(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Point() {
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
