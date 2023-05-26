package com.propcool.cmpm_project.manage;
/**
 * Менеджер контроля, разрешает или запрещает доступ к определённым ресурсам(только на словах)
 * */
public class ControlManager {
    public boolean isMenuOpen() {
        return menuOpen;
    }

    public boolean isSettingsOpen() {
        return settingsOpen;
    }

    public boolean isTablesOpen() {
        return tablesOpen;
    }

    public boolean isLineDragged() {
        return lineDragged;
    }

    public boolean isDirectionsShowed() {
        return directionsShowed;
    }
    public boolean isPortraitShowed() { return portraitShowed; }

    public void setMenuOpen() {
        menuOpen = !menuOpen;
    }

    public void setSettingsOpen() {
        settingsOpen = !settingsOpen;
    }
    public void setTableOpen() { tablesOpen = !tablesOpen; }

    public void setLineDragged(boolean lineDragged) {
        this.lineDragged = lineDragged;
    }
    public void setDirectionsShowed() { directionsShowed = !directionsShowed; }
    public void setPortraitShowed(boolean portraitShowed) { this.portraitShowed = portraitShowed; }
    private boolean menuOpen = false;
    private boolean settingsOpen = false;
    private boolean tablesOpen = false;
    private boolean lineDragged = false;
    private boolean directionsShowed = true;
    private boolean portraitShowed = false;
}
