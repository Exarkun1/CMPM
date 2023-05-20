package com.propcool.cmpm_project.util;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.VariableX;
import com.propcool.cmpm_project.functions.interpolate.Polynomial;
import com.propcool.cmpm_project.functions.interpolate.PolynomialWithX;

public class TabulateBuilder {
    public double[] deltas(double... Y) {
        int n = Y.length;
        double[] dY = new double[n-1];
        for(int i = 0; i < n-1; i++) {
            dY[i] = Y[i+1] - Y[i];
        }
        return dY;
    }
    public double[][] finiteDiffs(double... Y) {
        int n = Y.length;
        double[][] dY = new double[n-1][];
        for(int i = 0; i < n-1; i++) {
            if(i == 0) dY[i] = deltas(Y);
            else dY[i] = deltas(dY[i-1]);
        }
        return dY;
    }
    public PolynomialWithX interpolateUp(Function f, double x0, double h, double[] Y) {
        if(h <= 0) throw new RuntimeException("Шаг должен быть положительным");
        double[][] dY = finiteDiffs(Y);
        int n = Y.length;
        double[] A = new double[n];
        double[] X = new double[n-1];
        int fact = 1;
        double H = h;

        A[0] = Y[0];
        for(int i = 1; i < n; i++) {
            A[i] = dY[i-1][0] / (fact*H);
            X[i-1] = x0 + (i-1) * h;
            fact *= i+1;
            H *= h;
        }
        return new PolynomialWithX(f, A, X);
    }
    public PolynomialWithX interpolateDown(Function f, double xn, double h, double[] Y) {
        if(h >= 0) throw new RuntimeException("Шаг должен быть отрицательным");
        double[][] dY = finiteDiffs(Y);
        int n = Y.length;
        double[] A = new double[n];
        double[] X = new double[n-1];
        int fact = 1;
        double H = h;

        A[0] = Y[n-1];
        for(int i = 1; i < n; i++) {
            A[i] = dY[i-1][n-i-1] / (fact*H);
            X[n-i-1] = xn + i * h;
            fact *= i+1;
            H *= h;
        }
        return new PolynomialWithX(f, A, X);
    }

    public Polynomial approximation(Function f, double[] X, double[] Y, int k) {
        if(X.length != Y.length) throw new RuntimeException("Разные размеры X и Y");
        int n = X.length;
        double[] apX = new double[2*k+1];
        double[] b = new double[k+1];
        for(int i = 0; i < 2*k+1; i++) {
            double sum = 0;
            for (double x : X) {
                sum += Math.pow(x, i);
            }
            apX[i] = sum;
        }
        for(int i = 0; i < k+1; i++) {
            double sum = 0;
            for(int j = 0; j < n; j++) {
                sum += Math.pow(X[j], i) * Y[j];
            }
            b[i] = sum;
        }
        double[][] A = new double[k+1][k+1];
        for(int i = 0; i < k+1; i++) {
            System.arraycopy(apX, i, A[i], 0, k + 1);
        }
        double[] S = ss.solve(A, b);
        return new Polynomial(f, S);
    }
    public PolynomialWithX interpolateUp(double xn, double h, double[] Y) {
        return interpolateUp(new VariableX(), xn, h, Y);
    }
    public PolynomialWithX interpolateDown(double xn, double h, double[] Y) {
        return interpolateDown(new VariableX(), xn, h, Y);
    }
    public Polynomial approximation(double[] X, double[] Y, int k) {
        return approximation(new VariableX(), X, Y, k);
    }
    private final SystemSolver ss = new SystemSolver();
}
