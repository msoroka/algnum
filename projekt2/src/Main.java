import Matrix.MyMatrix;
import Matrix.Randomizer;

public class Main {

    final static int SIZE = 500;
    static long start = 0;
    static long elapsedTime = 0;

    public static void main(String[] args) {
        Randomizer[][] randomMatrix = new Randomizer().generateRandomMatrix(SIZE);
        Randomizer[][] randomVector = new Randomizer().generateRandomVector(SIZE);

//        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
//        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
//        floatMatrix.generate();
//        floatVector.generate();
//
//        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
//        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);
//        doubleMatrix.gener ate();
//        doubleVector.generate();

        start = System.currentTimeMillis();
        doubleWithFullChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Double z pełnym wyborem: " + elapsedTime / 1000F + "s.\n");

        start = System.currentTimeMillis();
        doubleWithPartialChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Double z częściowym wyborem: " + elapsedTime / 1000F + "s.\n");

        start = System.currentTimeMillis();
        doubleWithoutChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Double bez wyboru elementu: " + elapsedTime / 1000F + "s.\n");

        start = System.currentTimeMillis();
        floatWithFullChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Float z pełnym wyborem: " + elapsedTime / 1000F + "s.\n");

        start = System.currentTimeMillis();
        floatWithPartialChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Float z częściowym wyborem: " + elapsedTime / 1000F + "s.\n");

        start = System.currentTimeMillis();
        floatWithoutChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Float bez wyboru elementu: " + elapsedTime / 1000F + "s.\n");
    }

    public static void floatWithoutChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
        MyMatrix<Float> floatMatrixCopy = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);

        floatMatrix.generate();
        floatVector.generate();
        floatMatrixCopy.generate();

//        System.out.println("Macierz:");
//        System.out.println(floatMatrix);
//        System.out.println("Wektor:");
//        System.out.println(floatVector);



//        System.out.println("wektor podany floatWithoutCHoice"+floatVector);
        float vectorLength;
        MyMatrix<Float> floatResult = new MyMatrix<>(Float.class, SIZE, 1);
        vectorLength= floatResult.errorCalculationFloat(floatVector);
        floatResult = floatResult.gaussWithoutChoice(floatMatrix, floatVector,floatMatrixCopy);
        float vectorLengthWithError;
        float error;



        vectorLengthWithError = floatResult.errorCalculationFloat(floatResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
//        System.out.println(vectorLength);
//        System.out.println(vectorLengthWithError);
        System.out.println("Błąd bezwględny: " + error);

    }

    public static void doubleWithoutChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);
        MyMatrix<Double> doubleMatrixCopy = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        doubleMatrix.generate();
        doubleVector.generate();
        doubleMatrixCopy.generate();

//        System.out.println("wektor podany doubleWithoutChoice"+doubleVector);
        double vectorLength;

        MyMatrix<Double> doubleResult = new MyMatrix<>(Double.class, SIZE, 1);
        vectorLength= doubleResult.errorCalculationDouble(doubleVector);

        doubleResult = doubleResult.gaussWithoutChoice(doubleMatrix, doubleVector,doubleMatrixCopy);


        double vectorLengthWithError;
        double error;



        vectorLengthWithError = doubleResult.errorCalculationFloat(doubleResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
//        System.out.println(vectorLength);
//        System.out.println(vectorLengthWithError);
        System.out.println("Błąd bezwględny: " + error);
    }

    public static void floatWithPartialChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
        MyMatrix<Float> floatMatrixCopy = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        floatMatrix.generate();
        floatVector.generate();
        floatMatrixCopy.generate();

//        System.out.println("wektor podany floatWithPartialChoice"+floatVector);
        float vectorLength;
        MyMatrix<Float> floatResult = new MyMatrix<>(Float.class, SIZE, 1);
        vectorLength= floatResult.errorCalculationFloat(floatVector);
        floatResult = floatResult.gaussWithPartialChoice(floatMatrix, floatVector,floatMatrixCopy);

        float vectorLengthWithError;
        float error;



        vectorLengthWithError = floatResult.errorCalculationFloat(floatResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
//        System.out.println(vectorLength);
//        System.out.println(vectorLengthWithError);
        System.out.println("Błąd bezwględny: " + error);
    }

    public static void doubleWithPartialChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);
        MyMatrix<Double> doubleMatrixCopy = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        doubleMatrix.generate();
        doubleVector.generate();
        doubleMatrixCopy.generate();

//        System.out.println("wektor podany doubleWithPartialChoice"+doubleVector);
        double vectorLength;

        MyMatrix<Double> doubleResult = new MyMatrix<>(Double.class, SIZE, 1);
        vectorLength= doubleResult.errorCalculationDouble(doubleVector);

        doubleResult = doubleResult.gaussWithPartialChoice(doubleMatrix, doubleVector,doubleMatrixCopy);


        double vectorLengthWithError;
        double error;



        vectorLengthWithError = doubleResult.errorCalculationFloat(doubleResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
//        System.out.println(vectorLength);
//        System.out.println(vectorLengthWithError);
        System.out.println("Błąd bezwględny: " + error);
    }
    public static void floatWithFullChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
        MyMatrix<Float> floatMatrixCopy = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);

        floatMatrix.generate();
        floatVector.generate();
        floatMatrixCopy.generate();

//        System.out.println("wektor podany floatWithFullChoice"+floatVector);
        float vectorLength;
        MyMatrix<Float> floatResult = new MyMatrix<>(Float.class, SIZE, 1);
        vectorLength= floatResult.errorCalculationFloat(floatVector);
        floatResult = floatResult.gaussWithFullChoice(floatMatrix, floatVector,floatMatrixCopy);

        float vectorLengthWithError;
        float error;



        vectorLengthWithError = floatResult.errorCalculationFloat(floatResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
//        System.out.println(vectorLength);
//        System.out.println(vectorLengthWithError);
        System.out.println("Błąd bezwględny: " + error);
    }
    public static void doubleWithFullChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);
        MyMatrix<Double> doubleMatrixCopy = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);

        doubleMatrix.generate();
        doubleVector.generate();
        doubleMatrixCopy.generate();

//        System.out.println("wektor podany doubleWithFullChoice"+doubleVector);
        double vectorLength;

        MyMatrix<Double> doubleResult = new MyMatrix<>(Double.class, SIZE, 1);
        vectorLength= doubleResult.errorCalculationDouble(doubleVector);

        doubleResult = doubleResult.gaussWithFullChoice(doubleMatrix, doubleVector,doubleMatrixCopy);


        double vectorLengthWithError;
        double error;



        vectorLengthWithError = doubleResult.errorCalculationFloat(doubleResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
//        System.out.println(vectorLength);
//        System.out.println(vectorLengthWithError);
        System.out.println("Błąd bezwględny: " + error);
//
    }


}
