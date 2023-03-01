package com.propcool.cmpm_project.auxiliary;

import com.propcool.cmpm_project.CmpmApplication;
import com.propcool.cmpm_project.controllers.MainController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;

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

        colorPicker.setOnAction(actionEvent -> {
            textField.setDefaultColor(colorPicker.getValue());
            textField.processing();
        });
        URL url = CmpmApplication.class.getResource("exit.png");
        ImageView imageView = new ImageView(String.valueOf(url));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        imageView.setOnMousePressed(mouseEvent -> controller.removeTextField(this));
        getChildren().addAll(colorPicker, textField, imageView);
    }
    public TextFieldOnPage getTextField() {
        return textField;
    }
    private final TextFieldOnPage textField;
}
