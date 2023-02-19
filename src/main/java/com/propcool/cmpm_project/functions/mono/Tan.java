package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class Tan extends Mono {
    public Tan(Function f) {
        super(f);
    }
    public Tan(){}

    @Override
    public double get(double x) {
        return Math.sin(func.get(x)) / Math.cos(func.get(x));
    }
}
