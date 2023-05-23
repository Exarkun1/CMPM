package com.propcool.cmpm_project.analysing.stages;
/**
 * Состояние числа: A - число начинается с 0, B - с любой другой цифры,
 * C - после запятой, D - после одного знака после запятой*/
public enum Number implements State{
    A(0), B(1), C(2), D(3);
    Number(int index){
        this.index = index;
    }
    @Override
    public int checkSymbol(char c) {
        if(c >= '0' && c <= '9') return 0;
        else if(c >= 'a' && c <= 'z') return 1;
        else if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^') return 2;
        else if(c == '(') return 3;
        else if(c == ')') return 4;
        else if(c == '.') return 5;
        else if(c == ' ' || c == '\t' || c == '\n') return 6;
        else return 7;
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
            //1 a + ( ) . \t e f
            {End.E, Word.A, Base.A, Base.B, Base.C, C, Space.Y, End.E, End.F},
            {B, Word.A, Base.A, Base.B, Base.C, C, Space.Y, End.E, End.F},
            {D, End.E, End.E, End.E, End.E, End.E, End.E, End.E, End.E},
            {D, End.E, Base.A, End.E, Base.C, End.E, Space.Y, End.E, End.F}
    };
    private final int index;
}
