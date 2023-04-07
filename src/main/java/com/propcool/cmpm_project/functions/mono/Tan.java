package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class Tan extends Mono {
    public Tan(Function f) {
        super(f);
    }
    public Tan(){}

    @Override
    public double get(double x, double y) {
        return Math.sin(func.get(x, y)) / Math.cos(func.get(x, y));
    }
}
