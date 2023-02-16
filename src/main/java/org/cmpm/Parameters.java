package org.cmpm;

import org.cmpm.analysing.build.FunctionBuilder;
import org.cmpm.analysing.build.FunctionFactory;
import org.cmpm.functions.Function;
import org.cmpm.functions.basic.Constant;
import org.cmpm.functions.basic.Exp;
import org.cmpm.functions.basic.Log;
import org.cmpm.functions.basic.Pow;
import org.cmpm.functions.mono.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parameters {
    public static final Map<String, FunctionFactory> keyWords = new HashMap<>();
    public static final List<String> constants = List.of("pi", "e");;

    public static final Map<String, Function> functions = new HashMap<>();
    public static final Map<String, Constant> parameters = new HashMap<>();
    public static final FunctionBuilder builder = new FunctionBuilder();

    static {
        //List.of("sqrt", "exp", "abs", "log", "ln", "sin", "cos", "tan", "ctan", "asin", "acos", "atan");
        keyWords.put("sqrt", (b, e, s) -> new Pow(builder.buildingWithoutSaving(e), 0.5));
        keyWords.put("exp", (b, e, s) -> new Exp(builder.buildingWithoutSaving(e)));
        keyWords.put("abs", (b, e, s) -> new Abs(builder.buildingWithoutSaving(e)));
        keyWords.put("log", (b, e, s) -> new Log(2, builder.buildingWithoutSaving(e)));
        keyWords.put("ln", (b, e, s) -> new Log(builder.buildingWithoutSaving(e)));
        keyWords.put("sin", (b, e, s) -> new Sin(builder.buildingWithoutSaving(e)));
        keyWords.put("cos", (b, e, s) -> new Cos(builder.buildingWithoutSaving(e)));
        keyWords.put("tan", (b, e, s) -> new Tan(builder.buildingWithoutSaving(e)));
        keyWords.put("ctan", (b, e, s) -> new CTan(builder.buildingWithoutSaving(e)));
        keyWords.put("asin", (b, e, s) -> new ASin(builder.buildingWithoutSaving(e)));
        keyWords.put("asos", (b, e, s) -> new ACos(builder.buildingWithoutSaving(e)));
        keyWords.put("atan", (b, e, s) -> new ATan(builder.buildingWithoutSaving(e)));
    }
    private Parameters(){}
}
