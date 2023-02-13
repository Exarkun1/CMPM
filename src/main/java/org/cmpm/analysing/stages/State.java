package org.cmpm.analysing.stages;

public interface State {
    int checkSymbol(char c);
    State[][] getMatrix();
    int getIndex();
}
