package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;

public class VariableY implements Function {
    @Override
    public double get(double x, double y) {
        return y;
    }

    @Override
    public VariableY clone() {
        try {
            return (VariableY) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
