package com.propcool.cmpm_project.functions;

public interface Function extends Cloneable {
    double get(double x);
    default double getLeftBorder(){
        return -Double.MAX_VALUE;
    }
    default double getRightBorder(){
        return Double.MAX_VALUE;
    }
    Function clone();
}
