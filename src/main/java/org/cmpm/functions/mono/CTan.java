package org.cmpm.functions.mono;

import org.cmpm.functions.Function;

public class CTan extends Mono {
    public CTan(Function f) {
        super(f);
    }
    public CTan(){}
    @Override
    public double get(double x) {
        return Math.cos(func.get(x)) / Math.sin(func.get(x));
    }
}
