package com.propcool.cmpm_project.notebooks;

import com.propcool.cmpm_project.components.TextFieldBox;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.notebooks.data.FunctionData;
import com.propcool.cmpm_project.notebooks.data.ParameterData;

import java.util.LinkedHashSet;
import java.util.Set;
/**
 * Фабрика экранов
 * */
public class NotebookBuilder {
    public NotebookBuilder(FunctionManager functionManager){
        this.functionManager = functionManager;
    }
    public Notebook build(Set<TextFieldBox> textFields){
        Set<FunctionData> functionDataSet = new LinkedHashSet<>();
        for(var textFieldBox : textFields){
            functionDataSet.add(textFieldBox.getTextField().getFunctionData());
        }
        Set<ParameterData> parameterDataSet = new LinkedHashSet<>();
        for(var param : functionManager.getParameters().values()){
            parameterDataSet.add(param.getData());
        }
        Notebook notebook = new Notebook();
        notebook.setFunctionDataSet(functionDataSet);
        notebook.setParameterDataSet(parameterDataSet);

        return notebook;
    }
    private final FunctionManager functionManager;
}
