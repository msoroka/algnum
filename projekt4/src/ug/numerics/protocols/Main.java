package ug.numerics.protocols;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

    static int N = 20;
    static int YES = 6;
    static int NO = 6;
    static int UN = N - YES - NO;
    static int ITERATIONS = 2000;
    static boolean OPTIMIZATION = true;

    static int PRECISION = 14;

    static int COMBINATIONS = countCombinations(N);
    static int resultPosition = 0;
    static double[] jacobiResult = new double[COMBINATIONS];
    static double[] gaussSeidelResult = new double[COMBINATIONS];
    static double[] gaussResult = new double[COMBINATIONS];
    static double[] resultsJacobi = new double[COMBINATIONS];
    static double[][] matrix = new double[COMBINATIONS][COMBINATIONS];
    static double[][] matrixJacobi = new double[COMBINATIONS][COMBINATIONS];

    static double pairsCount = (N * (N - 1)) / 2;

    static State[] states;

    static long start = 0;
    static long elapsedTime = 0;

    static double gaussOptTime = 0;
    static double gaussTime = 0;
    static double gaussSeidelTime = 0;
    static double buildMatrixTime = 0;

    static int matrixSize = 0;

    static double[][] gaussSeidelMatrixAprox = new double[10][];
    static double[][] gaussMatrixAprox = new double[13][];
    static double[][] gaussOptMatrixAprox = new double[10][190];

    public static void main(String[] args) throws FileNotFoundException {

        int test = 0;
        int i = 10;
        int j = 1;

        PrintWriter pw = new PrintWriter(new File("czasy.csv"));
        StringBuilder sb = new StringBuilder();

        sb.append("Rozmiar układu");
        sb.append(',');
        sb.append("Gauss-Seidel");
        sb.append(',');
        sb.append("Gauss");
        sb.append(',');
        sb.append("Gauss z optymalizacją");
        sb.append(',');
        sb.append("Czas budowania");
        sb.append(',');
        sb.append("Gauss-Seidel aprox");
        sb.append(',');
        sb.append("Gauss aprox");
        sb.append(',');
        sb.append("Gauss z optymalizacją aprox");
        sb.append('\n');

        while (test <= 6000) {
            N = i;
            YES = i / 3;
            NO = i / 3;
            COMBINATIONS = countCombinations(N);
            states = setStates();

        System.out.println(COMBINATIONS);

            resultPosition = 0;
            jacobiResult = new double[COMBINATIONS];
            gaussSeidelResult = new double[COMBINATIONS];
            gaussResult = new double[COMBINATIONS];
            resultsJacobi = new double[COMBINATIONS];
            matrix = new double[COMBINATIONS][COMBINATIONS];
            matrixJacobi = new double[COMBINATIONS][COMBINATIONS];
            pairsCount = (N * (N - 1)) / 2;

            executeCalculations();

            System.out.println(gaussOptTime);

            i++;
            test = COMBINATIONS;

            sb.append(COMBINATIONS);
            sb.append(',');
            sb.append(gaussSeidelTime);
            sb.append(',');
            sb.append(gaussTime);
            sb.append(',');
            sb.append(gaussOptTime);
            sb.append(',');
            sb.append(buildMatrixTime);
            sb.append(',');
            sb.append(countGaussSeidelAprox());
            sb.append(',');
            sb.append(countGaussAprox());
            sb.append(',');
            sb.append(countGaussOptAprox());
            sb.append(',');
            sb.append('\n');

            matrixSize++;
        }

        pw.write(sb.toString());
        pw.close();

    }

    public static void executeCalculations() {
        gaussResult = fillVectorWithZeros();
        matrix = fillMatrixWithZeros();
        matrixJacobi = matrix;
        resultsJacobi = gaussResult;

        start = System.currentTimeMillis();
        buildMatrix();
        elapsedTime = (System.currentTimeMillis() - start);
        buildMatrixTime = elapsedTime / 1000F;

        start = System.currentTimeMillis();
        gaussSeidelResult = gaussSeidelMethod(matrixJacobi, resultsJacobi, COMBINATIONS, ITERATIONS, states);
        elapsedTime = (System.currentTimeMillis() - start);
        gaussSeidelTime = elapsedTime / 1000F;
        gaussSeidelAprox();

        start = System.currentTimeMillis();
        gaussResult = gaussWithPartialChoice(matrix, gaussResult, COMBINATIONS, states);
        elapsedTime = (System.currentTimeMillis() - start);
        gaussTime = elapsedTime / 1000F;
        gaussAprox();

        start = System.currentTimeMillis();
        gaussResult = gaussWithPartialChoiceWithOptimization(matrix, gaussResult, COMBINATIONS, states);
        elapsedTime = (System.currentTimeMillis() - start);
        gaussOptTime = elapsedTime / 1000F;
        gaussOptAprox();

        for (int i = 0; i < COMBINATIONS; i++) {
            if (states[i].getYes() == YES && states[i].getNo() == NO) {
                resultPosition = i;
                break;
            }
        }
    }

    public static void gaussSeidelAprox() {
        int x = COMBINATIONS;
        double g_x = gaussSeidelTime;

        gaussSeidelMatrixAprox[0][matrixSize] = x;
        gaussSeidelMatrixAprox[1][matrixSize] = g_x;
        gaussSeidelMatrixAprox[2][matrixSize] = Math.pow(x, 0);
        gaussSeidelMatrixAprox[3][matrixSize] = Math.pow(x, 1);
        gaussSeidelMatrixAprox[4][matrixSize] = Math.pow(x, 2);
        gaussSeidelMatrixAprox[5][matrixSize] = Math.pow(x, 3);
        gaussSeidelMatrixAprox[6][matrixSize] = Math.pow(x, 4);
        gaussSeidelMatrixAprox[7][matrixSize] = g_x * gaussSeidelMatrixAprox[2][matrixSize];
        gaussSeidelMatrixAprox[8][matrixSize] = g_x * gaussSeidelMatrixAprox[3][matrixSize];
        gaussSeidelMatrixAprox[9][matrixSize] = g_x * gaussSeidelMatrixAprox[4][matrixSize];
    }

    public static void gaussAprox() {
        int x = COMBINATIONS;
        double g_x = gaussTime;

        gaussMatrixAprox[0][matrixSize] = x;
        gaussMatrixAprox[1][matrixSize] = g_x;
        gaussMatrixAprox[2][matrixSize] = Math.pow(x, 0);
        gaussMatrixAprox[3][matrixSize] = Math.pow(x, 1);
        gaussMatrixAprox[4][matrixSize] = Math.pow(x, 2);
        gaussMatrixAprox[5][matrixSize] = Math.pow(x, 3);
        gaussMatrixAprox[6][matrixSize] = Math.pow(x, 4);
        gaussMatrixAprox[7][matrixSize] = Math.pow(x, 5);
        gaussMatrixAprox[8][matrixSize] = Math.pow(x, 6);
        gaussMatrixAprox[9][matrixSize] = g_x * gaussMatrixAprox[2][matrixSize];
        gaussMatrixAprox[10][matrixSize] = g_x * gaussMatrixAprox[3][matrixSize];
        gaussMatrixAprox[11][matrixSize] = g_x * gaussMatrixAprox[4][matrixSize];
        gaussMatrixAprox[12][matrixSize] = g_x * gaussMatrixAprox[5][matrixSize];
    }

    public static void gaussOptAprox() {
        int x = COMBINATIONS;
        double g_x = gaussOptTime;

        gaussOptMatrixAprox[0][matrixSize] = x;
        gaussOptMatrixAprox[1][matrixSize] = g_x;
        gaussOptMatrixAprox[2][matrixSize] = Math.pow(x, 0);
        gaussOptMatrixAprox[3][matrixSize] = Math.pow(x, 1);
        gaussOptMatrixAprox[4][matrixSize] = Math.pow(x, 2);
        gaussOptMatrixAprox[5][matrixSize] = Math.pow(x, 3);
        gaussOptMatrixAprox[6][matrixSize] = Math.pow(x, 4);
        gaussOptMatrixAprox[7][matrixSize] = g_x * gaussOptMatrixAprox[2][matrixSize];
        gaussOptMatrixAprox[8][matrixSize] = g_x * gaussOptMatrixAprox[3][matrixSize];
        gaussOptMatrixAprox[9][matrixSize] = g_x * gaussOptMatrixAprox[4][matrixSize];
    }

    public static double countGaussSeidelAprox() {
        double x_0 = 0;
        double x_1 = 0;
        double x_2 = 0;
        double x_3 = 0;
        double x_4 = 0;
        double y_x_0 = 0;
        double y_x_1 = 0;
        double y_x_2 = 0;

        for (int i = 0; i < matrixSize; i++) {
            x_0 += gaussSeidelMatrixAprox[2][i];
            x_1 += gaussSeidelMatrixAprox[3][i];
            x_2 += gaussSeidelMatrixAprox[4][i];
            x_3 += gaussSeidelMatrixAprox[5][i];
            x_4 += gaussSeidelMatrixAprox[6][i];
            y_x_0 += gaussSeidelMatrixAprox[7][i];
            y_x_1 += gaussSeidelMatrixAprox[8][i];
            y_x_2 += gaussSeidelMatrixAprox[9][i];
        }

        double[][] matrix = {
                {x_0, x_1, x_2},
                {x_1, x_2, x_3},
                {x_2, x_3, x_4},
        };

        double[] vector = {y_x_0, y_x_1, y_x_2};

        double[] result = gaussWithoutChoice(matrix, vector);

        double time = 0;

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
            time = result[i] * Math.pow(COMBINATIONS, i);
        }
        System.out.println();

        return time;
    }

    public static double countGaussAprox() {
        double x_0 = 0;
        double x_1 = 0;
        double x_2 = 0;
        double x_3 = 0;
        double x_4 = 0;
        double x_5 = 0;
        double x_6 = 0;
        double y_x_0 = 0;
        double y_x_1 = 0;
        double y_x_2 = 0;
        double y_x_3 = 0;

        for (int i = 0; i < matrixSize; i++) {
            x_0 += gaussMatrixAprox[2][i];
            x_1 += gaussMatrixAprox[3][i];
            x_2 += gaussMatrixAprox[4][i];
            x_3 += gaussMatrixAprox[5][i];
            x_4 += gaussMatrixAprox[6][i];
            x_5 += gaussMatrixAprox[7][i];
            x_6 += gaussMatrixAprox[8][i];
            y_x_0 += gaussMatrixAprox[9][i];
            y_x_1 += gaussMatrixAprox[10][i];
            y_x_2 += gaussMatrixAprox[11][i];
            y_x_3 += gaussMatrixAprox[12][i];
        }

        double[][] matrix = {
                {x_0, x_1, x_2, x_3},
                {x_1, x_2, x_3, x_4},
                {x_2, x_3, x_4, x_5},
                {x_3, x_4, x_5, x_6},
        };

        double[] vector = {y_x_0, y_x_1, y_x_2, y_x_3};

        double[] result = gaussWithoutChoice(matrix, vector);

        System.out.println();

        double time = 0;

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
            time = result[i] * Math.pow(COMBINATIONS, i);
        }
        System.out.println();

        return time;
    }

    public static double countGaussOptAprox() {
        double x_0 = 0;
        double x_1 = 0;
        double x_2 = 0;
        double x_3 = 0;
        double x_4 = 0;
        double y_x_0 = 0;
        double y_x_1 = 0;
        double y_x_2 = 0;

        for (int i = 0; i < matrixSize; i++) {
            x_0 += gaussOptMatrixAprox[2][i];
            x_1 += gaussOptMatrixAprox[3][i];
            x_2 += gaussOptMatrixAprox[4][i];
            x_3 += gaussOptMatrixAprox[5][i];
            x_4 += gaussOptMatrixAprox[6][i];
            y_x_0 += gaussOptMatrixAprox[7][i];
            y_x_1 += gaussOptMatrixAprox[8][i];
            y_x_2 += gaussOptMatrixAprox[9][i];
        }

        double[][] matrix = {
                {x_0, x_1, x_2},
                {x_1, x_2, x_3},
                {x_2, x_3, x_4},
        };

        double[] vector = {y_x_0, y_x_1, y_x_2};

        double[] result = gaussWithoutChoice(matrix, vector);

        double time = 0;

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
            time = result[i] * Math.pow(COMBINATIONS, i);
        }
        System.out.println();

        return time;
    }

    public static int countCombinations(int n) {
        int combinations = 0;

        for (int i = 0; i <= n; i++) {
            combinations += (i + 1);
        }

        return combinations;
    }

    public static State[] setStates() {
        State[] states = new State[COMBINATIONS];
        int k = -1;

        for (int i = 0; i <= N; i++) {

            int no = 0;
            int j = 0;
            do {
                k++;
                states[k] = new State();
                states[k].setNo(no);
                states[k].setYes(i);
                j = i + no;
                no++;

            } while (j < N);
        }

        return states;
    }

    public static double[] fillVectorWithZeros() {
        double vector[] = new double[COMBINATIONS];

        for (int i = 0; i < COMBINATIONS; i++) {
            vector[i] = 0;
        }

        return vector;
    }

    public static double[][] fillMatrixWithZeros() {
        double matrix[][] = new double[COMBINATIONS][COMBINATIONS];

        for (int i = 0; i < COMBINATIONS; i++) {
            for (int j = 0; j < COMBINATIONS; j++) {
                matrix[i][j] = 0;
            }
        }

        return matrix;
    }

    public static double[] gaussSeidelMethod(double A[][], double b[], int z, int iter, State states[]) {
        double[] results;
        double[] results2;
        results = fillVectorWithZeros();
        results2 = fillVectorWithZeros();
        double sum = 0, sum2 = 0;

        for (int j = 0; j < ITERATIONS; j++) {
            results2 = results;
            for (int i = 0; i < COMBINATIONS; i++) {
                sum = 0;
                sum2 = 0;
                for (int k = 0; k < i; k++) {
                    sum += (A[i][k] * (results[k]));
                }
                for (int n = i + 1; n < COMBINATIONS; n++) {

                    sum2 += (A[i][n] * (results2[n]));
                }
                results[i] = (-sum - sum2 + b[i]) / A[i][i];
            }
        }

        return results;
    }

    public static double[] gaussWithPartialChoice(double matrix[][], double[] results, int numCalc, State states[]) {
        double[][] matrixCopy = new double[numCalc][numCalc];
        double[] resultCheck = new double[numCalc];
        for (int i = 0; i < numCalc; i++) {


            for (int j = 0; j < numCalc; j++) {
                matrixCopy[i][j] = matrix[i][j];
            }
        }


        int n = matrix.length;
        double temp;
        for (int i = 0; i < n; i++) {
            int max = i;

            for (int j = i + 1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[max][i])) {
                    max = j;
                }
            }

            for (int j = 0; j < matrix.length; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[max][j];
                matrix[max][j] = temp;
            }

            temp = results[i];
            results[i] = results[max];
            results[max] = temp;


            for (int j = i + 1; j < n; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                matrix[j][0] = matrix[j][0] - factor * results[i];

                for (int k = i; k < n; k++) {
                    matrix[j][k] = matrix[j][k] - factor * matrix[i][k];
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix[i][j] * results[j];
            }
            results[i] = results[i] - sum / matrix[i][i];
        }


        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += matrixCopy[i][j] * results[j];

            }
            resultCheck[i] = sum;
        }

        return results;
    }

    public static double[] gaussWithPartialChoiceWithOptimization(double matrix[][], double[] results, int numCalc, State states[]) {
        double[][] matrixCopy = new double[numCalc][numCalc];
        double[] resultCheck = new double[numCalc];
        for (int i = 0; i < numCalc; i++) {


            for (int j = 0; j < numCalc; j++) {
                matrixCopy[i][j] = matrix[i][j];
            }
        }


        int n = matrix.length;
        double temp;
        for (int i = 0; i < n; i++) {
            int max = i;

            for (int j = i + 1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[max][i])) {
                    max = j;
                }
            }

            for (int j = 0; j < matrix.length; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[max][j];
                matrix[max][j] = temp;
            }

            temp = results[i];
            results[i] = results[max];
            results[max] = temp;


            for (int j = i + 1; j < n; j++) {
                if (OPTIMIZATION && matrix[j][i] == 0) {
                    //skip calculation
                } else {
                    double factor = matrix[j][i] / matrix[i][i];
                    matrix[j][0] = matrix[j][0] - factor * results[i];

                    for (int k = i; k < n; k++) {
                        matrix[j][k] = matrix[j][k] - factor * matrix[i][k];
                    }
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix[i][j] * results[j];
            }
            results[i] = results[i] - sum / matrix[i][i];
        }


        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += matrixCopy[i][j] * results[j];

            }
            resultCheck[i] = sum;
        }

        return results;
    }

    public static void buildMatrix() {
        for (int i = 0; i < COMBINATIONS; i++) {
            int y, n, u, changeRow, changeCol = 0;
            double temp = 0.0;
            y = states[i].getYes();
            n = states[i].getNo();
            u = N - y - n;
            temp = (y * (y - 1) / 2) + (n * (n - 1) / 2) + (u * (u - 1) / 2);

            matrix[i][i] = temp / pairsCount;//pozostanie w tym samym stanie
            if (states[i].getYes() != 0 || states[i].getNo() != 0 && states[i].getNo() != N) {
                matrix[i][i] = matrix[i][i] - 1; // tutaj !!!- TU JEST PRZENOSZENIE NA DRUGĄ STRONĘ OGÓLNIE ZAKOMENTOWANE ŻEBY ZOBACZYĆ CZY MACIERZ SIĘ ZGADZA ALE DLA OBLICZEŃ BEDZIE TO TRZEBA ODKOMENTOWAC
            }
            if (states[i].getYes() == N) {
                gaussResult[i] = 1;
                matrix[i][i] = 1;
            }
            temp = 0;
            if (y != 0 && n != 0) {            // zmiejszenie ilosci tak i nie  tak spotyka sie z nie Y,N -> U,U

                for (int m = 0; m < COMBINATIONS; m++) {
                    if (states[m].getYes() == y - 1 && states[m].getNo() == n - 1) {
                        changeCol = m;

                        break;
                    }
                }
                temp = y * n / pairsCount;
                matrix[i][changeCol] = y * n / pairsCount;
            }

            if (y != 0 && N - y != n) {       //zwiekszenie tak
                for (int m = 0; m < COMBINATIONS; m++) {
                    if (states[m].getYes() == y + 1 && states[m].getNo() == n) {
                        changeCol = m;

                        break;
                    }
                }
                temp = y * u / pairsCount;
                matrix[i][changeCol] = y * u / pairsCount;

            }

            if (n != 0 && N - n != y) { // zwiekszanie nie
                for (int m = 0; m < COMBINATIONS; m++) {
                    if (states[m].getYes() == y && states[m].getNo() == n + 1) {
                        changeCol = m;

                        break;
                    }
                }
                temp = n * u / pairsCount;
                matrix[i][changeCol] = n * u / pairsCount;
            }
        }
    }

    public static double[] gaussWithoutChoice(double[][] matrix, double[] vector) {
        int n = vector.length;

        for (int p = 0; p < n; p++) {

            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(matrix[i][p]) > Math.abs(matrix[max][p])) {
                    max = i;
                }
            }
            double[] temp = matrix[p];
            matrix[p] = matrix[max];
            matrix[max] = temp;
            double t = vector[p];
            vector[p] = vector[max];
            vector[max] = t;
            for (int i = p + 1; i < n; i++) {
                double alpha = matrix[i][p] / matrix[p][p];
                vector[i] -= alpha * vector[p];
                for (int j = p; j < n; j++) {
                    matrix[i][j] -= alpha * matrix[p][j];
                }
            }
        }


        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += matrix[i][j] * x[j];
            }
            x[i] = (vector[i] - sum) / matrix[i][i];
        }
        return x;
    }
}