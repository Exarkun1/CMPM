package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class Ch extends Mono {
    public Ch(Function f) {
        super(f);
    }
    public Ch() {}
    @Override
    public double get(double x, double y) {
        double z = func.get(x, y);
        return (Math.exp(z) + Math.exp(-z))/2;
    }
}
