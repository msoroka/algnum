import Matrix.Randomizer;
import Types.MyDouble;
import Types.MyFloat;

public class Main {

    public static void main(String[] args) {

        final int SIZE = 2;

        Randomizer[][] randomMatrix = new Randomizer().generateRandomMatrix(SIZE);

        MyMatrix<MyDouble> doubleMatrix = new MyMatrix<>(new MyDouble(randomMatrix).generateMatrix(SIZE));
        System.out.println(doubleMatrix);

        MyMatrix<MyFloat> floatMatrix = new MyMatrix<>(new MyFloat(randomMatrix).generateMatrix(SIZE));
        System.out.println(floatMatrix);
    }
}
