package com.propcool.cmpm_project.functions.integrate;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.mono.Mono;

public class Stud extends Mono {
    public Stud(Function f, int n) {
        super(f);
        this.n = n;
    }

    public Stud(int n) {
        this.n = n;
    }

    @Override
    public double get(double x, double y) {
        double z = func.get(x, y);
        return 0.5 + z * gammaRatio(0.5 * (n + 1.0), 0.5 * n) *
                hyperGeom(0.5, 0.5 * (n + 1.0), 1.5, -z*z / n, 20) / Math.sqrt(Math.PI * n);
    }
    public double gammaRatio(double x0, double x1) {
        double m = Math.abs(Math.max(x0, x1));
        if (m <= 100.0)
            return gamma.get(x0) / gamma.get(x1);
        return Math.pow(2, x0 - x1)  *
                gammaRatio(x0 * 0.5, x1 * 0.5)  *
                gammaRatio(x0 * 0.5 + 0.5, x1 * 0.5 + 0.5);
    }
    public double hyperGeom(double a, double b, double c, double z, int deep) {
        double S = 1.0, M;
        for (int i = 1; i <= deep; i++) {
            M = Math.pow(z, i);
            for (int j = 0; j < i; j++) {
                M *= (a + j) * (b + j) / ( (1.0 + j) * (c + j) );
            }
            S += M;
        }
        return S;
    }
    private final Gamma gamma = new Gamma();
    private final int n;
}
