package com.propcool.cmpm_project.analysing.build;

import java.util.ArrayList;
import java.util.List;

public class NamedFunction {
    public NamedFunction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getParams() {
        return params;
    }
    private final String name;
    private final List<String> params = new ArrayList<>();
}
