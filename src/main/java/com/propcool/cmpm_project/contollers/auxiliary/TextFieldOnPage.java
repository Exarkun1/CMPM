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
            if(nf == null || (Elements.functions.containsKey(nf.getName()) && !nf.getName().equals(functionName))
                    || (Elements.parameters.containsKey(nf.getName())
            )) {
                Elements.functions.remove(functionName);
                Elements.functionsWithParams.remove(functionName);
                controller.remove(functionName);
                functionName = null;
            } else {
                Elements.functions.remove(functionName);
                Elements.functions.put(nf.getName(), nf.getFunction());

                Elements.functionsWithParams.remove(functionName);
                Elements.functionsWithParams.put(nf.getName(), nf.getParams());

                controller.remove(functionName);
                functionName = nf.getName();

                controller.rebuildFunction(functionName);
                controller.redraw(functionName);
            }
            controller.removeTextField();
            controller.addTextField();
            controller.addSliders();
            controller.removeSliders();
            // System.out.println(Elements.functionsWithParams);
            // System.out.println(Elements.parameters.keySet());
        });
    }
    public TextFieldOnPage(MainController controller){
        this("", controller);
    }
    private String functionName;
}
