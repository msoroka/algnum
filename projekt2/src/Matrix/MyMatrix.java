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

    public MyMatrix(Class<T> type, int rows, int columns) {
        this.type = type;
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

    public MyMatrix<T> gaussWithoutChoice(MyMatrix<T> matrix, MyMatrix<T> vector) {
        int n = vector.rows;
        MyMatrix<T> resultVector = new MyMatrix(type, n, 1);

        if (type.equals(Float.class)) {
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    float factor = matrix.matrix[j][i].floatValue() / matrix.matrix[i][i].floatValue();
                    vector.matrix[j][0] = (T) (Float) (vector.matrix[j][0].floatValue() - factor * vector.matrix[i][0].floatValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Float) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].floatValue());
                    }
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                float sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();
                }
                resultVector.matrix[i][0] = (T) (Float) ((vector.matrix[i][0].floatValue() - sum) / matrix.matrix[i][i].floatValue());
            }
        }

        if (type.equals(Double.class)) {
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double factor = matrix.matrix[j][i].doubleValue() / matrix.matrix[i][i].doubleValue();
                    vector.matrix[j][0] = (T) (Double) (vector.matrix[j][0].doubleValue() - factor * vector.matrix[i][0].doubleValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Double) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].doubleValue());
                    }
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                double sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();
                }
                resultVector.matrix[i][0] = (T) (Double) ((vector.matrix[i][0].doubleValue() - sum) / matrix.matrix[i][i].doubleValue());
            }
        }

        return resultVector;
    }

    public MyMatrix<T> gaussWithPartialChoice(MyMatrix<T> matrix, MyMatrix<T> vector) {
        int n = vector.rows;
        MyMatrix<T> resultVector = new MyMatrix(type, n, 1);

        if (type.equals(Float.class)) {
            for (int i = 0; i < n; i++) {
                int max = i;

                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(matrix.matrix[j][i].floatValue()) > Math.abs(matrix.matrix[max][i].floatValue())) {
                        max = j;
                    }
                }

                for (int j = 0; j < matrix.columns; j++) {
                    T tmp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[max][j];
                    matrix.matrix[max][j] = tmp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T tmp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[max][j];
                    vector.matrix[max][j] = tmp;
                }

                for (int j = i + 1; j < n; j++) {
                    float factor = matrix.matrix[j][i].floatValue() / matrix.matrix[i][i].floatValue();
                    vector.matrix[j][0] = (T) (Float) (vector.matrix[j][0].floatValue() - factor * vector.matrix[i][0].floatValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Float) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].floatValue());
                    }
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                float sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();
                }
                resultVector.matrix[i][0] = (T) (Float) ((vector.matrix[i][0].floatValue() - sum) / matrix.matrix[i][i].floatValue());
            }
        }

        if (type.equals(Double.class)) {
            for (int i = 0; i < n; i++) {
                int max = i;

                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(matrix.matrix[j][i].doubleValue()) > Math.abs(matrix.matrix[max][i].doubleValue())) {
                        max = j;
                    }
                }

                for (int j = 0; j < matrix.columns; j++) {
                    T tmp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[max][j];
                    matrix.matrix[max][j] = tmp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T tmp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[max][j];
                    vector.matrix[max][j] = tmp;
                }

                for (int j = i + 1; j < n; j++) {
                    double factor = matrix.matrix[j][i].doubleValue() / matrix.matrix[i][i].doubleValue();
                    vector.matrix[j][0] = (T) (Double) (vector.matrix[j][0].doubleValue() - factor * vector.matrix[i][0].doubleValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Double) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].doubleValue());
                    }
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                double sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();
                }
                resultVector.matrix[i][0] = (T) (Double) ((vector.matrix[i][0].doubleValue() - sum) / matrix.matrix[i][i].doubleValue());
            }
        }

        return resultVector;
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
