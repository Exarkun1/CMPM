package com.propcool.cmpm_project.functions.combination;

import com.propcool.cmpm_project.functions.Function;
/** Логарифмическая функция */

public class Logarithm extends Combination {
    public Logarithm(Function f1, Function f2) {
        super(f1, f2);
    }

    @Override
    public double get(double x) {
        return Math.log(func2.get(x)) / Math.log(func1.get(x));
    }
}
