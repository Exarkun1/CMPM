package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableParameter;
import com.propcool.cmpm_project.controllers.MainController;
import javafx.scene.control.Slider;
/**
 * Класс ползунка для параметра
 * */
public class SliderOnPage extends Slider {
    public SliderOnPage(double min, double max, double value, String parameterName, DrawManager drawManager){
        super(min, max, value);
        this.parameterName = parameterName;
        setPrefHeight(50);
        setPrefWidth(300);
        setMajorTickUnit(2);
        setShowTickLabels(true);
        setShowTickMarks(true);
        setOnMouseDragged(mouseEvent -> {
            CustomizableParameter cp = Elements.parameters.get(parameterName);
            cp.setValue(getValue());
            cp.getParam().set(getValue());
            drawManager.makeNewFrame();
        });
    }
    public SliderOnPage(String parameterName, DrawManager drawManager){
        this(-10, 10, 1, parameterName, drawManager);
    }
    public void setParam(double v){
        setValue(v);
        CustomizableParameter cp = Elements.parameters.get(parameterName);
        cp.setValue(getValue());
        cp.getParam().set(getValue());
    }
    private final String parameterName;
}
