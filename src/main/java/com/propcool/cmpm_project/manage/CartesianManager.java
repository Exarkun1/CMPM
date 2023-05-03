package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.auxiliary.Point;
import com.propcool.cmpm_project.functions.Function;
/**
 * Менеджер координат, содержит все координатные параметры
 * */
public class CartesianManager extends CoordinateManager {
    @Override
    public String getName() {
        return "cartesian";
    }

    @Override
    public Point getCoordinate(Function function, double a){
        double y = getCenterY() - function.get((a - getCenterX()) * getPixelSize()) / getPixelSize();
        return new Point(a, y);
    }
    @Override
    public double getX(double pixelX, double pixelY){
        return (pixelX - getCenterX()) * getPixelSize();
    }
    @Override
    public double getY(double pixelX, double pixelY){
        return -(pixelY - getCenterY()) * getPixelSize();
    }

    @Override
    public double getMin() {
        return 0;
    }
    @Override
    public double getMax() {
        return getWight()-2;
    }

    @Override
    public double getStep() {
        return 1;
    }

    @Override
    public Point getCircleCoordinate(Function function, double x, double y) {
        Point point = getCoordinate(function, x);
        double new_y = point.getY();
        return new Point(x, new_y);
    }

    @Override
    public double getPixelX(double x, double y) {
        return x / getPixelSize() + getCenterX();
    }

    @Override
    public double getPixelY(double x, double y) {
        return -y / getPixelSize() + getCenterY();
    }
}
