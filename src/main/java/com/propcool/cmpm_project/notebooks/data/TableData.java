package com.propcool.cmpm_project.notebooks.data;

import com.propcool.cmpm_project.util.Point;

import java.io.Serializable;
import java.util.Set;

public class TableData implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Point> getRows() {
        return rows;
    }

    public void setRows(Set<Point> rows) {
        this.rows = rows;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }
    private String name;
    private Set<Point> rows;
    private String color;
    private int width;
    private boolean visible;
    private int k;
}
