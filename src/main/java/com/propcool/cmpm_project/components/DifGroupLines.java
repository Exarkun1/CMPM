package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.manage.ControlManager;
import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.util.Point;
import javafx.scene.paint.Color;
/**
 * Класс группы линий дифференциального уравнения
 * */
public class DifGroupLines extends AbstractGroupLines{
    public DifGroupLines(Function function, Color color, CoordinateManager coordinateManager, DrawManager drawManager, ControlManager controlManager) {
        super(coordinateManager, drawManager, function, color);
        // Увеличение линий
        setOnMousePressed(mouseEvent -> {
            try {
                drawManager.clearPoints();
                reduceLines();
                enlargeLines();
                addPoint(coordinateManager.getXY(mouseEvent.getX(), mouseEvent.getY()), color);
            } catch (IllegalArgumentException ignored) {}
        });
    }
}
