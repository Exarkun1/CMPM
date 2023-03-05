package com.propcool.cmpm_project.controllers;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.auxiliary.*;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.notebooks.Notebook;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
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
        double dx = event.getX()-mouseX;
        double dy = event.getY()-mouseY;

        centerX += dx;
        centerY += dy;

        mouseX = event.getX();
        mouseY = event.getY();

        makeNewFrame();
    }
    /**
     * Клик по экрану
     * */
    @FXML
    void mousePressed(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }
    /**
     * Масштабирование
     * */
    @FXML
    void scroll(ScrollEvent event) {
        double dx = event.getX()-centerX;
        double dy = event.getY()-centerY;

        if(event.getDeltaY() < 0){
            pixelSize *= scrollCoef;
            centerX += dx*(scrollCoef-1)/scrollCoef;
            centerY += dy*(scrollCoef-1)/scrollCoef;
        }
        else if(pixelSize > 0){
            pixelSize /= scrollCoef;
            centerX -= dx*(scrollCoef-1);
            centerY -= dy*(scrollCoef-1);
        }

        makeNewFrame();
    }
    /**
     * Открытие окна ввода
     * */
    @FXML
    void openTextFields(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), outgoingPanel);
        translateTransition.setToX(menuIsOpen ? -400 : 400);
        translateTransition.play();
        menuIsOpen = !menuIsOpen;
    }
    @FXML
    void openSettings(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), outgoingPanelSettings);
        translateTransition.setToX(settingsIsOpen ? -400 : 400);
        translateTransition.play();
        settingsIsOpen = !settingsIsOpen;
    }
    /**
     * Добавление текстового поля для записи функций
     * */
    @FXML
    public void addTextField(ActionEvent event){
        // делаем ползунки и кнопку добавления полей для параметров ниже текстовых полей
        newTextField(new TextFieldBox(this));
    }

    @FXML
    void recordNotebook(ActionEvent event) {
        Notebook notebook = Elements.notebookBuilder.build(textFields);

        String notebookName = nameNotebookField.getText();

        Notebook prevNotebook = Elements.notebooks.get(notebookName);
        Elements.notebooks.put(notebookName, notebook);

        if(prevNotebook == null) paneForNotebooks.getChildren().add(new NotebookBox(notebookName, this));
    }
    @FXML
    void saveNotebook(ActionEvent event) {
        File file = fileChooser.showSaveDialog(mainPanel.getScene().getWindow());
        Notebook notebook = Elements.notebookBuilder.build(textFields);
        try {
            if(file != null) Elements.notebookSaver.save(notebook, file);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить тетрадь в файл", e);
        }
    }
    @FXML
    void loadNotebook(ActionEvent event) {
        File file = fileChooser.showOpenDialog(mainPanel.getScene().getWindow());
        try {
            if(file != null) {
                Notebook notebook = Elements.notebookLoader.load(file);
                openNotebook(notebook);
                makeNewFrame();
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть файл", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось получить тетрадь из файла", e);
        }
    }
    /**
     * Пересоздание линий всех функций
     * */
    public void rebuildAllFunctions(){
        Group groupXY = new Group();
        double dist = 1/pixelSize;
        // Создание линий на расстоянии 1 для эмитации клеточек
        for(double y = centerY+dist; y < height; y += dist){
            if(y > 0){
                Line lineX = createLine(0, y, wight, y, Color.LIGHTGRAY, 2);
                groupXY.getChildren().add(lineX);
            }
        }
        for(double y = centerY-dist; y > 0; y -= dist){
            if(y < height){
                Line lineX = createLine(0, y, wight, y, Color.LIGHTGRAY, 2);
                groupXY.getChildren().add(lineX);
            }
        }
        for(double x = centerX+dist; x < wight; x += dist){
            if(x > 0){
                Line lineY = createLine(x, 0, x, height, Color.LIGHTGRAY, 2);
                groupXY.getChildren().add(lineY);
            }
        }
        for(double x = centerX-dist; x > 0; x -= dist){
            if(x < wight){
                Line lineY = createLine(x, 0, x, height, Color.LIGHTGRAY, 2);
                groupXY.getChildren().add(lineY);
            }
        }

        // Всё остальное
        Line lineX = new Line(0,centerY, wight, centerY);
        lineX.setStrokeWidth(2);

        Line lineY = new Line(centerX, 0, centerX, height);
        lineY.setStrokeWidth(2);

        if(centerY >= 0 && centerY <= height)
            groupXY.getChildren().add(lineX);
        if(centerX >= 0 && centerX <= wight)
            groupXY.getChildren().add(lineY);

        graphics.put("", groupXY);

        for (var functionName : Elements.functions.keySet()){
            rebuildFunction(functionName);
        }
    }

    private Line createLine(double x0, double y0, double x1, double y1, Color color, int strokeWidth){
        Line line = new Line(x0, y0, x1, y1);
        line.setStroke(color);
        line.setStrokeWidth(strokeWidth);
        return line;
    }
    /**
     * Пересоздание линий одной функции
     * */
    public void rebuildFunction(String functionName){
        CustomizableFunction cf = Elements.functions.get(functionName);
        if(cf == null) return;

        Function function = cf.getFunction();
        Color color = cf.getColor();
        int strokeWidth = cf.getWidth();

        Group groupLines = new GroupLines();
        double x0 = 1, y0 = getFunctionValue(function, x0);

        for (double x1 = x0; x1 < wight-4; x1 += step) {
            double y1 = getFunctionValue(function, x1);

            if (!Double.isNaN(y0) && !Double.isNaN(y1)) {
                Line line = createLine(x0, y0, x1, y1, color, strokeWidth);

                if (y0 <= height-2 && y1 <= height-2 && y0 >= 2 && y1 >= 2) {
                    groupLines.getChildren().add(line);
                } else if (y0 > height-2 && y1 <= height-2 && y1 >= 2) {
                    line.setStartY(height-2);
                    groupLines.getChildren().add(line);
                } else if (y0 <= height-2 && y1 > height-2 && y0 >= 2) {
                    line.setEndY(height-2);
                    groupLines.getChildren().add(line);
                } else if (y0 < 2 && y1 <= height-2 && y1 >= 2) {
                    line.setStartY(2);
                    groupLines.getChildren().add(line);
                } else if (y0 <= height-2 && y1 < 2 && y0 >= 2) {
                    line.setEndY(2);
                    groupLines.getChildren().add(line);
                }
            }
            x0 = x1;
            y0 = getFunctionValue(function, x0);
        }
        graphics.put(functionName, groupLines);
    }
    private double getFunctionValue(Function function, double x){
        return centerY - function.get((x - centerX) * pixelSize) / pixelSize;
    }
    /**
     * Отбражение всех функций
     * */
    public void redrawAll(){
        paneForGraphs.getChildren().addAll(graphics.values());
    }
    /**
     * Отбражение одной функции
     * */

    public void redraw(String functionName){
        Group group = graphics.get(functionName);
        if(group == null) return;
        paneForGraphs.getChildren().addAll(group);
    }
    /**
     * Очистка экрана
     * */
    public void clear(){
        paneForGraphs.getChildren().clear();
        graphics.clear();
    }
    /**
     * Удаление одной функции с экрана
     * */
    public void remove(String functionName){
        Group group = graphics.get(functionName);
        if(group == null) return;
        paneForGraphs.getChildren().removeAll(group);
        graphics.remove(functionName);
    }
    /**
     * Создание полностью нового кадра
     * */
    public void makeNewFrame(){
        clear();
        rebuildAllFunctions();
        redrawAll();
    }
    public void newTextField(TextFieldBox textFieldBox){
        paneForText.getChildren().removeAll(sliders.values());
        paneForText.getChildren().remove(creatFieldButton);

        textFields.add(textFieldBox);
        paneForText.getChildren().add(textFieldBox);

        paneForText.getChildren().add(creatFieldButton);
        paneForText.getChildren().addAll(sliders.values());
    }
    /**
     * Удаление текстового поля
     * */
    public void removeTextField(TextFieldBox box){
        paneForText.getChildren().remove(box);
        textFields.remove(box);

        String functionName = box.getTextField().getFunctionName();
        remove(functionName);
        Elements.functions.remove(functionName);

        removeSliders();
    }
    /**
     * Добавление ползунка для параметра
     * */
    public void addSliders(){
        for(var param : Elements.parameters.keySet()){
            if(!sliders.containsKey(param)){
                SliderBox sliderBox = new SliderBox(param, this);
                sliders.put(param, sliderBox);
                paneForText.getChildren().add(sliderBox);
            }
        }
    }
    /**
     * Удаление лишних ползунков
     * */
    public void removeSliders(){
        List<String> deletedParams = new ArrayList<>();
        met:
        for(var param : Elements.parameters.keySet()){
            for(var cf : Elements.functions.values()){
                if(cf.getParams().contains(param)) continue met;
            }
            deletedParams.add(param);
        }
        for(var param : deletedParams) {
            SliderBox sliderBox = sliders.remove(param);
            paneForText.getChildren().remove(sliderBox);
            Elements.parameters.remove(param);
        }
    }
    public void clearTextPane(){
        paneForText.getChildren().removeAll(textFields);
        paneForText.getChildren().removeAll(sliders.values());
        Elements.functions.clear();
        Elements.parameters.clear();
        textFields.clear();
        sliders.clear();
    }
    public void addAllTextFields(Notebook notebook){
        for(var data : notebook.getFunctionDataSet()){
            TextFieldBox box = new TextFieldBox(this);
            box.getTextField().setText(data.getExpression());
            box.getColorPicker().setValue(Color.valueOf(data.getColor()));
            box.getTextField().setDefaultColor(Color.valueOf(data.getColor()));
            box.getTextField().processing();
            textFields.add(box);
            newTextField(box);
        }
    }
    public void setSliders(Notebook notebook){
        for(var data : notebook.getParameterDataSet()){
            SliderBox box = sliders.get(data.getName());
            if(box != null) box.getSlider().setParam(data.getValue());
            //sliders.get(data.getName()).getSlider().setArea(data.getArea());
        }
    }
    public void openNotebook(Notebook notebook){
        clearTextPane();
        addAllTextFields(notebook);
        setSliders(notebook);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        outgoingPanel.setPrefHeight(height);
        outgoingPanel.setPrefWidth(400);
        outgoingPanel.setLayoutX(-400);

        outgoingPanelSettings.setPrefHeight(height);
        outgoingPanelSettings.setPrefWidth(400);
        outgoingPanelSettings.setLayoutX(-400);

        accordionSettings.setPrefHeight(height);
        accordionSettings.setPrefWidth(400);

        paneForGraphs.setPrefHeight(height);
        paneForGraphs.setPrefWidth(wight);

        creatFieldButton.setPrefWidth(400);

        newTextField(new TextFieldBox(this));
        makeNewFrame();
    }
    private double pixelSize = 0.01;
    private final int height = 1056-91, half_height = height/2;
    private final int wight = 1936, half_wight = wight/2;
    private double centerX = half_wight;
    private double centerY = half_height;
    private double mouseX;
    private double mouseY;
    private boolean menuIsOpen = false;
    private boolean settingsIsOpen = false;
    private final double scrollCoef = 1.075;
    private final int step = 1;
    private final Map<String, Group> graphics = new HashMap<>();
    private final LinkedHashSet<TextFieldBox> textFields = new LinkedHashSet<>();
    private final Map<String, SliderBox> sliders = new LinkedHashMap<>();
    private final FileChooser fileChooser = new FileChooser();
}
