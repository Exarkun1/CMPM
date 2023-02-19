package com.propcool.cmpm_project.analysing.stages;
/** Состояние параметра(параметр начинаться с буквы, и может заканчиваться цифрой):
 * A - буква, B - цифра
 * */
public enum Word implements State {
    A(0), B(1);
    Word(int index){
        this.index = index;
    }
    @Override
    public int checkSymbol(char c) {
        if(c >= 'a' && c <= 'z') return 0;
        else if(c >= '0' && c <= '9') return 1;
        else if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^') return 2;
        else if(c == '(') return 3;
        else if(c == ')') return 4;
        else if(c == ' ' || c == '\t' || c == '\n') return 5;
        else return 6;
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
            //a + ( ) \t e f
            {A, B, Base.A, Base.C, Base.D, Space.Y, End.E, End.F},
            {End.E, B, Base.A, Base.C, Base.D, Space.Y, End.E, End.F}
    };
    private final int index;
}
