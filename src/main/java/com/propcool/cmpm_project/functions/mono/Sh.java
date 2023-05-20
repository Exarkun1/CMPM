package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class Sh extends Mono {
    public Sh(Function f) {
        super(f);
    }
    public Sh() {}
    @Override
    public double get(double x, double y) {
        double z = func.get(x, y);
        return (Math.exp(z) - Math.exp(-z))/2;
    }
}
