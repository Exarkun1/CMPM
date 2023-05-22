package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.components.NotebookBox;
import com.propcool.cmpm_project.io.notebooks.Notebook;
import com.propcool.cmpm_project.io.notebooks.NotebookBuilder;
import com.propcool.cmpm_project.io.notebooks.NotebookLoader;
import com.propcool.cmpm_project.io.notebooks.NotebookSaver;
import javafx.scene.control.Alert;
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
    public NotebookManager(VBox paneForNotebooks, FunctionManager functionManager,
                           TextFieldsManager textFieldsManager, TablesManager tablesManager,
                           MainManager mainManager) {
        this.paneForNotebooks = paneForNotebooks;
        this.textFieldsManager = textFieldsManager;
        this.tablesManager = tablesManager;
        this.notebookBuilder = new NotebookBuilder(functionManager);
        this.mainManager = mainManager;
    }
    /**
     * Запись тетради
     * */
    public void record(String notebookName, CoordinateManager coordinateManager){
        Notebook notebook = notebookBuilder.build(coordinateManager);
        NotebookBox notebookBox = new NotebookBox(notebookName, mainManager, this);

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
    public void save(Window window, CoordinateManager coordinateManager){
        File file = fileChooser.showSaveDialog(window);
        Notebook notebook = notebookBuilder.build(coordinateManager);
        try {
            if(file != null) notebookSaver.save(notebook, file);
        } catch (IOException e) {
            saveAlert.show();
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
                mainManager.changingCoordinateSystem(notebook.getSystemName());
                mainManager.getDrawManager().makeNewRebuildFrame();
            }
        } catch (IOException e) {
            loadAlert.show();
        } catch (ClassNotFoundException e) {
            classAlert.show();
        }
    }
    /**
     * Открытие тетради
     * */
    public void openNotebook(Notebook notebook){
        textFieldsManager.clear();
        textFieldsManager.addNotebookFields(notebook);
        textFieldsManager.setNotebookSliders(notebook);
        tablesManager.clear();
        tablesManager.addNotebookTables(notebook);
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
    private final MainManager mainManager;
    private final TextFieldsManager textFieldsManager;
    private final TablesManager tablesManager;
    private final NotebookBuilder notebookBuilder;
    private final NotebookSaver notebookSaver = new NotebookSaver();
    private final NotebookLoader notebookLoader = new NotebookLoader();
    private final FileChooser fileChooser = new FileChooser();
    private final Alert saveAlert = new Alert(Alert.AlertType.ERROR, "Не удалось сохранить графики в файл");
    private final Alert loadAlert = new Alert(Alert.AlertType.ERROR,"Не удалось открыть файл");
    private final Alert classAlert = new Alert(Alert.AlertType.ERROR, "Не удалось получить графики из файла");
}
