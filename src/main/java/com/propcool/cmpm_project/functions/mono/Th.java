package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class Th extends Mono {
    public Th(Function f) {
        super(f);
    }
    public Th() {}
    @Override
    public double get(double x, double y) {
        double z = func.get(x, y);
        return (Math.exp(z) - Math.exp(-z)) / (Math.exp(z) + Math.exp(-z));
    }
}
