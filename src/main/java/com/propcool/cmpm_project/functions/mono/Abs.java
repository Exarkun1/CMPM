package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class Abs extends Mono {
    public Abs(Function f){
        super(f);
    }
    public Abs(){}
    @Override
    public double get(double x, double y) {
        return Math.abs(func.get(x, y));
    }
}
