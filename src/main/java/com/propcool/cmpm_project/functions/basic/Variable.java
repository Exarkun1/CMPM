package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
/** Функция равная самой переменной */
public class Variable implements Function {
    @Override
    public double get(double x) {
        return x;
    }
}
