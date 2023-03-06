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

    public boolean isLineDragged() {
        return lineDragged;
    }

    public void setMenuOpen() {
        menuOpen = !menuOpen;
    }

    public void setSettingsOpen() {
        settingsOpen = !settingsOpen;
    }

    public void setLineDragged() {
        lineDragged = !lineDragged;
    }

    private boolean menuOpen = false;
    private boolean settingsOpen = false;
    private boolean lineDragged = false;
}
