package com.propcool.cmpm_project.util;

import java.io.Serializable;
import java.util.Objects;

public class ComplexPoint implements Serializable {
    public ComplexPoint(Complex x, Complex y) {
        this.x = x;
        this.y = y;
    }
    public ComplexPoint(ComplexPoint point){
        this(point.getX(), point.getY());
    }
    public ComplexPoint add(ComplexPoint p) {
        return new ComplexPoint(x.add(p.x), y.add(p.y));
    }
    public ComplexPoint mul(ComplexPoint p) {
        return new ComplexPoint(x.mul(p.x), y.mul(p.y));
    }
    public ComplexPoint sub(ComplexPoint p) {
        return new ComplexPoint(x.sub(p.x), y.sub(p.y));
    }
    public ComplexPoint div(ComplexPoint p) {
        return new ComplexPoint(x.div(p.x), y.div(p.y));
    }
    public ComplexPoint add(Complex x, Complex y) {
        return add(new ComplexPoint(x, y));
    }
    public ComplexPoint mul(Complex x, Complex y) {
        return mul(new ComplexPoint(x, y));
    }
    public ComplexPoint sub(Complex x, Complex y) {
        return sub(new ComplexPoint(x, y));
    }
    public ComplexPoint div(Complex x, Complex y) {
        return div(new ComplexPoint(x, y));
    }
    public double norm() {
        return Math.sqrt(Math.pow(x.getRl(), 2) + Math.pow(x.getIm(), 2) + Math.pow(y.getRl(), 2) + Math.pow(y.getIm(), 2));
    }
    public Complex getX() {
        return x;
    }

    public void setX(Complex x) {
        this.x = x;
    }

    public Complex getY() {
        return y;
    }

    public void setY(Complex y) {
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
        ComplexPoint that = (ComplexPoint) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    private Complex x;
    private Complex y;
}
