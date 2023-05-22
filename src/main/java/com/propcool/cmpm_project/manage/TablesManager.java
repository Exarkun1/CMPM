package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.CmpmApplication;
import com.propcool.cmpm_project.components.TableBox;
import com.propcool.cmpm_project.controllers.TableController;
import com.propcool.cmpm_project.io.notebooks.Notebook;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class TablesManager {
    public TablesManager(VBox paneForTable, Button creatTableButton, FunctionManager functionManager, DrawManager drawManager){
        this.paneForTable = paneForTable;
        this.createTableButton = creatTableButton;
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
            controller.init(tableStage, this, functionManager, drawManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addTable(TableBox box) {
        paneForTable.getChildren().remove(createTableButton);
        tableBoxes.add(box);
        paneForTable.getChildren().add(box);
        paneForTable.getChildren().add(createTableButton);
    }
    public void remove(TableBox box) {
        tableBoxes.remove(box);
        paneForTable.getChildren().remove(box);
        functionManager.removeTable(box.getTableName());
        drawManager.remove(box.getTableName());
        drawManager.clearPoints();
    }
    public void loadNew() {
        load(null, null);
    }
    public void load(String name, TableBox tableBox) {
        controller.setCurrentTable(name, tableBox);
        tableStage.showAndWait();
    }
    public void clear() {
        paneForTable.getChildren().removeAll(tableBoxes);
        functionManager.clearTables();
        tableBoxes.clear();
        drawManager.makeNewRebuildFrame();
    }
    public void addNotebookTables(Notebook notebook) {
        for(var data : notebook.getTableDataSet()) {
            controller.approximatePoints(
                    data.getName(), data.getRows(), data.getK(),
                    Color.valueOf(data.getColor()), data.isVisible(), data.isPointsVisible()
            );
            TableBox box = new TableBox(functionManager, drawManager, this);
            box.setTableName(data.getName());
            box.setTableBody(getTableBody(data.getName()));
            addTable(box);
        }
    }
    public String getTableBody(String name) {
        double[] A = functionManager.getTable(name).getApproximate().getA();
        StringBuilder sb = new StringBuilder();
        int n = A.length-1;
        for(int i = n; i >= 0; i--) {
            String d = format.format(A[i]);
            if(i > 1) sb.append(d).append("*x^").append(i).append(" + ");
            else if(i == 1) sb.append(d).append("*x").append(" + ");
            else sb.append(d);
        }
        return sb.toString();
    }
    public Set<TableBox> getTableBoxes() {
        return tableBoxes;
    }
    private final Stage tableStage;
    private final TableController controller;
    private final VBox paneForTable;
    private final Button createTableButton;
    private final LinkedHashSet<TableBox> tableBoxes = new LinkedHashSet<>();
    private final DrawManager drawManager;
    private final FunctionManager functionManager;
    private final DecimalFormat format = new DecimalFormat("#.#####");
}
