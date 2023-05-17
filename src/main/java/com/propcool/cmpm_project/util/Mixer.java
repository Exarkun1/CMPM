package com.propcool.cmpm_project.util;

import javafx.scene.paint.Color;

/**
 * Смешиватель цветов
 * */
public class Mixer {
    public Color mix(Color color, double a) {
        return new Color(
                Math.min(color.getRed()+a, 1),
                Math.min(color.getGreen()+a, 1),
                Math.min(color.getBlue()+a, 1),
                1
        );
    }
    public Color mix(Color c1, Color c2) {
        return new Color(
                Math.min(c1.getRed()+c2.getRed(), 1),
                Math.min(c1.getGreen()+c2.getGreen(), 1),
                Math.min(c1.getBlue()+c2.getBlue(), 1),
                1
        );
    }
}
