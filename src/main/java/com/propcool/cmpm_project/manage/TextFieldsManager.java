package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.components.SliderBox;
import com.propcool.cmpm_project.components.TextFieldBox;
import com.propcool.cmpm_project.notebooks.Notebook;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;
/**
 * Менеджер текстовых полей
 * */
public class TextFieldsManager {
    public TextFieldsManager(VBox paneForText, Button creatFieldButton, FunctionManager functionManager, DrawManager drawManager){
        this.paneForText = paneForText;
        this.drawManager = drawManager;
        this.creatFieldButton = creatFieldButton;
        this.functionManager = functionManager;
    }
    /**
     * Добавление поля
     * */
    public void addTextField(TextFieldBox box){
        paneForText.getChildren().removeAll(sliders.values());
        paneForText.getChildren().remove(creatFieldButton);

        textFields.add(box);
        paneForText.getChildren().add(box);

        paneForText.getChildren().add(creatFieldButton);
        paneForText.getChildren().addAll(sliders.values());
    }
    /**
     * Удаление текстового поля
     * */
    public void removeTextField(TextFieldBox box){
        paneForText.getChildren().remove(box);
        textFields.remove(box);

        String functionName = box.getTextField().getFunctionName();
        drawManager.remove(functionName);
        functionManager.removeFunction(functionName);

        removeSliders();
    }
    /**
     * Добавление ползунка для параметра
     * */
    public void addSliders(){
        for(var param : functionManager.getParameters().keySet()){
            if(!sliders.containsKey(param)){
                SliderBox sliderBox = new SliderBox(param, functionManager, drawManager);
                sliders.put(param, sliderBox);
                paneForText.getChildren().add(sliderBox);
            }
        }
    }
    /**
     * Удаление лишних ползунков
     * */
    public void removeSliders(){
        List<String> deletedParams = new ArrayList<>();
        for(var param : functionManager.getParameters().keySet()){
            if(functionManager.getParam(param).refIsEmpty()) {
                deletedParams.add(param);
            }
        }
        for(var param : deletedParams) {
            SliderBox sliderBox = sliders.remove(param);
            paneForText.getChildren().remove(sliderBox);
            functionManager.removeParam(param);
        }
    }
    /**
     * Очистка панели с текстовыми полями
     * */
    public void clear(){
        paneForText.getChildren().removeAll(textFields);
        paneForText.getChildren().removeAll(sliders.values());
        functionManager.clearFunctions();
        functionManager.clearParams();
        textFields.clear();
        sliders.clear();
    }
    /**
     * Добавление всех полей с тетради
     * */
    public void addNotebookFields(Notebook notebook){
        for(var data : notebook.getFunctionDataSet()){
            TextFieldBox box = new TextFieldBox(functionManager, drawManager, this);
            box.getTextField().setText(data.getExpression());
            box.getColorPicker().setValue(Color.valueOf(data.getColor()));
            box.getTextField().setDefaultColor(Color.valueOf(data.getColor()));
            box.getTextField().setDefaultVisible(data.isVisible());
            box.getTextField().getProcess().processing();
            textFields.add(box);
            addTextField(box);
        }
    }
    /**
     * Настройка ползунков на тетради
     * */
    public void setNotebookSliders(Notebook notebook){
        for(var data : notebook.getParameterDataSet()){
            SliderBox box = sliders.get(data.getName());
            if(box != null) box.getSlider().setParam(data.getValue());
        }
    }

    public LinkedHashSet<TextFieldBox> getTextFields() {
        return textFields;
    }
    private final VBox paneForText;
    private final Button creatFieldButton;
    private final LinkedHashSet<TextFieldBox> textFields = new LinkedHashSet<>();
    private final Map<String, SliderBox> sliders = new LinkedHashMap<>();
    private final DrawManager drawManager;
    private final FunctionManager functionManager;
}
