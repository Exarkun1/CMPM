package org.cmpm.analysing.build;

import org.cmpm.functions.Function;

public interface Builder {
    static Function buildingBunch(String text, Builder typeFunction){
        int count = 0;
        for(int i = text.length()-1; i > 0; i--){
            char symbol = text.charAt(i);
            if(symbol == ')') count++;
            else if(symbol == '(') count--;

            if(count == 0 && typeFunction.contain(symbol)) {
                String begin = text.substring(0, i);
                String end = text.substring(i+1);
                return typeFunction.createFunction(begin, end, symbol);
            }
        }
        return null;
    }
    boolean contain(char symbol);
    Function createFunction(String begin, String end, char symbol);
}
