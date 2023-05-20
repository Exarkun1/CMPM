package com.propcool.cmpm_project.controllers;

import com.propcool.cmpm_project.components.TableBox;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TablesManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
/**
 * Контроллер страницы для создания таблиц
 * */
public class TableController {
    @FXML
    void saveTable(ActionEvent event) {
        Platform.runLater(() -> {
            tablesManager.addTable(new TableBox(functionManager, drawManager, tablesManager));
            stage.hide();
        });
    }
    public void init(Stage stage, TablesManager tablesManager, FunctionManager functionManager, DrawManager drawManager) {
        this.stage = stage;
        this.tablesManager = tablesManager;
        this.functionManager = functionManager;
        this.drawManager = drawManager;
    }
    private Stage stage;
    private TablesManager tablesManager;
    private FunctionManager functionManager;
    private DrawManager drawManager;
}
