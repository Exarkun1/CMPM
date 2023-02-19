package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;

public class CTan extends Mono {
    public CTan(Function f) {
        super(f);
    }
    public CTan(){}
    @Override
    public double get(double x) {
        return Math.cos(func.get(x)) / Math.sin(func.get(x));
    }
}
