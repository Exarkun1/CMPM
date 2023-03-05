package com.propcool.cmpm_project.auxiliary;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.controllers.MainController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class NotebookBox extends HBox {
    public NotebookBox(String notebookName, MainController controller){
        Text name = new Text(notebookName);
        Button openButton = new Button("открыть");
        setAlignment(Pos.CENTER);
        openButton.setOnAction(actionEvent -> {
            controller.openNotebook(Elements.notebooks.get(notebookName));
            controller.makeNewFrame();
        });
        getChildren().addAll(name, openButton);
    }
}
