package com.propcool.cmpm_project.util;

import java.io.Serializable;
import java.util.Objects;

public class Complex implements Serializable, Comparable<Complex> {
    public Complex(double real){
        this(real, 0.);
    }
    public Complex(){
        this(0.);
    }
    public Complex(double real, double imaginary){
        rl = real;
        im = imaginary;
    }
    public Complex add(Complex o){
        return new Complex(rl + o.rl, im + o.im);
    }
    public Complex sub(Complex o){
        return new Complex(rl - o.rl, im - o.im);
    }
    public Complex mul(Complex o){
        return new Complex(
                rl * o.rl - im * o.im,
                im * o.rl + rl * o.im
        );
    }
    public Complex div(Complex o){
        double denominator = o.rl * o.rl + o.im * o.im;
        return new Complex(
                (rl * o.rl + im * o.im) / denominator,
                (im * o.rl - rl * o.im) / denominator
        );
    }
    public Complex add(double o){
        return add(new Complex(o));
    }
    public Complex sub(double o){
        return sub(new Complex(o));
    }
    public Complex mul(double o){
        return mul(new Complex(o));
    }
    public Complex div(double o){
        return div(new Complex(o));
    }
    public double norm(){
        return Math.sqrt(rl * rl + im * im);
    }
    @Override
    public int compareTo(Complex o) {
        return Double.compare(norm(), o.norm());
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Complex c && rl == c.rl && im == c.im;
    }
    @Override
    public int hashCode() {
        return Objects.hash(rl, im);
    }
    @Override
    public String toString() {
        return rl + "+" + im + "i";
    }

    public double getRl() {
        return rl;
    }

    public double getIm() {
        return im;
    }

    public void setRl(double rl) {
        this.rl = rl;
    }

    public void setIm(double im) {
        this.im = im;
    }

    private double rl;
    private double im;

}
