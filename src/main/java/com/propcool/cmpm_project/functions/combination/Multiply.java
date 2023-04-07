package com.propcool.cmpm_project.functions.combination;

import com.propcool.cmpm_project.functions.Function;

public class Multiply extends Combination {
    public Multiply(Function f1, Function f2) {
        super(f1, f2);
    }
    @Override
    public double get(double x, double y) {
        return func1.get(x, y) * func2.get(x, y);
    }
}
