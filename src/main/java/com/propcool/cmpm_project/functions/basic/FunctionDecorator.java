package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.mono.Mono;

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
    @Override
    public double get(double x, double y) {
        return func.get(x, y);
    }

    public void setFunction(Function func){
        this.func = func;
    }
    private boolean changed = false;
}
