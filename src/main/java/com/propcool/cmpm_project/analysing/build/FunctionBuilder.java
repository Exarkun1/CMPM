package com.propcool.cmpm_project.analysing.build;

import com.propcool.cmpm_project.analysing.Analyser;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import com.propcool.cmpm_project.notebooks.data.CustomizableParameter;
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
    public String building(String text){
        String new_text = analyser.processing(text, functionManager);
        if(new_text != null){
            String functionName = new_text.replaceAll("\\(.+|=.+", "");
            String functionBase = new_text.replaceAll(".+=", "");

            // Если функция имеет уникальное имя, то возвращаем функцию с этим именем
            if(!functionName.equals("y")) {
                NamedFunction nf = new NamedFunction(functionName);
                CustomizableFunction cf = new CustomizableFunction(buildingNotNamed(functionBase, nf), nf.getParams());
                FunctionData functionData = cf.getData();
                functionData.setExpression(text);
                functionManager.putFunction(functionName, cf);
                return functionName;
            }
            // Иначе добаляем уникальный идентификатор
            else {
                String idY = String.valueOf(count_y++);
                NamedFunction nf = new NamedFunction(idY);
                CustomizableFunction cf = new CustomizableFunction(buildingNotNamed(functionBase, nf), nf.getParams());
                FunctionData functionData = cf.getData();
                functionData.setExpression(text);
                functionManager.putFunction(idY, cf);
                return idY;
            }
        } else {
            String idBad = (count_bad++)+"bad";
            CustomizableFunction cf = new CustomizableFunction(null, new ArrayList<>());
            FunctionData functionData = cf.getData();
            functionData.setExpression(text);
            functionManager.putFunction(idBad, cf);
            return idBad;
        }
    }
    /**
     * Внутренняя реализация построителя функций через рекурсию
     * (также ведётся сохранение параметров функции в список)
     * */
    public Function buildingNotNamed(String text, NamedFunction nf){
        // Удаление внешних пробелов, с проверкой, что функция не изменится
        text = removingOuterBrackets(text);
        // Выход из рекурсии
        if(text.equals("x")) return new Variable();
        else if(text.equals("e")) return new Constant(Math.E);
        else if(text.equals("pi")) return new Constant(Math.PI);
        else if(text.matches("\\d+|\\d+\\.\\d+")) return new Constant(Double.parseDouble(text));
        else if(text.matches("[a-z]+\\d+|[a-z]+")){
            //Временно удалена возможность добавления кастомных функций в другую функцию(на доработку)
            //CustomizableFunction cf = Elements.functions.get(text);
            //if(cf != null){
                //return cf.getFunction();
            //}
            CustomizableParameter cp = functionManager.getParam(text);
            if(cp == null) {
                Constant c = new Constant(1);
                cp = new CustomizableParameter(c);
                cp.setArea(10);
                cp.setValue(1);
                cp.setName(text);
                functionManager.putParam(text, cp);
            }
            nf.getParams().add(text);
            // Добавление ссылки на функцию в параметре
            cp.getRefFunctions().add(nf.getName());
            return cp.getParam();
        }

        // Тело рекурсии, последовательный поиск символов операций по возрастанию их "силы"
        for(var factory : factories){
            Function temp = searchingFunctions(text, nf, factory);
            if(temp != null) return temp;
        }
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

            new ContainFactoryAdapter((b, e, s, p) -> new Exponential(buildingNotNamed(b, p), buildingNotNamed(e, p)),
                    s -> s == '^'),

            new ContainFactoryAdapter((b, e, s, p) -> createFunctionByName(b, e, p),
                    s -> s == '!')
    );
    private Function createFunctionByName(String begin, String end, NamedFunction nf){
        return functionManager.getKeyWord(begin).createFunction(begin, end, '\0', nf);
    }
    private final Analyser analyser = new Analyser();
    private static int count_y;
    private static int count_bad;
    private final FunctionManager functionManager;
}
