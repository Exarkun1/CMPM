package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TablesManager;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class TableBox extends HBox {
    public TableBox(FunctionManager functionManager, DrawManager drawManager, TablesManager tablesManager) {
        tableName.setFont(new Font(25));
        tableName.setEditable(false);

        tableBody.setFont(new Font(25));
        tableBody.setEditable(false);

        IconButton openView = new IconButton("edit.png", 44);
        IconButton closeView = new IconButton("x.png", 44);

        openView.setOnMousePressed(mouseEvent -> {
            tablesManager.load(getTableName(), this);
        });
        closeView.setOnMousePressed(mouseEvent -> {
            tablesManager.remove(this);
            drawManager.makeNewFrame();
        });
        getChildren().addAll(openView, tableName, tableBody, closeView);
    }
    public String getTableName() {
        return tableName.getText();
    }
    public void setTableName(String text) {
        tableName.setText(text);
    }
    public String getTableBody() {
        return tableBody.getText();
    }
    public void setTableBody(String text) {
        tableBody.setText(text);
    }
    private final TextField tableName = new TextField();
    private final TextField tableBody = new TextField();
}
