package com.propcool.cmpm_project.util;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.*;
import com.propcool.cmpm_project.functions.combination.*;
import com.propcool.cmpm_project.functions.interpolate.Polynomial;
import com.propcool.cmpm_project.functions.mono.*;

/**
 * Класс для поиска производных
 * */
public class DifBuilder {
    /**
     * Производная по x
     * */
    public Function difX(Function function){
        return dif(function, new DifX());
    }
    /**
     * Производная по y
     * */
    public Function difY(Function function) {
        return dif(function, new DifY());
    }
    public Function dif(Function function) {
        return new Sum(difX(function), difY(function));
    }
    private Function dif(Function function, Dif d){
        if(function instanceof VariableX){
            return difVariableX(d);
        } else if(function instanceof VariableY){
            return difVariableY(d);
        } else if(function instanceof FunctionDecorator f){
            return difDecorator(f, d);
        } else if(function instanceof Constant){
            return difConstant();
        } else if(function instanceof Sum f){
            return difSum(f, d);
        } else if(function instanceof Difference f){
            return difDifference(f, d);
        } else if(function instanceof Multiply f){
            return difMultiply(f, d);
        } else if(function instanceof Division f){
            return difDivision(f, d);
        }else if(function instanceof Pow f){
            return difPow(f, d);
        } else if(function instanceof Exp f){
            return difExp(f, d);
        } else if(function instanceof Log f){
            return difLog(f, d);
        } else if(function instanceof Exponential f){
            return difExponential(f, d);
        } else if(function instanceof Sin f){
            return difSin(f, d);
        } else if(function instanceof Cos f){
            return difCos(f, d);
        } else if(function instanceof Tan f){
            return difTan(f, d);
        } else if(function instanceof CTan f){
            return difCTan(f, d);
        } else if(function instanceof ASin f){
            return difASin(f, d);
        } else if(function instanceof ACos f){
            return difACos(f, d);
        } else if(function instanceof ATan f){
            return difATan(f, d);
        } else if(function instanceof Abs f) {
            return difAbs(f, d);
        } else if(function instanceof Sh f) {
            return difSh(f, d);
        } else if(function instanceof Ch f) {
            return difCh(f, d);
        } else if(function instanceof Th f) {
            return difTh(f, d);
        } else if(function instanceof Polynomial f) {
            return difPolynomial(f, d);
        } else throw new RuntimeException("Не получилось найти производную");
    }
    private Function difVariableX(Dif d){
        if (d instanceof DifX) return new Constant(1);
        else return new Constant(0);
    }
    private Function difVariableY(Dif d){
        if (d instanceof DifY) return new Constant(1);
        else return new Constant(0);
    }
    private Function difDecorator(FunctionDecorator f, Dif d){
        return dif(f.getFunction(), d);
    }
    private Function difConstant(){
        return new Constant(0);
    }
    private Function difSum(Sum f, Dif d){
        return new Sum(d.dif(f.getFirst()), d.dif(f.getSecond()));
    }
    private Function difDifference(Difference f, Dif d){
        return new Difference(d.dif(f.getFirst()), d.dif(f.getSecond()));
    }
    private Function difMultiply(Multiply f, Dif d){
        return new Sum(new Multiply(d.dif(f.getFirst()), f.getSecond().clone()),
                new Multiply(f.getFirst().clone(), d.dif(f.getSecond()))
        ); // (uv)' = u'v+uv'
    }
    private Function difDivision(Division f, Dif d){
        return new Division(new Difference(
                new Multiply(d.dif(f.getFirst()), f.getSecond().clone()),
                new Multiply(f.getFirst().clone(), d.dif(f.getSecond()))
        ),
                new Pow(f.getSecond().clone(), 2)
        ); // (u/v)' = (u'v-uv')/v^2
    }
    private Function difPow(Pow f, Dif d){
        return new Multiply(
                new Multiply(
                        f.getSecond().clone(),
                        new Pow(
                                f.getFirst().clone(),
                                new ConstantSum(f.getSecond().clone(), -1)
                        )
                ),
                d.dif(f.getFirst())
        ); // (u^n)' = n*u^(n-1)*u'
    }
    private Function difExp(Exp f, Dif d){
        return new Multiply(
                new Multiply(
                        f.clone(),
                        new Log(f.getFirst().clone())
                ),
                d.dif(f.getSecond())
        ); // (a^u)' = a^u*ln(a)*u'
    }
    private Function difLog(Log f, Dif d){
        return new Division(
                d.dif(f.getSecond()),
                new Multiply(f.getSecond().clone(), new Log(f.getFirst().clone())
                )
        ); // (log(a, u))' = u'/(u*ln(a))
    }
    private Function difExponential(Exponential f, Dif d){
        return new Sum(
                new Multiply(
                        new Multiply(
                                f.getSecond().clone(),
                                new Exponential(f.getFirst().clone(),
                                        new Difference(f.getSecond().clone(), new Constant(1))
                                )), d.dif(f.getFirst())

                ),
                new Multiply(
                        new Multiply(
                                new Exponential(f.getFirst().clone(),
                                        f.getSecond().clone()),
                                new Log(f.getFirst().clone())
                        ),
                        d.dif(f.getSecond())
                )
        ); // (u^v)' = v*u^(v-1)*u'+u^v*ln(u)*v'
    }
    private Function difSin(Sin f, Dif d){
        return new Multiply(new Cos(f.getFunction().clone()), d.dif(f.getFunction()));
    }
    private Function difCos(Cos f, Dif d){
        return new Multiply(new Difference(new Constant(0), new Sin(f.getFunction().clone())), d.dif(f.getFunction()));
    }
    private Function difTan(Tan f, Dif d){
        return new Division(d.dif(f.getFunction()), new Pow(new Cos(f.getFunction().clone()), 2));
    }
    private Function difCTan(CTan f, Dif d){
        return new Difference(new Constant(0), new Division(d.dif(f.getFunction()), new Pow(new Sin(f.getFunction().clone()), 2)));
    }
    private Function difASin(ASin f, Dif d){
        return new Division(
                d.dif(f.getFunction()),
                new Pow(
                        new Difference(new Constant(1), new Pow(f.getFunction().clone(), 2)),
                        0.5
                )
        ); // (arcsin(x))' = u'/sqrt(1-u^2)
    }
    private Function difACos(ACos f, Dif d){
        return new Difference(new Constant(0),
                new Division(
                        d.dif(f.getFunction()),
                        new Pow(
                                new Difference(new Constant(1), new Pow(f.getFunction().clone(), 2)),
                                0.5
                        )
                )
        ); // (arccos(x))' = -u'/sqrt(1-u^2)
    }
    private Function difATan(ATan f, Dif d){
        return new Division(
                d.dif(f.getFunction()),
                new Sum(
                        new Constant(1),
                        new Pow(f.getFunction().clone(), 2)
                )
        );
    }
    private Function difAbs(Abs f, Dif d) {
        return new Division(
                new Multiply(f.getFunction().clone(), d.dif(f.getFunction())),
                new Abs(f.getFunction().clone())
        );
    }
    private Function difSh(Sh f, Dif d) {
        return new Multiply(new Ch(f.getFunction().clone()), d.dif(f.getFunction()));
    }
    private Function difCh(Ch f, Dif d) {
        return new Multiply(new Sh(f.getFunction().clone()), d.dif(f.getFunction()));
    }
    private Function difTh(Th f, Dif d){
        return new Division(d.dif(f.getFunction()), new Pow(new Ch(f.getFunction().clone()), 2));
    }
    private Function difPolynomial(Polynomial f, Dif d) {
        int n = f.dim()-1;
        if(n <= 0) return new Constant(0);
        double[] A = new double[n];
        for(int i = 0; i < n; i++) {
            A[i] = f.getA(i+1) * (i+1);
        }
        return new Multiply(new Polynomial(f.getFunction().clone(), A), d.dif(f.getFunction()));
    }
    private interface Dif{
        Function dif(Function f);
    }
    private class DifX implements Dif{
        public Function dif(Function f){
            return DifBuilder.this.difX(f);
        }
    }
    private class DifY implements Dif{
        public Function dif(Function f){
            return DifBuilder.this.difY(f);
        }
    }
}
