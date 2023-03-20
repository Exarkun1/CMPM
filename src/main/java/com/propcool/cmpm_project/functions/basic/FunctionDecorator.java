package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.mono.Mono;
/**
 * Функция для замены переменной, если один раз была вызвана для замены, больше обращений к ней не будет
 * */
public class FunctionDecorator extends Mono {
    public FunctionDecorator(Function f) {
        super(f);
    }
    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    public FunctionDecorator() {}
    @Override
    public double get(double x) {
        return func.get(x);
    }
    public void setFunction(Function func){
        this.func = func;
    }
    private boolean changed = false;
}
