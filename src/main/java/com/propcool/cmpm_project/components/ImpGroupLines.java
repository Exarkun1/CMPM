package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.VariableX;
import com.propcool.cmpm_project.functions.basic.VariableY;
import com.propcool.cmpm_project.functions.combination.Difference;
import com.propcool.cmpm_project.io.data.CustomizableTable;
import com.propcool.cmpm_project.manage.ControlManager;
import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.io.data.CustomizableFunction;
import com.propcool.cmpm_project.util.Point;
import javafx.scene.paint.Color;
/**
 * Класс группы линий неявной функции
 * */
public class ImpGroupLines extends AbstractGroupLines {
    public ImpGroupLines(Function function, Color color,
                         CoordinateManager coordinateManager, DrawManager drawManager,
                         ControlManager controlManager, FunctionManager functionManager
    ){
        super(coordinateManager, drawManager, function, color);
        this.functionManager = functionManager;
        // Появление точек
        setOnMousePressed(mouseEvent -> {
            try {
                drawManager.clearPoints();
                reduceLines();
                enlargeLines();
                intersect(function, mouseEvent.getX(), mouseEvent.getY());
                solveY(function, mouseEvent.getX(), mouseEvent.getY());
                solveX(function, mouseEvent.getX(), mouseEvent.getY());
                extreme(function, mouseEvent.getX(), mouseEvent.getY());
                addPoint(coordinateManager.getXY(mouseEvent.getX(), mouseEvent.getY()), color);
            } catch (IllegalArgumentException ignored) {}
        });
    }
    private void intersect(Function function, double pixelX, double pixelY) {
        double x = coordinateManager.getX(pixelX, pixelY);
        double y = coordinateManager.getY(pixelX, pixelY);
        for(var entry : functionManager.getFunctions().entrySet()) {
            String name = entry.getKey();
            CustomizableFunction cf = entry.getValue();
            if(function != cf.getFunction() && cf.isVisible()
                    && !name.matches("\\d+dif") && !name.matches("\\d+bad")
            ) {
                Point point;
                if(!name.matches("\\d+imp")) {
                    point = functionManager.searchIntersectsXY(function, new Difference(cf.getFunction(), new VariableY()), x, y);
                } else {
                    point = functionManager.searchIntersectsXY(function, cf.getFunction(), x, y);
                }
                addPoint(point, new Color(0.7, 1, 0.7, 1));
            }
        }
        for(var entry : functionManager.getTables().entrySet()) {
            CustomizableTable ct = entry.getValue();
            if(ct.isVisible()) {
                Point point = functionManager.searchIntersectsXY(function, new Difference(ct.getApproximate(), new VariableY()), x, y);
                addPoint(point, new Color(0.7, 1, 0.7, 1));
            }
        }
    }
    private void solveY(Function function, double pixelX, double pixelY) {
        double x = coordinateManager.getX(pixelX, pixelY);
        double y = coordinateManager.getY(pixelX, pixelY);
        Point point = functionManager.searchIntersectsXY(function, new VariableY(), x, y);
        addPoint(point, new Color(0.7, 0.7, 1, 1));
    }
    private void solveX(Function function, double pixelX, double pixelY) {
        double x = coordinateManager.getX(pixelX, pixelY);
        double y = coordinateManager.getY(pixelX, pixelY);
        Point point = functionManager.searchIntersectsXY(function, new VariableX(), x, y);
        addPoint(point, new Color(0.7, 0.7, 1, 1));
    }
    private void extreme(Function function, double pixelX, double pixelY) {
        double x = coordinateManager.getX(pixelX, pixelY);
        double y = coordinateManager.getY(pixelX, pixelY);
        Point point = functionManager.searchExtremeXY(function, x, y);
        addPoint(point, new Color(1, 0.7, 0.7, 1));
    }
    private final FunctionManager functionManager;
}
