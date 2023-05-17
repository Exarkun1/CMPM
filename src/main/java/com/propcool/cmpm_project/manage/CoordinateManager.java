package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.util.Point;
import com.propcool.cmpm_project.functions.Function;
import javafx.scene.control.Alert;

import java.awt.*;

public abstract class CoordinateManager {
    public CoordinateManager() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = (int)screenSize.getWidth();
        this.height = (int)(screenSize.getHeight()-115);
        this.centerX = width/2.;
        this.centerY = height/2.;
    }
    public abstract String getName();
    public abstract Point getCoordinate(Function function, double a);
    /**
     * Преобразование пиксельных X координат в обычные
     * */
    public abstract double getX(double pixelX, double pixelY);
    /**
     * Преобразование пиксельных Y координат в обычные
     * */
    public abstract double getY(double pixelX, double pixelY);
    /**
     * Минимум по x в пиксельных координатах
     * */
    public abstract double getMin();
    /**
     * Максимум по x в пиксельных координатах
     * */
    public abstract double getMax();
    /**
     * Шаг для отрисовки
     * */
    public abstract double getStep();
    public abstract Point getCircleCoordinate(Function function, double x, double y);
    /**
     * Преобразование X координат в пиксельные
     * */
    public abstract double getPixelX(double x, double y);
    /**
     * Преобразование Y координат в пиксельные
     * */
    public abstract double getPixelY(double x, double y);
    public double getX(Point p) {
        return getX(p.getX(), p.getY());
    }
    public double getY(Point p) {
        return getY(p.getX(), p.getY());
    }
    public Point getXY(Point p) {
        return new Point(getX(p), getY(p));
    }
    public double getPixelX(Point p) {
        return getPixelX(p.getX(), p.getY());
    }
    public double getPixelY(Point p) {
        return getPixelY(p.getX(), p.getY());
    }
    public Point getPixelXY(Point p) {
        return new Point(getPixelX(p), getPixelY(p));
    }
    /**
     * Изменение положения мышки
     * */
    public void setMouse(double x, double y){
        mouseX = x;
        mouseY = y;
    }
    /**
     * Сдвиг центра относительно мышки
     * */
    public void shift(double x, double y){
        double dx = x-mouseX;
        double dy = y-mouseY;

        shiftCenter(dx, dy);
        setMouse(x, y);
    }
    /**
     * Отдаление от точки
     * */
    public void zoomOut(double x, double y){
        double dx = x-centerX;
        double dy = y-centerY;

        zoomCenter(zoomCoef);
        shiftCenter(dx*(zoomCoef-1)/zoomCoef, dy*(zoomCoef-1)/zoomCoef);
    }
    /**
     * Приближение к точке
     * */
    public void zoomIn(double x, double y){
        double dx = centerX-x;
        double dy = centerY-y;

        zoomCenter(1/zoomCoef);
        shiftCenter(dx*(zoomCoef-1), dy*(zoomCoef-1));
    }
    public void shiftCenter(double dx, double dy) {
        centerX += dx;
        centerY += dy;
    }
    public void zoomCenter(double zoomCoef) {
        pixelSize *= zoomCoef;
    }
    public boolean onScreen(double x, double y){
        return 2 <= x && x <= width -2 && 2 <= y && y <= height-2;
    }
    public boolean onScreen(Point p){
        return onScreen(p.getX(), p.getY());
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double getPixelSize() {
        return pixelSize;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    private double pixelSize = 0.01;
    private final int height;
    private final int width;
    private double centerX, centerY;
    private double mouseX, mouseY;
    private final double zoomCoef = 1.075;
}
