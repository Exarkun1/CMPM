package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.components.TextFieldBox;
import com.propcool.cmpm_project.util.PhasePortrait;
import com.propcool.cmpm_project.util.Point;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        /*drawManager.setTestDraw(() -> {
            Group group = new Group();
            PhasePortrait phasePortrait = new PhasePortrait(coordinateManager,1);
            //List<Node> nodes = phasePortrait.solveLiner(new double[][]{{1, 0}, {0, 1}}, new double[] {0, 0}); // правильный узел
            //List<Node> nodes = phasePortrait.solveLiner(new double[][]{{-1, -1}, {1, -3}}, new double[] {0, 0}); // вырожденный узел
            //List<Node> nodes = phasePortrait.solveLiner(new double[][]{{4, 2}, {1, 3}}, new double[] {0, 0}); // узел
            //List<Node> nodes = phasePortrait.solveLiner(new double[][]{{1, -4}, {1, -1}}, new double[] {0, 0}); // центр
            //List<Node> nodes = phasePortrait.solveLiner(new double[][]{{-2, -2}, {-1, 2}}, new double[] {12, -3}); // седло
            //List<Node> nodes = phasePortrait.solveLiner(new double[][]{{3, 2}, {-5, 5}}, new double[] {1, 2}); // фокус
            group.getChildren().addAll(nodes);
            return group;
        });*/
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
        if(controlManager.isLineDragged()) {
            drawManager.getLastGroupLines().setDraggedGroup(x, y);
        } else {
            coordinateManagers.get("cartesian").shift(x, y);
            coordinateManagers.get("polar").shift(x, y);
            drawManager.makeNewFrame();
        }
    }
    public void stopDragged() {
        if(controlManager.isLineDragged()) {
            drawManager.getLastGroupLines().clearDraggedGroup();
            controlManager.setLineDragged(false);
        }
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
            case F1 -> openTables();
            case F2 -> openSettings();
        }
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
    public void showPhasePortrait(String f, String g) {
        try {
            double[][] A = new double[2][2];
            double[] b = new double[2];

            String reject = "[+|-]?\\d+(.\\d+)?\\*x[+|-]\\d+(.\\d+)?\\*y[+|-]\\d+(.\\d+)?";
            if(!f.matches(reject) || !g.matches(reject)) throw new RuntimeException("Не верные данные");

            Pattern pattern = Pattern.compile("([+|-]?\\d+(.\\d+)?)");
            Matcher matcher = pattern.matcher(f);

            if(matcher.find()) A[0][0] = Double.parseDouble(matcher.group());
            if(matcher.find()) A[0][1] = Double.parseDouble(matcher.group());
            if(matcher.find()) b[0] = Double.parseDouble(matcher.group());

            matcher = pattern.matcher(g);

            if(matcher.find()) A[1][0] = Double.parseDouble(matcher.group());
            if(matcher.find()) A[1][1] = Double.parseDouble(matcher.group());
            if(matcher.find()) b[1] = Double.parseDouble(matcher.group());

            if(A[0][0] / A[1][0] == A[0][1] / A[1][1]) throw new RuntimeException("Вырожденный случай");

            functionManager.setPhasePortrait(A, b);
            controlManager.setPortraitShowed(true);
            drawManager.makeNewFrame();
        } catch (RuntimeException e) {
            phaseAlert(e.getMessage());
        }
    }
    public void showFunctions() {
        controlManager.setPortraitShowed(false);
        drawManager.makeNewFrame();
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
    public void cauchyAlert() {
        functionManager.cauchyAlert();
    }
    public void polarAlert() {
        ((PolarManager)coordinateManagers.get("polar")).polarAlert();
    }
    public void phaseAlert(String text) {
        functionManager.phaseAlert(text);
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
