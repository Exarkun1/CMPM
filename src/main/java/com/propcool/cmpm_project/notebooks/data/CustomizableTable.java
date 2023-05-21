package com.propcool.cmpm_project.notebooks.data;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.util.Point;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Set;

public class CustomizableTable implements Serializable {
    public CustomizableTable(Function approximate) {
        this.approximate = approximate;
    }

    public Function getApproximate() {
        return approximate;
    }
    public String getName() {
        return tableData.getName();
    }
    public void setName(String name) {
        tableData.setName(name);
    }

    public Set<Point> getRows() {
        return tableData.getRows();
    }
    public void setRows(Set<Point> rows) {
        tableData.setRows(rows);
    }
    public Color getColor() {
        return Color.valueOf(tableData.getColor());
    }
    public void setColor(String color) {
        tableData.setColor(color);
    }
    public int getWidth() { return tableData.getWidth(); }

    public void setWidth(int width) { tableData.setWidth(width);}
    public boolean isVisible(){
        return tableData.isVisible();
    }
    public void setVisible(boolean visible){
        tableData.setVisible(visible);
    }
    public int getK() {
        return tableData.getK();
    }
    public void setK(int k) {
        tableData.setK(k);
    }
    public TableData getData() { return tableData;}
    private final Function approximate;
    private final TableData tableData = new TableData();
}
