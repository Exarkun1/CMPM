package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.controllers.MainController;
import com.propcool.cmpm_project.manage.DrawManager;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * Панель для кнопок элементов, связанных со слайдерами, и самих слайдеров
 * */
public class SliderBox extends HBox {
    public SliderBox(String paramName, DrawManager drawManager){
        this.slider = new SliderOnPage(paramName, drawManager);
        setAlignment(Pos.CENTER);
        Text name = new Text(paramName + ": ");
        name.setFont(Font.font (30));
        getChildren().addAll(name, slider);
    }
    public SliderOnPage getSlider() {
        return slider;
    }
    private final SliderOnPage slider;
}
