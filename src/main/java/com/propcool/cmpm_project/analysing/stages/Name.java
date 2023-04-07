package com.propcool.cmpm_project.analysing.stages;
/**
 * Состояние имени функции(не буду прописывать каждое), так как здесь всё линейно)
 * */
public enum Name implements State {
    A(0), B(1), C(2), D(3), E(4), F(5), G(6), I(7), K(8);
    Name(int index){
        this.index = index;
    }
    @Override
    public int checkSymbol(char c){
        if(c >= 'a' && c <= 'z' && c != 'y' && c != 'x') return 0;
        else if(c == 'y') return 1;
        else if(c == 'x') return 2;
        else if(c == '0') return 3;
        else if(c >= '1' && c <= '9') return 4;
        else if(c == '-') return 5;
        else if(c == '(') return 6;
        else if(c == ')') return 7;
        else if(c == '=') return 8;
        else if(c == ' ' || c == '\t' || c == '\n') return 9;
        else return 10;
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
            //a y x 0 1 - ( ) = \t e f
            {C, B, End.E, B, End.E, End.E, End.E, End.E, End.E, G, End.E, End.E},
            {C, End.E, End.E, End.E, End.E, End.E, E, End.E, D, I, End.E, End.E},
            {C, C, End.E, K, K, End.E, E, End.E, End.E, End.E, End.E, End.E},
            {Word.A, Word.A, Word.A, Number.A, Number.B, Base.A, Base.B, End.E, End.E, Space.N2, End.E, End.E},
            {End.E, End.E, F, End.E, End.E, End.E, End.E, End.E, End.E, End.E, End.E, End.E},
            {End.E, End.E, End.E, End.E, End.E, End.E, End.E, B, End.E, End.E, End.E, End.E},
            {End.E, End.E, End.E, End.E, End.E, End.E, End.E, End.E, End.E, G, End.E, End.E},
            {End.E, End.E, End.E, End.E, End.E, End.E, End.E, End.E, D, I, End.E, End.E},
            {End.E, End.E, End.E, K, K, End.E, E, End.E, End.E, End.E, End.E, End.E},
    };
    private final int index;

}
