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
        this(new VariableX());
    }
    @Override
    public Constant getFunction1(){
        return (Constant) super.getFunction1();
    }
    @Override
    public double getLeftBorder() {
        return func2 instanceof VariableX ? 0 : super.getLeftBorder();
    }
}
