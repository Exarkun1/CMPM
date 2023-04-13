package com.propcool.cmpm_project.analysing.build;

import com.propcool.cmpm_project.analysing.Analyser;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.*;
import com.propcool.cmpm_project.functions.combination.*;
import com.propcool.cmpm_project.notebooks.data.FunctionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Построитель функции
 * */
public class FunctionBuilder {
    public FunctionBuilder(FunctionManager functionManager){
        this.functionManager = functionManager;
    }
    /**
     * Анализ и построение функиии
     * */
    public String building(String text, int defaultName){
        String new_text = analyser.processing(text, functionManager);
        CustomizableFunction cf;
        String functionName;
        if(new_text != null){
            functionName = new_text.replaceAll("\\(.+|=.+", "");
            String functionBase = new_text.replaceAll(".+=", "");

            // Если функция именуется нулём, то она неявная
            if(functionName.equals("0")) {
                functionName = defaultName+"imp";
                NamedFunction nf = new NamedFunction(functionName);
                cf = new CustomizableFunction(buildingNotNamed(functionBase, nf));
                cf.addParams(nf.getParams());
            }
            // Если функция именуется y', то это дифференциальное уравнение
            else if(functionName.equals("y'")){
                functionName = defaultName+"dif";
                NamedFunction nf = new NamedFunction(functionName);
                cf = new CustomizableFunction(buildingNotNamed(functionBase, nf));
                cf.addParams(nf.getParams());
            }
            // Если функция имеет уникальное имя, то возвращаем функцию с этим именем
            else if(!functionName.equals("y")) {
                NamedFunction nf = new NamedFunction(functionName);
                cf = new CustomizableFunction(buildingNotNamed(functionBase, nf));
                cf.addParams(nf.getParams());
            }
            // Иначе добавляем уникальный идентификатор
            else {
                functionName = String.valueOf(defaultName);
                NamedFunction nf = new NamedFunction(functionName);
                cf = new CustomizableFunction(buildingNotNamed(functionBase, nf));
                cf.addParams(nf.getParams());
            }
        } else {
            functionName = (defaultName)+"bad";
            cf = new CustomizableFunction(null);
        }
        FunctionData functionData = cf.getData();
        functionData.setExpression(text);
        functionData.setDefaultName(defaultName);
        functionManager.putFunction(functionName, cf);
        return functionName;
    }
    /**
     * Внутренняя реализация построителя функций через рекурсию
     * (также ведётся сохранение параметров функции в список)
     * */
    public Function buildingNotNamed(String text, NamedFunction nf){
        // Удаление внешних пробелов, с проверкой, что функция не изменится
        text = removingOuterBrackets(text);
        // Выход из рекурсии
        if(text.equals("x")) return new FunctionDecoratorX();
        else if((nf.getName().matches("\\d+imp") || nf.getName().matches("\\d+dif")) && text.equals("y"))
            return new FunctionDecoratorY();
        else if(text.equals("e")) return new Constant(Math.E);
        else if(text.equals("pi")) return new Constant(Math.PI);
        else if(text.matches("\\d+|\\d+\\.\\d+")) return new Constant(Double.parseDouble(text));
        else if(text.matches("[a-z]+\\d+|[a-z]+")) return functionManager.returnParam(text, nf);

        // Тело рекурсии, последовательный поиск символов операций по возрастанию их "силы"
        for(var factory : factories){
            Function temp = searchingFunctions(text, nf, factory);
            if(temp != null) return temp;
        }
        Function dif = searchingDifferentials(text, nf);
        if(dif != null) return dif;
        throw new RuntimeException("Не верные знаки");
    }
    /**
     * Удаление внешних пробелов, если от них ничего не зависит
     * */
    private String removingOuterBrackets(String text){
        while (true){
            if (text.charAt(0) == '(' && text.charAt(text.length()-1) == ')') {
                String temp = text.substring(1, text.length()-1);
                // Так как мы уже изменили функцию(так что она уже не рабочаю), надо вернуть её в нормальное состояние для проверки
                String tempAnalysable = ("y=" + temp)
                        .replaceAll("!", "")
                        .replaceAll("#", "")
                        .replaceAll("\\.", ",");
                if(analyser.processing(tempAnalysable, functionManager) != null) {
                    text = temp;
                }
                else break;
            }
            else break;
        }
        return text;
    }
    /**
     * Поиск разделителя функций и создание
     * */
    private Function searchingFunctions(String text, NamedFunction nf, ContainFactoryAdapter factory){
        int count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && factory.contain(symbol)) {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                return factory.createFunction(begin, end, symbol, nf);
            }
        }
        return null;
    }
    /**
     * Создание производной функции
     * */
    private Function searchingDifferentials(String text, NamedFunction nf){
        int count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && symbol == '\'') {
                int j = i+1;
                while(symbol == '\''){
                    symbol = text.charAt(--i);
                }
                String begin = text.substring(0, i+1);
                String end = text.substring(j);

                CustomizableFunction cf = functionManager.getFunction(begin);
                Function dif;
                if(cf != null) {
                    dif = cf.getFunction().clone();
                    cf.getRefFunctions().add(nf.getName());
                }
                else {
                    // Производная стандартной функции или параметра
                    FunctionFactory ff = functionManager.getKeyWord(begin);
                    if(ff != null) dif = ff.createFunction(begin, end, '!', nf);
                    else return functionManager.buildDif(functionManager.returnParam(begin, nf));
                }

                // Ищем производную столько раз сколько '
                for(int k = 0; k < j-i-1; k++){
                    dif = functionManager.buildDif(dif);
                }

                // Подставляем в производную значения
                List<FunctionDecoratorX> decors = new ArrayList<>();
                functionManager.getFuncDecorators(dif, decors);
                for(var decor : decors){
                    decor.setFunction(buildingNotNamed(end, nf));
                }

                return dif;
            }
        }
        return null;
    }
    /**
     * Список фабрик для стандартных функций
     * */
    private final List<ContainFactoryAdapter> factories = List.of(
            new ContainFactoryAdapter((b, e, s, p) -> {
                if(s == '+') return new Sum(buildingNotNamed(b, p), buildingNotNamed(e, p));
                else return new Difference(buildingNotNamed(b, p), buildingNotNamed(e, p));
            }, s -> s == '+' || s == '-'),

            new ContainFactoryAdapter((b, e, s, p) -> {
                if(s == '*') return new Multiply(buildingNotNamed(b, p), buildingNotNamed(e, p));
                else return new Division(buildingNotNamed(b, p), buildingNotNamed(e, p));
            }, s -> s == '*' || s == '/'),

            new ContainFactoryAdapter((b, e, s, p) -> new Multiply(buildingNotNamed(b, p), buildingNotNamed(e, p)),
                    s -> s == '#'),

            new ContainFactoryAdapter((b, e, s, p) -> {
                Function first = buildingNotNamed(b, p);
                Function second = buildingNotNamed(e, p);
                if(second instanceof Constant c){
                    return new Pow(first, c);
                } else if (first instanceof Constant c) {
                    return new Exp(c, second);
                }
                else return new Exponential(first, second);
            },
                    s -> s == '^'),

            new ContainFactoryAdapter((b, e, s, p) -> createFunctionByName(b, e, p),
                    s -> s == '!')
    );
    private Function createFunctionByName(String begin, String end, NamedFunction nf){
        FunctionFactory functionFactory = functionManager.getKeyWord(begin);
        if(functionFactory != null) return functionFactory.createFunction(begin, end, '\0', nf);
        return functionManager.getFunctionFactory().createFunction(begin, end, '\0', nf);
    }
    private final Analyser analyser = new Analyser();
    private final FunctionManager functionManager;
}
