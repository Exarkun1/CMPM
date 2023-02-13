package org.cmpm.functions.basic;

import org.cmpm.functions.Function;
/** Функция равная самой переменной */
public class Variable implements Function {
    @Override
    public double get(double x) {
        return x;
    }
}
