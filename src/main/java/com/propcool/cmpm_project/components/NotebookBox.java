package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.MainManager;
import com.propcool.cmpm_project.manage.NotebookManager;
import com.propcool.cmpm_project.notebooks.Notebook;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Панель для элементов связанным с тетрадями(экранами)
 * */
public class NotebookBox extends HBox {
    public NotebookBox(String notebookName, MainManager mainManager, NotebookManager notebookManager){
        Text name = new Text(notebookName);
        name.setFont(new Font(24));
        Button openButton = new Button("открыть");
        openButton.setFont(new Font(16));
        Button deleteButton = new Button("удалить");
        deleteButton.setFont(new Font(16));
        setAlignment(Pos.CENTER);
        setSpacing(10);
        openButton.setOnAction(actionEvent -> {
            Notebook notebook = notebookManager.getNotebook(notebookName);
            notebookManager.openNotebook(notebook);
            mainManager.changingCoordinateSystem(notebook.getSystemName());
            mainManager.getDrawManager().makeNewFrame();
        });
        deleteButton.setOnAction(actionEvent -> Platform.runLater(() -> notebookManager.deleteNotebook(notebookName))
        );
        getChildren().addAll(name, openButton, deleteButton);
    }
}
