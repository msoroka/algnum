package Types;

import Matrix.MatrixOperations;
import Matrix.Randomizer;

public class MyDouble implements MatrixOperations<MyDouble, Double> {
    private double value;
    private Randomizer[][] randomMatrix;

    public MyDouble(int nominator, int denominator) {
        this.value = (double) nominator / denominator;
    }

    public MyDouble(Randomizer[][] randomMatrix) {
        this.randomMatrix = randomMatrix;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public int compareTo(MyDouble myDouble) {
        return this.getValue().compareTo(myDouble.getValue());
    }

    public MyDouble[][] generateMatrix(int size) {
        MyDouble[][] doubleMatrix = new MyDouble[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                doubleMatrix[i][j] = new MyDouble(randomMatrix[i][j].getNominator(), randomMatrix[i][j].getDenominator());
            }
        }

        return doubleMatrix;
    }

    public MyDouble[][] generateVector(int size) {
        MyDouble[][] doubleVector = new MyDouble[size][1];

        for (int j = 0; j < size; j++) {
            doubleVector[j][0] = new MyDouble(randomMatrix[j][0].getNominator(), randomMatrix[j][0].getDenominator());
        }

        return doubleVector;
    }
}


