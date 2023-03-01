package com.propcool.cmpm_project.auxiliary;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.analysing.build.NamedFunction;
import com.propcool.cmpm_project.controllers.MainController;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 * Класс текстового поля для функции
 * */
public class TextFieldOnPage extends TextField {
    public TextFieldOnPage(String text, MainController controller){
        super(text);
        this.controller = controller;
        setFont(new Font(25));
        setPrefWidth(285);

        functionData = new FunctionData();
        functionData.setExpression(getText());
        functionData.setColor(defaultColor);
        functionData.setWidth(defaultWidth);

        setOnKeyReleased(keyEvent -> processing());
        // Необходимо для обновления функций, зависящих от других функций
        // (кликом, обновляем старую функцию, так как действие не частое, но требовательное для автоматического обновления)
        setOnMouseClicked(mouseEvent -> processing());
    }
    public TextFieldOnPage(MainController controller){
        this("", controller);
    }
    public void processing(){
        NamedFunction nf = Elements.builder.building(getText());

        Elements.functions.remove(functionName);
        controller.remove(functionName);
        if(nf == null || (Elements.functions.containsKey(nf.getName()) && !nf.getName().equals(functionName))
                || (Elements.parameters.containsKey(nf.getName())
        )) {
            functionName = null;
            functionData = new FunctionData();
        } else {
            CustomizableFunction cf = new CustomizableFunction(nf.getFunction(), nf.getParams());
            functionData = cf.getData();
            Elements.functions.put(nf.getName(), cf);
            functionName = nf.getName();
            functionData = cf.getData();
        }
        functionData.setExpression(getText());
        functionData.setColor(defaultColor);
        functionData.setWidth(defaultWidth);

        controller.rebuildFunction(functionName);
        controller.redraw(functionName);

        controller.addSliders();
        controller.removeSliders();
    }
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
    private final MainController controller;
    private FunctionData functionData;
    private Color defaultColor = Color.GREEN;
    private int defaultWidth = 2;
}
