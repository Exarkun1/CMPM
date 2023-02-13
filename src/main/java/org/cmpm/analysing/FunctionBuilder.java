package org.cmpm.analysing;

import org.cmpm.Parameters;
import org.cmpm.functions.Function;
import org.cmpm.functions.basic.*;
import org.cmpm.functions.combination.*;
import org.cmpm.functions.mono.*;

public class FunctionBuilder {
    public Function building(String text){
        text = analyser.processing(text);
        if(text != null){
            String functionName = text.replaceAll("\\(.+|=.+", "");
            String functionBase = text.replaceAll(".+=", "");
            if(Parameters.functions.get(functionName) != null) return null;
            else{
                Parameters.functions.put(functionName, recursiveBuilding(functionBase));
                return Parameters.functions.get(functionName);
            }
        }
        return null;
    }
    private Function recursiveBuilding(String text){
        text = removingOuterBrackets(text);
        if(text.equals("x")) return new Variable();
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
        int count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && (symbol == '+' || symbol == '-')) {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                if(symbol == '+')return new Sum(recursiveBuilding(begin), recursiveBuilding(end));
                else return new Difference(recursiveBuilding(begin), recursiveBuilding(end));
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
                if(symbol == '*')return new Multiply(recursiveBuilding(begin), recursiveBuilding(end));
                else return new Division(recursiveBuilding(begin), recursiveBuilding(end));
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
                return new Multiply(recursiveBuilding(begin), recursiveBuilding(end));
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
                return new Exponential(recursiveBuilding(begin), recursiveBuilding(end));
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
                return createFunction(begin, end);
            }

        }
        return null;
    }
    private String removingOuterBrackets(String text){
        while (true){
            if (text.charAt(0) == '(' && text.charAt(text.length()-1) == ')') {
                String temp = text.substring(1, text.length()-1);
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
    private Function createFunction(String begin, String end){
        switch (begin) {
            case "sqrt" -> {
                return new Pow(recursiveBuilding(end), 0.5);
            }
            case "exp" -> {
                return new Exp(recursiveBuilding(end));
            }
            case "abs" -> {
                return new Abs(recursiveBuilding(end));
            }
            case "log" -> {
                return new Log(2, recursiveBuilding(end));
            }
            case "ln" -> {
                return new Log(recursiveBuilding(end));
            }
            case "sin" -> {
                return new Sin(recursiveBuilding(end));
            }
            case "cos" -> {
                return new Cos(recursiveBuilding(end));
            }
            case "tan" -> {
                return new Tan(recursiveBuilding(end));
            }
            case "ctan" -> {
                return new CTan(recursiveBuilding(end));
            }
            case "asin" -> {
                return new ASin(recursiveBuilding(end));
            }
            case "acos" -> {
                return new ACos(recursiveBuilding(end));
            }
            case "atan" -> {
                return new ATan(recursiveBuilding(end));
            }
            default -> {
                return null;
            }
        }
    }
    private final Analyser analyser = new Analyser();
}
