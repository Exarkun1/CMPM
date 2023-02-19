package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.Variable;
/** Внешняя функция одной переменной */
public abstract class Mono implements Function {
    public Mono(Function f){
        this.func = f;
    }
    public Mono(){
        this.func = new Variable();
    }
    protected final Function func;
}
