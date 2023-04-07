package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.mono.Mono;

public class FunctionDecoratorY extends FunctionDecorator {
    public FunctionDecoratorY(Function f) {
        super(f);
    }
    public FunctionDecoratorY() { super(new VariableY()); }
}
