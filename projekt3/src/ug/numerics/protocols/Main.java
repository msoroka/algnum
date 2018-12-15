package ug.numerics.protocols;

import java.io.FileNotFoundException;

public class Main {

    static int N = 60;
    static int YES = 10;
    static int NO = 10;
    static int UN = N - YES - NO;
    static int ITERATIONS = 500;
    static int MONTE_CARLO_TESTS = 20;
    static boolean OPTIMIZATION = true;
    static boolean TESTS = true;

    //Precyzje, dla ktorych nalezy przeprowadzic testy to 6, 10 oraz 14
    static int PRECISION = 20;

    static int COMBINATIONS = countCombinations();
    static int resultPosition = 0;
    static double[] jacobiResult = new double[COMBINATIONS];
    static double[] gaussSeidelResult = new double[COMBINATIONS];
    static double[] gaussResult = new double[COMBINATIONS];
    static double[] resultsJacobi = new double[COMBINATIONS];
    static double[][] matrix = new double[COMBINATIONS][COMBINATIONS];
    static double[][] matrixJacobi = new double[COMBINATIONS][COMBINATIONS];
    static double pairsCount = (N * (N - 1)) / 2;
    static State[] states = setStates();

    static long start = 0;
    static long elapsedTime = 0;

    public static void main(String[] args) throws FileNotFoundException {


        System.out.println("Parameters: ");
        System.out.println("N:              " + N);
        System.out.println("YES:            " + YES);
        System.out.println("NO:             " + NO);
        System.out.println("PRECISION:      " + PRECISION);
        System.out.println("OPTIMITZATION:  " + OPTIMIZATION);
        System.out.println();

        executeCalculations();

        System.out.println();
        System.out.print("Gauss partial method:         ");
        System.out.printf("%." + PRECISION + "f \n", gaussResult[resultPosition]);
        System.out.print("Jacobi method:                ");
        System.out.printf("%." + PRECISION + "f \n", jacobiResult[resultPosition]);
        System.out.print("Gauss-Seidel partial method:  ");
        System.out.printf("%." + PRECISION + "f \n", gaussSeidelResult[resultPosition]);

        System.out.println();
        System.out.print("Error between Gauss and Jacobii:         ");
        System.out.printf("%." + PRECISION + "f \n", error(gaussResult[resultPosition], jacobiResult[resultPosition]));
        System.out.print("Error between Gauss and Gauss-Seidel:    ");
        System.out.printf("%." + PRECISION + "f \n", error(gaussResult[resultPosition], gaussSeidelResult[resultPosition]));
        System.out.print("Error between Jacobii and Gauss-Seidel:  ");
        System.out.printf("%." + PRECISION + "f \n", error(gaussSeidelResult[resultPosition], jacobiResult[resultPosition]));
        System.out.println();
        System.out.println("Error between Gauss and Jacobii:         " + error(gaussResult[resultPosition], jacobiResult[resultPosition]));
        System.out.println("Error between Gauss and Gauss-Seidel:    " + error(gaussResult[resultPosition], gaussSeidelResult[resultPosition]));
        System.out.println("Error between Jacobii and Gauss-Seidel:  " + error(gaussSeidelResult[resultPosition], jacobiResult[resultPosition]));

        if(TESTS){
            String fileName = "MonteCarlo for ITER_" + MONTE_CARLO_TESTS + "N_" + N + " YES_" + YES + " NO_" + NO + ".csv";
            new MonteCarlo(N, MONTE_CARLO_TESTS, YES, NO, gaussResult[resultPosition], jacobiResult[resultPosition], gaussSeidelResult[resultPosition], fileName);
        }
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
        System.out.println("Gauss-Seidel time:         " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        jacobiResult = jacobiMethod(matrixJacobi, resultsJacobi, COMBINATIONS, ITERATIONS, states);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Jacobi time:        " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        gaussResult = gaussWithPartialChoice(matrix, gaussResult, COMBINATIONS, states);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Gauss time:  " + elapsedTime / 1000F + "s.");

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

    public static double[] gaussSeidelMethod(double A[][], double b[], int z, int iter, State states[]) {

        double[] results;
        double[] results2;
        results=fillVectorWithZeros();
        results2=fillVectorWithZeros();
        double sum=0,sum2=0;




        for(int j=0;j<ITERATIONS;j++) {
            results2=results;
            for (int i = 0; i < COMBINATIONS; i++) {
                sum=0;
                sum2=0;
                for(int k=0;k<i;k++){

                        sum += (A[i][k]*(results[k])) ;
                    }


                    for(int n=i+1;n<COMBINATIONS;n++){

                            sum2 += (A[i][n]*(results2[n])) ;

                    }
                    results[i]= (-sum -sum2 +b[i])/A[i][i];

            }








        }

        return results;

    }

    public static double[] jacobiMethod(double[][] A, double[] b, int num, int iter, State[] states) {


        double[] results;
        double[] results2;
        results=fillVectorWithZeros();
        results2=fillVectorWithZeros();
        double sum=0;




            for(int j=0;j<ITERATIONS;j++) {
            for (int i = 0; i < COMBINATIONS; i++) {
                sum=0;
                for(int k=0;k<COMBINATIONS;k++){
                    if(i!=k) {
                        sum += (A[i][k]*(results2[k])) ;
                    }
                }

                results[i]=(b[i]-sum)/ A[i][i];
            }
            results2=results;

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
                if(OPTIMIZATION && matrix[j][i] == 0) {
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
}