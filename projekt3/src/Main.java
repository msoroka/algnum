public class Main {

    final static int N = 4;
    final static int YES = 1;
    final static int NO = 1;
    final static int Un = N - YES - NO;
    static int COMBINATIONS = 0;

    public static void main(String[] args) {
        countCombinations();
        double gaussLength,gaussSeidelaLength,jacobiLength;
        double[] x1 = new double[COMBINATIONS];

        double[] x2 = new double[COMBINATIONS];
        double[][] matrix = new double[COMBINATIONS][COMBINATIONS];
        double[][] matrixJacobi = new double[COMBINATIONS][COMBINATIONS];
        double[] results = new double[COMBINATIONS];
        double[] resultsJacobi = new double[COMBINATIONS];
        State[] states = new State[COMBINATIONS];
        int k = -1;
        double pairsCount = 0.0;
        pairsCount = (N * (N - 1)) / 2;
        for (int i = 0; i < COMBINATIONS; i++) {
            results[i] = 0;
        }
        for (int i = 0; i < COMBINATIONS; i++) {

            for (int j = 0; j < COMBINATIONS; j++) {
                matrix[i][j] = 0;
            }
        }
        for (int i = 0; i <= N; i++) {

            int no = 0;
            int j = 0;
            do {
                k++;
                states[k] = new State();
                states[k].setNo(no);
                states[k].setYes(i);
//                System.out.println(i +"  "+ no);
                j = i + no;
                no++;

            } while (j < N);
        }
//        for (int i=0;i<COMBINATIONS;i++)
//        {
//            System.out.println(states[i].getYes() + " " +states[i].getNo());
//
//        }
        matrixJacobi = matrix;
        resultsJacobi = results;


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
                results[i] = 1;
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
//               System.out.println(changeCol+"!!!!!!");
//               System.out.println(temp);
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
//               System.out.println(changeCol + "!!!!!!");
//               System.out.println(temp);
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
//               System.out.println(changeCol + "!!!!!!");
//               System.out.println(temp);

            }
        }
        /*
        results to wektor wynikow z gausaa zwykłego
              x2 to wektor wynikow z gausaa seszela xD
              x1 to wektor wynikow z jacobiego

         sa tez dlugosci wektorow  i potem funckja error ktora po prostu liczy błąd bezwzględny
         
         ogólnie w mainie jest wyliczania tej macierzy  współczyników nazywa się matrix resaultsjacobi i matrixJacobi to po prostu kopie ale w sumie niepotrzebnie
         */

        x2 = gaussS(matrixJacobi, resultsJacobi, COMBINATIONS, 20, states);
        System.out.println("\n \n \n \n \n ");
        x1 = jacobi(matrixJacobi, resultsJacobi, COMBINATIONS, 20, states);
        partialChoice(matrix, results, COMBINATIONS, states);
        jacobiLength= vectorLength(x1,COMBINATIONS);
        gaussSeidelaLength= vectorLength(x2,COMBINATIONS);
        gaussLength= vectorLength(results,COMBINATIONS);

        System.out.println("   jacobi-->"+jacobiLength+"\n gausss Scośtam "+gaussSeidelaLength+"\n zwykly gauss -->"+gaussLength);
        System.out.println("\nerror jacobii gauss  "+error(jacobiLength,gaussLength));
        System.out.println("\nerror gaussSeidela jacobii  "+error(jacobiLength,gaussSeidelaLength));
        System.out.println("\nerror gaussSeidela gauss  "+error(gaussSeidelaLength,gaussLength));

        for (int m = 0; m < COMBINATIONS; m++) {

            if (states[m].getYes() == YES && states[m].getNo() == NO) {
                System.out.println("\n\nzwykly gauss po prostu wyniki  "+results[m]);
                break;
            }
        }


        System.out.println();
//        for (int i=0;i<COMBINATIONS;i++){
//
//
//            System.out.println();
//            for (int j=0;j<COMBINATIONS;j++){
//                System.out.print("  " +matrixJacobi[i][j]);
//
//            }
//        }



        for (int m = 0; m < COMBINATIONS; m++) {

            if (states[m].getYes() == YES && states[m].getNo() == NO) {
                System.out.println("jacobiii po prostu wyniki  "+x1[m]);
                break;
            }
        }
        for (int m = 0; m < COMBINATIONS; m++) {

            if (states[m].getYes() == YES && states[m].getNo() == NO) {
                System.out.println("\ngauss scośtam po prostu wyniki  "+x2[m]);
                break;
            }
        }
    }
    public static double error(double length,double length2){
        double errorValue;
        errorValue=Math.abs(length-length2);

        return errorValue;
    }

    public static void countCombinations() {
        for (int i = 0; i <= N; i++) {
            COMBINATIONS += (i + 1);
        }
    }

    public static double[] gaussS(double A[][], double b[], int n, int iter, State states[]) {


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

        System.out.println("Wynik");
        for (i = 0; i < n; i++)
            System.out.println("x[" + (i + 1) + "] = " + x[i]);

        return x;
    }

    public static double vectorLength(double vector[], int n){

        double sum=0;
        for(int i=0;i<n;i++){
            sum+=Math.pow(vector[i],2);
        }
        sum= Math.sqrt(sum);
        return sum;
    }
    public static double[] jacobi(double A[][], double b[], int num, int iter, State states[]) {

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

        for (i = 0; i < num; i++)
            System.out.println("x[" + (i + 1) + "] = " + x1[i]);


        return x1;
    }

    public static void partialChoice(double matrix[][], double results[], int numCalc, State states[]) {
        double[][] matrixCopy = new double[numCalc][numCalc];
        double[] resultsCheack = new double[numCalc];
//        for (int i=0;i<numCalc;i++){
////            System.out.println(results[i]);
//        }
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
            resultsCheack[i] = sum;
        }

//        for (int i=0;i<numCalc;i++){
//
//
//            System.out.println();
//            for (int j=0;j<numCalc;j++){
//                System.out.print("  " +matrix[i][j]);
//
//            }
//        }
//        System.out.println();



    }
}