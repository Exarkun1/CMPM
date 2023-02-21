package com.propcool.cmpm_project.analysing.build;

import com.propcool.cmpm_project.functions.Function;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс с данными о фунции
 * */
public class NamedFunction{
    public NamedFunction(String name, Function function, List<String> params){
        this.name = name;
        this.function = function;
        addAll(params);
    }
    public NamedFunction(String name, Function function){
        this.name = name;
        this.function = function;
    }
    public String getName() {
        return name;
    }
    public Function getFunction() {
        return function;
    }
    public boolean contain(String param){
        return params.contains(param);
    }
    public void addAll(List<String> params){
        this.params.addAll(params);
    }
    public List<String> getParams() {
        return params;
    }

    private final String name;
    private Function function;
    private final List<String> params = new ArrayList<>();
}
