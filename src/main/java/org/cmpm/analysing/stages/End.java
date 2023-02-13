package org.cmpm.analysing.stages;

public enum End implements State{
    E(0), F(1);
    End(int index){
        this.index = index;
    }
    @Override
    public int checkSymbol(char c) {
        return 0;
    }

    @Override
    public State[][] getMatrix() {
        return new State[0][];
    }

    @Override
    public int getIndex() {
        return index;
    }
    private int index;
}
