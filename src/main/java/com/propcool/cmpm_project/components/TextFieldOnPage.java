package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FProcess;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TextFieldsManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import com.propcool.cmpm_project.notebooks.data.CustomizableParameter;
import com.propcool.cmpm_project.notebooks.data.FunctionData;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Set;

/**
 * Класс текстового поля для функции
 * */
public class TextFieldOnPage extends TextField {
    public TextFieldOnPage(String text, FunctionManager functionManager, DrawManager drawManager, TextFieldsManager textFieldsManager){
        super(text);
        this.functionManager = functionManager;
        this.drawManager = drawManager;
        this.textFieldsManager = textFieldsManager;
        setFont(new Font(25));
        setPrefWidth(285);

        setOnKeyReleased(keyEvent -> process.processing());
        //setOnMouseClicked(mouseEvent -> processing());
    }
    public TextFieldOnPage(FunctionManager functionManager, DrawManager drawManager, TextFieldsManager textFieldsManager){
        this("", functionManager, drawManager, textFieldsManager);
    }

    public FProcess getProcess() {
        return process;
    }
    private final FProcess process = new FProcess() {
        @Override
        public void processing() {
            functionManager.removeParamRefs(functionName);
            //functionManager.removeFunctionRefs(functionName);
            functionManager.removeFunction(functionName);
            drawManager.remove(functionName);

            functionName = functionManager.buildFunction(getText());
            functionData = functionManager.getFunction(functionName).getData();
            functionData.setColor(defaultColor.toString());
            functionData.setWidth(defaultWidth);

            drawManager.rebuildFunction(functionName);
            drawManager.redraw(functionName);

            textFieldsManager.addSliders();
            textFieldsManager.removeSliders();
        }
    };
    public void setDefaultColor(Color color){
        this.defaultColor = color;
    }
    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }
    public FunctionData getFunctionData() {
        return functionData;
    }
    public String getFunctionName() {
        return functionName;
    }
    private String functionName;
    private final FunctionManager functionManager;
    private final DrawManager drawManager;
    private final TextFieldsManager textFieldsManager;
    private FunctionData functionData;
    private Color defaultColor = Color.GREEN;
    private int defaultWidth = 3;
}
