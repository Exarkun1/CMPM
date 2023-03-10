package org.cmpm.analysing.build;

import org.cmpm.functions.Function;
/**
 * Адаптер для объединения FunctionFactory и Contain
 * */
public class ContainFactoryAdapter implements FunctionFactory, Contain{
    public ContainFactoryAdapter(FunctionFactory factory, Contain contain) {
        this.factory = factory;
        this.contain = contain;
    }

    @Override
    public boolean contain(char symbol) {
        return contain.contain(symbol);
    }

    @Override
    public Function createFunction(String begin, String end, char symbol) {
        return factory.createFunction(begin, end, symbol);
    }
    private final FunctionFactory factory;
    private final Contain contain;
}
