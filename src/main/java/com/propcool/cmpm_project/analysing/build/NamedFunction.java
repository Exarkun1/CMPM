package com.propcool.cmpm_project.analysing.build;

import com.propcool.cmpm_project.functions.Function;

import java.util.Map;

public class NamedFunction implements Map.Entry<String, Function>{
    public NamedFunction(String name, Function function){
        this.name = name;
        this.function = function;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public Function getValue() {
        return function;
    }

    @Override
    public Function setValue(Function value) {
        Function f = function;
        function = value;
        return f;
    }

    private final String name;
    private Function function;
}
