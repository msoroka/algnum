import Matrix.MyMatrix;
import Matrix.Randomizer;

public class Main {

    public static void main(String[] args) {

        final int SIZE = 2;

        Randomizer[][] randomMatrix = new Randomizer().generateRandomMatrix(SIZE);
        Randomizer[][] randomVector = new Randomizer().generateRandomVector(SIZE);

        MyMatrix<Float> floatMatrix = new MyMatrix<>(Float.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Double> doubleMatrix = new MyMatrix<>(Double.class, randomMatrix, SIZE, SIZE);
        MyMatrix<Float> floatVector = new MyMatrix<>(Float.class, randomVector, SIZE, 1);
        MyMatrix<Double> doubleVector = new MyMatrix<>(Double.class, randomVector, SIZE, 1);

        floatMatrix.generate();
        doubleMatrix.generate();
        floatVector.generate();
        doubleVector.generate();

        System.out.println(floatMatrix);
        System.out.println(doubleMatrix);
        System.out.println(floatVector);
        System.out.println(doubleVector);
    }
}
