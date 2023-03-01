package com.propcool.cmpm_project.auxiliary;

import java.util.List;

public class Notebook {
    public List<FunctionData> getFunctionDataList() {
        return functionDataList;
    }

    public void setFunctionDataList(List<FunctionData> functionDataList) {
        this.functionDataList = functionDataList;
    }

    public List<ParameterData> getParameterDataList() {
        return parameterDataList;
    }

    public void setParameterDataList(List<ParameterData> parameterDataList) {
        this.parameterDataList = parameterDataList;
    }

    private List<FunctionData> functionDataList;
    private List<ParameterData> parameterDataList;
}
