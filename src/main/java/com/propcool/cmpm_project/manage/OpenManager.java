package com.propcool.cmpm_project.manage;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
/**
 * Менеджер открытия вкладок
 * */
public class OpenManager {
    public OpenManager(BorderPane outgoingPanel, BorderPane outgoingPanelSettings, BorderPane outgoingTablePanel, ControlManager controlManager) {
        this.outgoingPanel = outgoingPanel;
        this.outgoingPanelSettings = outgoingPanelSettings;
        this.outgoingTablePanel = outgoingTablePanel;
        this.controlManager = controlManager;
    }
    /**
     * Открытие панели с текстовыми полями
     * */
    public void openTextFields(){
        if(controlManager.isSettingsOpen()) openSettings();
        if(controlManager.isTablesOpen()) openTables();
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
        if(controlManager.isTablesOpen()) openTables();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), outgoingPanelSettings);
        translateTransition.setToX(controlManager.isSettingsOpen() ? -400 : 400);
        translateTransition.play();
        controlManager.setSettingsOpen();
    }
    /**
     * Открытие панели таблиц
     * */
    public void openTables() {
        if(controlManager.isMenuOpen()) openTextFields();
        if(controlManager.isSettingsOpen()) openSettings();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), outgoingTablePanel);
        translateTransition.setToX(controlManager.isTablesOpen() ? -400 : 400);
        translateTransition.play();
        controlManager.setTableOpen();
    }
    private final BorderPane outgoingPanel;
    private final BorderPane outgoingPanelSettings;
    private final BorderPane outgoingTablePanel;
    private final ControlManager controlManager;
}
