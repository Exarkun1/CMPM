package com.propcool.cmpm_project.analysing.build;

import com.propcool.cmpm_project.functions.Function;

import java.util.List;

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
    public Function createFunction(String begin, String end, char symbol, List<String> params) {
        return factory.createFunction(begin, end, symbol, params);
    }
    private final FunctionFactory factory;
    private final Contain contain;
}
