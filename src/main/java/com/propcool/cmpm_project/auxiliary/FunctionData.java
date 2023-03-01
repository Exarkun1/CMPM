package com.propcool.cmpm_project.auxiliary;

import javafx.scene.paint.Color;

public class FunctionData {
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private String expression;
    private Color color;
    private int width;
}
