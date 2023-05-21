package com.propcool.cmpm_project.functions.interpolate;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.mono.Mono;

public class PolynomialWithX extends Mono {
    public PolynomialWithX(Function f, double[] A, double[] X) {
        super(f);
        this.A = A;
        this.X = X;
    }
    public PolynomialWithX(double[] A, double[] X) {
        this.A = A;
        this.X = X;
    }
    @Override
    public double get(double x, double y) {
        double z = func.get(x, y);
        double sum = A[0];
        double mul = 1;
        for(int i = 1; i < A.length; i++) {
            mul *= z - X[i-1];
            sum += A[i] * mul;
        }
        return sum;
    }

    @Override
    public PolynomialWithX clone() {
        PolynomialWithX polynomialWithX = (PolynomialWithX) super.clone();
        System.arraycopy(A, 0, polynomialWithX.A, 0, A.length);
        return polynomialWithX;
    }
    protected final double[] A;
    protected final double[] X;
}
