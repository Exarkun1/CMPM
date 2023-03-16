package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.auxiliary.Point;
import com.propcool.cmpm_project.functions.Function;

public class PolarManager extends CoordinateManager {
    @Override
    public String getName() {
        return "polar";
    }

    @Override
    public Point getCoordinate(Function function, double a) {
        double x = getCenterX() + function.get(a)*Math.cos(a) / getPixelSize();
        double y = getCenterY() - function.get(a)*Math.sin(a) / getPixelSize();
        return new Point(x, y);
    }

    @Override
    public double getX(double pixelX, double pixelY) {
        double new_x = (pixelX - getCenterX()) * getPixelSize();
        double new_y = -(pixelY - getCenterY()) * getPixelSize();
        double fi = Math.atan(new_y / new_x);
        if(new_x < 0) fi = Math.PI + fi;
        if(new_x > 0 && new_y < 0) fi = 2*Math.PI + fi;
        return fi;
    }

    @Override
    public double getY(double pixelX, double pixelY) {
        double new_x = (pixelX - getCenterX()) * getPixelSize();
        double new_y = -(pixelY - getCenterY()) * getPixelSize();
        return Math.sqrt(Math.pow(new_x, 2) + Math.pow(new_y, 2));
    }

    @Override
    public double getMin() {
        return 0;
    }

    @Override
    public double getMax() {
        return 4*Math.PI;
    }

    @Override
    public double getStep() {
        return 0.02;
    }

    @Override
    public Point getCircleCoordinate(Function function, double x, double y) {
        double new_x = (x - getCenterX()) * getPixelSize();
        double new_y = -(y - getCenterY()) * getPixelSize();
        double fi = Math.atan(new_y / new_x);
        if(new_x < 0) fi = Math.PI + fi;
        if(new_x > 0 && new_y < 0) fi = 2*Math.PI + fi;
        double r = function.get(fi);
        double xx = getCenterX() + (r * Math.cos(2*Math.PI + fi) / getPixelSize());
        double yy = getCenterY() - (r * Math.sin(2*Math.PI + fi) / getPixelSize());
        return new Point(xx, yy);
        //y=-sin(x)/(cos(x)^2+sin(x)*cos(x))
    }
}
