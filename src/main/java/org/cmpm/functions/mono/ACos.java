package org.cmpm.functions.mono;

import org.cmpm.functions.Function;
import org.cmpm.functions.basic.Variable;

public class ACos extends Mono {
    public ACos(Function f){
        super(f);
    }
    public ACos(){}
    @Override
    public double get(double x) {
        return Math.acos(func.get(x));
    }
    @Override
    public double getLeftBorder(){
        return func instanceof Variable ? -1 : super.getLeftBorder();
    }
    @Override
    public double getRightBorder(){
        return func instanceof Variable ? 1 : super.getRightBorder();
    }
}
