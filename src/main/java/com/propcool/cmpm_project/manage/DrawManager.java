package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.components.AbstractGroupLines;
import com.propcool.cmpm_project.components.DifGroupLines;
import com.propcool.cmpm_project.components.ImpGroupLines;
import com.propcool.cmpm_project.io.data.CustomizableTable;
import com.propcool.cmpm_project.util.Inclines;
import com.propcool.cmpm_project.util.Mixer;
import com.propcool.cmpm_project.util.Point;
import com.propcool.cmpm_project.util.Quadtree;
import com.propcool.cmpm_project.components.GroupLines;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.io.data.CustomizableFunction;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    public Group rebuildFrame() {
        double height = coordinateManager.getHeight();
        double wight = coordinateManager.getWidth();
        double centerX = coordinateManager.getCenterX();
        double centerY = coordinateManager.getCenterY();
        double pixelSize = coordinateManager.getPixelSize();

        Group groupXY = new Group();
        Group groupText = new Group();
        double dist = 1/pixelSize;
        // Создание линий на расстоянии 1 для имитации клеточек
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
        if(pixelSize < 0.02 && centerX >= 20 && centerX <= wight-20 && centerY >= 20 && centerY <= height-20)
            groupText.getChildren().add(counter);

        graphics.add(groupXY);
        return groupText;
    }
    /**
     * Пересоздание линий всех функций
     * */
    public void rebuildAllFunctions(Group groupText){
        for (var functionName : functionManager.getFunctions().keySet()){
            rebuildFunction(functionName);
        }
        for (var tableName : functionManager.getTables().keySet()) {
            rebuildTable(tableName);
        }
        graphics.add(groupText);
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

        if(functionName.matches("\\d+imp") && coordinateManager instanceof CartesianManager){
            rebuildImplicitFunction(function, color, strokeWidth);
        } else if(functionName.matches("\\d+dif") && coordinateManager instanceof CartesianManager){
            rebuildDifferentialEquation(function, color, strokeWidth);
        } else if(!functionName.matches("\\d+imp") && !functionName.matches("\\d+dif")) {
            rebuildExplicitFunction(function, color, strokeWidth);
        }
    }
    /**
     * Построение неявной функции
     * */
    public void rebuildImplicitFunction(Function function, Color color, int strokeWidth){
        int x0 = (int)(-coordinateManager.getCenterX()*coordinateManager.getPixelSize())-1;
        int x1 = (int)((coordinateManager.getWidth()-coordinateManager.getCenterX())*coordinateManager.getPixelSize())+1;

        int y0 = (int)((coordinateManager.getCenterY()-coordinateManager.getHeight())*coordinateManager.getPixelSize())-1;
        int y1 = (int)(coordinateManager.getCenterY()*coordinateManager.getPixelSize())+1;

        double step = coordinateManager.getPixelSize()*100;
        List<Line> lines = new ArrayList<>();
        for(double i = y0; i < y1; i+=step){
            for(double j = x0; j < x1; j+=step){
                Quadtree quadtree = new Quadtree(
                        new Point(j, i), step,
                        1, function, coordinateManager,
                        color, strokeWidth
                        );
                lines.addAll(quadtree.gridSearch());
            }
        }
        Group group = new ImpGroupLines(function, color, coordinateManager, this, controlManager, functionManager);
        group.getChildren().addAll(lines);
        graphics.add(group);
    }
    /**
     * Построение явной функции
     * */
    public void rebuildExplicitFunction(Function function, Color color, int strokeWidth){
        Group groupLines = new GroupLines(function, color, coordinateManager, this, controlManager, functionManager);
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
        graphics.add(groupLines);
    }
    /**
     * Отображение дифференциального уравнения
     * */
    public void rebuildDifferentialEquation(Function function, Color color, int strokeWidth){
        Inclines inclines = new Inclines(function, coordinateManager, color, strokeWidth);
        List<Line> lines = new ArrayList<>();
        if(controlManager.isDirectionsShowed()) lines.addAll(inclines.tangentField());

        lines.addAll(inclines.integralCurve(functionManager.getCauchyPoint()));
        Group group = new DifGroupLines(function, color, coordinateManager, this, controlManager);
        group.getChildren().addAll(lines);

        graphics.add(group);
    }
    public void rebuildTable(String tableName) {
        CustomizableTable ct = functionManager.getTable(tableName);
        if(ct == null || ct.getApproximate() == null) return;

        Function function = ct.getApproximate();
        Color color = ct.getColor();
        int strokeWidth = ct.getWidth();

        if(ct.isVisible()) rebuildExplicitFunction(function, color, strokeWidth);

        if(ct.isPointsVisible()) {
            Group points = new Group();
            for(var point : ct.getRows()) {
                Point pixelPoint = coordinateManager.getPixelXY(point);
                if(coordinateManager.onScreen(pixelPoint)) {
                    Circle circle = new Circle(pixelPoint.getX(), pixelPoint.getY(), 5);
                    circle.setFill(mix.mix(color, 0.65));
                    points.getChildren().add(circle);
                }
            }
            graphics.add(points);
        }
    }
    /**
     * Отображение всех элементов
     * */
    public void redrawAll(){
        paneForGraphs.getChildren().addAll(graphics);
    }
    /**
     * Очистка экрана
     * */
    public void clear(){
        paneForGraphs.getChildren().clear();
        graphics.clear();
        clearPoints();
    }
    /**
     * Создание полностью нового кадра
     * */
    public void makeNewFrame() {
        setLastGroupLines(null);
        clear();
        Group groupText = rebuildFrame();
        rebuildAllFunctions(groupText);
        redrawAll();
    }
    /**
     * Добавление какого элемента на изображение
     * */
    public void addNodes(Node... nodes){
        for(var node : nodes) {
            if(!paneForGraphs.getChildren().contains(node)) {
                paneForGraphs.getChildren().add(node);
            }
        }
    }
    /**
     * Удаление какого элемента с изображения
     * */
    public void removeNodes(Node... nodes){
        paneForGraphs.getChildren().removeAll(nodes);
    }
    /**
     * Добавление точки на изображение
     * */
    public void addPoint(Circle point) {
        points.add(point);
        addNodes(point);
    }
    /**
     * Очистка точек
     * */
    public void clearPoints() {
        if(points.isEmpty()) return;
        removeNodes(points.toArray(Node[]::new));
        points.clear();
    }

    public AbstractGroupLines getLastGroupLines() {
        return lastGroupLines;
    }

    public void setLastGroupLines(AbstractGroupLines lastGroupLines) {
        this.lastGroupLines = lastGroupLines;
    }

    private final int step = 2;
    private final AnchorPane paneForGraphs;
    private final Set<Group> graphics = new LinkedHashSet<>();
    private CoordinateManager coordinateManager;
    private final FunctionManager functionManager;
    private final ControlManager controlManager;
    private final Set<Circle> points = new HashSet<>();
    private AbstractGroupLines lastGroupLines;
    private final DecimalFormat format = new DecimalFormat("#.#");
    private final Mixer mix = new Mixer();
}
