package com.propcool.cmpm_project.manage;

import com.propcool.cmpm_project.notebooks.data.CustomizableTable;
import com.propcool.cmpm_project.util.DifBuilder;
import com.propcool.cmpm_project.analysing.build.FunctionBuilder;
import com.propcool.cmpm_project.analysing.build.FunctionFactory;
import com.propcool.cmpm_project.analysing.build.NamedFunction;
import com.propcool.cmpm_project.util.TabulateBuilder;
import com.propcool.cmpm_project.util.Point;
import com.propcool.cmpm_project.util.RootSearcher;
import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.*;
import com.propcool.cmpm_project.functions.combination.Combination;
import com.propcool.cmpm_project.functions.mono.*;
import com.propcool.cmpm_project.notebooks.data.CustomizableFunction;
import com.propcool.cmpm_project.notebooks.data.CustomizableParameter;
import javafx.scene.control.Alert;

import java.util.*;
/**
 * Менеджер управления функциями и параметрами
 * */
public class FunctionManager {
    public void putFunction(String name, CustomizableFunction function){
        functions.put(name, function);
    }
    public CustomizableFunction removeFunction(String name){
        return functions.remove(name);
    }
    public CustomizableFunction getFunction(String name){
        return functions.get(name);
    }
    public FunctionFactory getKeyWord(String name) {
        return keyWords.get(name);
    }
    public void putParam(String name, CustomizableParameter param){
        parameters.put(name, param);
    }
    public CustomizableParameter removeParam(String name){
        return parameters.remove(name);
    }
    public CustomizableParameter getParam(String name){
        return parameters.get(name);
    }
    public void putTable(String name, CustomizableTable table) {
        tables.put(name, table);
    }
    public CustomizableTable removeTable(String name) { return tables.remove(name); }
    public CustomizableTable getTable(String name) {
        return tables.get(name);
    }
    public String buildFunction(String text, int defaultName){
        return functionBuilder.building(text, defaultName);
    }
    public void clearFunctions(){
        functions.clear();
    }
    public void clearParams(){
        parameters.clear();
    }
    public void clearTables() { tables.clear(); }
    public Map<String, CustomizableFunction> getFunctions(){
        return Collections.unmodifiableMap(functions);
    }
    public Map<String, CustomizableParameter> getParameters(){
        return Collections.unmodifiableMap(parameters);
    }
    public Map<String, CustomizableTable> getTables(){
        return Collections.unmodifiableMap(tables);
    }
    public Map<String, FunctionFactory> getKeyWords(){
        return Collections.unmodifiableMap(keyWords);
    }
    public Function returnParam(String name, NamedFunction nf){
        CustomizableParameter cp = getParam(name);
        if(cp == null) {
            Constant c = new Constant(1);
            cp = new CustomizableParameter(c);
            cp.setArea(10);
            cp.setValue(1);
            cp.setName(name);
            putParam(name, cp);
        }
        nf.getParams().add(name);
        // Добавление ссылки на функцию в параметре
        cp.getRefFunctions().add(nf.getName());
        return cp.getParam();
    }
    /**
     * Удаление ссылок на функцию в параметрах
     * */
    public void removeParamRefs(String functionName){
        CustomizableFunction function = getFunction(functionName);
        // Удаление ссылок на данную функцию у параметров
        if(function != null) {
            for (var param : function.getParams()) {
                getParam(param).removeRef(functionName);
            }
        }
    }
    /**
     * Создание фабрики для кастомной функции
     * */
    public FunctionFactory getFunctionFactory(){
        return (begin, end, symbol, nf) -> {
            getFunction(begin).getRefFunctions().add(nf.getName());
            Function function = getFunction(begin).getFunction().clone();
            List<FunctionDecoratorX> decors = new ArrayList<>();
            getFuncDecorators(function, decors);
            for(var decor : decors){
                decor.setFunction(functionBuilder.buildingNotNamed(end, nf));
            }
            return function;
        };
    }
    /**
     * Выдача функций находящихся над x
     * */
    public void getFuncDecorators(Function function, List<FunctionDecoratorX> decorators){
        if(function instanceof FunctionDecoratorX decorator && !decorator.isChanged()) {
            decorators.add(decorator);
            decorator.setChanged(true);
        } else if(function instanceof FunctionDecoratorX decorator) {
            getFuncDecorators(decorator.getFunction(), decorators);
        } else if(function instanceof Mono mono){
            getFuncDecorators(mono.getFunction(), decorators);
        } else if(function instanceof Combination combination){
            getFuncDecorators(combination.getFirst(), decorators);
            getFuncDecorators(combination.getSecond(), decorators);
        }
    }
    /**
     * Преобразование функций, ссылающих на параметр с таким же именем, что и у передаваемой
     * */
    public List<String> rebuildRefsWithParam(String functionName){
        List<String> refs = new ArrayList<>();
        CustomizableParameter cp = getParam(functionName);
        if(cp == null) return refs;
        for(var name : cp.getRefFunctions()){
            if(name.equals(functionName)) continue;
            CustomizableFunction oldCf = removeFunction(name);
            String newFunctionName = buildFunction(oldCf.getExpression(), oldCf.getDefaultName());
            CustomizableFunction newCf = getFunction(newFunctionName);
            newCf.setColor(oldCf.getColor());
            newCf.setWidth(oldCf.getWidth());
            newCf.setDefaultName(oldCf.getDefaultName());
            newCf.setVisible(oldCf.isVisible());
            refs.add(name);
        }
        for(var name : refs){
            cp.getRefFunctions().remove(name);
        }
        return refs;
    }
    /**
     * Преобразование функций ссылающихся на данную
     * */
    public List<String> rebuildRefsWithFunction(CustomizableFunction cf){
        List<String> refs = new ArrayList<>();
        if(cf == null) return refs;
        for(var name : cf.getRefFunctions()){
            CustomizableFunction oldCf = removeFunction(name);
            if(oldCf == null) {
                refs.add(name);
                continue;
            } // Исправление ошибки пустой ссылки на функцию
            String newFunctionName = buildFunction(oldCf.getExpression(), oldCf.getDefaultName());
            CustomizableFunction newCf = getFunction(newFunctionName);
            newCf.setColor(oldCf.getColor());
            newCf.setWidth(oldCf.getWidth());
            newCf.setDefaultName(oldCf.getDefaultName());
            newCf.setVisible(oldCf.isVisible());
            refs.add(name);
        }
        for(var name : refs){
            cf.getRefFunctions().remove(name);
        }
        return refs;
    }
    /**
     * Построение производной
     * */
    public Function buildDif(Function function){
        return difBuilder.difX(function);
    }
    /**
     * Поиск корней функций
     * */
    public Set<Point> searchRoots(Function function, double start, double end) {
        Set<Point> points = new HashSet<>();
        for(double step = start; step < end; step += 0.1) {
            try {
                double x = rootSearcher.rootX(function, step, step + 0.1);
                points.add(new Point(x, 0));
            } catch (RuntimeException ignored) {}
        }
        return points;
    }
    /**
     * Поиск пересечений функций
     * */
    public Set<Point> searchIntersects(Function f, Function g, double start, double end) {
        Set<Point> points = new HashSet<>();
        for(double step = start; step < end; step += 0.1) {
            try {
                Point point = rootSearcher.intersectionX(f, g, step, step + 0.1);
                points.add(point);
            } catch (RuntimeException ignored) {}
        }
        return points;
    }
    /**
     * Поиск экстремумов функции
     * */
    public Set<Point> searchExtremes(Function f, double start, double end) {
        Set<Point> points = new HashSet<>();
        for(double step = start; step < end; step += 0.1) {
            try {
                Point point = rootSearcher.extremeX(f, step, step + 0.1);
                points.add(point);
            } catch (RuntimeException ignored) {}
        }
        return points;
    }
    /**
     * Поиск пересечений неявных функций
     * */
    public Point searchIntersectsXY(Function f, Function g, double x, double y) {
        try {
            return rootSearcher.intersectionXY(f, g, x, y);
        } catch (RuntimeException ignored) {
            return null;
        }
    }
    /**
     * Поиск экстремумов неявных функций
     * */
    public Point searchExtremeXY(Function f, double x, double y) {
        try {
            return rootSearcher.extremeXY(f, x, y);
        } catch (RuntimeException ignored) {
            return null;
        }
    }
    public Function approximation(Set<Point> points, int k) {
        double[] X = new double[points.size()], Y = new double[points.size()];
        int i = 0;
        for(var point : points) {
            X[i] = point.getX();
            Y[i] = point.getY();
            i++;
        }
        return tabulateBuilder.approximation(new FunctionDecoratorX(), X, Y, k);
    }

