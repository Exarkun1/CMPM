package com.propcool.cmpm_project.functions.interpolate;

import com.propcool.cmpm_project.functions.Function;

public class Polynomial extends PolynomialWithX {
    public Polynomial(Function f, double[] A) {
        super(f, A, new double[A.length]);
    }
    public Polynomial(double[] A) {
        super(A, new double[A.length]);
    }
    public int dim() { return A.length; }
    public double getA(int index) { return A[index]; }
}
