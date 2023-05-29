package com.propcool.cmpm_project.util;

public class SystemSolver {
    public double[] solve(double[][] A, double[] B) {
        int n = A.length;
        double[] X = new double[n];

        double D = det(A);
        if (D == 0) {
            throw new RootException("Корни не найдены");
        }

        for (int i = 0; i < n; i++) {
            double[][] Ai = replaceCol(A, i, B);
            double Di = det(Ai);
            X[i] = Di / D;
        }

        return X;
    }

    private double det(double[][] A) {
        int n = A.length;
        if (n == 1) {
            return A[0][0];
        }

        double det = 0;
        int sign = 1;

        for (int j = 0; j < n; j++) {
            double[][] M = minor(A, 0, j);
            det += sign * A[0][j] * det(M);
            sign *= -1;
        }

        return det;
    }

    private double[][] minor(double[][] A, int i, int j) {
        int n = A.length;
        double[][] M = new double[n - 1][n - 1];

        for (int k = 0; k < n; k++) {
            if (k == i) {
                continue;
            }

            for (int l = 0; l < n; l++) {
                if (l == j) {
                    continue;
                }

                int p = k < i ? k : k - 1;
                int q = l < j ? l : l - 1;

                M[p][q] = A[k][l];
            }
        }

        return M;
    }

    private double[][] replaceCol(double[][] A, int j, double[] B) {
        int n = A.length;
        double[][] Ai = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                if (k == j) {
                    Ai[i][k] = B[i];
                } else {
                    Ai[i][k] = A[i][k];
                }
            }
        }

        return Ai;
    }
}

