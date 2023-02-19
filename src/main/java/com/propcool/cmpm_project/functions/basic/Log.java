package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.combination.Logarithm;

public class Log extends Logarithm {
    public Log(double number, Function f){
        super(new Constant(number), f);
    }
    public Log(Function f){
        this(Math.E, f);
    }
    public Log(){
        this(new Variable());
    }
    @Override
    public double getLeftBorder() {
        return func2 instanceof Variable ? 0 : super.getLeftBorder();
    }
}
