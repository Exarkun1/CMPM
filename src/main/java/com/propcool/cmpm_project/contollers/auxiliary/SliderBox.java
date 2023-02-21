package com.propcool.cmpm_project.contollers.auxiliary;

import com.propcool.cmpm_project.contollers.MainController;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SliderBox extends HBox {
    public SliderBox(String paramName, MainController controller){
        this.paramName = paramName;
        this.slider = new SliderOnPage(paramName, controller);
        setAlignment(Pos.CENTER);
        Text name = new Text(paramName + ": ");
        name.setFont(Font.font (30));
        getChildren().addAll(name, slider);
    }
    public String getParamName() {
        return paramName;
    }
    public Slider getSlider() {
        return slider;
    }
    private final String paramName;
    private final Slider slider;
}
