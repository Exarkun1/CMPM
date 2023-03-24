package com.propcool.cmpm_project.functions.basic;

public class ConstantSum extends Constant{
    public ConstantSum(Constant constant, double number) {
        super(number);
        this.constant = constant;
    }
    @Override
    public double get(double x) {
        return constant.get(x) + super.get(x);
    }
    private final Constant constant;
}
