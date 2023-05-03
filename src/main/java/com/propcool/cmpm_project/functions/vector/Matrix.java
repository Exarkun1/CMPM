package com.propcool.cmpm_project.functions.vector;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.Constant;
import com.propcool.cmpm_project.functions.combination.Combination;
import com.propcool.cmpm_project.functions.combination.Difference;
import com.propcool.cmpm_project.functions.combination.Division;
import com.propcool.cmpm_project.functions.combination.Multiply;

public class Matrix extends Combination {
    public Matrix(Function f1, Function f2, Function f3, Function f4) {
        super(new Vector(f1, f2), new Vector(f3, f4));
    }
    public Matrix(Vector v1, Vector v2) {
        super(v1, v2);
    }
    @Override
    public double get(double x, double y) {
        return det().get(x, y);
    }
    public Vector getFunc1() {
        return (Vector) super.getFunc1();
    }

    public Vector getFunc2() {
        return (Vector) super.getFunc2();
    }
    public double[][] getMat(double x, double y) {
        return new double[][]{
                getFunc1().getVec(x, y),
                getFunc2().getVec(x, y)
        };
    }
    public Matrix getMatC(double x, double y) {
        double[][] m = getMat(x, y);
        return new Matrix(
                new Constant(m[0][0]),
                new Constant(m[0][1]),
                new Constant(m[1][0]),
                new Constant(m[1][1])
        );
    }
    public Function det() {
        return new Difference(
                new Multiply(getFunc1().getFunc1(), getFunc2().getFunc2()),
                new Multiply(getFunc1().getFunc2(), getFunc2().getFunc1())
        );
    }
    public Vector mul(Vector v) {
        return new Vector(getFunc1().mul(v), getFunc2().mul(v));
    }
    public Matrix rev() {
        Function det = det();
        return new Matrix(
                new Division(getFunc2().getFunc2(), det),
                new Difference(new Division(getFunc1().getFunc2(), det)),
                new Difference(new Division(getFunc2().getFunc1(), det)),
                new Division(getFunc1().getFunc1(), det)
        );
    }
}
