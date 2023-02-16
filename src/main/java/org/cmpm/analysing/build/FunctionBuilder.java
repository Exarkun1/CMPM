package org.cmpm.analysing.build;

import org.cmpm.Parameters;
import org.cmpm.analysing.Analyser;
import org.cmpm.functions.Function;
import org.cmpm.functions.basic.*;
import org.cmpm.functions.combination.*;
import org.cmpm.functions.mono.*;

import java.util.List;

/**
 * Построитель функции
 * */
public class FunctionBuilder {
    /**
     * Анализ и построение функиии
     * */
    public Function building(String text){
        text = analyser.processing(text);
        if(text != null){
            String functionName = text.replaceAll("\\(.+|=.+", "");
            String functionBase = text.replaceAll(".+=", "");
            if(Parameters.functions.get(functionName) != null) return null;
            else{
                // Если функция имеет уникальное имя, то добавляем это имя в параметры приложения
                if(!functionName.equals("y")) {
                    Parameters.functions.put(functionName, buildingWithoutSaving(functionBase));
                    return Parameters.functions.get(functionName);
                }
                // Иначе добаляем уникальный идентификатор
                else {
                    String index = String.valueOf(count++);
                    Parameters.functions.put(index, buildingWithoutSaving(functionBase));
                    return Parameters.functions.get(index);
                }
            }
        }
        return null;
    }
    /**
     * Внутренняя реализация построителя функций через рекурсия
     * */
    public Function buildingWithoutSaving(String text){
        // Удаление внешних пробелов, с проверкой, что функция не изменится
        text = removingOuterBrackets(text);
        if(text == null) return null;
        // Выход из рекурсии
        if(text.equals("x")) return new Variable();
        else if(text.equals("e")) return new Constant(Math.E);
        else if(text.equals("pi")) return new Constant(Math.PI);
        else if(text.matches("\\d+|\\d+\\.\\d+")) return new Constant(Double.parseDouble(text));
        else if(text.matches("[a-z]+\\d?+")){
            Function f = Parameters.functions.get(text);
            if(f != null){
                return f;
            }
            Constant c = Parameters.parameters.get(text);
            if(c == null) {
                c = new Constant(0);
                Parameters.parameters.put(text, c);
            }
            return c;
        }

        // Тело рекурсии, последовательный поиск символов операций по возрастанию их "силы"
        for(var factory : factories){
            Function temp = searchingFunctions(text, factory);
            if(temp != null) return temp;
        }
        return null;
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
    private Function searchingFunctions(String text, ContainFactoryAdapter factory){
        int count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && factory.contain(symbol)) {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                return factory.createFunction(begin, end, symbol);
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
            new ContainFactoryAdapter((b, e, s) -> {
                if(s == '+') return new Sum(buildingWithoutSaving(b), buildingWithoutSaving(e));
                else return new Difference(buildingWithoutSaving(b), buildingWithoutSaving(e));
            }, s -> s == '+' || s == '-'),

            new ContainFactoryAdapter((b, e, s) -> {
                if(s == '*') return new Multiply(buildingWithoutSaving(b), buildingWithoutSaving(e));
                else return new Division(buildingWithoutSaving(b), buildingWithoutSaving(e));
            }, s -> s == '*' || s == '/'),

            new ContainFactoryAdapter((b, e, s) -> new Multiply(buildingWithoutSaving(b), buildingWithoutSaving(e)),
                    s -> s == '#'),

            new ContainFactoryAdapter((b, e, s) -> new Exponential(buildingWithoutSaving(b), buildingWithoutSaving(e)),
                    s -> s == '^'),

            new ContainFactoryAdapter((b, e, s) -> createFunctionByName(b, e),
                    s -> s == '!')
    );
    private Function createFunctionByName(String begin, String end){
        return Parameters.keyWords.get(begin).createFunction(begin, end, '\0');
    }
}
