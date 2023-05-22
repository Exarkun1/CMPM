package com.propcool.cmpm_project.controllers;

import com.propcool.cmpm_project.components.RowBox;
import com.propcool.cmpm_project.components.TableBox;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.interpolate.Polynomial;
import com.propcool.cmpm_project.io.tables.TableLoader;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TablesManager;
import com.propcool.cmpm_project.io.data.CustomizableTable;
import com.propcool.cmpm_project.util.Point;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
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
    private ToggleButton pointsButton;
    @FXML
    private ToggleButton visibleButton;

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
        File file = fileChooser.showOpenDialog(approximateField.getScene().getWindow());
        try{
            if(file != null) {
                Set<Point> points = tableLoader.load(file);
                clearRows();
                addPoints(points);
            }
        } catch (FileNotFoundException e) {
            loadAlert.show();
        } catch (InputMismatchException e) {
            classAlert.show();
        }
    }
    public void init(Stage stage, TablesManager tablesManager, FunctionManager functionManager, DrawManager drawManager) {
        this.stage = stage;
        this.tablesManager = tablesManager;
        this.functionManager = functionManager;
        this.drawManager = drawManager;
    }
    public void setCurrentTable(String currentTable, TableBox currentTableBox) {
        clearScene();
        this.currentTable = currentTable;
        this.currentTableBox = currentTableBox;
        openScene();
    }
    public void saveTable(int k) {
        if(currentTable == null) {
            String name = nameTableField.getText();
            if(functionManager.getObject(name) != null || !name.matches("[a-z]+\\d+|[a-z]+"))
                throw new RuntimeException("Такое имя не подходит или оно уже занято");
            approximateRows(name, k);
            TableBox tableBox = new TableBox(functionManager, drawManager, tablesManager);
            tableBox.setTableName(name);
            tableBox.setTableBody(tablesManager.getTableBody(name));
            tablesManager.addTable(tableBox);
            currentTable = name;
        } else {
            String name = nameTableField.getText();
            if(functionManager.getObject(name) != null || !name.matches("[a-z]+\\d+|[a-z]+"))
                throw new RuntimeException("Такое имя уже есть");
            approximateRows(currentTable, k);
            currentTableBox.setTableBody(tablesManager.getTableBody(currentTable));
            currentTableBox.setTableName(name);
        }
        drawManager.clearPoints();
        drawManager.makeNewRebuildFrame();
        stage.hide();
    }
    public void addPoint(Point point) {
        rowPane.getChildren().remove(addPointButton);
        RowBox rowBox = new RowBox(point, this);
        rowBoxes.add(rowBox);
        rowPane.getChildren().add(rowBox);
        rowPane.getChildren().add(addPointButton);
    }
    public void addPoints(Set<Point> points) {
        for(var point : points) {
            addPoint(point);
        }
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
        approximatePoints(name, points, k, colorPicker.getValue(), !visibleButton.isSelected(), pointsButton.isSelected());
    }
    public void approximatePoints(String name, Set<Point> points, int k, Color color, boolean visible, boolean pointsVisible) {
        Polynomial f = functionManager.approximation(points, k);
        functionManager.putTable(name, new CustomizableTable(f));
        CustomizableTable ct = functionManager.getTable(name);
        ct.setRows(points);
        ct.setColor(color.toString());
        ct.setVisible(visible);
        ct.setWidth(2);
        ct.setK(k);
        ct.setName(name);
        ct.setPointsVisible(pointsVisible);
    }
    public void clearRows() {
        rowBoxes.clear();
        rowPane.getChildren().clear();
        rowPane.getChildren().add(addPointButton);
    }
    public void clearScene() {
        clearRows();
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
        visibleButton.setSelected(!ct.isVisible());
        pointsButton.setSelected(ct.isPointsVisible());
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
    private TableBox currentTableBox;
    private final FileChooser fileChooser = new FileChooser();
    private final TableLoader tableLoader = new TableLoader();
    private final Alert loadAlert = new Alert(Alert.AlertType.ERROR,"Не удалось открыть файл");
    private final Alert classAlert = new Alert(Alert.AlertType.ERROR, "Не удалось получить тетрадь из файла");
}
