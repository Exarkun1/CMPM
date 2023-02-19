package com.propcool.cmpm_project.analysing.stages;

public interface State {
    int checkSymbol(char c);
    State[][] getMatrix();
    int getIndex();
}
