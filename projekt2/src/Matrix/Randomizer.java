package Matrix;

import java.util.Random;

public class Randomizer {

    private int nominator;
    private int denominator = 65536;
    private int max = 65536 - 1;
    private int min = -65536;

    Random rand = new Random();

    public Randomizer() {
        nominator = rand.nextInt((max - min) + 1) + min;
    }

    public int getNominator() {
        return nominator;
    }

    public int getDenominator() {
        return denominator;
    }


    public static Randomizer[][] generateRandomMatrix(int size) {
        Randomizer[][] randomMatrix = new Randomizer[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                randomMatrix[i][j] = new Randomizer();
            }
        }

        return randomMatrix;
    }

    public static Randomizer[][] generateRandomVector(int size) {
        Randomizer[][] randomVector = new Randomizer[size][1];

        for (int j = 0; j < size; j++) {
            randomVector[j][0] = new Randomizer();
        }


        return randomVector;
    }


}
