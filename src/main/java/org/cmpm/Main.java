package org.cmpm;

import org.cmpm.analysing.Analyser;
import org.cmpm.analysing.FunctionBuilder;
import org.cmpm.functions.Function;

public class Main {
    public static void main(String[] args) {
        Analyser analyser = new Analyser();
        System.out.println(analyser.processing("f(x) = -ln( -22*sin(x) -(  0,1/1,1*x ))x * ln(x^2) + f1"));

        Function f = new FunctionBuilder().building("y = -ln( -22*sin(x) -(  0,1/1,1*x ))x * ln(x^2) + exp(x+1)");
        System.out.println(f.get(-2));
        System.out.println(1);
    }
}