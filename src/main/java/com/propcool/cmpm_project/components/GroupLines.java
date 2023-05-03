package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.auxiliary.Point;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.manage.ControlManager;
import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.Set;

/**
 * Класс для группы линий
 * */
public class GroupLines extends Group {
    public GroupLines(Function function, CoordinateManager coordinateManager, DrawManager drawManager, ControlManager controlManager, FunctionManager functionManager){
        this.coordinateManager = coordinateManager;
        this.drawManager = drawManager;
        this.functionManager = functionManager;
        text.setFont(new Font(16));
        circle.setFill(Color.GRAY);
        circle.setStroke(Color.BLACK);
        // Вызывается кружок с координатами при нажатии
        setOnMousePressed(mouseEvent -> {
            Platform.runLater(() -> {
                try {
                    controlManager.setLineDragged();
                    Point point = coordinateManager.getCircleCoordinate(function, mouseEvent.getX(), mouseEvent.getY());
                    double x = point.getX();
                    double y = point.getY();
                    newPosition(x, y);
                    drawManager.addNodes(circle, text);

                    reduceLines();
                    enlargeLines();
                    solve(function);
                    intersect(function);
                } catch (IllegalArgumentException ignored) {}
            });
        });
        // Смещение кружка
        setOnMouseDragged(mouseEvent -> {
            Platform.runLater(() -> {
                Point point = coordinateManager.getCircleCoordinate(function, mouseEvent.getX(), mouseEvent.getY());
                double x = point.getX();
                double y = point.getY();
                if (coordinateManager.onScreen(x, y)) {
                    visibleCircle();
                    newPosition(x, y);
                } else {
                    noVisibleCircle();
                }
            });
        });
        // Удаление кружка
        setOnMouseReleased(mouseEvent -> {
            Platform.runLater(() -> {
                drawManager.removeNodes(circle, text);
                controlManager.setLineDragged();
            });
        });
    }
    private String getText(double x, double y, String pattern){
        DecimalFormat format = new DecimalFormat(pattern);
        return "("+ format.format(coordinateManager.getX(x, y)) + ", " + format.format(coordinateManager.getY(x, y))+ ")";
    }
    private void enlargeLines() {
        if(isEnlarges) return;
        for(var elem : getChildren()){
            Line line = (Line)elem;
            line.setStrokeWidth(line.getStrokeWidth()*2);
        }
        isEnlarges = true;
        drawManager.setLastGroupLines(this);
    }
    private void reduceLines() {
        GroupLines groupLines = drawManager.getLastGroupLines();
        if(groupLines == null || groupLines == this) return;
        for(var elem : groupLines.getChildren()){
            Line line = (Line)elem;
            line.setStrokeWidth(line.getStrokeWidth()/2);
        }
    }
    private void visibleCircle(){
        circle.setVisible(true);
        text.setVisible(true);
    }
    private void noVisibleCircle(){
        circle.setVisible(false);
        text.setVisible(false);
    }
    private void newPosition(double x, double y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
        text.setText(getText(x, y, "#.###"));
        text.setX(x + 10);
        text.setY(y);
    }
    private void solve(Function function) {
        double x0 = coordinateManager.getX(coordinateManager.getMin(), 0);
        double x1 = coordinateManager.getX(coordinateManager.getMax(), 0);
        Set<Point> points = functionManager.searchRoots(function, x0, x1);
        addPoints(points);
    }
    private void intersect(Function function) {
        for(var entry : functionManager.getFunctions().entrySet()) {
            String name = entry.getKey();
            CustomizableFunction cf = entry.getValue();
            if(function != cf.getFunction() && !name.matches("\\d+imp") && !name.matches("\\d+dif") && !name.matches("\\d+bad")) {
                Function other = cf.getFunction();
                double x0 = coordinateManager.getX(coordinateManager.getMin(), 0);
                double x1 = coordinateManager.getX(coordinateManager.getMax(), 0);
                Set<Point> points = functionManager.searchIntersects(function, other, x0, x1);
                addPoints(points);
            }
        }
    }
    private void addPoints(Set<Point> points) {
        for(var point : points) {
            double x = coordinateManager.getPixelX(point.getX(), point.getY());
            double y = coordinateManager.getPixelY(point.getX(), point.getY());
            if(!Double.isNaN(x) && !Double.isNaN(y)) {
                Circle circle = new Circle(x, y, 5);
                circle.setFill(Color.LIGHTGRAY);
                StackPane pane = getTextPane(x, y, 18);
                circle.setOnMouseEntered(mouseEvent -> Platform.runLater(() -> drawManager.addNodes(pane)));
                circle.setOnMouseExited(mouseEvent -> Platform.runLater(() -> drawManager.removeNodes(pane)));
                drawManager.addPoint(circle);
            }
        }
    }

    private StackPane getTextPane(double x, double y, double f) {
        Text text = new Text(getText(x, y, "#.#####"));
        text.setFont(new Font(f));
        double textWight = text.getBoundsInLocal().getWidth() + 4;
        double textHeight = text.getBoundsInLocal().getHeight();
        Rectangle box = new Rectangle(textWight, textHeight);
        box.setFill(Color.LIGHTGRAY);
        StackPane pane = new StackPane(box, text);
        pane.setLayoutX(x-textWight/2);
        pane.setLayoutY(y-textHeight-10);
        return pane;
    }
    private final Circle circle = new Circle(5);
    private final Text text = new Text();
    private final CoordinateManager coordinateManager;
    private final DrawManager drawManager;
    private final FunctionManager functionManager;
    private boolean isEnlarges = false;
}
