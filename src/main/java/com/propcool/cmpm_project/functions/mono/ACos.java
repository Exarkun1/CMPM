package com.propcool.cmpm_project.functions.mono;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.VariableX;

public class ACos extends Mono {
    public ACos(Function f){
        super(f);
    }
    public ACos(){}

    @Override
    public double get(double x, double y) {
        return Math.acos(func.get(x, y));
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
