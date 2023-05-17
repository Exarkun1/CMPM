package com.propcool.cmpm_project.util;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.combination.Difference;
import com.propcool.cmpm_project.functions.combination.Division;
import com.propcool.cmpm_project.functions.vector.Matrix;
import com.propcool.cmpm_project.functions.vector.Vector;

/**
 * Поиск корней уравнений, их пересечений и экстремумов
 * */
public class RootSearcher {
    public RootSearcher(double e, int maxIteration) {
        this.e = e;
        this.maxIteration = maxIteration;
    }
    public double rootX(Function f, double a, double b) {
        if(f.get(a) * f.get(b) > 0) throw new RuntimeException("Границы значений функции одного знака");
        Function df = db.difX(f);
        Function ddf = db.difX(df);
        double x = f.get(a)*ddf.get(a) >= 0 ? a : b, prevX;
         do{
            prevX = x;
            x -= f.get(x) / df.get(x);
        } while(Math.abs(x-prevX) >= e);
        return x;
    }
    public Point intersectionX(Function f, Function g, double a, double b) {
        double x = rootX(new Difference(f, g), a, b);
        return new Point(x, f.get(x));
    }
    public Point extremeX(Function f, double a, double b) {
        Function dif = db.difX(f);
        double x = rootX(dif, a, b);
        return new Point(x, f.get(x));
    }
    public Point intersectionXY(Function f, Function g, double x0, double y0) {
        Vector FG = new Vector(f, g);
        Matrix J = J(FG).rev();
        double x = x0, y = y0, prevX, prevY;
        int index = 0;
        do {
            Matrix Jn = J.getMatC(x, y); // Jn - на самом деле не Jn, а Jn^(-1)
            Vector FGn = FG.getVecC(x, y);
            double[] dXY = Jn.dot(FGn).getVec(0, 0);
            prevX = x;
            prevY = y;
            x -= dXY[0];
            y -= dXY[1];
        } while(++index < maxIteration && norm(x-prevX, y-prevY) >= e);
        if(index < maxIteration) return new Point(x, y);
        else throw new RuntimeException("Не найден корень за " + maxIteration + " итераций");
    }
    public Point extremeXY(Function f, double x0, double y0) {
        Function dFx = db.difX(f), dFy = db.difY(f);
        Function dY = new Difference(new Division(dFx, dFy));
        return intersectionXY(dY, f, x0, y0);
    }
    public Point extremeZ(Function f, double x0, double y0) {
        Function dFx = db.difX(f), dFy = db.difY(f);
        Point point = intersectionXY(dFx, dFy, x0, y0);
        Matrix J = J(dFx, dFy);
        if(J.get(point) > 0) return point;
        else throw new RuntimeException("Не найден экстремум за " + maxIteration + " итераций");
    }
    public Matrix J(Function f, Function g) {
        return new Matrix(
                db.difX(f), db.difY(f),
                db.difX(g), db.difY(g)
        );
    }
    public Matrix J(Vector H) {
        return J(H.getFirst(), H.getSecond());
    }
    private double norm(double x, double y) {
        return Math.sqrt(y*y + x*x);
    }
    private final double e;
    private final int maxIteration;
    private final DifBuilder db = new DifBuilder();
}
