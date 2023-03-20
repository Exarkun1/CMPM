package com.propcool.cmpm_project.notebooks.data;

import com.propcool.cmpm_project.functions.Function;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Класс хранящий всю необходимую информацию о функциях
 * */
public class CustomizableFunction {
    public CustomizableFunction(Function function){
        this.function = function;
    }
    public Function getFunction() {
        return function;
    }
    public Set<String> getParams() {
        return params;
    }
    public Set<String> getRefFunctions() {
        return refFunctions;
    }

    public String getExpression() {
        return functionData.getExpression();
    }

    public void setExpression(String expression) {
        functionData.setExpression(expression);
    }

    public Color getColor() {
        return Color.valueOf(functionData.getColor());
    }

    public void setColor(Color color) {
        functionData.setColor(color.toString());
    }

    public int getWidth() { return functionData.getWidth(); }

    public void setWidth(int width) { functionData.setWidth(width);}

    public FunctionData getData() { return functionData;}
    public void addParams(Collection<String> params){
        this.params.addAll(params);
    }
    public void addRefFunctions(Collection<String> functions){
        refFunctions.addAll(functions);
    }
    public void removeRef(String name){
        refFunctions.remove(name);
    }
    private final Function function;
    private final Set<String> params = new HashSet<>();
    private final Set<String> refFunctions = new HashSet<>();
    private final FunctionData functionData = new FunctionData();
}
