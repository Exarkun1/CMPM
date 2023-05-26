package com.propcool.cmpm_project.util;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.Constant;
import com.propcool.cmpm_project.functions.basic.VariableX;
import com.propcool.cmpm_project.functions.basic.VariableY;
import com.propcool.cmpm_project.functions.combination.Multiply;
import com.propcool.cmpm_project.functions.combination.Sum;
import com.propcool.cmpm_project.manage.CoordinateManager;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PhasePortrait {
    public PhasePortrait(CoordinateManager coordinateManager, int strokeWidth) {
        this.coordinateManager = coordinateManager;
        this.strokeWidth = strokeWidth;
    }
    public List<Node> solveLiner(double[][] A, double[] b) {
        List<Node> nodes = new ArrayList<>();
        Point min = new Point(coordinateManager.getMinX(), coordinateManager.getMinY());
        Point max = new Point(coordinateManager.getMaxX(), coordinateManager.getMaxY());
        double step = coordinateManager.getPixelSize();

        ComplexPoint lambda = eigen(A);
        if(lambda.getX().getIm() != 0) {
            if(lambda.getX().getRl() == 0) { // Центр
                nodes.addAll(phaseRect(min, max, step*75, A, b, 1, 0.001));
                nodes.addAll(getPoint(A, b, "Центр"));
            } else if(lambda.getX().getRl() > 0) { // Неустойчивый фокус
                nodes.addAll(phaseRect(min, max, step*75, A, b, -1, 0.001));
                nodes.addAll(getPoint(A, b, "Неустойчивый фокус"));
            } else { // Устойчивый фокус
                nodes.addAll(phaseRect(min, max, step*75, A, b, 1,0.001));
                nodes.addAll(getPoint(A, b, "Устойчивый фокус"));
            }
        } else {
            if(lambda.getX().getRl() != lambda.getY().getRl()) {
                if(lambda.getX().getRl() > 0 && lambda.getY().getRl() > 0) { // Неустойчивый узел
                    nodes.addAll(phaseRect(min, max, step*100, A, b, -1, 0.002));
                    nodes.addAll(getPoint(A, b, "Неустойчивый узел"));
                } else if(lambda.getX().getRl() < 0 && lambda.getY().getRl() < 0) { // Устойчивый узел
                    nodes.addAll(phaseRect(min, max, step*100, A, b, 1, 0.001));
                    nodes.addAll(getPoint(A, b, "Устойчивый узел"));
                } else { // Седло
                    nodes.addAll(phaseRect(min, max, step*20, A, b, 1, 0.002));
                    nodes.addAll(getPoint(A, b, "Седло"));
                }
            } else {
                if(lambda.getX().getRl() > 0) {
                    if(A[0][1] == 0 && A[1][0] == 0) { // Неустойчивый правильный узел
                        nodes.addAll(phaseRect(min, max, step*150, A, b, -1, 0.01));
                        nodes.addAll(getPoint(A, b, "Неустойчивый правильный узел"));
                    } else { // Неустойчивый вырожденный узел
                        nodes.addAll(phaseRect(min, max, step*100, A, b, -1, 0.002));
                        nodes.addAll(getPoint(A, b, "Неустойчивый вырожденный узел"));
                    }
                } else {
                    if(A[0][1] == 0 && A[1][0] == 0) { // Устойчивый правильный узел
                        nodes.addAll(phaseRect(min, max, step*150, A, b, 1, 0.01));
                        nodes.addAll(getPoint(A, b, "Устойчивый правильный узел"));
                    } else { // Устойчивый вырожденный узел
                        nodes.addAll(phaseRect(min, max, step*100, A, b, 1, 0.002));
                        nodes.addAll(getPoint(A, b, "Устойчивый вырожденный узел"));
                    }
                }
            }
        }
        return nodes;
    }
    /**
     * Прямоугольник для портрета
     * */
    private List<Node> phaseRect(Point min, Point max, double step, double[][] A, double[] b, int direction, double e) {
        Function f = new Sum(new Sum(new Multiply(new Constant(A[0][0]), new VariableX()), new Multiply(new Constant(A[0][1]), new VariableY())), new Constant(b[0]));
        Function g = new Sum(new Sum(new Multiply(new Constant(A[1][0]), new VariableX()), new Multiply(new Constant(A[1][1]), new VariableY())), new Constant(b[1]));
        double height = coordinateManager.getHeight();
        double wight = coordinateManager.getWidth();

        List<Node> nodes = new ArrayList<>();
        for(double x = min.getX(); x <= max.getX(); x+= step) {
            double pixelX = coordinateManager.getPixelX(x, 0);
            nodes.addAll(phaseCurve(f, g, new Point(pixelX, 2), direction, e));
            nodes.addAll(phaseCurve(f, g, new Point(pixelX, height-2), direction, e));
        }
        for(double y = min.getY(); y <= max.getY(); y+= step) {
            double pixelY = coordinateManager.getPixelY(0, y);
            nodes.addAll(phaseCurve(f, g, new Point(2, pixelY), direction, e));
            nodes.addAll(phaseCurve(f, g, new Point(wight-2, pixelY), direction, e));
        }
        return nodes;
    }
    /**
     * Построение фазовой кривой, проходящей через заданную начальную точку
     * */
    private List<Line> phaseCurve(Function f, Function g, Point p, int direction, double e) {
        List<Line> lines = new ArrayList<>();
        //Строим кривую, пока не выйдем за границы изображения(экрана)
        while (coordinateManager.onScreen(p)){
            //Вычисляем значения в зависимости от шага и направления
            double dx = f.get(coordinateManager.getXY(p)) * direction;
            double dy = -g.get(coordinateManager.getXY(p)) * direction;
            Point dp = p.add(dx, dy);
            //Если изменение слишком малое, то останавливаем построение(Достигли особой точки)
            if(Math.abs(dx) < e || Math.abs(dy) < e) break;
            //Строим прямую
            if(coordinateManager.onScreen(dp)) {
                Line line = new Line(p.getX(), p.getY(), dp.getX(), dp.getY());
                if(direction == 1) line.setStroke(Color.RED);
                else line.setStroke(Color.BLUE);
                line.setStrokeWidth(strokeWidth);
                lines.add(line);
            }
            //Делаем сдвиг
            p = dp;
        }
        return lines;
    }
    private List<Node> getPoint(double[][] A, double[] b, String type) {
        DecimalFormat format = new DecimalFormat("#.#####");
        double[] XY = ss.solve(A, new double[]{-b[0], -b[1]});
        Point p = coordinateManager.getPixelXY(XY[0], XY[1]);
        if(!Double.isNaN(p.getX()) && !Double.isNaN(p.getY()) && coordinateManager.onScreen(p)) {
            Circle circle = new Circle(p.getX(), p.getY(), 5);
            circle.setFill(new Color(0, 0.7, 0, 1));
            circle.setStroke(Color.BLACK);
            Text text = new Text("(" + type + "; "
                    + format.format(coordinateManager.getX(p.getX(), p.getY()))
                    + "; " + format.format(coordinateManager.getY(p.getX(), p.getY())) + ")");
            text.setFont(new Font(18));
            double textWight = text.getBoundsInLocal().getWidth() + 4;
            double textHeight = text.getBoundsInLocal().getHeight();
            Rectangle box = new Rectangle(textWight, textHeight);
            box.setFill(Color.LIGHTGRAY);
            StackPane pane = new StackPane(box, text);
            pane.setVisible(false);
            pane.setLayoutX(p.getX()-textWight/2);
            pane.setLayoutY(p.getY()-textHeight-10);
            circle.setOnMouseEntered(mouseEvent -> pane.setVisible(true));
            circle.setOnMouseExited(mouseEvent -> pane.setVisible(false));
            return List.of(circle, pane);
        }
        return List.of();
    }
    /**
     * Собственные числа матрицы
     * */
    private ComplexPoint eigen(double[][] A) {
        double discriminant = Math.pow(A[0][0] + A[1][1], 2) - 4 * (A[0][0] * A[1][1] - A[0][1] * A[1][0]);
        Complex sqrtD = sqrt(discriminant);
        Complex x = new Complex(A[0][0] + A[1][1]).add(sqrtD).div(2);
        Complex y = new Complex(A[0][0] + A[1][1]).sub(sqrtD).div(2);
        return new ComplexPoint(x, y);
    }
    private Complex sqrt(double d) {
        if(d >= 0) return new Complex(Math.sqrt(d), 0);
        else return new Complex(0, Math.sqrt(-d));
    }
    private final CoordinateManager coordinateManager;
    private final int strokeWidth;
    private final SystemSolver ss = new SystemSolver();
}
