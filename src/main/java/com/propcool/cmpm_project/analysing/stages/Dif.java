package com.propcool.cmpm_project.analysing.stages;

public enum Dif implements State {
    A(0);
    Dif(int index){
        this.index = index;
    }
    @Override
    public int checkSymbol(char c) {
        if(c == '\'') return 0;
        else if(c == '(') return 1;
        else return 2;
    }

    @Override
    public State[][] getMatrix() {
        return matrix;
    }

    @Override
    public int getIndex() {
        return index;
    }
    private static final State[][] matrix = {
            // ' ( e f
            {A, Base.B, End.E, End.E}
    };
    private final int index;
}
