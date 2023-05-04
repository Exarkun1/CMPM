package com.propcool.cmpm_project.simple;

import com.propcool.cmpm_project.util.DifBuilder;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.*;
import com.propcool.cmpm_project.functions.combination.*;
import com.propcool.cmpm_project.functions.mono.*;

public class SimpleBuilder {
    public Function build(String text) {
        text = analyser.processing(text);
        if(text != null){
            String functionName = text.replaceAll("\\(.+|=.+", "");
            String functionBase = text.replaceAll(".+=", "");
            // Если функция имеет уникальное имя, то возвращаем функцию с этим именем
            return building(functionName, functionBase);
        }
        throw new RuntimeException("Ошибка ввода");
    }
    private Function building(String functionName, String functionBase) {
        String text = removingOuterBrackets(functionBase);
        // Выход из рекурсии
        if(text.equals("x")) return new VariableX();
        else if((functionName.equals("0") || functionName.equals("y'")) && text.equals("y")) return new VariableY();
        else if(text.equals("e")) return new Constant(Math.E);
        else if(text.equals("pi")) return new Constant(Math.PI);
        else if(text.matches("\\d+|\\d+\\.\\d+")) return new Constant(Double.parseDouble(text));
        else if(text.matches("[a-z]+\\d+|[a-z]+")) return new Constant(1);

        // Тело рекурсии, последовательный поиск символов операций по возрастанию их "силы"
        int count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && (symbol == '+' || symbol == '-')) {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                if(symbol == '+')return new Sum(building(functionName, begin), building(functionName, end));
                else return new Difference(building(functionName, begin), building(functionName, end));
            }

        }

        count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && (symbol == '*' || symbol == '/')) {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                if(symbol == '*')return new Multiply(building(functionName, begin), building(functionName, end));
                else return new Division(building(functionName, begin), building(functionName, end));
            }

        }

        count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && symbol == '#') {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                return new Multiply(building(functionName, begin), building(functionName, end));
            }

        }

        count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && symbol == '^') {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                Function first = building(functionName, begin);
                Function second = building(functionName, end);
                if(second instanceof Constant c){
                    return new Pow(first, c);
                } else if (first instanceof Constant c) {
                    return new Exp(c, second);
                }
                else return new Exponential(first, second);
            }
        }

        count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && symbol == '!') {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                return createFunction(functionName, begin, end);
            }

        }

        Function dif = searchingDifferentials(functionName, text);
        if(dif != null) return dif;
        throw new RuntimeException("Не верные знаки");
    }
    private String removingOuterBrackets(String text){
        while (true){
            if (text.charAt(0) == '(' && text.charAt(text.length()-1) == ')') {
                String temp = text.substring(1, text.length()-1);
                // Так как мы уже изменили функцию(так что она уже не рабочая), надо вернуть её в нормальное состояние для проверки
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
    private Function createFunction(String functionName, String begin, String end){
        switch (begin) {
            case "sqrt" -> {
                return new Pow(building(functionName, end), 0.5);
            }
            case "exp" -> {
                return new Exp(building(functionName, end));
            }
            case "abs" -> {
                return new Abs(building(functionName, end));
            }
            case "log" -> {
                return new Log(2, building(functionName, end));
            }
            case "ln" -> {
                return new Log(building(functionName, end));
            }
            case "sin" -> {
                return new Sin(building(functionName, end));
            }
            case "cos" -> {
                return new Cos(building(functionName, end));
            }
            case "tan" -> {
                return new Tan(building(functionName, end));
            }
            case "ctan" -> {
                return new CTan(building(functionName, end));
            }
            case "arcsin" -> {
                return new ASin(building(functionName, end));
            }
            case "arccos" -> {
                return new ACos(building(functionName, end));
            }
            case "arctan" -> {
                return new ATan(building(functionName, end));
            }
            default -> {
                return null;
            }
        }
    }
    private Function searchingDifferentials(String functionName, String text){
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

                Function dif;
                if(keyWords.contain(begin)) dif = createFunction(functionName, begin, end);
                else return new Constant(0);

                // Ищем производную столько раз сколько '
                for(int k = 0; k < j-i-1; k++){
                    dif = difBuilder.difX(dif);
                }

                return dif;
            }
        }
        return null;
    }
    private final KeyWords keyWords = new KeyWords();
    private final SimpleAnalyser analyser = new SimpleAnalyser(keyWords);
    private final DifBuilder difBuilder = new DifBuilder();
}
