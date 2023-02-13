package org.cmpm.functions.mono;

import org.cmpm.functions.Function;

public class Abs extends Mono {
    public Abs(Function f){
        super(f);
    }
    public Abs(){}
    @Override
    public double get(double x) {
        return Math.abs(func.get(x));
    }
}
