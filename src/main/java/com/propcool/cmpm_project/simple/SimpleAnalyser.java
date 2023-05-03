package com.propcool.cmpm_project.simple;

import com.propcool.cmpm_project.analysing.stages.Base;
import com.propcool.cmpm_project.analysing.stages.End;
import com.propcool.cmpm_project.analysing.stages.Name;
import com.propcool.cmpm_project.analysing.stages.State;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleAnalyser {
    public SimpleAnalyser(KeyWords keyWords) {
        this.keyWords = keyWords;
    }
    public String processing(String text){
        if(checkingString(text)){
            text = text.replaceAll("\\s+", ""); // 1
            text = text.replaceAll(",", "."); // 2
            text = text.replaceAll("=-", "=0-"); // 3
            text = text.replaceAll("\\(-", "(0-");

            String functionBase = text.replaceAll(".+=", ""); // Тело функции

            Pattern pattern = Pattern.compile("[a-z]+\\(");
            Matcher matcher = pattern.matcher(functionBase);
            int count = 1;
            while (matcher.find()) {
                String group = matcher.group().substring(0, matcher.group().length()-1);
                int index = matcher.end();
                if(!keyWords.contain(group)) {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "#").toString(); // 4
                }
                else {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "!").toString(); // 5
                }
            }
            pattern = Pattern.compile("\\d[a-z]|\\d\\(");
            matcher = pattern.matcher(functionBase);
            count = 1;
            while (matcher.find()) {
                String group = matcher.group().substring(0, matcher.group().length()-1);
                int index = matcher.end();
                if(!keyWords.contain(group)) {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "#").toString(); // 4
                }
            }
            text = text.replaceAll("=.+", "=" + functionBase);

            String functionName = text.replaceAll("\\(.+|=.+", ""); // 6
            if(keyWords.contain(functionName)) return null;

            return text;
        }
        return null;
    }
    /**
     * Проверка строки на пригодность
     * */
    public boolean checkingString(String text) {
        State state = Name.A;
        // Счётчик скобок
        int counter = 0;
        for (int i = 0; i < text.length(); i++) {
            // у каждого состояния своя матрица, проверка символов и индексы
            state = state.getMatrix()[state.getIndex()][state.checkSymbol(text.charAt(i))];

            if (state == Base.B) counter++;
            else if (state == Base.C) counter--;

            if (state == End.E || counter < 0) return false;
        }
        // Последнее состояние любой матрицы указывает на способность выхода из автомата в данный момент
        return counter == 0 && state.getMatrix()[state.getIndex()][state.getMatrix()[0].length-1] == End.F;
    }
    private final KeyWords keyWords;
}
