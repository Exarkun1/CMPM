package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class Sin extends Mono {
    public Sin(Function f) {
        super(f);
    }
    public Sin(){}
    @Override
    public double get(double x, double y) {
        return Math.sin(func.get(x, y));
    }
}
