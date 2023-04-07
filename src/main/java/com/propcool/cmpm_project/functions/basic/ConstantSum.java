package com.propcool.cmpm_project.functions.basic;

public class ConstantSum extends Constant{
    public ConstantSum(Constant constant, double number) {
        super(number);
        this.constant = constant;
    }
    @Override
    public double get(double x, double y) {
        return constant.get(x, y) + super.get(x, y);
    }
    private final Constant constant;
}
