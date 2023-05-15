package com.propcool.cmpm_project.util;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.combination.Difference;
import com.propcool.cmpm_project.functions.vector.Matrix;
import com.propcool.cmpm_project.functions.vector.Vector;

public class RootSearcher {
    public RootSearcher(double e, int maxIteration) {
        this.e = e;
        this.maxIteration = maxIteration;
    }
    public double rootX(Function f, double a, double b) {
        if(f.get(a) * f.get(b) > 0) throw new RuntimeException("Границы значений функции одного знака");
        Function df = db.difX(f);
        double x = f.get(a)*db.difX(df).get(a) >= 0 ? a : b, prevX;
         do{
            double v = f.get(x);
            double dv = df.get(x);
            double newX = x - v/dv;
            prevX = x;
            x = newX;
        } while(Math.abs(x-prevX) >= e);
        return x;
    }
    public Point intersectionX(Function f, Function g, double a, double b) {
        double x = rootX(new Difference(f, g), a, b);
        return new Point(x, f.get(x));
    }
    public Point intersectionXY(Function f, Function g, double a, double b) {
        Function opF = new Difference(f);
        Function opG = new Difference(g);
        double x = a, y = b;
        double prevX, prevY;
        int index = 0;
        do {
            Matrix J = J(f, g).getMatC(x, y);
            Vector H = new Vector(opF, opG).getVecC(x, y);
            double[] dXY = J.rev().dot(H).getVec(0, 0);
            prevX = x;
            prevY = y;
            x += dXY[0];
            y += dXY[1];
        } while(++index < maxIteration && norm(x-prevX, y-prevY) >= e);
        if(index < maxIteration) return new Point(x, y);
        else throw new RuntimeException("Не найден корень за " + maxIteration + " итераций");
    }
    public Matrix J(Function f, Function g) {
        return new Matrix(
                db.difX(f), db.difY(f),
                db.difX(g), db.difY(g)
        );
    }
    public Point extremeX(Function f, double a, double b) {
        Function dif = db.difX(f);
        double x = rootX(dif, a, b);
        return new Point(x, f.get(x));
    }
    private double norm(double x, double y) {
        return Math.sqrt(y*y + x*x);
    }
    private final double e;
    private final int maxIteration;
    private final DifBuilder db = new DifBuilder();
}
