package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.controllers.TableController;
import com.propcool.cmpm_project.util.Point;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class RowBox extends HBox {
    public RowBox(String x, String y, TableController controller) {
        xTextField = new TextField(x);
        xTextField.setPromptText("x");
        xTextField.setPrefWidth(200);
        yTextField = new TextField(y);
        yTextField.setPromptText("y");
        yTextField.setPrefWidth(200);

        IconButton closeView = new IconButton("x.png", 14);

        closeView.setOnMousePressed(mouseEvent -> {
            controller.removePoint(this);
        });

        getChildren().addAll(xTextField, yTextField, closeView);
    }
    public RowBox(Point p, TableController controller) {
        this(String.valueOf(p.getX()), String.valueOf(p.getY()), controller);
    }
    public RowBox(TableController controller) {
        this("", "", controller);
    }
    public double getX() {
        try {
            return Double.parseDouble(xTextField.getText());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Не верные данные в полях таблицы", e);
        }
    }
    public double getY() {
        try {
            return Double.parseDouble(yTextField.getText());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Не верные данные в полях таблицы", e);
        }
    }
    public Point getRow() {
        return new Point(getX(), getY());
    }
    private final TextField xTextField;
    private final TextField yTextField;
}
