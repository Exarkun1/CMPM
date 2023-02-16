package org.cmpm;

import org.cmpm.analysing.Analyser;
import org.cmpm.analysing.build.FunctionBuilder;
import org.cmpm.functions.Function;

public class Main {
    public static void main(String[] args) {
        Analyser analyser = new Analyser();
        System.out.println(analyser.processing("f(x) = -ln( -22*sin(x) -(  0,1/1,1*x ))x * ln(x^2) + f1"));

        Function f = Parameters.builder.building("f(x) = (-ln( -22*sin(x) -(  0,1/1,1*x ))x * ln(x^2) + exp(x+1)) + e");
        Function f1 = Parameters.builder.building("f1(x) = -ln( -22*sin(x) -(  0,1/1,1*x ))x * ln(x^2) + exp(x+1)");
        System.out.println(f.get(-2));
        System.out.println(Parameters.functions.keySet());
    }
}