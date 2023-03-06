package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.components.NotebookBox;
import com.propcool.cmpm_project.notebooks.Notebook;
import com.propcool.cmpm_project.notebooks.NotebookBuilder;
import com.propcool.cmpm_project.notebooks.NotebookLoader;
import com.propcool.cmpm_project.notebooks.NotebookSaver;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
/**
 * Менеджер тетрадей(экранов)
 * */
public class NotebookManager {
    public NotebookManager(VBox paneForNotebooks, DrawManager drawManager, TextFieldsManager textFieldsManager) {
        this.paneForNotebooks = paneForNotebooks;
        this.drawManager = drawManager;
        this.textFieldsManager = textFieldsManager;
    }
    /**
     * Запись тетради
     * */
    public void record(String notebookName){
        Notebook notebook = notebookBuilder.build(textFieldsManager.getTextFields());

        Notebook prevNotebook = Elements.notebooks.get(notebookName);
        Elements.notebooks.put(notebookName, notebook);

        notebookBox = new NotebookBox(notebookName, drawManager, this);
        if(prevNotebook == null) paneForNotebooks.getChildren().add(notebookBox);
    }
    /**
     * Сохранение теради
     * */
    public void save(Window window){
        File file = fileChooser.showSaveDialog(window);
        Notebook notebook = notebookBuilder.build(textFieldsManager.getTextFields());
        try {
            if(file != null) notebookSaver.save(notebook, file);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить тетрадь в файл", e);
        }
    }
    /**
     * Загрузка тетради
     * */
    public void load(Window window){
        File file = fileChooser.showOpenDialog(window);
        try {
            if(file != null) {
                Notebook notebook = notebookLoader.load(file);
                openNotebook(notebook);
                drawManager.makeNewFrame();
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть файл", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось получить тетрадь из файла", e);
        }
    }
    /**
     * Открытие тетради
     * */
    public void openNotebook(Notebook notebook){
        textFieldsManager.clear();
        textFieldsManager.addNotebookFields(notebook);
        textFieldsManager.setNotebookSliders(notebook);
    }
    /**
     * Удаление тетради
     * */
    public void deleteNotebook(String notebookName){
        Elements.notebooks.remove(notebookName);
        paneForNotebooks.getChildren().remove(notebookBox);
    }
    private NotebookBox notebookBox;
    private final VBox paneForNotebooks;
    private final DrawManager drawManager;
    private final TextFieldsManager textFieldsManager;
    private final NotebookBuilder notebookBuilder = new NotebookBuilder();
    private final NotebookSaver notebookSaver = new NotebookSaver();
    private final NotebookLoader notebookLoader = new NotebookLoader();
    private final FileChooser fileChooser = new FileChooser();
}
