package org.cmpm.functions.combination;

import org.cmpm.functions.Function;
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
