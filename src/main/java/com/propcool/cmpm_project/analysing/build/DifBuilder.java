package com.propcool.cmpm_project.analysing.build;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.*;
import com.propcool.cmpm_project.functions.combination.*;
import com.propcool.cmpm_project.functions.mono.*;

public class DifBuilder {
    public Function buildDif(Function function){
        if(function instanceof Variable){
          return new Constant(1);
        } else if(function instanceof FunctionDecorator f){
            return buildDif(f.getFunction());
        } else if(function instanceof Constant){
            return new Constant(0);
        } else if(function instanceof Sum f){
            return new Sum(buildDif(f.getFunction1()), buildDif(f.getFunction2()));
        } else if(function instanceof Difference f){
            return new Difference(buildDif(f.getFunction1()), buildDif(f.getFunction2()));
        } else if(function instanceof Multiply f){
            return new Sum(new Multiply(buildDif(f.getFunction1()), f.getFunction2().clone()),
                    new Multiply(f.getFunction1().clone(), buildDif(f.getFunction2()))
            ); // (uv)' = u'v+uv'
        } else if(function instanceof Division f){
            return new Division(new Difference(
                    new Multiply(buildDif(f.getFunction1()), f.getFunction2().clone()),
                    new Multiply(f.getFunction1().clone(), buildDif(f.getFunction2()))
            ),
                    new Pow(f.getFunction2().clone(), 2)
            ); // (u/v)' = (u'v-uv')/v^2
        }else if(function instanceof Pow f){
            return new Multiply(
                    new Multiply(
                            f.getFunction2().clone(),
                            new Pow(
                                    f.getFunction1().clone(),
                                    new ConstantSum(f.getFunction2().clone(), -1)
                            )
                    ),
                    buildDif(f.getFunction1())
            ); // (u^n)' = n*u^(n-1)*u'
        } else if(function instanceof Exp f){
            return new Multiply(
                   new Multiply(
                           f.clone(),
                           new Log(f.getFunction1().clone())
                   ),
                   buildDif(f.getFunction2())
            ); // (a^u)' = a^u*ln(a)*u'
        } else if(function instanceof Log f){
            return new Division(
                    buildDif(f.getFunction2()),
                    new Multiply(f.getFunction2().clone(), new Log(f.getFunction1().clone())
                    )
            ); // (log(a, u))' = u'/(u*ln(a))
        } else if(function instanceof Exponential f){
            return new Sum(
                    new Multiply(
                            new Multiply(
                                    f.getFunction2().clone(),
                                    new Exponential(f.getFunction1().clone(),
                                            new Difference(f.getFunction2().clone(), new Constant(1))
                                    )), buildDif(f.getFunction1())

                            ),
                    new Multiply(
                            new Multiply(
                                    new Exponential(f.getFunction1().clone(),
                                            f.getFunction2().clone()),
                                    new Log(f.getFunction1().clone())
                            ),
                            buildDif(f.getFunction2())
                    )
            ); // (u^v)' = v*u^(v-1)*u'+u^v*ln(u)*v'
        } else if(function instanceof Sin f){
            return new Multiply(new Cos(f.getFunction().clone()), buildDif(f.getFunction()));
        } else if(function instanceof Cos f){
            return new Multiply(new Difference(new Constant(0), new Sin(f.getFunction().clone())), buildDif(f.getFunction()));
        } else if(function instanceof Tan f){
            return new Division(buildDif(f.getFunction()), new Pow(new Cos(f.getFunction().clone()), 2));
        } else if(function instanceof CTan f){
            return new Difference(new Constant(0), new Division(buildDif(f.getFunction()), new Pow(new Sin(f.getFunction().clone()), 2)));
        } else if(function instanceof ASin f){
            return new Division(
                    buildDif(f.getFunction()),
                    new Pow(
                            new Difference(new Constant(1), new Pow(f.getFunction().clone(), 2)),
                            0.5
                    )
            ); // (arcsin(x))' = u'/sqrt(1-u^2)
        } else if(function instanceof ACos f){
            return new Difference(new Constant(0),
                new Division(
                        buildDif(f.getFunction()),
                        new Pow(
                                new Difference(new Constant(1), new Pow(f.getFunction().clone(), 2)),
                                0.5
                        )
                )
            ); // (arccos(x))' = -u'/sqrt(1-u^2)
        } else if(function instanceof ATan f){
            return new Division(
                    buildDif(f.getFunction()),
                    new Sum(
                            new Constant(1),
                            new Pow(f.getFunction().clone(), 2)
                    )
            );
        } else throw new RuntimeException("Не получилось найти производную");
    }
}
