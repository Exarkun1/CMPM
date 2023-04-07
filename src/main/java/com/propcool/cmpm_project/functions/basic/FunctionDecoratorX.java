package com.propcool.cmpm_project.functions.basic;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.mono.Mono;
/**
 * Функция для замены переменной, если один раз была вызвана для замены, больше обращений к ней не будет
 * */
public class FunctionDecoratorX extends FunctionDecorator {
    public FunctionDecoratorX(Function f) {
        super(f);
    }
    public FunctionDecoratorX() {super(new VariableX());}
}
