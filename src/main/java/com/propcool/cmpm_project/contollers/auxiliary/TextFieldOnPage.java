package com.propcool.cmpm_project.contollers.auxiliary;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.analysing.build.NamedFunction;
import com.propcool.cmpm_project.contollers.MainController;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 * Класс текстового поля для функции
 * */
public class TextFieldOnPage extends TextField {
    public TextFieldOnPage(String text, MainController controller){
        super(text);
        this.controller = controller;
        setFont(new Font(25));
        //setPrefWidth(350);

        setOnKeyReleased(keyEvent -> processing(defaultColor));
        // Необходимо для обновления функций, зависящих от других функций
        // (кликом, обновляем старую функцию, так как действие не частое, но требовательное для автоматического обновления)
        setOnMouseClicked(mouseEvent -> processing(defaultColor));
    }
    public TextFieldOnPage(MainController controller){
        this("", controller);
    }
    public void processing(Color color){
        this.defaultColor = color;
        String textOfField = getText();
        NamedFunction nf = Elements.builder.building(textOfField);
        if(nf == null || (Elements.functions.containsKey(nf.getName()) && !nf.getName().equals(functionName))
                || (Elements.parameters.containsKey(nf.getName())
        )) {
            Elements.functions.remove(functionName);
            Elements.functionsWithParams.remove(functionName);
            Elements.functionsWithColor.remove(functionName);

            controller.remove(functionName);
            functionName = null;
        } else {
            Elements.functions.remove(functionName);
            Elements.functions.put(nf.getName(), nf.getFunction());

            Elements.functionsWithParams.remove(functionName);
            Elements.functionsWithParams.put(nf.getName(), nf.getParams());

            Elements.functionsWithColor.remove(functionName);
            Elements.functionsWithColor.put(nf.getName(), color);

            controller.remove(functionName);
            functionName = nf.getName();

            controller.rebuildFunction(functionName);
            controller.redraw(functionName);
        }
        controller.removeTextField();
        controller.addTextField();
        controller.addSliders();
        controller.removeSliders();
    }
    private String functionName;
    private final MainController controller;
    private Color defaultColor = Color.GREEN;
}
