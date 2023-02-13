package org.cmpm.functions.mono;

import org.cmpm.functions.Function;

public class Tan extends Mono {
    public Tan(Function f) {
        super(f);
    }
    public Tan(){}

    @Override
    public double get(double x) {
        return Math.tan(func.get(x));
    }
}
