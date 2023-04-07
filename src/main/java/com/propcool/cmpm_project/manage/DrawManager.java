package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.auxiliary.Point;
import com.propcool.cmpm_project.auxiliary.Quadtree;
import com.propcool.cmpm_project.components.GroupLines;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.FunctionDecoratorY;
import com.propcool.cmpm_project.functions.combination.Difference;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.*;

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
        Group groupText = new Group();
        double dist = 1/pixelSize;
        // Создание линий на расстоянии 1 для эмитации клеточек
        for(double y = centerY+dist; y < height; y += dist){
            if(y > 0){
                Line lineX = createLine(0, y, wight, y, Color.LIGHTGRAY, 2);
                groupXY.getChildren().add(lineX);
                Text counter = createCounterY(y);
                if(pixelSize < 0.02 && y+dist/3 < height && centerX >= 20 && centerX <= wight-20) groupText.getChildren().add(counter);
            }
        }
        for(double y = centerY-dist; y > 0; y -= dist){
            if(y < height){
                Line lineX = createLine(0, y, wight, y, Color.LIGHTGRAY, 2);
                groupXY.getChildren().add(lineX);
                Text counter = createCounterY(y);
                if(pixelSize < 0.02 && y-dist/3 > 0 && centerX >= 20 && centerX <= wight-20) groupText.getChildren().add(counter);
            }
        }
        for(double x = centerX+dist; x < wight; x += dist){
            if(x > 0){
                Line lineY = createLine(x, 0, x, height, Color.LIGHTGRAY, 2);
                groupXY.getChildren().add(lineY);
                Text counter = createCounterX(x);
                if(pixelSize < 0.02 && x+dist/3 < wight && centerY >= 20 && centerY <= height-20) groupText.getChildren().add(counter);
            }
        }
        for(double x = centerX-dist; x > 0; x -= dist){
            if(x < wight){
                Line lineY = createLine(x, 0, x, height, Color.LIGHTGRAY, 2);
                groupXY.getChildren().add(lineY);
                Text counter = createCounterX(x);
                if(pixelSize < 0.02 && x-dist/3 > 0 && centerY >= 20 && centerY <= height-20) groupText.getChildren().add(counter);
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

        Text counter = createCounterX(coordinateManager.getCenterX());
        if(pixelSize < 0.02 && centerX >= 20 && centerX <= wight-20 && centerY >= 20 && centerY <= height-20) groupText.getChildren().add(counter);

        graphics.put("1xy", groupXY);

        for (var functionName : functionManager.getFunctions().keySet()){
            rebuildFunction(functionName);
        }
        graphics.put("1t", groupText);
    }

    private Line createLine(double x0, double y0, double x1, double y1, Color color, int strokeWidth){
        Line line = new Line(x0, y0, x1, y1);
        line.setStroke(color);
        line.setStrokeWidth(strokeWidth);
        return line;
    }
    private Text createCounterY(double y){
        double y0 = -(y-coordinateManager.getCenterY())*coordinateManager.getPixelSize();
        Text counter = new Text(format.format(y0));
        counter.setLayoutX(coordinateManager.getCenterX()+3);
        counter.setLayoutY(y+15);
        counter.setFont(new Font(14));
        return counter;
    }
    private Text createCounterX(double x){
        double x0 = (x-coordinateManager.getCenterX())*coordinateManager.getPixelSize();
        Text counter = new Text(format.format(x0));
        counter.setLayoutX(x+3);
        counter.setLayoutY(coordinateManager.getCenterY()+15);
        counter.setFont(new Font(14));
        return counter;
    }
    /**
     * Пересоздание линий одной функции
     * */
    public void rebuildFunction(String functionName){
        CustomizableFunction cf = functionManager.getFunction(functionName);
        if(cf == null || cf.getFunction() == null || !cf.isVisible()) return;

        Function function = cf.getFunction();
        Color color = cf.getColor();
        int strokeWidth = cf.getWidth();

        if(functionName.matches("\\d+imp")) rebuildImplicitFunction(functionName, function, color, strokeWidth);
        else rebuildExplicitFunction(functionName, function, color, strokeWidth);
    }
    /**
     * Построение неявной функции
     * */
    public void rebuildImplicitFunction(String functionName, Function function, Color color, int strokeWidth){
        // Здесь будет алгоритм отрисовки неявных функций
        int x0 = (int)(-coordinateManager.getCenterX()*coordinateManager.getPixelSize())-1;
        int x1 = (int)((coordinateManager.getWight()-coordinateManager.getCenterX())*coordinateManager.getPixelSize())+1;

        int y0 = (int)((coordinateManager.getCenterY()-coordinateManager.getHeight())*coordinateManager.getPixelSize())-1;
        int y1 = (int)(coordinateManager.getCenterY()*coordinateManager.getPixelSize())+1;

        double step = coordinateManager.getPixelSize()*100;
        List<Line> lines = new ArrayList<>();
        for(double i = y0; i < y1; i+=step){
            for(double j = x0; j < x1; j+=step){
                Quadtree quadtree = new Quadtree(new Point(j, i), step, 1, function, coordinateManager);
                lines.addAll(quadtree.gridSearch(color, strokeWidth));
            }
        }
        /*Quadtree quadtree = new Quadtree(new Point(-1, -3), 1, 1, function, coordinateManager);
        lines.addAll(quadtree.gridSearch(color, strokeWidth));*/
        Group group = new Group(lines.toArray(Line[]::new));
        graphics.put(functionName, group);
    }
    /**
     * Построение явной функции
     * */
    public void rebuildExplicitFunction(String functionName, Function function, Color color, int strokeWidth){

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
            }
            x0 = x1;
            y0 = y1;
        }
        graphics.put(functionName, groupLines);
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
    public void addNodes(Node... nodes){
        paneForGraphs.getChildren().addAll(nodes);
    }
    public void removeNodes(Node... nodes){
        paneForGraphs.getChildren().removeAll(nodes);
    }
    private final int step = 2;
    private final AnchorPane paneForGraphs;
    private final Map<String, Group> graphics = new LinkedHashMap<>();
    private CoordinateManager coordinateManager;
    private final FunctionManager functionManager;
    private final ControlManager controlManager;
    private final DecimalFormat format = new DecimalFormat("#.#");
}
