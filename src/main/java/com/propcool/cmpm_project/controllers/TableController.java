package com.propcool.cmpm_project.controllers;

import com.propcool.cmpm_project.components.RowBox;
import com.propcool.cmpm_project.components.TableBox;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TablesManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableTable;
import com.propcool.cmpm_project.util.Point;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Контроллер страницы для создания таблиц
 * */
public class TableController {
    @FXML
    private TextField approximateField;

    @FXML
    private TextField nameTableField;

    @FXML
    private VBox rowPane;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button addPointButton;

    @FXML
    void approximateTable(ActionEvent event) {
        try {
            int k = Integer.parseInt(approximateField.getText());
            saveTable(k);
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.show();
        }
    }

    @FXML
    void interpolateTable(ActionEvent event) {
        try {
            saveTable(rowBoxes.size()-1);
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.show();
        }
    }
    @FXML
    void addPoint(ActionEvent event) {
        rowPane.getChildren().remove(addPointButton);
        RowBox rowBox = new RowBox(this);
        rowBoxes.add(rowBox);
        rowPane.getChildren().add(rowBox);
        rowPane.getChildren().add(addPointButton);
    }
    @FXML
    void loadTable(MouseEvent event) {

    }
    public void init(Stage stage, TablesManager tablesManager, FunctionManager functionManager, DrawManager drawManager) {
        this.stage = stage;
        this.tablesManager = tablesManager;
        this.functionManager = functionManager;
        this.drawManager = drawManager;
    }
    public void setCurrentTable(String currentTable) {
        clearScene();
        this.currentTable = currentTable;
        openScene();
    }
    public void saveTable(int k) {
        drawManager.remove(currentTable);
        drawManager.clearPoints();
        if(currentTable == null) {
            String name = counter + "tab";
            approximateRows(name, k);
            TableBox tableBox = new TableBox(functionManager, drawManager, tablesManager);
            tableBox.setTableName(name);
            tablesManager.addTable(tableBox);
            currentTable = name;
            counter++;
        } else {
            approximateRows(currentTable, k);
        }
        drawManager.rebuildTable(currentTable);
        //drawManager.redraw(currentTable);
        drawManager.makeNewFrame();
        stage.hide();
    }
    public void addPoint(Point point) {
        rowPane.getChildren().remove(addPointButton);
        RowBox rowBox = new RowBox(point, this);
        rowBoxes.add(rowBox);
        rowPane.getChildren().add(rowBox);
        rowPane.getChildren().add(addPointButton);
    }
    public void removePoint(RowBox rowBox) {
        rowBoxes.remove(rowBox);
        rowPane.getChildren().remove(rowBox);
    }
    public void approximateRows(String name, int k) {
        Set<Point> points = new LinkedHashSet<>();
        for(var row : rowBoxes) {
            points.add(row.getRow());
        }
        approximatePoints(name, points, k, colorPicker.getValue(), true);
    }
    public void approximatePoints(String name, Set<Point> points, int k, Color color, boolean visible) {
        Function f = functionManager.approximation(points, k);
        functionManager.putTable(name, new CustomizableTable(f));
        CustomizableTable ct = functionManager.getTable(name);
        ct.setRows(points);
        ct.setColor(color.toString());
        ct.setVisible(visible);
        ct.setWidth(2);
        ct.setK(k);
        ct.setName(name);
    }
    public void clearScene() {
        rowBoxes.clear();
        rowPane.getChildren().clear();
        rowPane.getChildren().add(addPointButton);
        approximateField.setText("");
        colorPicker.setValue(Color.GREEN);
        nameTableField.setText("");
    }
    public void openScene() {
        if(currentTable == null) return;
        CustomizableTable ct = functionManager.getTable(currentTable);
        approximateField.setText(String.valueOf(ct.getK()));
        colorPicker.setValue(ct.getColor());
        nameTableField.setText(ct.getName());
        for(var rows : ct.getRows()) {
            addPoint(rows);
        }
    }
    private final Set<RowBox> rowBoxes = new LinkedHashSet<>();
    private Stage stage;
    private TablesManager tablesManager;
    private FunctionManager functionManager;
    private DrawManager drawManager;
    private String currentTable;
    private static int counter = 0;
}
