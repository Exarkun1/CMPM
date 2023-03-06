package com.propcool.cmpm_project.notebooks;

import com.propcool.cmpm_project.notebooks.data.FunctionData;
import com.propcool.cmpm_project.notebooks.data.ParameterData;

import java.io.Serializable;
import java.util.Set;
/**
 * Класс экрана графиков
 * */
public class Notebook implements Serializable {
    public Set<FunctionData> getFunctionDataSet() {
        return functionDataSet;
    }

    public void setFunctionDataSet(Set<FunctionData> functionDataList) {
        this.functionDataSet = functionDataList;
    }

    public Set<ParameterData> getParameterDataSet() {
        return parameterDataSet;
    }

    public void setParameterDataSet(Set<ParameterData> parameterDataList) {
        this.parameterDataSet = parameterDataList;
    }

    private Set<FunctionData> functionDataSet;
    private Set<ParameterData> parameterDataSet;
}
