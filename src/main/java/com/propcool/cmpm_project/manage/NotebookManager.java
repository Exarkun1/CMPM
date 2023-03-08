package com.propcool.cmpm_project.manage;

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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Менеджер тетрадей(экранов)
 * */
public class NotebookManager {
    public NotebookManager(VBox paneForNotebooks, FunctionManager functionManager, DrawManager drawManager, TextFieldsManager textFieldsManager) {
        this.paneForNotebooks = paneForNotebooks;
        this.drawManager = drawManager;
        this.textFieldsManager = textFieldsManager;
        this.notebookBuilder = new NotebookBuilder(functionManager);
    }
    /**
     * Запись тетради
     * */
    public void record(String notebookName){
        Notebook notebook = notebookBuilder.build();
        NotebookBox notebookBox = new NotebookBox(notebookName, drawManager, this);

        NotebookBox prevNotebook = notebookBoxMap.get(notebookName);
        notebooks.put(notebookName, notebook);

        if(prevNotebook == null) {
            paneForNotebooks.getChildren().add(notebookBox);
            notebookBoxMap.put(notebookName, notebookBox);
        }
    }
    /**
     * Сохранение теради
     * */
    public void save(Window window){
        File file = fileChooser.showSaveDialog(window);
        Notebook notebook = notebookBuilder.build();
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
        NotebookBox box = notebookBoxMap.remove(notebookName);
        paneForNotebooks.getChildren().remove(box);
        notebooks.remove(notebookName);
    }
    public Notebook getNotebook(String notebookName){
        return notebooks.get(notebookName);
    }

    public final Map<String, NotebookBox> notebookBoxMap = new HashMap<>();
    public final Map<String, Notebook> notebooks = new LinkedHashMap<>();
    private final VBox paneForNotebooks;
    private final DrawManager drawManager;
    private final TextFieldsManager textFieldsManager;
    private final NotebookBuilder notebookBuilder;
    private final NotebookSaver notebookSaver = new NotebookSaver();
    private final NotebookLoader notebookLoader = new NotebookLoader();
    private final FileChooser fileChooser = new FileChooser();
}
