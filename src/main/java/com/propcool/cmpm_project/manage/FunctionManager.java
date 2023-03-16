package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.analysing.build.FunctionBuilder;
import com.propcool.cmpm_project.analysing.build.FunctionFactory;
import com.propcool.cmpm_project.functions.basic.Exp;
import com.propcool.cmpm_project.functions.basic.Log;
import com.propcool.cmpm_project.functions.basic.Pow;
import com.propcool.cmpm_project.functions.mono.*;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import com.propcool.cmpm_project.notebooks.data.CustomizableParameter;

import java.util.*;
/**
 * Менеджер управления функциями и параметрами
 * */
public class FunctionManager {
    public void putFunction(String name, CustomizableFunction function){
        functions.put(name, function);
    }
    public CustomizableFunction removeFunction(String name){
        return functions.remove(name);
    }
    public CustomizableFunction getFunction(String name){
        return functions.get(name);
    }
    public FunctionFactory getKeyWord(String name) {
        return keyWords.get(name);
    }
    public void putParam(String name, CustomizableParameter param){
        parameters.put(name, param);
    }
    public CustomizableParameter removeParam(String name){
        return parameters.remove(name);
    }
    public CustomizableParameter getParam(String name){
        return parameters.get(name);
    }
    public String buildFunction(String text){
        return functionBuilder.building(text);
    }
    public void clearFunctions(){
        functions.clear();
    }
    public void clearParams(){
        parameters.clear();
    }
    public Map<String, CustomizableFunction> getFunctions(){
        return Collections.unmodifiableMap(functions);
    }
    public Map<String, CustomizableParameter> getParameters(){
        return Collections.unmodifiableMap(parameters);
    }
    public Map<String, FunctionFactory> getKeyWords(){
        return Collections.unmodifiableMap(keyWords);
    }
    /**
     * Удаление ссылок на функцию в параметрах
     * */
    public void removeParamRef(String functionName){
        CustomizableFunction function = getFunction(functionName);
        // Удаление ссылок на данную функцию у параметров
        if(function != null) {
            for (var param : function.getParams()) {
                getParam(param).removeRef(functionName);
            }
        }
    }

    private final Map<String, FunctionFactory> keyWords = new HashMap<>();
    private final List<String> constants = List.of("pi", "e");

    private final  Map<String, CustomizableFunction> functions = new HashMap<>();
    private final  Map<String, CustomizableParameter> parameters = new HashMap<>();
    private final FunctionBuilder functionBuilder = new FunctionBuilder(this);

    public FunctionManager(){
        keyWords.put("sqrt", (b, e, s, p) -> new Pow(functionBuilder.buildingNotNamed(e, p), 0.5));
        keyWords.put("exp", (b, e, s, p) -> new Exp(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("abs", (b, e, s, p) -> new Abs(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("log", (b, e, s, p) -> new Log(2, functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("ln", (b, e, s, p) -> new Log(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("sin", (b, e, s, p) -> new Sin(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("cos", (b, e, s, p) -> new Cos(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("tan", (b, e, s, p) -> new Tan(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("ctan", (b, e, s, p) -> new CTan(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("arcsin", (b, e, s, p) -> new ASin(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("arccos", (b, e, s, p) -> new ACos(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("arctan", (b, e, s, p) -> new ATan(functionBuilder.buildingNotNamed(e, p)));
    }
}
