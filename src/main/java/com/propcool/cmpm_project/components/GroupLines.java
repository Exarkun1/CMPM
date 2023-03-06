package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.manage.ControlManager;
import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.manage.DrawManager;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
/**
 * Класс для группы линий
 * */
public class GroupLines extends Group {
    public GroupLines(Function function, CoordinateManager coordinateManager, DrawManager drawManager, ControlManager controlManager){
        this.coordinateManager = coordinateManager;
        // Вызывается кружок с координатами при нажатии
        setOnMousePressed(mouseEvent -> {
            enlargeLines();
            controlManager.setLineDragged();
            double x = mouseEvent.getX();
            double y = coordinateManager.getPixelY(function, x);
            circle = new Circle(mouseEvent.getX(), y, 5);
            text = new Text(x+10,y, getText(x, y));
            text.setFont(new Font(16));
            circle.setFill(Color.GRAY);
            circle.setStroke(Color.BLACK);
            drawManager.addNodes(circle, text);
        });
        // Смещение кружка
        setOnMouseDragged(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = coordinateManager.getPixelY(function, x);
            if(coordinateManager.onScreen(x, y)) {
                visibleCircle();
                circle.setCenterX(x);
                circle.setCenterY(y);
                text.setText(getText(x, y));
                text.setX(x + 10);
                text.setY(y);
            } else {
                noVisibleCircle();
            }
        });
        // Удаление кружка
        setOnMouseReleased(mouseEvent -> {
            drawManager.removeNodes(circle, text);
            controlManager.setLineDragged();
            reduceLines();

        });
    }
    private String getText(double x, double y){
        return "("+ format.format(coordinateManager.getX(x)) + ", " + format.format(coordinateManager.getY(y))+ ")";
    }
    private void enlargeLines(){
        for(var elem : getChildren()){
            Line line = (Line)elem;
            line.setStrokeWidth(line.getStrokeWidth()*5/3);
        }
    }
    private void reduceLines(){
        for(var elem : getChildren()){
            Line line = (Line)elem;
            line.setStrokeWidth(line.getStrokeWidth()*3/5);
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
    private Circle circle;
    private Text text;
    private final DecimalFormat format = new DecimalFormat("#.###");
    private final CoordinateManager coordinateManager;
}
