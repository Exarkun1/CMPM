package org.cmpm.functions;

public interface Function {
    double get(double x);
    default double getLeftBorder(){
        return -Double.MAX_VALUE;
    }
    default double getRightBorder(){
        return Double.MAX_VALUE;
    }
}
