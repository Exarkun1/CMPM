package org.cmpm.functions.combination;

import org.cmpm.functions.Function;

public class Multiply extends Combination {
    public Multiply(Function f1, Function f2) {
        super(f1, f2);
    }

    @Override
    public double get(double x) {
        return func1.get(x) * func2.get(x);
    }
}
