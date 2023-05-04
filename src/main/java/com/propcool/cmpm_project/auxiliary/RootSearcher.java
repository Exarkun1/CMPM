package com.propcool.cmpm_project.auxiliary;

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
        double x = a, prevX = b;
        while(Math.abs(x-prevX) >= e) {
            double v = f.get(x);
            double dv = df.get(x);
            double newX = x - v/dv;
            prevX = x;
            x = newX;
        }
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
        double prevX = x+1, prevY = y+1;
        while(Math.sqrt(Math.pow(x-prevX, 2) + Math.pow(y-prevY, 2)) >= e) {
            Matrix J = J(f, g).getMatC(x, y);
            Vector H = new Vector(opF, opG).getVecC(x, y);
            double[] dXY = J.rev().mul(H).getVec(0, 0);
            prevX = x;
            prevY = y;
            x += dXY[0];
            y += dXY[1];
        }
        return new Point(x, y);
    }
    public Matrix J(Function f, Function g) {
        return new Matrix(
                db.difX(f), db.difY(f),
                db.difX(g), db.difY(g)
        );
    }
    public Point extreme(Function f, double a, double b) {
        Function dif = db.difX(f);
        double x = rootX(dif, a, b);
        return new Point(x, f.get(x));
    }
    private final double e;
    private final int maxIteration;
    private final DifBuilder db = new DifBuilder();
}
