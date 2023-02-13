package org.cmpm.functions.basic;

import org.cmpm.functions.basic.Constant;
import org.cmpm.functions.Function;
import org.cmpm.functions.combination.Exponential;

public class Exp extends Exponential {
    public Exp(double number, Function f) {
        super(new Constant(number), f);
    }
    public Exp(Function f) {
        this(Math.E, f);
    }
    public Exp() {
        this(new Variable());
    }
    public Exp(double number){this(number, new Variable());}
}