    public Point getCauchyPoint() {
        return cauchyPoint;
    }
    /**
     * Сохранение точки для задачи Коши
     * */
    public void setCauchyPoint(double x, double y) {
        cauchyPoint.setX(x);
        cauchyPoint.setY(y);
    }
    public void cauchyAlert() {
        cauchyAlert.show();
    }
    private final Map<String, FunctionFactory> keyWords = new LinkedHashMap<>();
    private final List<String> constants = List.of("pi", "e");
    private final  Map<String, CustomizableFunction> functions = new LinkedHashMap<>();
    private final  Map<String, CustomizableParameter> parameters = new LinkedHashMap<>();
    private final Map<String, CustomizableTable> tables = new LinkedHashMap<>();
    private final FunctionBuilder functionBuilder = new FunctionBuilder(this);
    private final DifBuilder difBuilder = new DifBuilder();
    private final RootSearcher rootSearcher = new RootSearcher(1e-6, 100);
    private final TabulateBuilder tabulateBuilder = new TabulateBuilder();
    private final Point cauchyPoint = new Point(0, 0);
    private final Alert cauchyAlert = new Alert(Alert.AlertType.ERROR,"Не верные данные в задаче Коши");

    public FunctionManager(){
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
        keyWords.put("arccos", (b, e, s, p) -> new ACos(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("arctan", (b, e, s, p) -> new ATan(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("sh", (b, e, s, p) -> new Sh(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("ch", (b, e, s, p) -> new Ch(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("th", (b, e, s, p) -> new Th(functionBuilder.buildingNotNamed(e, p)));
        keyWords.put("pol", (b, e, s, p) ->
                new TabulateBuilder().approximation(
                        functionBuilder.buildingNotNamed(e, p), new double[]{1, 2}, new double[] {3, 4}, 1
                )
        );
    }
}
