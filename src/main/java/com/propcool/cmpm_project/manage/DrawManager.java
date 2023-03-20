package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.auxiliary.Point;
import com.propcool.cmpm_project.components.GroupLines;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * Менеджер рисования, создаёт линии для графиков и добавляет их на панель
 * */
public class DrawManager {
    public DrawManager(AnchorPane paneForGraphs, FunctionManager functionManager, CoordinateManager coordinateManager, ControlManager controlManager){
        this.paneForGraphs = paneForGraphs;
        this.functionManager = functionManager;
        this.coordinateManager = coordinateManager;
        this.controlManager = controlManager;
    }
    public void setCoordinateManager(CoordinateManager coordinateManager){
        this.coordinateManager = coordinateManager;
    }
    /**
     * Пересоздание линий всех функций
     * */
    public void rebuildAllFunctions(){
        double height = coordinateManager.getHeight();
        double wight = coordinateManager.getWight();
        double centerX = coordinateManager.getCenterX();
        double centerY = coordinateManager.getCenterY();
        double pixelSize = coordinateManager.getPixelSize();

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

        for (var functionName : functionManager.getFunctions().keySet()){
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
        CustomizableFunction cf = functionManager.getFunction(functionName);
        if(cf == null || cf.getFunction() == null) return;

        Function function = cf.getFunction();
        Color color = cf.getColor();
        int strokeWidth = cf.getWidth();

        Group groupLines = new GroupLines(function, coordinateManager, this, controlManager);
        Point point = coordinateManager.getCoordinate(function, coordinateManager.getMin());
        double x0 = point.getX(), y0 = point.getY();

        double step = coordinateManager.getStep();
        for (double a = coordinateManager.getMin(); a < coordinateManager.getMax(); a += step) {
            point = coordinateManager.getCoordinate(function, a);
            double x1 = point.getX(), y1 = point.getY();

            boolean start = coordinateManager.onScreen(x0, y0);
            boolean end = coordinateManager.onScreen(x1, y1);
            if (!Double.isNaN(y0) && !Double.isNaN(y1) && start && end) {
                Line line = createLine(x0, y0, x1, y1, color, strokeWidth);
                groupLines.getChildren().add(line);
                /*if (y0 <= height-2 && y1 <= height-2 && y0 >= 2 && y1 >= 2) {
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
                }*/
            }
            x0 = x1;
            y0 = y1;
        }
        graphics.put(functionName, groupLines);
    }
    public void rebuildFunctions(Collection<String> functionNames){
        for(var name : functionNames){
            rebuildFunction(name);
        }
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
    public void redraw(Collection<String> functionNames){
        for(var name : functionNames){
            redraw(name);
        }
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
    public void addNodes(Node... nodes){
        paneForGraphs.getChildren().addAll(nodes);
    }
    public void removeNodes(Node... nodes){
        paneForGraphs.getChildren().removeAll(nodes);
    }
    private final int step = 2;
    private final AnchorPane paneForGraphs;
    private final Map<String, Group> graphics = new HashMap<>();
    private CoordinateManager coordinateManager;
    private final FunctionManager functionManager;
    private final ControlManager controlManager;
}
