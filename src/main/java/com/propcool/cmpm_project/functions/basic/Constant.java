package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
/** Постоянная функция (параметр) */
public class Constant implements Function {
    public Constant(double number){
        set(number);
    }

    @Override
    public double get(double x, double y) {
        return number;
    }

    @Override
    public Constant clone() {
        return this;
    }

    public void set(double number){
        this.number = number;
    }
    private double number;
}
