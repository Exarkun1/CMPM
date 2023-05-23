package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TextFieldsManager;
import com.propcool.cmpm_project.io.data.CustomizableFunction;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Панель для элементов, связанных с текстовыми полями, и самим полем
 * */

public class TextFieldBox extends HBox {
    public TextFieldBox(FunctionManager functionManager, DrawManager drawManager, TextFieldsManager textFieldsManager){
        this.textField = new TextFieldOnPage(functionManager, drawManager, textFieldsManager);
        setAlignment(Pos.CENTER);
        colorPicker.setValue(Color.GREEN);
        colorPicker.setPrefWidth(50);
        colorPicker.setPrefHeight(50);

        colorPicker.setOnAction(actionEvent -> {
            textField.setDefaultColor(colorPicker.getValue());
            textField.processing();
            drawManager.makeNewFrame();
        });
        colorPicker.setOnMousePressed(mouseEvent -> {
            CustomizableFunction cf = functionManager.getFunction(textField.getFunctionName());
            if(mouseEvent.isSecondaryButtonDown() && cf != null) {
                cf.setVisible(!cf.isVisible());
                textField.setDefaultVisible(cf.isVisible());
                visibleFlag = true;
                drawManager.makeNewFrame();
            }
        });
        colorPicker.setOnMouseReleased(mouseEvent -> {
            if(visibleFlag) colorPicker.hide();
            visibleFlag = false;
        });
        IconButton closeView = new IconButton("x.png", 44);

        closeView.setOnMousePressed(mouseEvent -> {
            functionManager.removeParamRefs(textField.getFunctionName());
            CustomizableFunction cf = functionManager.removeFunction(textField.getFunctionName());
            textFieldsManager.removeTextField(this);

            functionManager.rebuildRefsWithFunction(cf);

            textFieldsManager.addSliders();
            textFieldsManager.removeSliders();
            drawManager.makeNewFrame();
        });
        getChildren().addAll(colorPicker, textField, closeView);
    }
    public TextFieldOnPage getTextField() {
        return textField;
    }
    public ColorPicker getColorPicker() {
        return colorPicker;
    }
    private final TextFieldOnPage textField;
    private final ColorPicker colorPicker = new ColorPicker();
    private boolean visibleFlag = false;
}
