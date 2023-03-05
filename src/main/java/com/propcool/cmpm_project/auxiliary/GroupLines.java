package com.propcool.cmpm_project.auxiliary;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class GroupLines extends Group {
    public GroupLines(){
        setOnMouseClicked(mouseEvent -> System.out.println("hello"));
        setOnMouseEntered(mouseEvent -> {
            for(var elem : getChildren()){
                Line line = (Line)elem;
                line.setStrokeWidth(line.getStrokeWidth()*2);
            }
        });
        setOnMouseExited(mouseEvent -> {
            for(var elem : getChildren()){
                Line line = (Line)elem;
                line.setStrokeWidth(line.getStrokeWidth()/2);
            }
        });
    }
}
