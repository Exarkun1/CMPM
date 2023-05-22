package com.propcool.cmpm_project.io.data;

import com.propcool.cmpm_project.functions.basic.Constant;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс хранящий всю необходимую информацию о параметрах
 * */
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

    public Set<String> getRefFunctions() {
        return refFunctions;
    }
    public boolean refIsEmpty(){
        return refFunctions.isEmpty();
    }
    public void removeRef(String name){
        refFunctions.remove(name);
    }

    private final Constant param;
    private final ParameterData parameterData = new ParameterData();
    private final Set<String> refFunctions = new HashSet<>();
}
