package com.propcool.cmpm_project.notebooks.data;

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
    private String expression;
    private String color;
    private int width;
    private int defaultName;
}
