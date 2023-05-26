package com.propcool.cmpm_project.simple;

import java.util.Set;

public class KeyWords {
    public boolean contain(String name) {
        return keyWords.contains(name);
    }
    private final Set<String> keyWords = Set.of("sqrt", "exp", "abs", "log", "ln", "sin", "cos", "tan", "ctan", "arcsin", "arccos", "arctan", "sh", "ch", "th");
}
