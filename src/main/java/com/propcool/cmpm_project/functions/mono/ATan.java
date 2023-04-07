package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class ATan extends Mono{
    public ATan(Function f){
        super(f);
    }
    public ATan(){}

    @Override
    public double get(double x, double y) {
        return Math.atan(func.get(x, y));
    }
}
