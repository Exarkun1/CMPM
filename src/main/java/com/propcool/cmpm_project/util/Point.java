package com.propcool.cmpm_project.util;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(Point point){
        this(point.getX(), point.getY());
    }
    public Point add(Point p) {
        return new Point(x + p.x, y + p.y);
    }
    public Point mul(Point p) {
        return new Point(x * p.x, y * p.y);
    }
    public Point sub(Point p) {
        return new Point(x - p.x, y - p.y);
    }
    public Point div(Point p) {
        return new Point(x / p.x, y / p.y);
    }
    public Point add(double x, double y) {
        return add(new Point(x, y));
    }
    public Point mul(double x, double y) {
        return mul(new Point(x, y));
    }
    public Point sub(double x, double y) {
        return sub(new Point(x, y));
    }
    public Point div(double x, double y) {
        return div(new Point(x, y));
    }
    public double norm() {
        return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
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
        return "{" + x + ", " + y + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    private double x;
    private double y;
}
