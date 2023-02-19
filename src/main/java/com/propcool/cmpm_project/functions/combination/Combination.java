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
    protected final Function func1;
    protected final Function func2;
}
