package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.components.TextFieldBox;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
/**
 * Менеджер управления другими менеджерами
 * */
public class MainManager {
    public MainManager(AnchorPane mainPanel, AnchorPane paneForGraphs, BorderPane outgoingPanel,
                       VBox paneForText, Button creatFieldButton,
                       BorderPane outgoingPanelSettings, VBox paneForNotebooks
    ){
        this.mainPanel = mainPanel;
        openManager = new OpenManager(outgoingPanel, outgoingPanelSettings, controlManager);
        drawManager = new DrawManager(paneForGraphs, functionManager, coordinateManager, controlManager);
        textFieldsManager = new TextFieldsManager(paneForText, creatFieldButton, functionManager, drawManager);
        notebookManager = new NotebookManager(paneForNotebooks, functionManager, textFieldsManager, this);

        coordinateManagers.put("cartesian", coordinateManager);
        coordinateManagers.put("polar", new PolarManager());
        Platform.runLater(() -> {
            textFieldsManager.addTextField(new TextFieldBox(functionManager, drawManager, textFieldsManager));
            drawManager.makeNewFrame();
        });
    }
    public int getWidth(){
        return coordinateManager.getWight();
    }
    public int getHeight(){
        return coordinateManager.getHeight();
    }
    public double getCenterX() {
        return coordinateManager.getCenterX();
    }
    public double getCenterY() {
        return coordinateManager.getCenterY();
    }
    public double getPixelSize() {
        return coordinateManager.getPixelSize();
    }
    public void shift(double x, double y){
        if(controlManager.isLineDragged()) return;
        coordinateManagers.get("cartesian").shift(x, y);
        coordinateManagers.get("polar").shift(x, y);
        drawManager.makeNewFrame();
    }
    public void setMouse(double x, double y){
        coordinateManagers.get("cartesian").setMouse(x, y);
        coordinateManagers.get("polar").setMouse(x, y);
    }
    public void scale(double delta, double x, double y){
        if(delta < 0){
            coordinateManagers.get("cartesian").zoomOut(x, y);
            coordinateManagers.get("polar").zoomOut(x, y);
        }
        else{
            coordinateManagers.get("cartesian").zoomIn(x, y);
            coordinateManagers.get("polar").zoomIn(x, y);
        }
        drawManager.makeNewFrame();
    }
    public void openTextFields(){
        openManager.openTextFields();
    }
    public void openSettings(){
        openManager.openSettings();
    }
    public void addTextField(){
        // делаем ползунки и кнопку добавления полей для параметров ниже текстовых полей
        Platform.runLater(() -> textFieldsManager.addTextField(new TextFieldBox(functionManager, drawManager, textFieldsManager)));
    }
    public void recordNotebook(String name){
        notebookManager.record(name, coordinateManager);
    }
    public void saveNotebook(){
        notebookManager.save(mainPanel.getScene().getWindow(), coordinateManager);
    }
    public void loadNotebook(){
        Platform.runLater(() -> notebookManager.load(mainPanel.getScene().getWindow()));
    }
    public void changingCoordinateSystem(String name){
        Platform.runLater(() -> {
            coordinateManager = coordinateManagers.get(name);
            drawManager.setCoordinateManager(coordinateManager);
            drawManager.makeNewFrame();
        });
    }
    private final FunctionManager functionManager = new FunctionManager();
    private CoordinateManager coordinateManager = new CartesianManager();
    private final ControlManager controlManager = new ControlManager();
    private final OpenManager openManager;
    private final DrawManager drawManager;
    private final TextFieldsManager textFieldsManager;
    private final NotebookManager notebookManager;
    private final AnchorPane mainPanel;
    private final Map<String, CoordinateManager> coordinateManagers = new HashMap<>();
}
