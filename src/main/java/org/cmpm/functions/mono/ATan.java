package org.cmpm.functions.mono;

import org.cmpm.functions.Function;

public class ATan extends Mono{
    public ATan(Function f){
        super(f);
    }
    public ATan(){}
    @Override
    public double get(double x) {
        return Math.atan(func.get(x));
    }
}
