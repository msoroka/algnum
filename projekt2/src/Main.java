import Matrix.MyMatrix;
import Matrix.Randomizer;

public class Main {

    final static int SIZE = 200;
    static long start;
    static long elapsedTime;

    public static void main(String[] args) {
        Randomizer[][] randomMatrix = new Randomizer().generateRandomMatrix(SIZE);
        Randomizer[][] randomVector = new Randomizer().generateRandomVector(SIZE);

//        floatWithoutChoice(randomMatrix, randomVector);
//        doubleWithoutChoice(randomMatrix, randomVector);
//        floatWithPartialChoice(randomMatrix, randomVector);
//        doubleWithPartialChoice(randomMatrix, randomVector);

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        start = System.currentTimeMillis();
//        floatWithoutChoice(randomMatrix, randomVector);
//        doubleWithoutChoice(randomMatrix, randomVector);
//        floatWithPartialChoice(randomMatrix, randomVector);
//        doubleWithPartialChoice(randomMatrix, randomVector);
//        elapsedTime = (System.currentTimeMillis() - start);
//        System.out.println("Przedskoczek: " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        floatWithPartialChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Float z częściowym wyborem: " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        doubleWithPartialChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Double z częściowym wyborem: " + elapsedTime / 1000F + "s.");
//
            start = System.currentTimeMillis();
            floatWithoutChoice(randomMatrix, randomVector);
            elapsedTime = (System.currentTimeMillis() - start);
            System.out.println("Float bez wyboru elementu: " + elapsedTime / 1000F + "s.");
//
        start = System.currentTimeMillis();
        doubleWithoutChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Double bez wyboru elementu: " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        floatWithFullChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("float z pełnym wyborem: " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        doubleWithFullChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("double z pełnym wyborem: " + elapsedTime / 1000F + "s.");
    }

    public static void floatWithoutChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
        MyMatrix<Float> floatMatrixCopy = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);

        floatMatrix.generate();
        floatVector.generate();
        floatMatrixCopy.generate();

        System.out.println("wektor podany floatWithoutCHoice"+floatVector);
        float vectorLength;
        MyMatrix<Float> floatResult = new MyMatrix<>(Float.class, SIZE, 1);
        vectorLength= floatResult.errorCalculationFloat(floatVector);
        floatResult = floatResult.gaussWithoutChoice(floatMatrix, floatVector,floatMatrixCopy);

        float vectorLengthWithError;
        float error;



        vectorLengthWithError = floatResult.errorCalculationFloat(floatResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
        System.out.println(vectorLength);
        System.out.println(vectorLengthWithError);
        System.out.println(error);

    }

    public static void doubleWithoutChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);
        MyMatrix<Double> doubleMatrixCopy = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        doubleMatrix.generate();
        doubleVector.generate();
        doubleMatrixCopy.generate();

        System.out.println("wektor podany doubleWithoutChoice"+doubleVector);
        double vectorLength;

        MyMatrix<Double> doubleResult = new MyMatrix<>(Double.class, SIZE, 1);
        vectorLength= doubleResult.errorCalculationDouble(doubleVector);

        doubleResult = doubleResult.gaussWithoutChoice(doubleMatrix, doubleVector,doubleMatrixCopy);


        double vectorLengthWithError;
        double error;



        vectorLengthWithError = doubleResult.errorCalculationFloat(doubleResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
        System.out.println(vectorLength);
        System.out.println(vectorLengthWithError);
        System.out.println(error);
    }

    public static void floatWithPartialChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
        MyMatrix<Float> floatMatrixCopy = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        floatMatrix.generate();
        floatVector.generate();
        floatMatrixCopy.generate();

        System.out.println("wektor podany floatWithPartialChoice"+floatVector);
        float vectorLength;
        MyMatrix<Float> floatResult = new MyMatrix<>(Float.class, SIZE, 1);
        vectorLength= floatResult.errorCalculationFloat(floatVector);
        floatResult = floatResult.gaussWithPartialChoice(floatMatrix, floatVector,floatMatrixCopy);

        float vectorLengthWithError;
        float error;



        vectorLengthWithError = floatResult.errorCalculationFloat(floatResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
        System.out.println(vectorLength);
        System.out.println(vectorLengthWithError);
        System.out.println(error);
    }

    public static void doubleWithPartialChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);
        MyMatrix<Double> doubleMatrixCopy = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        doubleMatrix.generate();
        doubleVector.generate();
        doubleMatrixCopy.generate();

        System.out.println("wektor podany doubleWithPartialChoice"+doubleVector);
        double vectorLength;

        MyMatrix<Double> doubleResult = new MyMatrix<>(Double.class, SIZE, 1);
        vectorLength= doubleResult.errorCalculationDouble(doubleVector);

        doubleResult = doubleResult.gaussWithPartialChoice(doubleMatrix, doubleVector,doubleMatrixCopy);


        double vectorLengthWithError;
        double error;



        vectorLengthWithError = doubleResult.errorCalculationFloat(doubleResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
        System.out.println(vectorLength);
        System.out.println(vectorLengthWithError);
        System.out.println(error);
    }
    public static void floatWithFullChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
        MyMatrix<Float> floatMatrixCopy = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);

        floatMatrix.generate();
        floatVector.generate();
        floatMatrixCopy.generate();

        System.out.println("wektor podany floatWithFullChoice"+floatVector);
        float vectorLength;
        MyMatrix<Float> floatResult = new MyMatrix<>(Float.class, SIZE, 1);
        vectorLength= floatResult.errorCalculationFloat(floatVector);
        floatResult = floatResult.gaussWithFullChoice(floatMatrix, floatVector,floatMatrixCopy);

        float vectorLengthWithError;
        float error;



        vectorLengthWithError = floatResult.errorCalculationFloat(floatResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
        System.out.println(vectorLength);
        System.out.println(vectorLengthWithError);
        System.out.println(error);
    }
    public static void doubleWithFullChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);
        MyMatrix<Double> doubleMatrixCopy = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);

        doubleMatrix.generate();
        doubleVector.generate();
        doubleMatrixCopy.generate();

        System.out.println("wektor podany doubleWithFullChoice"+doubleVector);
        double vectorLength;

        MyMatrix<Double> doubleResult = new MyMatrix<>(Double.class, SIZE, 1);
        vectorLength= doubleResult.errorCalculationDouble(doubleVector);

        doubleResult = doubleResult.gaussWithFullChoice(doubleMatrix, doubleVector,doubleMatrixCopy);


        double vectorLengthWithError;
        double error;



        vectorLengthWithError = doubleResult.errorCalculationFloat(doubleResult);
        error=Math.abs(vectorLength-vectorLengthWithError);
        System.out.println(vectorLength);
        System.out.println(vectorLengthWithError);
        System.out.println(error);
//


    }


}
