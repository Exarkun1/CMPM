package org.cmpm.analysing;

import org.cmpm.Parameters;
import org.cmpm.analysing.stages.Base;
import org.cmpm.analysing.stages.End;
import org.cmpm.analysing.stages.Name;
import org.cmpm.analysing.stages.State;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyser {
    public String processing(String text){
        if(checkingString(text)){
            text = text.replaceAll("\\s+", "");
            text = text.replaceAll(",", ".");
            text = text.replaceAll("=-", "=0-");
            text = text.replaceAll("\\(-", "(0-");

            String functionBase = text.replaceAll(".+=", "");

            Pattern pattern = Pattern.compile("[a-z]+\\(|\\d[a-z]+|\\)x");
            Matcher matcher = pattern.matcher(functionBase);
            int count = 1;
            while (matcher.find()) {
                String group = matcher.group().substring(0, matcher.group().length()-1);
                int index = matcher.end();
                if(!Parameters.keyWords.contains(group)) {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "#").toString();
                }
                else {
                    functionBase = new StringBuffer(functionBase).insert(index-count--, "!").toString();
                }
            }
            text = text.replaceAll("=.+", "=" + functionBase);

            String functionName = text.replaceAll("\\(.+|=.+", "");
            pattern = Pattern.compile("[a-z]+\\d?+");
            matcher = pattern.matcher(functionBase);
            while (matcher.find()) {
                String t = matcher.group();
                if(matcher.group().equals(functionName)) return null;
            }

            return text;
        }
        return null;
    }
    public boolean checkingString(String text) {
        State state = Name.A;
        int counter = 0;
        for (int i = 0; i < text.length(); i++) {
            state = state.getMatrix()[state.getIndex()][state.checkSymbol(text.charAt(i))];

            if (state == Base.C) counter++;
            else if (state == Base.D) counter--;

            if (state == End.E || counter < 0) return false;
        }
        return counter == 0 && state.getMatrix()[state.getIndex()][state.getMatrix()[0].length-1] == End.F;
    }
}
