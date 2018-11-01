package Types;

import Matrix.MatrixOperations;
import Matrix.Randomizer;

public class MyFloat implements MatrixOperations<MyFloat, Float> {
    private float value;
    private Randomizer[][] randomMatrix;

    public MyFloat(int nominator, int denominator) {
        this.value = (float) nominator / denominator;
    }

    public MyFloat(Randomizer[][] randomMatrix) {
        this.randomMatrix = randomMatrix;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public int compareTo(MyFloat myFloat) {
        return this.getValue().compareTo(myFloat.getValue());
    }

    public MyFloat[][] generateMatrix(int size) {
        MyFloat[][] floatMatrix = new MyFloat[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                floatMatrix[i][j] = new MyFloat(randomMatrix[i][j].getNominator(), randomMatrix[i][j].getDenominator());
            }
        }

        return floatMatrix;
    }

    public MyFloat[][] generateVector(int size) {
        MyFloat[][] floatVector = new MyFloat[size][1];

        for (int j = 0; j < size; j++) {
            floatVector[j][0] = new MyFloat(randomMatrix[j][0].getNominator(), randomMatrix[j][0].getDenominator());
        }

        return floatVector;
    }
}
