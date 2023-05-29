package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.util.Point;
import com.propcool.cmpm_project.functions.Function;
import javafx.scene.control.Alert;

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
        return min;
    }

    @Override
    public double getMax() {
        return max;
    }

    @Override
    public double getStep() {
        return 0.02;
    }

    @Override
    public double getPixelX(double fi, double r) {
        double x = r * Math.cos(fi);
        return x / getPixelSize() + getCenterX();
    }

    @Override
    public double getPixelY(double fi, double r) {
        double y = r * Math.sin(fi);
        return -y / getPixelSize() + getCenterY();
    }

    public void polarAlert() {
        polarAlert.show();
    }
    public void setBorders(double start, double end) {
        min = start;
        max = end;
    }
    private final Alert polarAlert = new Alert(Alert.AlertType.WARNING,"Не верные данные в границах");
    private double min = 0;
    private double max = 4*Math.PI;
}
