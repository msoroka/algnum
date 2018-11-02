package Matrix;

public class MyMatrix<T extends Number> {

    T[][] matrix;
    int rows;
    int columns;
    Class<T> type;
    Randomizer[][] randomMatrix;

    public MyMatrix(Class<T> type, Randomizer[][] randomMatrix, int rows, int columns) {
        this.type = type;
        this.randomMatrix = randomMatrix;
        this.rows = rows;
        this.columns = columns;
        this.matrix = (T[][]) new Number[rows][columns];
    }

    public void generate() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (type.equals(Float.class)) {
                    matrix[i][j] = (T) Float.valueOf((float) randomMatrix[i][j].getNominator() / (float) randomMatrix[i][j].getDenominator());
                }
                if (type.equals(Double.class)) {
                    matrix[i][j] = (T) Double.valueOf((double) randomMatrix[i][j].getNominator() / (double) randomMatrix[i][j].getDenominator());
                }
            }
        }
    }


    public String toString() {
        String result = "";

        for (int i = 0; i < matrix.length; i++) {
            result += "|";
            for (int j = 0; j < matrix[i].length; j++) {
                result = result + matrix[i][j] + " ";
            }
            result += "|\n";
        }
        result += "\n";

        return result;
    }
}
