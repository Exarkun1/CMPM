package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.NotebookManager;
import com.propcool.cmpm_project.notebooks.Notebook;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Панель для элементов связанным с тетрадями(экранами)
 * */
public class NotebookBox extends HBox {
    public NotebookBox(String notebookName, DrawManager drawManager, NotebookManager notebookManager){
        Text name = new Text(notebookName);
        Button openButton = new Button("открыть");
        Button deleteButton = new Button("удалить");
        setAlignment(Pos.CENTER);
        openButton.setOnAction(actionEvent -> {
            notebookManager.openNotebook(notebookManager.getNotebook(notebookName));
            drawManager.makeNewFrame();
        });
        deleteButton.setOnAction(actionEvent -> notebookManager.deleteNotebook(notebookName));
        getChildren().addAll(name, openButton, deleteButton);
    }
}
