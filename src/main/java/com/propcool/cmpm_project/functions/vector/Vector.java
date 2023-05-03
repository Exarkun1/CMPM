package com.propcool.cmpm_project.functions.vector;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.Constant;
import com.propcool.cmpm_project.functions.combination.Combination;
import com.propcool.cmpm_project.functions.combination.Difference;
import com.propcool.cmpm_project.functions.combination.Multiply;
import com.propcool.cmpm_project.functions.combination.Sum;

public class Vector extends Combination {
    public Vector(Function f1, Function f2) {
        super(f1, f2);
    }

    @Override
    public double get(double x, double y) {
        return func1.get(x, y) * func2.get(x, y);
    }
    public double[] getVec(double x, double y) {
        return new double[]{func1.get(x, y), func2.get(x, y)};
    }
    public Vector getVecC(double x, double y) {
        double[] v = getVec(x, y);
        return new Vector(new Constant(v[0]), new Constant(v[1]));
    }
    public Sum mul(Vector v) {
        return new Sum(new Multiply(func1, v.func1), new Multiply(func2, v.func2));
    }
}
