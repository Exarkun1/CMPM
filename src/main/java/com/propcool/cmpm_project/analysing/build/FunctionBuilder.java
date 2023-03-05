package com.propcool.cmpm_project.analysing.build;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.analysing.Analyser;
import com.propcool.cmpm_project.auxiliary.CustomizableFunction;
import com.propcool.cmpm_project.auxiliary.CustomizableParameter;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.*;
import com.propcool.cmpm_project.functions.combination.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Построитель функции
 * */
public class FunctionBuilder {
    /**
     * Анализ и построение функиии
     * */
    public NamedFunction building(String text){
        text = analyser.processing(text);
        if(text != null){
            String functionName = text.replaceAll("\\(.+|=.+", "");
            String functionBase = text.replaceAll(".+=", "");

            List<String> params = new ArrayList<>();
            // Если функция имеет уникальное имя, то возвращаем функцию с этим именем
            if(!functionName.equals("y")) {
                return new NamedFunction(functionName, buildingNotNamed(functionBase, params), params);
            }
            // Иначе добаляем уникальный идентификатор
            else {
                String index = String.valueOf(count++);
                return new NamedFunction(index, buildingNotNamed(functionBase, params), params);
            }
        }
        return null;
    }
    /**
     * Внутренняя реализация построителя функций через рекурсию
     * (также ведётся сохранение параметров функции в список)
     * */
    public Function buildingNotNamed(String text, List<String> params){
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
            CustomizableParameter cp = Elements.parameters.get(text);
            if(cp == null) {
                Constant c = new Constant(1);
                cp = new CustomizableParameter(c);
                cp.setArea(10);
                cp.setValue(1);
                cp.setName(text);
                Elements.parameters.put(text, cp);
            }
            params.add(text);
            return cp.getParam();
        }

        // Тело рекурсии, последовательный поиск символов операций по возрастанию их "силы"
        for(var factory : factories){
            Function temp = searchingFunctions(text, params, factory);
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
                if(analyser.processing(tempAnalysable) != null) {
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
    private Function searchingFunctions(String text,List<String> params, ContainFactoryAdapter factory){
        int count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && factory.contain(symbol)) {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                return factory.createFunction(begin, end, symbol, params);
            }
        }
        return null;
    }
    private final Analyser analyser = new Analyser();
    private static int count;
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
    private Function createFunctionByName(String begin, String end, List<String> params){
        return Elements.keyWords.get(begin).createFunction(begin, end, '\0', params);
    }
}
