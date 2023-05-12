package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.ControlManager;
import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.DrawManager;
import javafx.application.Platform;
import javafx.scene.paint.Color;

public class DifGroupLines extends AbstractGroupLines{
    public DifGroupLines(Color color, CoordinateManager coordinateManager, DrawManager drawManager, ControlManager controlManager) {
        super(coordinateManager, drawManager, color);
        setOnMousePressed(mouseEvent -> {
            Platform.runLater(() -> {
                try {
                    controlManager.setLineDragged();
                    newPosition(mouseEvent.getX(), mouseEvent.getY());

                    drawManager.clearPoints();
                    reduceLines();
                    enlargeLines();

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
}
