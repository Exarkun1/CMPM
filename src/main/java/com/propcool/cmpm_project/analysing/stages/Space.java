package com.propcool.cmpm_project.analysing.stages;
/**
 * Состояние пробела: Y - функция может закончиться этим пробелом, N - не может
 * */
public enum Space implements State{
    Y(0), N(1), N2(2);
    Space(int index){
        this.index = index;
    }
    @Override
    public int checkSymbol(char c) {
        if(c == '0') return 0;
        else if(c >= '1' && c <= '9') return 1;
        else if(c >= 'a' && c <= 'z' && c != 'y' && c != 'x') return 2;
        else if(c == 'x') return 3;
        else if(c == '+' || c == '*' || c == '/' || c == '^') return 4;
        else if(c == '-' ) return 5;
        else if(c == '(') return 6;
        else if(c == ')') return 7;
        else if(c == ' ' || c == '\t' || c == '\n') return 8;
        else return 9;
    }

    @Override
    public State[][] getMatrix() {
        return matrix;
    }

    @Override
    public int getIndex() {
        return index;
    }
    // 0
    private static final State[][] matrix = {
            // 0 1 a x + - ( ) \t e f
            {End.E, End.E, End.E, End.E, Base.A, Base.A, End.E, Base.D, Y, End.E, End.F},
            {Number.A, Number.B, Word.A, Base.B, End.E, End.E, Base.C, End.E, N, End.E, End.E},
            {Number.A, Number.B, Word.A, Base.B, End.E, Base.E, Base.C, End.E, N2, End.E, End.E}
    };
    private int index;
}
