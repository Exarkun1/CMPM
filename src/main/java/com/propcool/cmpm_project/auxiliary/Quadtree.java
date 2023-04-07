package com.propcool.cmpm_project.auxiliary;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.manage.CoordinateManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
/**
 * Квадрант для построения неявной функции
 * */
public class Quadtree {
    public Quadtree(Point point, double size, int level, Function function, CoordinateManager coordinateManager) {
        this.level = level;
        this.size = size;
        this.point = point;
        this.function = function;
        this.coordinateManager = coordinateManager;
    }

    public void subdivide(){
        double child_size = size / 2;
        int child_level = level + 1;
        children[0] = new Quadtree(
                new Point(point.getX(), point.getY()), child_size, child_level, function, coordinateManager
        );
        children[1] = new Quadtree(
                new Point(point.getX() + child_size, point.getY()), child_size, child_level, function, coordinateManager
        );
        children[2] = new Quadtree(
                new Point(point.getX() + child_size, point.getY() + child_size), child_size, child_level, function, coordinateManager
        );
        children[3] = new Quadtree(
                new Point(point.getX(), point.getY() + child_size), child_size, child_level, function, coordinateManager
        );
        this.isSubDiv = true;
    }

    public Quadtree[] getChildren(){
        if(isSubDiv) return children;
        else return null;
    }
    public void setX(double x) { point.setX(x); }
    public double getX() { return point.getX(); }
    public void setY(double y) { point.setY(y); }
    public double getY() { return point.getY(); }
    public double getSize() { return size; }
    public Boolean isSubDiv() { return isSubDiv; }

    public Boolean isIntersect(){
        for(int i = 1; i < 4; i++) {
            if(signs[i] != signs[i - 1]) return true;
        }
        return false;
    }
    public List<Line> gridSearch(Color color, int strokeWidth){
        List<Line> lines = new ArrayList<>();
        gridSearch(lines, color, strokeWidth);
        return lines;
    }

    private Point interpolateX(double x1, double y1, double x2, double y2) {
        double f1 = function.get(x1, y1);
        double f2 = function.get(x2, y2);
        double r = f2 - f1;
        return new Point(x2 - f2 * (x2 - x1)/r, y1);
    }
    private Point interpolateY(double x1, double y1, double x2, double y2){
        double f1 = function.get(x1, y1);
        double f2 = function.get(x2, y2);
        double r = f2 - f1;
        return new Point(x1, y2 -  f2 * (y2 - y1)/r);
    }
    private void interpolate(List<Line> lines, Color color, int strokeWidth){
        Point p1, p2;
        if(signs[0] != signs[1] && signs[1] == signs[2] && signs[1] == signs[3]) {
            p1 = interpolateX(point.getX(), point.getY(), point.getX() + size, point.getY());
            p2 = interpolateY(point.getX(), point.getY(), point.getX(), point.getY() + size);
        }
        else if(signs[0] != signs[1] && signs[0] == signs[2] && signs[0] == signs[3]) {
            p1 = interpolateX(point.getX(), point.getY(), point.getX() + size, point.getY());
            p2 = interpolateY(point.getX() + size, point.getY(), point.getX() + size, point.getY() + size);
        }
        else if(signs[0] != signs[2] && signs[0] == signs[1] && signs[0] == signs[3]) {
            p1 = interpolateX(point.getX(), point.getY() + size, point.getX() + size, point.getY() + size);
            p2 = interpolateY(point.getX() + size, point.getY(), point.getX() + size, point.getY() + size);
        }
        else if(signs[0] != signs[3] && signs[0] == signs[2] && signs[0] == signs[1]) {
            p1 = interpolateX(point.getX(), point.getY() + size, point.getX() + size, point.getY() + size);
            p2 = interpolateY(point.getX(), point.getY(), point.getX(), point.getY() + size);
        }
        else if(signs[0] == signs[3] && signs[0] != signs[1] && signs[1] == signs[2]) {
            p1 = interpolateX(point.getX(), point.getY(), point.getX() + size, point.getY());
            p2 = interpolateX(point.getX(), point.getY() + size, point.getX() + size, point.getY() + size);
        }
        else if(signs[0] == signs[1] && signs[1] != signs[2] && signs[2] == signs[3]) {
            p1 = interpolateY(point.getX(), point.getY(), point.getX(), point.getY() + size);
            p2 = interpolateY(point.getX() + size, point.getY(), point.getX() + size, point.getY() + size);
        }
        else {
            p1 = new Point(0, 0);
            p2 = new Point(0, 0);
        }
        //Line(p1, p2) Отрисовываем линию
        double centerX = coordinateManager.getCenterX();
        double centerY = coordinateManager.getCenterY();
        double pixelSize = coordinateManager.getPixelSize();

        double x0 = (p1.getX() / pixelSize + centerX);
        double y0 = (-p1.getY() / pixelSize + centerY);
        double x1 = (p2.getX() / pixelSize + centerX);
        double y1 = (-p2.getY() / pixelSize + centerY);

        if(coordinateManager.onScreen(x0, y0) && coordinateManager.onScreen(x1, y1)) {
            Line line = new Line(x0, y0, x1, y1);
            line.setStroke(color);
            line.setStrokeWidth(strokeWidth);
            lines.add(line);
        }
    }

    private void gridSearch(List<Line> lines, Color color, int strokeWidth){
        /*boolean isZeros = false;
        signs[0] = (int)function.get(point.getX(), point.getY());
        signs[1] = (int)function.get(point.getX() + size, point.getY());
        signs[2] = (int)function.get(point.getX() + size, point.getY() + size);
        signs[3] = (int)function.get(point.getX(), point.getY() + size);
        if(isZeros()) isZeros = true;*/

        signs[0] = getSign(point.getX(), point.getY());
        signs[1] = getSign(point.getX() + size, point.getY());
        signs[2] = getSign(point.getX() + size, point.getY() + size);
        signs[3] = getSign(point.getX(), point.getY() + size);
        zerosToSign();

        if(isIntersect()) {
            isSegment = true;
            if(level < maxLevel){
                subdivide();
                for(int i = 0; i < 4; i++){
                    children[i].gridSearch(lines, color, strokeWidth);
                }
            }
            else interpolate(lines, color, strokeWidth);
        }
    }
    private int getSign(double x, double y){
        double z = function.get(x, y);
        return (z < 0) ? -1 : (z > 0) ? 1 : 0;
    }
    private void zerosToSign(){
        met:
        for(int i = 0; i < 4; i++){
            if(signs[i] != 0) continue;
            for(int j = 0; j < 4; j++){
                if(signs[j] == 1) {
                    signs[i] = -1;
                    continue met;
                }
                else if(signs[j] == -1) {
                    signs[i] = 1;
                    continue met;
                }
            }
            signs[i] = 1;
        }
    }
    private boolean isZeros(){
        return signs[0] == 0 && signs[1] == 0 && signs[2] == 0 && signs[3] == 0;
    }

    private final Quadtree[] children = new Quadtree[4];
    private final Point point;
    private final double size;
    private final int level;
    private static final int maxLevel = 5;
    private Boolean isSubDiv;
    private Boolean isSegment;
    private final int[] signs = new int[4];
    private final Function function;
    private final CoordinateManager coordinateManager;
}
