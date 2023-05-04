package com.propcool.cmpm_project.manage;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
/**
 * Менеджер открытия вкладок
 * */
public class OpenManager {
    public OpenManager(BorderPane outgoingPanel, BorderPane outgoingPanelSettings, ControlManager controlManager) {
        this.outgoingPanel = outgoingPanel;
        this.outgoingPanelSettings = outgoingPanelSettings;
        this.controlManager = controlManager;
    }
    /**
     * Открытие панели с текстовыми полями
     * */
    public void openTextFields(){
        if(controlManager.isSettingsOpen()) openSettings();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), outgoingPanel);
        translateTransition.setToX(controlManager.isMenuOpen() ? -400 : 400);
        translateTransition.play();
        controlManager.setMenuOpen();
    }
    /**
     * Открытие панели настроек
     * */
    public void openSettings(){
        if(controlManager.isMenuOpen()) openTextFields();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), outgoingPanelSettings);
        translateTransition.setToX(controlManager.isSettingsOpen() ? -400 : 400);
        translateTransition.play();
        controlManager.setSettingsOpen();
    }
    private final BorderPane outgoingPanel;
    private final BorderPane outgoingPanelSettings;
    private final ControlManager controlManager;
}
