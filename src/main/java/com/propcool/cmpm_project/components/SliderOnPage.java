package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.io.data.CustomizableParameter;
import javafx.scene.control.Slider;
/**
 * Класс ползунка для параметра
 * */
public class SliderOnPage extends Slider {
    public SliderOnPage(double min, double max, double value, String parameterName, FunctionManager functionManager, DrawManager drawManager){
        super(min, max, value);
        this.parameterName = parameterName;
        this.functionManager = functionManager;
        setPrefHeight(50);
        setPrefWidth(300);
        setMajorTickUnit(2);
        setShowTickLabels(true);
        setShowTickMarks(true);
        setOnMouseDragged(mouseEvent -> {
            CustomizableParameter cp = functionManager.getParam(parameterName);
            cp.setValue(getValue());
            cp.getParam().set(getValue());
            drawManager.makeNewRebuildFrame();
        });
    }
    public SliderOnPage(String parameterName, FunctionManager functionManager, DrawManager drawManager){
        this(-10, 10, 1, parameterName, functionManager, drawManager);
    }
    public void setParam(double v){
        setValue(v);
        CustomizableParameter cp = functionManager.getParam(parameterName);
        cp.setValue(getValue());
        cp.getParam().set(getValue());
    }
    private final String parameterName;
    private final FunctionManager functionManager;
}
