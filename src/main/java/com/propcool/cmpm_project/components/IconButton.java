package com.propcool.cmpm_project.components;

import javafx.scene.control.Button;

public class IconButton extends Button {
    public IconButton(String path, int size) {
        setPrefSize(size, size);
        IconView icon = new IconView(path, size);
        setGraphic(icon);
    }
}
