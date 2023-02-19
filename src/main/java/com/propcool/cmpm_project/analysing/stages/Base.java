package com.propcool.cmpm_project.analysing.stages;
/**
 * Основное состояние: A - переход к переменным, B - к операциям,
 * C - после открывающей скобки, D - после закрывающей, E - после минуса, который идёт в начале выражения
 * */
public enum Base implements State{
    A(0), B(1), C(2), D(3), E(4);
    Base(int index){
        this.index = index;
    }
    @Override
    public int checkSymbol(char c) {
        if (c == 'x') return 0;
        else if(c >= 'a' && c <= 'z' && c != 'y') return 1;
        else if(c == '0') return 2;
        else if(c >= '1' && c <= '9') return 3;
        else if(c == '+' || c == '*' || c == '/' || c == '^') return 4;
        else if(c == '-') return 5;
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
    private static final State[][] matrix = {
            //x a 0 1 + - ( ) \t e f
            {B, Word.A, Number.A, Number.B, End.E, End.E, C, End.E, Space.N, End.E, End.E},
            {End.E, End.E, End.E, End.E, A, A, End.E, D, Space.Y, End.E, End.F},
            {B, Word.A, Number.A, Number.B, End.E, A, C, End.E, Space.N2, End.E, End.E},
            {B, End.E, End.E, End.E, A, A, End.E, D, Space.Y, End.E, End.F},
            {B, Word.A, Number.A, Number.B, End.E, End.E, C, End.E, End.E, End.E, End.E},
    };
    private final int index;
}
