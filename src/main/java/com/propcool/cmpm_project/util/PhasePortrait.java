package com.propcool.cmpm_project.util;

import com.propcool.cmpm_project.functions.Function;
import javafx.scene.shape.Line;

import java.util.List;

public class PhasePortrait {
    public List<Line> solveLiner(Function f, Function g, double[][] A, double[] b) {
        return null;
    }
    /**
     * Решение системы методом Гаусса
     * */
    private Point gauss(double[][] A) {
        //Особая точка портрета, нормируем относительно первого столбца
        for(int i = 0; i < 2; i++) {
            double a = A[i][0];
            for(int j = 0; j < 2; j++) A[i][j] /= a;
        }
        for(int i = 0; i < 2; i++) {
            A[1][i] -= A[0][i];
        }
        //Вычисление особой точки
        double y = 0 / A[1][1];
        return new Point(0 - A[0][1]*y, y);
    }
    /**
     * Вычисление длины вектора
     * */
    private double dist(Point p) {
        return Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2));
    }
}
