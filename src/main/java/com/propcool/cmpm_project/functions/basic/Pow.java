package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.combination.Exponential;

public class Pow extends Exponential {
    public Pow(Function f, Constant c){
        super(f, c);
    }
    public Pow(Function f, double number) {
        this(f, new Constant(number));
    }
    public Pow(double number) {
        this(new VariableX(), number);
    }
    @Override
    public Constant getFunction2() {
        return (Constant) func2;
    }
    @Override
    public double getLeftBorder() {
        return (func2.get(0) - (int)func2.get(0) == 0) ? super.getLeftBorder() : 0;
    }
}
