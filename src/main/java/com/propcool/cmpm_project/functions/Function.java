package com.propcool.cmpm_project.functions;

public interface Function extends Cloneable {
    default double get(double x){
        return get(x, 0);
    }
    double get(double x, double y);
    default double getLeftBorder(){
        return -Double.MAX_VALUE;
    }
    default double getRightBorder(){
        return Double.MAX_VALUE;
    }
    Function clone();
}
