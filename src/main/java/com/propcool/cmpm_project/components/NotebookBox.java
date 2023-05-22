package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.MainManager;
import com.propcool.cmpm_project.manage.NotebookManager;
import com.propcool.cmpm_project.io.notebooks.Notebook;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Панель для элементов связанным с тетрадями(экранами)
 * */
public class NotebookBox extends HBox {
    public NotebookBox(String notebookName, MainManager mainManager, NotebookManager notebookManager){
        TextField name = new TextField(notebookName);
        name.setFont(new Font(20));
        name.setEditable(false);

        IconButton openView = new IconButton("open.png", 34);
        IconButton closeView = new IconButton("x.png", 34);
        openView.setOnAction(actionEvent -> {
            Notebook notebook = notebookManager.getNotebook(notebookName);
            notebookManager.openNotebook(notebook);
            mainManager.changingCoordinateSystem(notebook.getSystemName());
            mainManager.getDrawManager().makeNewRebuildFrame();
        });
        closeView.setOnAction(actionEvent -> Platform.runLater(() -> notebookManager.deleteNotebook(notebookName))
        );
        getChildren().addAll(openView, name, closeView);
    }
}
