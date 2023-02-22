package com.propcool.cmpm_project.contollers;

import com.propcool.cmpm_project.Elements;
import com.propcool.cmpm_project.contollers.auxiliary.SliderBox;
import com.propcool.cmpm_project.contollers.auxiliary.SliderOnPage;
import com.propcool.cmpm_project.contollers.auxiliary.TextFieldBox;
import com.propcool.cmpm_project.contollers.auxiliary.TextFieldOnPage;
import com.propcool.cmpm_project.functions.Function;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

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
    private AnchorPane paneForGraphs;

    @FXML
    private VBox paneForText;
    /**
     * Сдвиг координат
     * */
    @FXML
    void mouseDragged(MouseEvent event) {
        shiftX -= (mouseX-event.getX());
        shiftY -= (mouseY-event.getY());

        centerX = half_wight + shiftX;
        centerY = half_height + shiftY;

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
        if(event.getDeltaY() < 0){
            pixelSize *= 1.1;
            shiftX -= (centerX-event.getX())/4;
            shiftY -= (centerY-event.getY())/4;
        }
        else if(pixelSize > 0){
            pixelSize /= 1.1;
            shiftX += (centerX-event.getX())/4;
            shiftY += (centerY-event.getY())/4;
        }

        centerX = half_wight + shiftX;
        centerY = half_height + shiftY;

        makeNewFrame();
    }
    /**
     * Открытие окна ввода
     * */
    @FXML
    void openTextFields(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), outgoingPanel);
        translateTransition.setToX(menuIsOpen ? -350 : 350);
        translateTransition.play();
        menuIsOpen = !menuIsOpen;
    }
    /**
     * Пересоздание линий всех функций
     * */
    public void rebuildAllFunctions(){
        Group groupXY = new Group();
        double dist = 1/pixelSize;
        // Создание линий на расстоянии 1 для эмитации клеточек(требует рефакторинга)
        for(double y = centerY+dist; y < height; y += dist){
            if(y > 0){
                Line lineX = new Line(0,y, wight, y);
                lineX.setStroke(Color.LIGHTGRAY);
                lineX.setStrokeWidth(2);
                groupXY.getChildren().add(lineX);
            }
        }
        for(double y = centerY-dist; y > 0; y -= dist){
            if(y < height){
                Line lineX = new Line(0,y, wight, y);
                lineX.setStroke(Color.LIGHTGRAY);
                lineX.setStrokeWidth(2);
                groupXY.getChildren().add(lineX);
            }
        }
        for(double x = centerX+dist; x < wight; x += dist){
            if(x > 0){
                Line lineX = new Line(x, 0, x, height);
                lineX.setStroke(Color.LIGHTGRAY);
                lineX.setStrokeWidth(2);
                groupXY.getChildren().add(lineX);
            }
        }
        for(double x = centerX-dist; x > 0; x -= dist){
            if(x < wight){
                Line lineX = new Line(x, 0, x, height);
                lineX.setStroke(Color.LIGHTGRAY);
                lineX.setStrokeWidth(2);
                groupXY.getChildren().add(lineX);
            }
        }

        // Всё остальное
        Line lineX = new Line(0,centerY, wight, centerY);
        lineX.setStrokeWidth(2);

        Line lineY = new Line(centerX, 0, centerX, height);
        lineY.setStrokeWidth(2);

        //Group groupXY = new Group();
        if(centerY >= 0 && centerY <= height)
            groupXY.getChildren().add(lineX);
        if(centerX >= 0 && centerX <= wight)
            groupXY.getChildren().add(lineY);

        graphics.put("", groupXY);

        for (var functionName : Elements.functions.keySet()){
            rebuildFunction(functionName);
        }
    }
    /**
     * Пересоздание линий одной функции
     * */
    public void rebuildFunction(String functionName){
        Function function = Elements.functions.get(functionName);
        Color color = Elements.functionsWithColor.get(functionName);

        Group groupLines = new Group();
        double x0 = 0, y0 = getFunctionValue(function, x0);

        for (double x1 = x0; x1 < wight; x1 += step) {
            double y1 = getFunctionValue(function, x1);

            if (!Double.isNaN(y0) && !Double.isNaN(y1)) {
                Line line = new Line(x0, y0, x1, y1);
                line.setStroke(color);
                line.setStrokeWidth(2);

                if (y0 <= height && y1 <= height && y0 >= 0 && y1 >= 0) {
                    groupLines.getChildren().add(line);
                } else if (y0 > height && y1 <= height && y1 >= 0) {
                    line.setStartY(height);
                    groupLines.getChildren().add(line);
                } else if (y0 <= height && y1 > height && y0 >= 0) {
                    line.setEndY(height);
                    groupLines.getChildren().add(line);
                } else if (y0 < 0 && y1 <= height && y1 >= 0) {
                    line.setStartY(0);
                    groupLines.getChildren().add(line);
                } else if (y0 <= height && y1 < 0 && y0 >= 0) {
                    line.setEndY(0);
                    groupLines.getChildren().add(line);
                }
            }
            x0 = x1;
            y0 = getFunctionValue(function, x0);
        }
        graphics.put(functionName, groupLines);
    }
    private double getFunctionValue(Function function, double x){
        return half_height - function.get((x - half_wight - shiftX) * pixelSize) / pixelSize + shiftY;
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
        paneForGraphs.getChildren().addAll(graphics.get(functionName));
    }
    /**
     * Очистка экрана
     * */
    public void clear(){
        paneForGraphs.getChildren().removeAll(graphics.values());
        graphics.clear();
    }
    /**
     * Удаление одной функции с экрана
     * */
    public void remove(String functionName){
        paneForGraphs.getChildren().removeAll(graphics.get(functionName));
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
    /**
     * Добавление текстового поля для записи функций
     * */

    public void addTextField(){
        if(textFields.isEmpty() || !textFields.getLast().getTextField().getText().equals("")) {
            TextFieldBox textFieldBox = new TextFieldBox(this);
            textFields.add(textFieldBox);
            paneForText.getChildren().add(textFieldBox);
        }
    }
    /**
     * Удаление текстового поля
     * */
    public void removeTextField(){
        if(textFields.size() >= 2) {
            TextFieldBox textFieldBox = textFields.get(textFields.size() - 1);
            TextField tf1 = textFields.get(textFields.size() - 1).getTextField();
            TextField tf2 = textFields.get(textFields.size() - 2).getTextField();
            if (tf1.getText().equals("") && tf2.getText().equals("")) {
                textFields.remove(textFieldBox);
                paneForText.getChildren().remove(textFieldBox);
            }
        }
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
            for(var params : Elements.functionsWithParams.values()){
                if(params.contains(param)) continue met;
            }
            deletedParams.add(param);
        }
        for(var param : deletedParams) {
            SliderBox sliderBox = sliders.remove(param);
            paneForText.getChildren().remove(sliderBox);
            Elements.parameters.remove(param);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTextField();

        outgoingPanel.setPrefHeight(height);
        outgoingPanel.setPrefWidth(350);
        outgoingPanel.setLayoutX(-350);

        paneForGraphs.setPrefHeight(height);
        paneForGraphs.setPrefWidth(wight);

        makeNewFrame();
    }
    private double pixelSize = 0.01;
    private double shiftX = 0;
    private double shiftY = 0;
    private final int height = 1056-91, half_height = height/2;
    private final int wight = 1936, half_wight = wight/2;
    private double centerX = half_wight + shiftX;
    private double centerY = half_height + shiftY;
    private boolean menuIsOpen = false;
    private final int step = 2;
    private final Map<String, Group> graphics = new HashMap<>();
    private final LinkedList<TextFieldBox> textFields = new LinkedList<>();
    private final Map<String, SliderBox> sliders = new LinkedHashMap<>();
    private double mouseX;
    private double mouseY;
}
