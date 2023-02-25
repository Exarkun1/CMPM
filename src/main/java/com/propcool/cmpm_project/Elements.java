package com.propcool.cmpm_project;

import com.propcool.cmpm_project.analysing.build.FunctionBuilder;
import com.propcool.cmpm_project.analysing.build.FunctionFactory;
import com.propcool.cmpm_project.controllers.auxiliary.CustomizableFunction;
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

    public static final Map<String, CustomizableFunction> functions = new HashMap<>();
    public static final Map<String, Constant> parameters = new HashMap<>();
    public static final FunctionBuilder builder = new FunctionBuilder();

    static {
        //List.of("sqrt", "exp", "abs", "log", "ln", "sin", "cos", "tan", "ctan", "asin", "acos", "atan");
        keyWords.put("sqrt", (b, e, s, p) -> new Pow(builder.buildingNotNamed(e, p), 0.5));
        keyWords.put("exp", (b, e, s, p) -> new Exp(builder.buildingNotNamed(e, p)));
        keyWords.put("abs", (b, e, s, p) -> new Abs(builder.buildingNotNamed(e, p)));
        keyWords.put("log", (b, e, s, p) -> new Log(2, builder.buildingNotNamed(e, p)));
        keyWords.put("ln", (b, e, s, p) -> new Log(builder.buildingNotNamed(e, p)));
        keyWords.put("sin", (b, e, s, p) -> new Sin(builder.buildingNotNamed(e, p)));
        keyWords.put("cos", (b, e, s, p) -> new Cos(builder.buildingNotNamed(e, p)));
        keyWords.put("tan", (b, e, s, p) -> new Tan(builder.buildingNotNamed(e, p)));
        keyWords.put("ctan", (b, e, s, p) -> new CTan(builder.buildingNotNamed(e, p)));
        keyWords.put("arcsin", (b, e, s, p) -> new ASin(builder.buildingNotNamed(e, p)));
        keyWords.put("arcsos", (b, e, s, p) -> new ACos(builder.buildingNotNamed(e, p)));
        keyWords.put("arctan", (b, e, s, p) -> new ATan(builder.buildingNotNamed(e, p)));
    }
    private Elements(){}
}
