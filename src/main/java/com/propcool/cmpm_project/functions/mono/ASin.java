package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.VariableX;

public class ASin extends Mono{
    public ASin(Function f){
        super(f);
    }
    public ASin(){}

    @Override
    public double get(double x, double y) {
        return Math.asin(func.get(x, y));
    }

    @Override
    public double getLeftBorder(){
        return func instanceof VariableX ? -1 : super.getLeftBorder();
    }
    @Override
    public double getRightBorder(){
        return func instanceof VariableX ? 1 : super.getRightBorder();
    }
}
