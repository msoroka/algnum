import Matrix.MyMatrix;
import Matrix.Randomizer;

public class Main {

    final static int SIZE = 1000;
    static long start;
    static long elapsedTime;

    public static void main(String[] args) {
        Randomizer[][] randomMatrix = new Randomizer().generateRandomMatrix(SIZE);
        Randomizer[][] randomVector = new Randomizer().generateRandomVector(SIZE);

//        floatWithoutChoice(randomMatrix, randomVector);
//        doubleWithoutChoice(randomMatrix, randomVector);
//        floatWithPartialChoice(randomMatrix, randomVector);
//        doubleWithPartialChoice(randomMatrix, randomVector);

        start = System.currentTimeMillis();
        floatWithoutChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Float bez wyboru elementu: " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        doubleWithoutChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Double bez wyboru elementu: " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        floatWithPartialChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Float z częściowym wyborem: " + elapsedTime / 1000F + "s.");

        start = System.currentTimeMillis();
        doubleWithPartialChoice(randomMatrix, randomVector);
        elapsedTime = (System.currentTimeMillis() - start);
        System.out.println("Double z częściowym wyborem: " + elapsedTime / 1000F + "s.");
    }

    public static void floatWithoutChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
        floatMatrix.generate();
        floatVector.generate();

        MyMatrix<Float> floatResult = new MyMatrix<>(Float.class, SIZE, 1);
        floatResult = floatResult.gaussWithoutChoice(floatMatrix, floatVector);
    }

    public static void doubleWithoutChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);
        doubleMatrix.generate();
        doubleVector.generate();

        MyMatrix<Double> doubleResult = new MyMatrix<>(Double.class, SIZE, 1);
        doubleResult = doubleResult.gaussWithoutChoice(doubleMatrix, doubleVector);
    }

    public static void floatWithPartialChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
        floatMatrix.generate();
        floatVector.generate();

        MyMatrix<Float> floatResult = new MyMatrix<>(Float.class, SIZE, 1);
        floatResult = floatResult.gaussWithPartialChoice(floatMatrix, floatVector);
    }

    public static void doubleWithPartialChoice(Randomizer[][] randomMatrix, Randomizer[][] randomVector) {
        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);
        doubleMatrix.generate();
        doubleVector.generate();

        MyMatrix<Double> doubleResult = new MyMatrix<>(Double.class, SIZE, 1);
        doubleResult = doubleResult.gaussWithPartialChoice(doubleMatrix, doubleVector);
    }
}
