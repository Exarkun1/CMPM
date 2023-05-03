package com.propcool.cmpm_project.functions.combination;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.Constant;

public class Difference extends Combination {

    public Difference(Function f1, Function f2) {
        super(f1, f2);
    }
    public Difference(Function f) {
        super(new Constant(0), f);
    }

    @Override
    public double get(double x, double y) {
        return func1.get(x, y) - func2.get(x, y);
    }
}
