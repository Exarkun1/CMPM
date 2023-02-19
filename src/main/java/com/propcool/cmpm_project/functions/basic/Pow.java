package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.combination.Exponential;

public class Pow extends Exponential {
    public Pow(Function f, double number) {
        super(f, new Constant(number));
    }
    public Pow(double number) {
        this(new Variable(), number);
    }
    @Override
    public double getLeftBorder() {
        return (func2.get(0) - (int)func2.get(0) == 0) ? super.getLeftBorder() : 0;
    }
}
