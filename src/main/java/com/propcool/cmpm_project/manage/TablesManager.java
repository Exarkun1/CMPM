package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.components.TableBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.LinkedHashSet;

public class TablesManager {
    public TablesManager(VBox paneForTable, Button creatTableButton, FunctionManager functionManager, DrawManager drawManager){
        this.paneForTable = paneForTable;
        this.createTableButton = creatTableButton;
        this.functionManager = functionManager;
        this.drawManager = drawManager;
    }
    public void addTable(TableBox box) {
        paneForTable.getChildren().remove(createTableButton);

        tableBoxes.add(box);
        paneForTable.getChildren().add(box);

        paneForTable.getChildren().add(createTableButton);
    }
    private final VBox paneForTable;
    private final Button createTableButton;
    private final LinkedHashSet<TableBox> tableBoxes = new LinkedHashSet<>();
    private final DrawManager drawManager;
    private final FunctionManager functionManager;
}
