package com.propcool.cmpm_project.io.notebooks;

import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.io.data.FunctionData;
import com.propcool.cmpm_project.io.data.ParameterData;
import com.propcool.cmpm_project.io.data.TableData;

import java.util.LinkedHashSet;
import java.util.Set;
/**
 * Фабрика экранов
 * */
public class NotebookBuilder {
    public NotebookBuilder(FunctionManager functionManager){
        this.functionManager = functionManager;
    }
    public Notebook build(CoordinateManager coordinateManager){
        Set<FunctionData> functionDataSet = new LinkedHashSet<>();
        for(var function : functionManager.getFunctions().values()){
            functionDataSet.add(function.getData());
        }
        Set<ParameterData> parameterDataSet = new LinkedHashSet<>();
        for(var param : functionManager.getParameters().values()){
            parameterDataSet.add(param.getData());
        }
        Set<TableData> tableDataSet = new LinkedHashSet<>();
        for(var table : functionManager.getTables().values()){
            tableDataSet.add(table.getData());
        }
        Notebook notebook = new Notebook();
        notebook.setFunctionDataSet(functionDataSet);
        notebook.setParameterDataSet(parameterDataSet);
        notebook.setTableDataSet(tableDataSet);
        notebook.setSystemName(coordinateManager.getName());

        return notebook;
    }
    private final FunctionManager functionManager;
}
