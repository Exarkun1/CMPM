package com.propcool.cmpm_project.analysing.stages;
/**
 * Основное состояние: A - переход к переменным, B - к операциям,
 * C - после открывающей скобки, D - после закрывающей, E - после минуса, который идёт в начале выражения
 * */
public enum Base implements State{
    A(0), B(1), C(2), E(3);
    Base(int index){
        this.index = index;
    }
    @Override
    public int checkSymbol(char c) {
        if(c >= 'a' && c <= 'z') return 0;
        else if(c == '0') return 1;
        else if(c >= '1' && c <= '9') return 2;
        else if(c == '+' || c == '*' || c == '/' || c == '^') return 3;
        else if(c == '-') return 4;
        else if(c == '(') return 5;
        else if(c == ')') return 6;
        else if(c == ' ' || c == '\t' || c == '\n') return 7;
        else return 8;
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
            //a 0 1 + - ( ) \t e f
            {Word.A, Number.A, Number.B, End.E, End.E, B, End.E, Space.N, End.E, End.E},
            {Word.A, Number.A, Number.B, End.E, A, B, End.E, Space.N2, End.E, End.E},
            {End.E, End.E, End.E, A, A, End.E, C, Space.Y, End.E, End.F},
            {Word.A, Number.A, Number.B, End.E, End.E, B, End.E, End.E, End.E, End.E},
    };
    private final int index;
}
