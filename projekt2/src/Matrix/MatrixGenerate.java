package Matrix;

public class MatrixGenerate {
    private static int factorial(int i) {
        if (i < 1)
            return 1;
        else
            return i * factorial(i - 1);
    }

    public double[][] matrixGenerate(int n) {

        int size = 0;

        double probability;
        probability = 2 / (n * (n - 1));
        for (int i = 0; i <= n + 1; n++) {
            size += i;
        }
        size--;

        double[][] Matrix = new double[size][size];
        double[][] Results = new double[size][1];


        for (int i = 0; i < n; n++) {

            Results[i][1] = 0;
        }
        Map<>

        for (int i = size-n; i < n; n++) {

            Results[i][1] = 0;
        }

        return Results;
    }

}
