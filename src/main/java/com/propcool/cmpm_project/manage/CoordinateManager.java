package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.auxiliary.Point;
import com.propcool.cmpm_project.functions.Function;

public abstract class CoordinateManager {
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
    public abstract double getMin();
    public abstract double getMax();
    public abstract double getStep();
    public abstract Point getCircleCoordinate(Function function, double x, double y);
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

        centerX += dx;
        centerY += dy;

        setMouse(x, y);
    }
    /**
     * Приближение к точке
     * */
    public void zoomIn(double x, double y){
        double dx = x-centerX;
        double dy = y-centerY;

        pixelSize *= zoomCoef;
        centerX += dx*(zoomCoef-1)/zoomCoef;
        centerY += dy*(zoomCoef-1)/zoomCoef;
    }
    /**
     * Отдпление от точки
     * */
    public void zoomOut(double x, double y){
        double dx = x-centerX;
        double dy = y-centerY;

        pixelSize /= zoomCoef;
        centerX -= dx*(zoomCoef-1);
        centerY -= dy*(zoomCoef-1);
    }
    /**
     * Получение пиксельных Y координат
     * */
    public boolean onScreen(double x, double y){
        return 2 <= x && x <= wight-2 && 2 <= y && y <= height-2;
    }

    public int getHeight() {
        return height;
    }

    public int getWight() {
        return wight;
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
    private final int height = 1056-91, half_height = height/2;
    private final int wight = 1936, half_wight = wight/2;
    private double centerX = half_wight, centerY = half_height;
    private double mouseX, mouseY;
    private final double zoomCoef = 1.075;
}
