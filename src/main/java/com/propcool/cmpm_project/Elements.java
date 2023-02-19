package com.propcool.cmpm_project;

import com.propcool.cmpm_project.analysing.build.FunctionBuilder;
import com.propcool.cmpm_project.analysing.build.FunctionFactory;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.Constant;
import com.propcool.cmpm_project.functions.basic.Exp;
import com.propcool.cmpm_project.functions.basic.Log;
import com.propcool.cmpm_project.functions.basic.Pow;
import com.propcool.cmpm_project.functions.mono.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elements {
    public static final Map<String, FunctionFactory> keyWords = new HashMap<>();
    public static final List<String> constants = List.of("pi", "e");

    public static final Map<String, Function> functions = new HashMap<>();
    public static final Map<String, Constant> parameters = new HashMap<>();
    public static final FunctionBuilder builder = new FunctionBuilder();

    static {
        //List.of("sqrt", "exp", "abs", "log", "ln", "sin", "cos", "tan", "ctan", "asin", "acos", "atan");
        keyWords.put("sqrt", (b, e, s) -> new Pow(builder.buildingNotNamed(e), 0.5));
        keyWords.put("exp", (b, e, s) -> new Exp(builder.buildingNotNamed(e)));
        keyWords.put("abs", (b, e, s) -> new Abs(builder.buildingNotNamed(e)));
        keyWords.put("log", (b, e, s) -> new Log(2, builder.buildingNotNamed(e)));
        keyWords.put("ln", (b, e, s) -> new Log(builder.buildingNotNamed(e)));
        keyWords.put("sin", (b, e, s) -> new Sin(builder.buildingNotNamed(e)));
        keyWords.put("cos", (b, e, s) -> new Cos(builder.buildingNotNamed(e)));
        keyWords.put("tan", (b, e, s) -> new Tan(builder.buildingNotNamed(e)));
        keyWords.put("ctan", (b, e, s) -> new CTan(builder.buildingNotNamed(e)));
        keyWords.put("arcsin", (b, e, s) -> new ASin(builder.buildingNotNamed(e)));
        keyWords.put("arcsos", (b, e, s) -> new ACos(builder.buildingNotNamed(e)));
        keyWords.put("arctan", (b, e, s) -> new ATan(builder.buildingNotNamed(e)));
    }
    private Elements(){}
}
