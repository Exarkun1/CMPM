package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.components.TextFieldBox;
import com.propcool.cmpm_project.util.Point;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
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
                       BorderPane outgoingPanelSettings, VBox paneForNotebooks,
                       BorderPane outgoingTablePanel, VBox paneForTable, Button createTableButton
    ){
        this.mainPanel = mainPanel;
        openManager = new OpenManager(outgoingPanel, outgoingPanelSettings, outgoingTablePanel, controlManager);
        drawManager = new DrawManager(paneForGraphs, functionManager, coordinateManager, controlManager);
        textFieldsManager = new TextFieldsManager(paneForText, creatFieldButton, functionManager, drawManager);
        tablesManager = new TablesManager(paneForTable, createTableButton, functionManager, drawManager);
        notebookManager = new NotebookManager(paneForNotebooks, functionManager, textFieldsManager, tablesManager, this);

        coordinateManagers.put("cartesian", coordinateManager);
        coordinateManagers.put("polar", new PolarManager());
        textFieldsManager.addTextField(new TextFieldBox(functionManager, drawManager, textFieldsManager));
        drawManager.makeNewFrame();
    }
    public int getWidth(){
        return coordinateManager.getWidth();
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
    public void scale(double delta){
        if(delta < 0){
            coordinateManagers.get("cartesian").zoomCenter(1.3);
            coordinateManagers.get("polar").zoomCenter(1.3);
        }
        else{
            coordinateManagers.get("cartesian").zoomCenter(1/1.3);
            coordinateManagers.get("polar").zoomCenter(1/1.3);
        }
        drawManager.makeNewFrame();
    }
    public void openTextFields(){
        openManager.openTextFields();
    }
    public void openSettings(){
        openManager.openSettings();
    }
    public void openTables() { openManager.openTables(); }
    public void addTextField(){
        // делаем ползунки и кнопку добавления полей для параметров ниже текстовых полей
        textFieldsManager.addTextField(new TextFieldBox(functionManager, drawManager, textFieldsManager));
    }
    public void addTable() {
        tablesManager.loadNew();
    }
    public void recordNotebook(String name){
        notebookManager.record(name, coordinateManager);
    }
    public void saveNotebook(){
        notebookManager.save(mainPanel.getScene().getWindow(), coordinateManager);
    }
    public void loadNotebook(){
        notebookManager.load(mainPanel.getScene().getWindow());
    }
    public void changingCoordinateSystem(String name){
        coordinateManager = coordinateManagers.get(name);
        drawManager.setCoordinateManager(coordinateManager);
        drawManager.makeNewFrame();
    }
    public void keyPressed(KeyCode code) {
        switch (code) {
            case RIGHT -> {
                coordinateManagers.get("cartesian").shiftCenter(-20, 0);
                coordinateManagers.get("polar").shiftCenter(-20, 0);
                drawManager.makeNewFrame();
            }
            case LEFT -> {
                coordinateManagers.get("cartesian").shiftCenter(20, 0);
                coordinateManagers.get("polar").shiftCenter(20, 0);
                drawManager.makeNewFrame();
            }
            case UP -> {
                coordinateManagers.get("cartesian").shiftCenter(0, 20);
                coordinateManagers.get("polar").shiftCenter(0, 20);
                drawManager.makeNewFrame();
            }
            case DOWN -> {
                coordinateManagers.get("cartesian").shiftCenter(0, -20);
                coordinateManagers.get("polar").shiftCenter(0, -20);
                drawManager.makeNewFrame();
            }
            case EQUALS -> {
                coordinateManagers.get("cartesian").zoomCenter(1/1.3);
                coordinateManagers.get("polar").zoomCenter(1/1.3);
                drawManager.makeNewFrame();
            }
            case MINUS -> {
                coordinateManagers.get("cartesian").zoomCenter(1.3);
                coordinateManagers.get("polar").zoomCenter(1.3);
                drawManager.makeNewFrame();
            }
            case ESCAPE -> openTextFields();
            case F1 -> openSettings();
        }
    }
    public void cauchyAlert() {
        functionManager.cauchyAlert();
    }
    public void showDirectionsField() {
        controlManager.setDirectionsShowed();
        drawManager.makeNewFrame();
    }
    public void saveCauchy(String x, String y) {
        try {
            setPoint(x, y, new Point(0, 0), this::setCauchyPoint);
        } catch (NumberFormatException e) {
            cauchyAlert();
        }
    }
    public void savePolar(String x, String y) {
        try {
            setPoint(x, y, new Point(0, 4*Math.PI), this::setPolarBorders);
        } catch (NumberFormatException e) {
            polarAlert();
        }
    }
    private void setPoint(String x, String y, Point def, PointSetter ps) {
        double X, Y;
        if(x.equals("") || x.matches(" +")) X = def.getX();
        else X = Double.parseDouble(x);
        if(y.equals("") || y.matches(" +")) Y = def.getY();
        else Y = Double.parseDouble(y);
        ps.set(X, Y);
    }
    private void setCauchyPoint(double x, double y) {
        functionManager.setCauchyPoint(x, y);
        drawManager.makeNewFrame();
    }
    private void setPolarBorders(double start, double end) {
        ((PolarManager)coordinateManagers.get("polar")).setBorders(start, end);
        if(coordinateManager instanceof PolarManager) drawManager.makeNewFrame();
    }
    public void polarAlert() {
        ((PolarManager)coordinateManagers.get("polar")).polarAlert();
    }
    public DrawManager getDrawManager() {
        return drawManager;
    }
    private final FunctionManager functionManager = new FunctionManager();
    private CoordinateManager coordinateManager = new CartesianManager();
    private final ControlManager controlManager = new ControlManager();
    private final OpenManager openManager;
    private final DrawManager drawManager;
    private final TextFieldsManager textFieldsManager;
    private final TablesManager tablesManager;
    private final NotebookManager notebookManager;
    private final AnchorPane mainPanel;
    private final Map<String, CoordinateManager> coordinateManagers = new HashMap<>();
    private interface PointSetter {
        void set(double x, double y);
    }
}
