package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.io.data.CustomizableTable;
import com.propcool.cmpm_project.util.Point;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.manage.ControlManager;
import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.io.data.CustomizableFunction;
import javafx.scene.paint.Color;

import java.util.Set;

/**
 * Класс группы линий обычной функции
 * */
public class GroupLines extends AbstractGroupLines {
    public GroupLines(Function function, Color color,
                      CoordinateManager coordinateManager, DrawManager drawManager,
                      ControlManager controlManager, FunctionManager functionManager
    ){
        super(coordinateManager, drawManager, function, color);
        this.functionManager = functionManager;
        this.function = function;
        this.color = color;
        // Вызывается все возможные точки
        setOnMousePressed(mouseEvent -> {
            controlManager.setLineDragged(true);
            drawManager.clearPoints();
            reduceLines();
            enlargeLines();
            solve(function);
            intersect(function);
            extremes(function);

            draggedGroup = null;
            setDraggedGroup(mouseEvent.getX(), mouseEvent.getY());
        });
    }
    private void solve(Function function) {
        double x0 = coordinateManager.getX(coordinateManager.getMin(), 0);
        double x1 = coordinateManager.getX(coordinateManager.getMax(), 0);
        Set<Point> points = functionManager.searchRoots(function, x0, x1);
        addPoints(points, new Color(0.7, 0.7, 1, 1));
    }
    private void intersect(Function function) {
        for(var entry : functionManager.getFunctions().entrySet()) {
            String name = entry.getKey();
            CustomizableFunction cf = entry.getValue();
            if(function != cf.getFunction() && cf.isVisible() &&
                    !name.matches("\\d+imp") && !name.matches("\\d+dif") && !name.matches("\\d+bad")
            ) {
                Function other = cf.getFunction();
                double x0 = coordinateManager.getX(coordinateManager.getMin(), 0);
                double x1 = coordinateManager.getX(coordinateManager.getMax(), 0);
                Set<Point> points = functionManager.searchIntersects(function, other, x0, x1);
                addPoints(points, new Color(0.7, 1, 0.7, 1));
            }
        }
        for(var entry : functionManager.getTables().entrySet()) {
            CustomizableTable ct = entry.getValue();
            if(ct.isVisible()) {
                Function other = ct.getApproximate();
                double x0 = coordinateManager.getX(coordinateManager.getMin(), 0);
                double x1 = coordinateManager.getX(coordinateManager.getMax(), 0);
                Set<Point> points = functionManager.searchIntersects(function, other, x0, x1);
                addPoints(points, new Color(0.7, 1, 0.7, 1));
            }
        }
    }
    private void extremes(Function function) {
        double x0 = coordinateManager.getX(coordinateManager.getMin(), 0);
        double x1 = coordinateManager.getX(coordinateManager.getMax(), 0);
        Set<Point> points = functionManager.searchExtremes(function, x0, x1);
        addPoints(points, new Color(1, 0.7, 0.7, 1));
    }
    private void addPoints(Set<Point> points, Color color) {
        for(var point : points) {
            addPoint(point, color);
        }
    }
    private final FunctionManager functionManager;
}
