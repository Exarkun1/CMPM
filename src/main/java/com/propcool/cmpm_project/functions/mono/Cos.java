package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class Cos extends Mono {
    public Cos(Function f) {
        super(f);
    }
    public Cos(){}

    @Override
    public double get(double x, double y) {
        return Math.cos(func.get(x, y));
    }
}
