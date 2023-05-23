package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TextFieldsManager;
import com.propcool.cmpm_project.io.data.CustomizableFunction;
import com.propcool.cmpm_project.io.data.FunctionData;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.textfield.CustomTextField;

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

        setOnKeyReleased(keyEvent -> {
            processing();
            drawManager.makeNewFrame();
        });
    }
    public TextFieldOnPage(FunctionManager functionManager, DrawManager drawManager, TextFieldsManager textFieldsManager){
        this("", functionManager, drawManager, textFieldsManager);
    }
    /**
     * Обработка изменения текста в поле с функциями
     * */
    public void processing() {
        functionManager.removeParamRefs(functionName);
        CustomizableFunction cf = functionManager.removeFunction(functionName);

        functionName = functionManager.buildFunction(getText(), index);
        functionData = functionManager.getFunction(functionName).getData();
        functionData.setColor(defaultColor.toString());
        functionData.setVisible(defaultVisible);
        functionData.setWidth(defaultWidth);

        // Обновление функций ссылающихся на данную
        functionManager.rebuildRefsWithFunction(cf);

        // Обновление функций ссылающихся на параметр с именем данной
        functionManager.rebuildRefsWithParam(functionName);

        textFieldsManager.addSliders();
        textFieldsManager.removeSliders();
    }
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
    private int defaultWidth = 2;
    private final int index;
    private static int counter = 0;
}
