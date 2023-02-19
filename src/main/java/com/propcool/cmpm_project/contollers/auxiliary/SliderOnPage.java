package com.propcool.cmpm_project.contollers.auxiliary;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.contollers.MainController;
import com.propcool.cmpm_project.functions.basic.Constant;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

public class SliderOnPage extends Slider {
    public SliderOnPage(double min, double max, double value, MainController controller, String parameterName){
        super(min, max, value);
        setPrefHeight(50);
        setOnMouseDragged(mouseEvent -> {
            Constant param = Elements.parameters.get(parameterName);
            param.set(getValue());
            controller.makeNewFrame();
        });
    }
    public SliderOnPage(MainController controller, String parameterName){
        this(-10, 10, 1, controller, parameterName);
    }
}
