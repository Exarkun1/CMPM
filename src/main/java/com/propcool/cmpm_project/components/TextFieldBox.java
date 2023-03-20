package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.CmpmApplication;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TextFieldsManager;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;

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
            textField.getProcess().processing();
        });
        URL url = CmpmApplication.class.getResource("x.png");
        ImageView imageView = new ImageView(String.valueOf(url));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        imageView.setOnMousePressed(mouseEvent -> {
            functionManager.removeParamRefs(textField.getFunctionName());
            //functionManager.removeFunctionRefs(textField.getFunctionName());
            textFieldsManager.removeTextField(this);
        });
        getChildren().addAll(colorPicker, textField, imageView);
    }
    public TextFieldOnPage getTextField() {
        return textField;
    }
    public ColorPicker getColorPicker() {
        return colorPicker;
    }
    private final TextFieldOnPage textField;
    private final ColorPicker colorPicker = new ColorPicker();
}
