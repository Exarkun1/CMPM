package com.propcool.cmpm_project.notebooks;

import java.io.Serializable;

public class ParameterData implements Serializable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
    private String name;
    private double value;
    private double area;
}
