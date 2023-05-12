package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.VariableX;
import com.propcool.cmpm_project.functions.basic.VariableY;
import com.propcool.cmpm_project.functions.combination.Difference;
import com.propcool.cmpm_project.manage.ControlManager;
import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import com.propcool.cmpm_project.util.Point;
import javafx.application.Platform;
import javafx.scene.paint.Color;

public class ImpGroupLines extends AbstractGroupLines {
    public ImpGroupLines(Function function, Color color,
                         CoordinateManager coordinateManager, DrawManager drawManager,
                         ControlManager controlManager, FunctionManager functionManager
    ){
        super(coordinateManager, drawManager, color);
        this.functionManager = functionManager;

        setOnMousePressed(mouseEvent -> {
            Platform.runLater(() -> {
                try {
                    controlManager.setLineDragged();
                    newPosition(mouseEvent.getX(), mouseEvent.getY());

                    drawManager.clearPoints();
                    reduceLines();
                    enlargeLines();
                    intersect(function, mouseEvent.getX(), mouseEvent.getY());
                    solveY(function, mouseEvent.getX(), mouseEvent.getY());
                    solveX(function, mouseEvent.getX(), mouseEvent.getY());

                    drawManager.addNodes(circle, paneForText);
                } catch (IllegalArgumentException ignored) {}
            });
        });
        setOnMouseReleased(mouseEvent -> {
            Platform.runLater(() -> {
                drawManager.removeNodes(circle, paneForText);
                controlManager.setLineDragged();
            });
        });
    }
    private void intersect(Function function, double pixelX, double pixelY) {
        double x = coordinateManager.getX(pixelX, pixelY);
        double y = coordinateManager.getY(pixelX, pixelY);
        for(var entry : functionManager.getFunctions().entrySet()) {
            String name = entry.getKey();
            CustomizableFunction cf = entry.getValue();
            if(function != cf.getFunction() && !name.matches("\\d+dif") && !name.matches("\\d+bad")) {
                Point point;
                if(!name.matches("\\d+imp")) {
                    point = functionManager.searchIntersectsXY(function, new Difference(cf.getFunction(), new VariableY()), x, y);
                } else {
                    point = functionManager.searchIntersectsXY(function, cf.getFunction(), x, y);
                }
                addPoint(point);
            }
        }
    }
    private void solveY(Function function, double pixelX, double pixelY) {
        double x = coordinateManager.getX(pixelX, pixelY);
        double y = coordinateManager.getY(pixelX, pixelY);
        Point point = functionManager.searchIntersectsXY(function, new VariableY(), x, y);
        addPoint(point);
    }
    private void solveX(Function function, double pixelX, double pixelY) {
        double x = coordinateManager.getX(pixelX, pixelY);
        double y = coordinateManager.getY(pixelX, pixelY);
        Point point = functionManager.searchIntersectsXY(function, new VariableX(), x, y);
        addPoint(point);
    }
    private final FunctionManager functionManager;
}