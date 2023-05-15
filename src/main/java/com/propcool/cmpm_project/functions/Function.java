package com.propcool.cmpm_project.functions;

import com.propcool.cmpm_project.util.Point;

public interface Function extends Cloneable {
    double get(double x, double y);
    default double get(double x){
        return get(x, 0);
    }
    default double get(Point p) {
        return get(p.getX(), p.getY());
    }
    default double getLeftBorder(){
        return -Double.MAX_VALUE;
    }
    default double getRightBorder(){
        return Double.MAX_VALUE;
    }
    Function clone();
}
