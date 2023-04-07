package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.VariableX;
/** Внешняя функция одной переменной */
public abstract class Mono implements Function {
    public Mono(Function f){
        this.func = f;
    }
    public Mono(){
        this.func = new VariableX();
    }
    public Function getFunction(){
        return func;
    }
    @Override
    public Mono clone() {
        try {
            Mono mono = (Mono) super.clone();
            mono.func = func.clone();
            return mono;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    protected Function func;
}
