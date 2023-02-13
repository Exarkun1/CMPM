package org.cmpm.functions.mono;

import org.cmpm.functions.Function;

public class Sin extends Mono {
    public Sin(Function f) {
        super(f);
    }
    public Sin(){}

    @Override
    public double get(double x) {
        return Math.sin(func.get(x));
    }
}
