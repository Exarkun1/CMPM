package com.propcool.cmpm_project.analysing;

import com.propcool.cmpm_project.analysing.stages.Base;
import com.propcool.cmpm_project.analysing.stages.End;
import com.propcool.cmpm_project.analysing.stages.Name;
import com.propcool.cmpm_project.analysing.stages.State;
import com.propcool.cmpm_project.manage.FunctionManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Анализатор строки
 * */
public class Analyser {
    /**
     * Проверка и обработка функции: 1)здесь происходит удаление пробелов,
     * 2)замена запятых на точки,
     * 3)добавление 0 перед свободным минусом,
     * 4)вставка # в тех местах, где подразумевается умножение(2x -> 2#x),
     * 5)вставка ! в тех местах, где подразумевается функция(ln(x) -> ln!(x)),
     * 6)проверка на то, что в функции нет параметра с тем же именем
     * */
    public String processing(String text, FunctionManager functionManager){
        if(checkingString(text)){
            text = text.replaceAll("\\s+", ""); // 1
            text = text.replaceAll(",", "."); // 2
            text = text.replaceAll("=-", "=0-"); // 3
            text = text.replaceAll("\\(-", "(0-");

            String functionBase = text.replaceAll(".+=", ""); // Тело функции

            Pattern pattern = Pattern.compile("[a-z]+\\(|\\)x|\\d\\(");
            Matcher matcher = pattern.matcher(functionBase);
            int count = 1;
            while (matcher.find()) {
                String group = matcher.group().substring(0, matcher.group().length()-1);
                int index = matcher.end();
                if(!functionManager.getKeyWords().containsKey(group)) {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "#").toString(); // 4
                }
                else {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "!").toString(); // 5
                }
            }
            pattern = Pattern.compile("\\d[a-z]");
            matcher = pattern.matcher(functionBase);
            count = 1;
            while (matcher.find()) {
                String group = matcher.group().substring(0, matcher.group().length()-1);
                int index = matcher.end();
                if(!functionManager.getKeyWords().containsKey(group)) {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "#").toString(); // 4
                }
            }
            text = text.replaceAll("=.+", "=" + functionBase);

            /*String functionName = text.replaceAll("\\(.+|=.+", ""); // имя функции
            pattern = Pattern.compile(functionName);
            matcher = pattern.matcher(functionBase);
            if(matcher.find()) return null;*/ // 6

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

            if (state == Base.C) counter++;
            else if (state == Base.D) counter--;

            if (state == End.E || counter < 0) return false;
        }
        // Последнее состояние любой матрицы указывает на способность выхода из автомата в данный момент
        return counter == 0 && state.getMatrix()[state.getIndex()][state.getMatrix()[0].length-1] == End.F;
    }
}
