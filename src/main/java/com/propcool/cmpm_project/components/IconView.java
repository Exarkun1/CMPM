package com.propcool.cmpm_project.components;

import com.propcool.cmpm_project.CmpmApplication;
import javafx.scene.image.ImageView;

public class IconView extends ImageView {
    public IconView(String path, int size) {
        super(String.valueOf(CmpmApplication.class.getResource(path)));
        setFitWidth(size);
        setFitHeight(size);
    }
}
