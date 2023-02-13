package org.cmpm.functions.mono;

import org.cmpm.functions.Function;
import org.cmpm.functions.basic.Variable;
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
