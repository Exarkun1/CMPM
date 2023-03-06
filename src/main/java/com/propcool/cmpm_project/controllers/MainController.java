package com.propcool.cmpm_project.controllers;

import com.propcool.cmpm_project.components.TextFieldBox;
import com.propcool.cmpm_project.manage.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;
/**
 * Контроллер главной страницы
 * */
public class MainController implements Initializable {
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private BorderPane outgoingPanel;
    @FXML
    private BorderPane outgoingPanelSettings;
    @FXML
    private Accordion accordionSettings;

    @FXML
    private AnchorPane paneForGraphs;

    @FXML
    private VBox paneForText;
    @FXML
    private Button creatFieldButton;

    @FXML
    private TextField nameNotebookField;
    @FXML
    private VBox paneForNotebooks;
    /**
     * Сдвиг координат
     * */
    @FXML
    void mouseDragged(MouseEvent event) {
        if(controlManager.isLineDragged()) return;
        coordinateManager.shift(event.getX(), event.getY());
        drawManager.makeNewFrame();
    }
    /**
     * Клик по экрану
     * */
    @FXML
    void mousePressed(MouseEvent event) {
        coordinateManager.setMouse(event.getX(), event.getY());
    }
    /**
     * Масштабирование
     * */
    @FXML
    void scroll(ScrollEvent event) {
        if(event.getDeltaY() < 0)coordinateManager.zoomIn(event.getX(), event.getY());
        else coordinateManager.zoomOut(event.getX(), event.getY());
        drawManager.makeNewFrame();
    }
    /**
     * Открытие окна ввода
     * */
    @FXML
    void openTextFields(MouseEvent event) {
        openManager.openTextFields();
    }
    /**
     * Открытие окна настроек
     * */
    @FXML
    void openSettings(MouseEvent event) {
        openManager.openSettings();
    }
    /**
     * Добавление текстового поля для записи функций
     * */
    @FXML
    public void addTextField(ActionEvent event){
        // делаем ползунки и кнопку добавления полей для параметров ниже текстовых полей
        textFieldsManager.addTextField(new TextFieldBox(functionManager, drawManager, textFieldsManager));
    }
    /**
     * Запись тетради
     * */
    @FXML
    void recordNotebook(ActionEvent event) {
        notebookManager.record(nameNotebookField.getText());
    }
    /**
     * Сохранение тетради
     * */
    @FXML
    void saveNotebook(ActionEvent event) {
        notebookManager.save(mainPanel.getScene().getWindow());
    }
    /**
     * Загрузка тетради
     * */
    @FXML
    void loadNotebook(ActionEvent event) {
        notebookManager.load(mainPanel.getScene().getWindow());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        outgoingPanel.setPrefHeight(coordinateManager.getHeight());
        outgoingPanel.setPrefWidth(400);
        outgoingPanel.setLayoutX(-400);

        outgoingPanelSettings.setPrefHeight(coordinateManager.getHeight());
        outgoingPanelSettings.setPrefWidth(400);
        outgoingPanelSettings.setLayoutX(-400);

        accordionSettings.setPrefHeight(coordinateManager.getHeight());
        accordionSettings.setPrefWidth(400);

        paneForGraphs.setPrefHeight(coordinateManager.getHeight());
        paneForGraphs.setPrefWidth(coordinateManager.getHeight());

        creatFieldButton.setPrefWidth(400);

        openManager = new OpenManager(outgoingPanel, outgoingPanelSettings, controlManager);
        drawManager = new DrawManager(paneForGraphs, functionManager, coordinateManager, controlManager);
        textFieldsManager = new TextFieldsManager(paneForText, creatFieldButton, functionManager, drawManager);
        notebookManager = new NotebookManager(paneForNotebooks, functionManager, drawManager, textFieldsManager);
        textFieldsManager.addTextField(new TextFieldBox(functionManager, drawManager, textFieldsManager));
        drawManager.makeNewFrame();
    }
    public final FunctionManager functionManager =new FunctionManager();
    public final CoordinateManager coordinateManager = new CoordinateManager();
    private final ControlManager controlManager = new ControlManager();
    public OpenManager openManager;
    private DrawManager drawManager;
    private TextFieldsManager textFieldsManager;
    private NotebookManager notebookManager;
}
