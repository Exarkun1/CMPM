package com.propcool.cmpm_project.notebooks;

import com.propcool.cmpm_project.notebooks.data.FunctionData;
import com.propcool.cmpm_project.notebooks.data.ParameterData;
import com.propcool.cmpm_project.notebooks.data.TableData;

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
    public String getSystemName() {
        return systemName;
    }
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    public Set<TableData> getTableDataSet() {
        return tableDataSet;
    }
    public void setTableDataSet(Set<TableData> tableDataSet) {
        this.tableDataSet = tableDataSet;
    }
    private Set<FunctionData> functionDataSet;
    private Set<ParameterData> parameterDataSet;
    private Set<TableData> tableDataSet;
    private String systemName;
}
