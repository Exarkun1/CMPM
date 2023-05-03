package com.propcool.cmpm_project.functions.combination;

import com.propcool.cmpm_project.functions.Function;
/** Комбинирование 2 функций */
public abstract class Combination implements Function {
    public Combination(Function f1, Function f2){
        this.func1 = f1;
        this.func2 = f2;
    }
    @Override
    public double getLeftBorder() {
        return Math.max(func1.getLeftBorder(), func2.getLeftBorder());
    }

    @Override
    public double getRightBorder() {
        return Math.min(func1.getLeftBorder(), func2.getLeftBorder());
    }

    public Function getFunc1() {
        return func1;
    }

    public Function getFunc2() {
        return func2;
    }

    @Override
    public Combination clone() {
        try {
            Combination combination = (Combination) super.clone();
            combination.func1 = func1.clone();
            combination.func2 = func2.clone();
            return combination;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    protected Function func1;
    protected Function func2;
}
