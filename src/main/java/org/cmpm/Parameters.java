package org.cmpm;

import org.cmpm.functions.Function;
import org.cmpm.functions.basic.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parameters {
    public static final List<String> keyWords = List.of("sqrt", "exp", "abs", "log", "ln", "sin", "cos", "tan", "ctan", "asin", "acos", "atan");
    public static final List<String> constants = List.of("pi", "e");

    public static final Map<String, Function> functions = new HashMap<>();
    public static final Map<String, Constant> parameters = new HashMap<>();
    private Parameters(){}
}
