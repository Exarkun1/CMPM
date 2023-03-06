package com.propcool.cmpm_project;

import com.propcool.cmpm_project.analysing.build.FunctionBuilder;
import com.propcool.cmpm_project.analysing.build.FunctionFactory;
import com.propcool.cmpm_project.manage.CoordinateManager;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import com.propcool.cmpm_project.notebooks.data.CustomizableParameter;
import com.propcool.cmpm_project.notebooks.Notebook;
import com.propcool.cmpm_project.functions.basic.Exp;
import com.propcool.cmpm_project.functions.basic.Log;
import com.propcool.cmpm_project.functions.basic.Pow;
import com.propcool.cmpm_project.functions.mono.*;
import com.propcool.cmpm_project.notebooks.NotebookBuilder;
import com.propcool.cmpm_project.notebooks.NotebookLoader;
import com.propcool.cmpm_project.notebooks.NotebookSaver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elements {
    public static final Map<String, FunctionFactory> keyWords = new HashMap<>();
    public static final List<String> constants = List.of("pi", "e");
    public static final Map<String, Notebook> notebooks = new HashMap<>();

    public static final  Map<String, CustomizableFunction> functions = new HashMap<>();
    public static final  Map<String, CustomizableParameter> parameters = new HashMap<>();
    public static final FunctionBuilder functionBuilder = new FunctionBuilder();

    static {
        //List.of("sqrt", "exp", "abs", "log", "ln", "sin", "cos", "tan", "ctan", "asin", "acos", "atan");
        keyWords.put("sqrt", (b, e, s, p) -> new Pow(functionBuilder.buildingNotNamed(e, p), 0.5));
        keyWords.put("exp", (b, e, s, p) -> new Exp(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("abs", (b, e, s, p) -> new Abs(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("log", (b, e, s, p) -> new Log(2, functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("ln", (b, e, s, p) -> new Log(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("sin", (b, e, s, p) -> new Sin(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("cos", (b, e, s, p) -> new Cos(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("tan", (b, e, s, p) -> new Tan(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("ctan", (b, e, s, p) -> new CTan(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("arcsin", (b, e, s, p) -> new ASin(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("arcsos", (b, e, s, p) -> new ACos(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("arctan", (b, e, s, p) -> new ATan(functionBuilder.buildingNotNamed(e, p)));
    }

    public static void goToOtherNotebook(String name){
        Notebook nb = notebooks.get(name);
    }
    private Elements(){}
}
