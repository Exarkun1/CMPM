package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.CmpmApplication;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TextFieldsManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;

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
        URL urlX = CmpmApplication.class.getResource("x.png");
        ImageView xView = new ImageView(String.valueOf(urlX));
        xView.setFitWidth(50);
        xView.setFitHeight(50);

        xView.setOnMousePressed(mouseEvent -> {
            functionManager.removeParamRefs(textField.getFunctionName());
            CustomizableFunction cf = functionManager.removeFunction(textField.getFunctionName());
            textFieldsManager.removeTextField(this);

            List<String> functionRefs = functionManager.rebuildRefsWithFunction(cf);
            for(var name : functionRefs){
                drawManager.remove(name);
                drawManager.rebuildFunction(name);
                drawManager.redraw(name);
            }

            textFieldsManager.addSliders();
            textFieldsManager.removeSliders();
        });
        getChildren().addAll(colorPicker, textField, xView);
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
