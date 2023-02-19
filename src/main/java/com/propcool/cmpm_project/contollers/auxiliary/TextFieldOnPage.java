package com.propcool.cmpm_project.contollers.auxiliary;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.analysing.build.NamedFunction;
import com.propcool.cmpm_project.contollers.MainController;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class TextFieldOnPage extends TextField {
    public TextFieldOnPage(String text, MainController controller){
        super(text);
        setFont(new Font(25));
        setOnKeyReleased(keyEvent -> {
            String textOfField = getText();
            NamedFunction nf = Elements.builder.building(textOfField);
            if(nf == null || nf.getValue() == null
                    || (Elements.functions.containsKey(nf.getKey()) && !nf.getKey().equals(functionName))
                    || (Elements.parameters.containsKey(nf.getKey())
            )) {
                Elements.functions.remove(functionName);
                controller.remove(functionName);
                functionName = null;
            } else {
                Elements.functions.remove(functionName);
                Elements.functions.put(nf.getKey(), nf.getValue());
                controller.remove(functionName);
                functionName = nf.getKey();

                controller.rebuildFunction(functionName);
                controller.redraw(functionName);
            }
            controller.removeTextField();
            controller.addTextField();
            controller.addSliders();
            // System.out.println(Elements.functions.keySet());
        });
    }
    public TextFieldOnPage(MainController controller){
        this("", controller);
    }
    private String functionName;
}
