package com.propcool.cmpm_project.functions.combination;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.Constant;

/** Показательная функция */

public class Exponential extends Combination {
    public Exponential(Function f1, Function f2) {
        super(f1, f2);
    }

    @Override
    public double get(double x) {
        if(!(func2 instanceof Constant c && c.get(0)-(int)c.get(0) == 0) && func1.get(x) < 0) return Double.NaN;
        return Math.pow(func1.get(x), func2.get(x));
    }
}
