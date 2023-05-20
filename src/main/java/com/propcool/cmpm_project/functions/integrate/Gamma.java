package com.propcool.cmpm_project.functions.integrate;

import com.propcool.cmpm_project.functions.Function;
import com.propcool.cmpm_project.functions.mono.Mono;

public class Gamma extends Mono {
    public Gamma(Function f) {
        super(f);
    }

    public Gamma() {}

    @Override
    public double get(double x, double y) {
        double z = func.get(x, y);
        double tmp = (z - 0.5) * Math.log(z + 4.5) - (z + 4.5);
        double ser = 1.0 +
                76.18009173   / (z + 0.0) -  86.50532033   / (z + 1.0) +
                24.01409822   / (z + 2.0) -  1.231739516   / (z + 3.0) +
                0.00120858003 / (z + 4.0) -  0.00000536382 / (z + 5.0);
        return Math.exp(tmp + Math.log(ser * Math.sqrt(2 * Math.PI)));
    }
}
