package com.propcool.cmpm_project.controllers.auxiliary;

import com.propcool.cmpm_project.functions.Function;
import javafx.scene.paint.Color;

import java.util.List;

public class CustomizableFunction {
    public CustomizableFunction(Function function, List<String> params){
        this.function = function;
        this.params = params;
    }
    public Function getFunction() {
        return function;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    private final Function function;
    private List<String> params;
    private Color color = Color.GREEN;
}
