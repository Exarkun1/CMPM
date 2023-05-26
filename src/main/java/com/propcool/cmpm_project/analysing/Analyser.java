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
     * 6)проверка на то, что в функции не именованна ключевым словом
     * */
    public String processing(String text, FunctionManager functionManager){
        if(checkingString(text)){
            text = text.replaceAll("\\s+", ""); // 1
            //text = text.replaceAll(",", "."); // 2
            text = text.replaceAll("=-", "=0-"); // 3
            text = text.replaceAll("\\(-", "(0-");

            String functionBase = text.replaceAll(".+=", ""); // Тело функции

            Pattern pattern = Pattern.compile("[a-z]+\\(");
            Matcher matcher = pattern.matcher(functionBase);
            int count = 1;
            while (matcher.find()) {
                String group = matcher.group().substring(0, matcher.group().length()-1);
                int index = matcher.end();
                if(functionManager.getKeyWords().containsKey(group) || functionManager.getFunctions().containsKey(group)) {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "!").toString(); // 5
                } else {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "#").toString(); // 4
                }
            }
            pattern = Pattern.compile("\\d[a-z]|\\d\\(");
            matcher = pattern.matcher(functionBase);
            count = 1;
            while (matcher.find()) {
                int index = matcher.end();
                functionBase = new StringBuffer(functionBase).insert(index-count--, "#").toString(); // 4
            }
            text = text.replaceAll("=.+", "=" + functionBase);

            String functionName = text.replaceAll("\\(.+|=.+", ""); // 6
            if(functionManager.getKeyWord(functionName) != null) return null;

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
}
