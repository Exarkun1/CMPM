package org.cmpm.analysing.build;

import org.cmpm.functions.Function;
/**
 * Интерфейс для создания функции по её началу, концу и знаку между ними
 * */
public interface FunctionFactory {
    Function createFunction(String begin, String end, char symbol);
}
