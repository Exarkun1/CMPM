package com.propcool.cmpm_project.io.data;

import java.io.Serializable;
/**
 * Данные о функции для её сохранения и загрузки
 * */
public class FunctionData implements Serializable {
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public int getDefaultName() {
        return defaultName;
    }
    public void setDefaultName(int defaultName) {
        this.defaultName = defaultName;
    }
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    private String expression;
    private String color;
    private int width;
    private int defaultName;
    private boolean visible;
}
