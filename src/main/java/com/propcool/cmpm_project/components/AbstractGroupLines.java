package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.util.Point;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.text.DecimalFormat;

public abstract class AbstractGroupLines extends Group {
    public AbstractGroupLines(CoordinateManager coordinateManager, DrawManager drawManager, Function function, Color color) {
        this.coordinateManager = coordinateManager;
        this.drawManager = drawManager;
        this.function = function;
        this.color = color;
    }
    protected void enlargeLines() {
        if(isEnlarges) return;
        for(var elem : getChildren()){
            Line line = (Line)elem;
            line.setStrokeWidth(line.getStrokeWidth()*2);
        }
        isEnlarges = true;
        drawManager.setLastGroupLines(this);
    }
    protected void reduceLines() {
        AbstractGroupLines groupLines = drawManager.getLastGroupLines();
        if(groupLines == null || groupLines == this) return;
        for(var elem : groupLines.getChildren()){
            Line line = (Line)elem;
            line.setStrokeWidth(line.getStrokeWidth()/2);
        }
        groupLines.setEnlarges(false);
    }
    protected String getText(double x, double y, String pattern){
        DecimalFormat format = new DecimalFormat(pattern);
        return "("+ format.format(coordinateManager.getX(x, y)) + "; " + format.format(coordinateManager.getY(x, y))+ ")";
    }
    protected StackPane getTextPane(double x, double y) {
        Text text = new Text(getText(x, y, "#.#####"));
        text.setFont(new Font(18));
        return getPane(text, x, y);
    }
    protected StackPane getPane(Text text, double x, double y) {
        double textWight = text.getBoundsInLocal().getWidth() + 4;
        double textHeight = text.getBoundsInLocal().getHeight();
        Rectangle box = new Rectangle(textWight, textHeight);
        box.setFill(Color.LIGHTGRAY);
        StackPane pane = new StackPane(box, text);
        pane.setLayoutX(x-textWight/2);
        pane.setLayoutY(y-textHeight-10);
        return pane;
    }
    protected void addPoint(Point point, Color color) {
        if(point == null) return;
        double x = coordinateManager.getPixelX(point.getX(), point.getY());
        double y = coordinateManager.getPixelY(point.getX(), point.getY());
        if(!Double.isNaN(x) && !Double.isNaN(y) && coordinateManager.onScreen(x, y)) {
            Circle circle = new Circle(x, y, 5);
            circle.setFill(color);
            StackPane pane = getTextPane(x, y);
            circle.setOnMouseEntered(mouseEvent -> drawManager.addNodes(pane));
            circle.setOnMouseExited(mouseEvent -> drawManager.removeNodes(pane));
            drawManager.addPoint(circle);
        }
    }
    protected Pair<Circle, Pane> addDraggedPoint(Point point, Color color) {
        if(point == null) return null;
        double x = coordinateManager.getPixelX(point.getX(), point.getY());
        double y = coordinateManager.getPixelY(point.getX(), point.getY());
        if(!Double.isNaN(x) && !Double.isNaN(y) && coordinateManager.onScreen(x, y)) {
            Circle circle = new Circle(x, y, 5);
            circle.setFill(color);
            StackPane pane = getTextPane(x, y);
            drawManager.addNodes(circle, pane);
            return new Pair<>(circle, pane);
        }
        return null;
    }
    public void setCircle(double pixelX, double pixelY) {
        drawManager.removeNodes(draggedCircle, draggedPane);
        double x = coordinateManager.getX(pixelX, pixelY);
        double y = function.get(x);
        var pair = addDraggedPoint(new Point(x, y), color);
        if(pair != null) {
            draggedCircle = pair.getKey();
            draggedPane = pair.getValue();
        }
    }
    public void clearCircle() {
        drawManager.removeNodes(draggedCircle, draggedPane);
    }
    public boolean isEnlarges() {
        return isEnlarges;
    }
    public void setEnlarges(boolean enlarges) {
        isEnlarges = enlarges;
    }
    protected final CoordinateManager coordinateManager;
    protected final DrawManager drawManager;
    protected boolean isEnlarges = false;
    protected Function function;
    protected Color color;
    protected Circle draggedCircle;
    protected Pane draggedPane;
}
