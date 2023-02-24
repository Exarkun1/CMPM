package com.propcool.cmpm_project.contollers.auxiliary;

import com.propcool.cmpm_project.contollers.MainController;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Панель для элементов, связанных с текстовыми полями, и самим полем
 * */

public class TextFieldBox extends HBox {
    public TextFieldBox(MainController controller){
        this.textField = new TextFieldOnPage(controller);
        setAlignment(Pos.CENTER);
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.GREEN);
        colorPicker.setPrefWidth(50);
        colorPicker.setPrefHeight(50);

        colorPicker.setOnAction(actionEvent -> textField.processing(colorPicker.getValue()));
        getChildren().addAll(textField, colorPicker);
    }

    public TextField getTextField() {
        return textField;
    }
    private final TextFieldOnPage textField;
}
