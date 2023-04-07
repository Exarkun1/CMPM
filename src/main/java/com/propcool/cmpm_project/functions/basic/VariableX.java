package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
/** Функция равная самой переменной */
public class VariableX implements Function {
    @Override
    public double get(double x, double y) {
        return x;
    }

    @Override
    public VariableX clone() {
        try {
            return (VariableX) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
