package com.propcool.cmpm_project.functions.vector;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.basic.Constant;
import com.propcool.cmpm_project.functions.combination.*;

public class Matrix extends Combination {
    public Matrix(Function f1, Function f2, Function f3, Function f4) {
        super(new Vector(f1, f2), new Vector(f3, f4));
    }
    public Matrix(Vector v1, Vector v2) {
        super(v1, v2);
        Vector rowF = getRowF();
        Vector rowS = getRowS();
        this.colF = new Vector(rowF.getFirst(), rowS.getFirst());
        this.colS = new Vector(rowF.getSecond(), rowS.getSecond());
    }
    @Override
    public double get(double x, double y) {
        return det().get(x, y);
    }
    public Vector getRowF() {
        return (Vector) super.getFirst();
    }
    public Vector getRowS() {
        return (Vector) super.getSecond();
    }
    public Vector getColF() {
        return colF;
    }
    public Vector getColS() {
        return colS;
    }
    public double[][] getMat(double x, double y) {
        return new double[][]{
                getRowF().getVec(x, y),
                getRowS().getVec(x, y)
        };
    }
    public Matrix getMatC(double x, double y) {
        double[][] m = getMat(x, y);
        return new Matrix(
                new Constant(m[0][0]),
                new Constant(m[0][1]),
                new Constant(m[1][0]),
                new Constant(m[1][1])
        );
    }
    public Function det() {
        return new Difference(
                new Multiply(getRowF().getFirst(), getRowS().getSecond()),
                new Multiply(getRowF().getSecond(), getRowS().getFirst())
        );
    }
    public Matrix add(Matrix m) {
        return new Matrix(getRowF().add(m.getRowF()), getRowS().add(m.getRowS()));
    }
    public Matrix mul(Matrix m) {
        return new Matrix(getRowF().mul(m.getRowF()), getRowS().mul(m.getRowS()));
    }
    public Matrix add(Vector v) {
        return add(new Matrix(v, v));
    }
    public Matrix mul(Vector v) {
        return mul(new Matrix(v, v));
    }
    public Vector dot(Vector v) {
        return new Vector(getRowF().dot(v), getRowS().dot(v));
    }
    public Matrix dot(Matrix m) {
        Function f1 = getRowF().dot(m.getColF());
        Function f2 = getRowF().dot(m.getColS());
        Function f3 = getRowS().dot(m.getColF());
        Function f4 = getRowS().dot(m.getColS());
        return new Matrix(f1, f2, f3, f4);
    }
    public Matrix rev() {
        Function det = det();
        return new Matrix(
                new Division(getRowS().getSecond(), det),
                new Difference(new Division(getRowF().getSecond(), det)),
                new Difference(new Division(getRowS().getFirst(), det)),
                new Division(getRowF().getFirst(), det)
        );
    }
    @Override
    public Matrix clone() {
        Matrix matrix = (Matrix) super.clone();
        Vector rowF = matrix.getRowF();
        Vector rowS = matrix.getRowS();
        matrix.colF = new Vector(rowF.getFirst(), rowS.getFirst());
        matrix.colS = new Vector(rowF.getSecond(), rowS.getSecond());
        return matrix;
    }
    protected Vector colF;
    protected Vector colS;
}
