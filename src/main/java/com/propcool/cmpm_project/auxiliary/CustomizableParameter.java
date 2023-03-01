package com.propcool.cmpm_project.auxiliary;

import com.propcool.cmpm_project.functions.basic.Constant;

public class CustomizableParameter {
    public CustomizableParameter(Constant param) {
        this.param = param;
    }
    public Constant getParam() {
        return param;
    }
    public String getName() {
        return parameterData.getName();
    }

    public void setName(String name) {
        parameterData.setName(name);
    }

    public double getValue() {
        return parameterData.getValue();
    }

    public void setValue(double value) {
        parameterData.setValue(value);
    }

    public double getArea() {
        return parameterData.getArea();
    }

    public void setArea(double area) {
        parameterData.setArea(area);
    }
    public ParameterData getData() {
        return parameterData;
    }
    private final Constant param;

    private final ParameterData parameterData = new ParameterData();
}
