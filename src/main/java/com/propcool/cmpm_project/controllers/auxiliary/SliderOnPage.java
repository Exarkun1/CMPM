package com.propcool.cmpm_project.controllers.auxiliary;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.controllers.MainController;
import com.propcool.cmpm_project.functions.basic.Constant;
import javafx.scene.control.Slider;
/**
 * Класс ползунка для параметра
 * */
public class SliderOnPage extends Slider {
    public SliderOnPage(double min, double max, double value, String parameterName, MainController controller){
        super(min, max, value);
        setPrefHeight(50);
        setPrefWidth(250);
        setMajorTickUnit(2);
        setShowTickLabels(true);
        setShowTickMarks(true);
        setOnMouseDragged(mouseEvent -> {
            Constant param = Elements.parameters.get(parameterName);
            param.set(getValue());
            controller.makeNewFrame();
        });
    }
    public SliderOnPage(String parameterName, MainController controller){
        this(-10, 10, 1, parameterName, controller);
    }
}
