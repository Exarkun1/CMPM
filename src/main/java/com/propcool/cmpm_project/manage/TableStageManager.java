package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.CmpmApplication;
import com.propcool.cmpm_project.controllers.TableController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TableStageManager {
    public TableStageManager(TablesManager tablesManager, FunctionManager functionManager, DrawManager drawManager) {
        this.tablesManager = tablesManager;
        this.functionManager = functionManager;
        this.drawManager = drawManager;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CmpmApplication.class.getResource("table-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            tableStage = new Stage();
            tableStage.setTitle("Table builder");
            tableStage.getIcons().add(new Image(Objects.requireNonNull(CmpmApplication.class.getResourceAsStream("Icon.png"))));
            tableStage.setScene(scene);
            tableStage.setResizable(false);
            tableStage.initModality(Modality.APPLICATION_MODAL);
            controller = fxmlLoader.getController();
            controller.init(tableStage, tablesManager, functionManager, drawManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void load() {
        tableStage.showAndWait();
    }
    private final Stage tableStage;
    private final TableController controller;
    private final TablesManager tablesManager;
    private final FunctionManager functionManager;
    private final DrawManager drawManager;
}
