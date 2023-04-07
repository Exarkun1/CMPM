package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FProcess;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TextFieldsManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import com.propcool.cmpm_project.notebooks.data.FunctionData;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.textfield.CustomTextField;

import java.util.List;

/**
 * Класс текстового поля для функции
 * */
public class TextFieldOnPage extends CustomTextField {
    public TextFieldOnPage(String text, FunctionManager functionManager, DrawManager drawManager, TextFieldsManager textFieldsManager){
        this.index = counter++;
        this.functionManager = functionManager;
        this.drawManager = drawManager;
        this.textFieldsManager = textFieldsManager;
        setFont(new Font(25));
        setPrefWidth(285);

        setOnKeyReleased(keyEvent -> process.processing());
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
            CustomizableFunction cf = functionManager.removeFunction(functionName);
            drawManager.remove(functionName);

            functionName = functionManager.buildFunction(getText(), index);
            functionData = functionManager.getFunction(functionName).getData();
            functionData.setColor(defaultColor.toString());
            functionData.setVisible(defaultVisible);
            functionData.setWidth(defaultWidth);

            drawManager.rebuildFunction(functionName);
            drawManager.redraw(functionName);

            // Обновление функций ссылающихся на данную
            List<String> functionRefs = functionManager.rebuildRefsWithFunction(cf);
            for(var name : functionRefs){
                drawManager.remove(name);
                drawManager.rebuildFunction(name);
                drawManager.redraw(name);
            }

            // Обновление функций ссылающихся на параметр с именем данной
            functionRefs = functionManager.rebuildRefsWithParam(functionName);
            for(var name : functionRefs){
                drawManager.remove(name);
                drawManager.rebuildFunction(name);
                drawManager.redraw(name);
            }

            textFieldsManager.addSliders();
            textFieldsManager.removeSliders();
        }
    };
    public void setDefaultColor(Color color){
        this.defaultColor = color;
    }
    public void setDefaultVisible(boolean visible){
        this.defaultVisible = visible;
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
    private boolean defaultVisible = true;
    private int defaultWidth = 3;
    private final int index;
    private static int counter = 0;
}
