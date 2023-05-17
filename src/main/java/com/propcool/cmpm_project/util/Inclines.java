package com.propcool.cmpm_project.util;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.manage.CoordinateManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс для отрисовки дифференциальных уравнений
 * */
public class Inclines {
    public Inclines(Function function,
                    CoordinateManager coordinateManager,
                    Color color, int strokeWidth
    ) {
        this.function = function;
        this.coordinateManager = coordinateManager;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }
    /**
     * Отрисовка поля направлений
     * */
    public List<Line> tangentField() {
        List<Line> lines = new ArrayList<>();
        //Кол-во векторов по оси Х
        int xc = 20;
        //Кол-во векторов по оси Y
        int yc = 20;
        //Делаем определенный шаг по х и у, вычисляем касательную к решению и строим единичный вектор в направлении касательной
        for(int i= 1; i < xc; i++) {
            for (int j = 1; j < yc; j++) {
                double x0 = getX(i, xc);
                double y0 = getY(j, yc);
                //Вычисление угла касательной к оХ
                double k = function.get(coordinateManager.getX(x0, y0), coordinateManager.getY(x0, y0));
                double alpha = Math.atan(k);
                //Вычисление координат вектора через прямоугольный треугольник
                double dy = Math.sin(alpha) * 0.5;
                double dx = Math.cos(alpha) * 0.5;

                double x1 = getX(i + dx, xc);
                double y1 = getY(j + dy, yc);

                if(coordinateManager.onScreen(x0, y0) && coordinateManager.onScreen(x1, y1)) {
                    Line line = new Line(x0, y0, x1, y1);
                    line.setStroke(mix.mix(color, 0.65));
                    line.setStrokeWidth(strokeWidth);
                    lines.add(line);
                }
            }
        }
        return lines;
    }
    /**
     * Отрисовка задачи Коши
     * */
    public List<Line> integralCurve(Point point) {
        List<Line> lines = new ArrayList<>();
        Point p = coordinateManager.getPixelXY(point);
        buildingCurve(lines, p, 1);
        buildingCurve(lines, p, -1);
        //Строим кривую в противоположном направлении, пока не дойдем до конца экрана
        return lines;
    }
    private void buildingCurve(List<Line> lines, Point point, double a) {
        Point p = new Point(point);
        while (coordinateManager.onScreen(p)) {
            //Вычисляем угол наклона
            double k = function.get(coordinateManager.getX(p), coordinateManager.getY(p));
            double alpha = Math.atan(k);
            //Вычисляем координаты конца вектора через прямоугольный треугольник
            double dy = Math.sin(alpha)*a;
            double dx = Math.cos(alpha)*a;

            //Минус dy, чтобы перевернуть
            Line line = new Line(p.getX(), p.getY(), p.getX()+dx, p.getY()-dy);
            line.setStroke(color);
            line.setStrokeWidth(strokeWidth);
            lines.add(line);

            p.setX(p.getX()+dx);
            p.setY(p.getY()-dy);
        }
    }
    private double getX(double i, double xc) {
        return i * (coordinateManager.getWidth()/xc);
    }
    private double getY(double j, double yc) {
        return coordinateManager.getHeight() - j * (coordinateManager.getHeight()/yc);
    }

    private final Function function;
    private final CoordinateManager coordinateManager;
    private final Color color;
    private final int strokeWidth;
    private final Mixer mix = new Mixer();
}
