package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.combination.Exponential;

public class Exp extends Exponential {
    public Exp(Constant c, Function f){
        super(c, f);
    }
    public Exp(double number, Function f) {
        this(new Constant(number), f);
    }
    public Exp(Function f) {
        this(Math.E, f);
    }
    public Exp() {
        this(new VariableX());
    }
    public Exp(double number){this(number, new VariableX());}
    @Override
    public Constant getFunction1() {
        return (Constant) func1;
    }
}
