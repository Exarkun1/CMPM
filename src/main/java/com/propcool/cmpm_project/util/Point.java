package com.propcool.cmpm_project.util;

public class Point {
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(Point point){
        this(point.getX(), point.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    private double x;
    private double y;
}
