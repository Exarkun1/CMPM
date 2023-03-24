package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.analysing.build.DifBuilder;
import com.propcool.cmpm_project.analysing.build.FunctionBuilder;
import com.propcool.cmpm_project.analysing.build.FunctionFactory;
import com.propcool.cmpm_project.analysing.build.NamedFunction;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.*;
import com.propcool.cmpm_project.functions.combination.Combination;
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
    public String buildFunction(String text, int defaultName){
        return functionBuilder.building(text, defaultName);
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
    public Function returnParam(String name, NamedFunction nf){
        CustomizableParameter cp = getParam(name);
        if(cp == null) {
            Constant c = new Constant(1);
            cp = new CustomizableParameter(c);
            cp.setArea(10);
            cp.setValue(1);
            cp.setName(name);
            putParam(name, cp);
        }
        nf.getParams().add(name);
        // Добавление ссылки на функцию в параметре
        cp.getRefFunctions().add(nf.getName());
        return cp.getParam();
    }
    /**
     * Удаление ссылок на функцию в параметрах
     * */
    public void removeParamRefs(String functionName){
        CustomizableFunction function = getFunction(functionName);
        // Удаление ссылок на данную функцию у параметров
        if(function != null) {
            for (var param : function.getParams()) {
                getParam(param).removeRef(functionName);
            }
        }
    }
    /**
     * Создание фабрики для кастомной функции
     * */
    public FunctionFactory getFunctionFactory(){
        return (begin, end, symbol, nf) -> {
            getFunction(begin).getRefFunctions().add(nf.getName());
            Function function = getFunction(begin).getFunction().clone();
            List<FunctionDecorator> decors = new ArrayList<>();
            getFuncDecorators(function, decors);
            for(var decor : decors){
                decor.setFunction(functionBuilder.buildingNotNamed(end, nf));
            }
            return function;
        };
    }
    /**
     * Выдача функций находящихся над x
     * */
    public void getFuncDecorators(Function function, List<FunctionDecorator> decorators){
        if(function instanceof FunctionDecorator decorator && !decorator.isChanged()) {
            decorators.add(decorator);
            decorator.setChanged(true);
        } else if(function instanceof FunctionDecorator decorator) {
            getFuncDecorators(decorator.getFunction(), decorators);
        } else if(function instanceof Mono mono){
            getFuncDecorators(mono.getFunction(), decorators);
        } else if(function instanceof Combination combination){
            getFuncDecorators(combination.getFunction1(), decorators);
            getFuncDecorators(combination.getFunction2(), decorators);
        }
    }
    /**
     * Преодразование функций, ссылающих на параметр с таким же именен, что и у передаваемой
     * */
    public List<String> rebuildRefsWithParam(String functionName){
        List<String> refs = new ArrayList<>();
        CustomizableParameter cp = getParam(functionName);
        if(cp == null) return refs;
        for(var name : cp.getRefFunctions()){
            if(name.equals(functionName)) continue;
            CustomizableFunction oldCf = removeFunction(name);
            String newFunctionName = buildFunction(oldCf.getExpression(), oldCf.getDefaultName());
            CustomizableFunction newCf = getFunction(newFunctionName);
            newCf.setColor(oldCf.getColor());
            newCf.setWidth(oldCf.getWidth());
            newCf.setDefaultName(oldCf.getDefaultName());
            refs.add(name);
        }
        for(var name : refs){
            cp.getRefFunctions().remove(name);
        }
        return refs;
    }
    /**
     * Преобразование функций ссылающихся на данную
     * */
    public List<String> rebuildRefsWithFunction(CustomizableFunction cf){
        List<String> refs = new ArrayList<>();
        if(cf == null) return refs;
        for(var name : cf.getRefFunctions()){
            CustomizableFunction oldCf = removeFunction(name);
            String newFunctionName = buildFunction(oldCf.getExpression(), oldCf.getDefaultName());
            CustomizableFunction newCf = getFunction(newFunctionName);
            newCf.setColor(oldCf.getColor());
            newCf.setWidth(oldCf.getWidth());
            newCf.setDefaultName(oldCf.getDefaultName());
            refs.add(name);
        }
        for(var name : refs){
            cf.getRefFunctions().remove(name);
        }
        return refs;
    }

    public Function buildDif(Function function){
        return difBuilder.buildDif(function);
    }

    private final Map<String, FunctionFactory> keyWords = new HashMap<>();
    private final List<String> constants = List.of("pi", "e");

    private final  Map<String, CustomizableFunction> functions = new HashMap<>();
    private final  Map<String, CustomizableParameter> parameters = new HashMap<>();
    private final FunctionBuilder functionBuilder = new FunctionBuilder(this);
    private final DifBuilder difBuilder = new DifBuilder();

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
