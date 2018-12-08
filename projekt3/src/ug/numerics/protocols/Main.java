package ug.numerics.protocols;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Main {

    static int N = 120;
    static int YES = 50;
    static int NO = 55;
    static int UN = N - YES - NO;
    static int ITERATIONS = 1000;
    static int MONTE_CARLO_TESTS = 100;

    //Precyzje, dla ktorych nalezy przeprowadzic testy to 6, 10 oraz 14
    static int PRECISION = 6;

    final static int START_OF_RANGE = 20;
    final static int END_OF_RANGE = 120;

    static int COMBINATIONS = countCombinations();
    static int resultPosition = 0;
    static double[] jacobiResult = new double[COMBINATIONS];
    static double[] gaussSeidelResult = new double[COMBINATIONS];
    static double[] gaussResult = new double[COMBINATIONS];
    static double[] resultsJacobi = new double[COMBINATIONS];
    static double[] resultsGaussMethod = new double[MONTE_CARLO_TESTS];
    static double[] resultsJacobiMethod = new double[MONTE_CARLO_TESTS];
    static double[] resultsGaussSeidelMethod = new double[MONTE_CARLO_TESTS];
    static double[][] matrix = new double[COMBINATIONS][COMBINATIONS];
    static double[][] matrixJacobi = new double[COMBINATIONS][COMBINATIONS];
    static double pairsCount = (N * (N - 1)) / 2;
    static State[] states = setStates();

    static long start = 0;
    static long elapsedTime = 0;

    public static void main(String[] args) throws FileNotFoundException {
        executeCalculations();

        System.out.println();
        System.out.print("Gauss partial method: ");
        System.out.printf("%." + PRECISION + "f \n",gaussResult[resultPosition]);
        System.out.print("Jacobi method: ");
        System.out.printf("%." + PRECISION + "f \n",jacobiResult[resultPosition]);
        System.out.print("Gauss-Seidel partial method: ");
        System.out.printf("%." + PRECISION + "f \n",gaussSeidelResult[resultPosition]);

        System.out.println();
        System.out.print("Error between Gauss and Jacobii: ");
        System.out.printf("%." + PRECISION + "f \n",error(gaussResult[resultPosition], jacobiResult[resultPosition]));
        System.out.print("Error between Gauss and Gauss-Seidel: ");
        System.out.printf("%." + PRECISION + "f \n",error(gaussResult[resultPosition], gaussSeidelResult[resultPosition]));
        System.out.print("Error between Jacobii and Gauss-Seidel: ");
        System.out.printf("%." + PRECISION + "f \n",error(gaussSeidelResult[resultPosition], jacobiResult[resultPosition]));

        //Uncomment to perform tests
//        executeTests();
    }

    public static void executeCalculations() {
        gaussResult = fillVectorWithZeros();
        matrix = fillMatrixWithZeros();
        matrixJacobi = matrix;
        resultsJacobi = gaussResult;
        buildMatrix();

        start = System.currentTimeMillis();
        gaussSeidelResult = gaussSeidelMethod(matrixJacobi, resultsJacobi, COMBINATIONS, ITERATIONS, states);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Gauss time: " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        jacobiResult = jacobiMethod(matrixJacobi, resultsJacobi, COMBINATIONS, ITERATIONS, states);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Jacobi time: " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        gaussResult = gaussWithPartialChoice(matrix, gaussResult, COMBINATIONS, states);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Gauss-Seidel time: " + elapsedTime / 1000F + "s.");

        for (int i = 0; i < COMBINATIONS; i++) {
            if (states[i].getYes() == YES && states[i].getNo() == NO) {
                resultPosition = i;
                break;
            }
        }
    }

    public static double error(double length, double length2) {
        return Math.abs(length - length2);
    }

    public static int countCombinations() {
        int combinations = 0;

        for (int i = 0; i <= N; i++) {
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

    public static double[] gaussSeidelMethod(double A[][], double b[], int n, int iter, State states[]) {

        double L[][];
        double D[][];
        double U[][];
        double x[];

        int i, j, k;

        L = new double[n][n];
        D = new double[n][n];
        U = new double[n][n];
        x = new double[n];

        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++) {
                if (i < j) {
                    U[i][j] = A[i][j];
                } else if (i > j) {
                    L[i][j] = A[i][j];
                } else {
                    D[i][j] = A[i][j];
                }
            }


        for (i = 0; i < n; i++)
            D[i][i] = 1 / D[i][i];

        for (i = 0; i < n; i++)
            b[i] *= D[i][i];


        for (i = 0; i < n; i++)
            for (j = 0; j < i; j++)
                L[i][j] *= D[i][i];


        for (i = 0; i < n; i++)
            for (j = i + 1; j < n; j++)
                U[i][j] *= D[i][i];


        for (i = 0; i < n; i++)
            x[i] = 0;


        for (k = 0; k < iter; k++)
            for (i = 0; i < n; i++) {
                x[i] = b[i];
                for (j = 0; j < i; j++)
                    x[i] -= L[i][j] * x[j];
                for (j = i + 1; j < n; j++)
                    x[i] -= U[i][j] * x[j];
            }

//        System.out.println("Gauss-Seidel results: ");
//        for (i = 0; i < n; i++)
//            System.out.println("x[" + (i + 1) + "] = " + x[i]);

        return x;
    }

    public static double[] jacobiMethod(double[][] A, double[] b, int num, int iter, State[] states) {

        double M[][];
        double N[];
        double x1[];
        double x2[];

        int i, j, k;

        M = new double[num][num];
        N = new double[num];

        x1 = new double[num];
        x2 = new double[num];

        for (i = 0; i < num; i++)
            N[i] = 1 / A[i][i];


        for (i = 0; i < num; i++)
            for (j = 0; j < num; j++)
                if (i == j)
                    M[i][j] = 0;
                else
                    M[i][j] = -(A[i][j] * N[i]);

        for (i = 0; i < num; i++)
            x1[i] = 0;


        for (k = 0; k < iter; k++) {
            for (i = 0; i < num; i++) {
                x2[i] = N[i] * b[i];
                for (j = 0; j < num; j++)
                    x2[i] += M[i][j] * x1[j];
            }
            for (i = 0; i < num; i++)
                x1[i] = x2[i];
        }

//        System.out.println("Jacobi results: ");
//        for (i = 0; i < num; i++)
//            System.out.println("x[" + (i + 1) + "] = " + x1[i]);


        return x1;
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

    public static void executeTests() throws FileNotFoundException {
        System.out.print("\nMonte carlo processing.. ");

        PrintWriter pw = new PrintWriter(new File("monte_carlo_test.csv"));
        StringBuilder sb = new StringBuilder();

        sb.append("TEST");
        sb.append(',');
        sb.append("N");
        sb.append(',');
        sb.append("Yes");
        sb.append(',');
        sb.append("No");
        sb.append(',');
        sb.append("Gauss results");
        sb.append(',');
        sb.append("Gauss-Seidel results");
        sb.append(',');
        sb.append("Jacobi results");
        sb.append('\n');

        for (int i = 0; i < MONTE_CARLO_TESTS; i++) {
            Random rand = new Random();
            int randomEvenNumber = START_OF_RANGE + rand.nextInt((END_OF_RANGE - START_OF_RANGE) / 2) * 2;

            N = randomEvenNumber;
            YES = NO = randomEvenNumber / 2;

            COMBINATIONS = countCombinations();
            pairsCount = (N * (N - 1)) / 2;
            states = setStates();
            matrix = new double[COMBINATIONS][COMBINATIONS];
            gaussResult = new double[COMBINATIONS];
            gaussResult = fillVectorWithZeros();
            matrix = fillMatrixWithZeros();
            matrixJacobi = matrix;
            resultsJacobi = gaussResult;

            buildMatrix();

            gaussSeidelResult = gaussSeidelMethod(matrixJacobi, resultsJacobi, COMBINATIONS, ITERATIONS, states);
            jacobiResult = jacobiMethod(matrixJacobi, resultsJacobi, COMBINATIONS, ITERATIONS, states);
            gaussResult = gaussWithPartialChoice(matrix, gaussResult, COMBINATIONS, states);

            for (int j = 0; j < COMBINATIONS; j++) {
                if (states[j].getYes() == YES && states[j].getNo() == NO) {
                    resultPosition = j;
                    break;
                }
            }

            resultsGaussMethod[i] = gaussResult[resultPosition];
            resultsJacobiMethod[i] = jacobiResult[resultPosition];
            resultsGaussSeidelMethod[i] = gaussSeidelResult[resultPosition];

            sb.append(i);
            sb.append(',');
            sb.append(N);
            sb.append(',');
            sb.append(YES);
            sb.append(',');
            sb.append(NO);
            sb.append(',');
            sb.append(resultsGaussMethod[i]);
            sb.append(',');
            sb.append(resultsGaussSeidelMethod[i]);
            sb.append(',');
            sb.append(resultsJacobiMethod[i]);
            sb.append('\n');
        }

        pw.write(sb.toString());
        pw.close();
        differenceInMonteCarlo();

        System.out.print("done.\n");
    }

    public static void differenceInMonteCarlo() {
        Arrays.sort(resultsGaussMethod);
        Arrays.sort(resultsGaussSeidelMethod);
        Arrays.sort(resultsJacobiMethod);

        System.out.println("Biggest difference in Gauss method: " + error(resultsGaussMethod[0], resultsGaussMethod[resultsGaussMethod.length - 1]));
        System.out.println("Biggest difference in Jacobi method: " + error(resultsJacobiMethod[0], resultsJacobiMethod[resultsJacobiMethod.length - 1]));
        System.out.println("Biggest difference in Gauss-Seidel method: " + error(resultsGaussSeidelMethod[0], resultsGaussSeidelMethod[resultsGaussSeidelMethod.length - 1]));
    }
}