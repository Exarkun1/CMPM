package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.manage.DrawManager;
import com.propcool.cmpm_project.manage.FunctionManager;
import com.propcool.cmpm_project.manage.TablesManager;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class TableBox extends HBox {
    public TableBox(FunctionManager functionManager, DrawManager drawManager, TablesManager tablesManager) {
        setTableName("First");
        Button openButton = new Button("Открыть");
        Button deleteButton = new Button("Удалить");

        openButton.setOnAction(actionEvent -> {
            tablesManager.load(getTableName());
        });
        deleteButton.setOnAction(actionEvent -> {
            tablesManager.remove(this);
        });
        getChildren().addAll(tableName, openButton, deleteButton);
    }
    public String getTableName() {
        return tableName.getText();
    }
    public void setTableName(String text) {
        tableName.setText(text);
    }
    private final Text tableName = new Text();
}
