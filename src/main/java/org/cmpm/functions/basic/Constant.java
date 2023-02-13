package org.cmpm.functions.basic;

import org.cmpm.functions.Function;
/** Постоянная функция (параметр) */
public class Constant implements Function {
    public Constant(double number){
        set(number);
    }
    @Override
    public double get(double x) {
        return number;
    }
    public void set(double number){
        this.number = number;
    }
    private double number;
}
