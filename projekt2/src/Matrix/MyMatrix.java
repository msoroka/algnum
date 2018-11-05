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

    public MyMatrix<T> gaussWithoutChoice(MyMatrix<T> matrix, MyMatrix<T> vector, MyMatrix<T> MatrixCopy) {
        int n = vector.rows;
        MyMatrix<T> resultVector = new MyMatrix(type, n, 1);
        MyMatrix<T> VectorCheck = new MyMatrix<>(type, n, 1);


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

            for (int i = 0; i < n; i++) {
                float sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();

                }
                VectorCheck.matrix[i][0] = (T) (Float) (sum) ;
            }

            System.out.println("sprawdzenie wektora "+VectorCheck);
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


            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();

                }
                VectorCheck.matrix[i][0] = (T) (Double) (sum) ;
            }

            System.out.println("sprawdzenie wektora "+VectorCheck);
        }

        return VectorCheck;
    }

    public MyMatrix<T> gaussWithPartialChoice(MyMatrix<T> matrix, MyMatrix<T> vector, MyMatrix<T> MatrixCopy) {
        int n = vector.rows;
        MyMatrix<T> resultVector = new MyMatrix(type, n, 1);
        MyMatrix<T> VectorCheck = new MyMatrix<>(type, n, 1);

        if (type.equals(Float.class)) {
            for (int i = 0; i < n; i++) {
                int max = i;

                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(matrix.matrix[j][i].floatValue()) > Math.abs(matrix.matrix[max][i].floatValue())) {
                        max = j;
                    }
                }

                for (int j = 0; j < matrix.columns; j++) {
                    T temp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[max][j];
                    matrix.matrix[max][j] = temp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T temp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[max][j];
                    vector.matrix[max][j] = temp;
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



            for (int i = 0; i < n; i++) {
                float sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();

                }
                VectorCheck.matrix[i][0] = (T) (Float) (sum) ;
            }

            System.out.println("sprawdzenie wektora "+VectorCheck);
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
                    T temp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[max][j];
                    matrix.matrix[max][j] = temp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T temp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[max][j];
                    vector.matrix[max][j] = temp;
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


            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();

                }
                VectorCheck.matrix[i][0] = (T) (Double) (sum) ;
            }

            System.out.println("sprawdzenie wektora "+VectorCheck);

        }

        return VectorCheck;
    }

    public MyMatrix<T> gaussWithFullChoice(MyMatrix<T> matrix, MyMatrix<T> vector, MyMatrix<T> MatrixCopy) {
        int n = vector.rows;
        MyMatrix<T> resultVector = new MyMatrix(type, n, 1);
        MyMatrix<T> trueResultVector = new MyMatrix(type, n, 1);


        MyMatrix<T> VectorCheck = new MyMatrix<>(type, n, 1);



        ;

        int[] truePosition;
        truePosition= new int[n];

        for (int j = 0; j < n; j++) {
            truePosition[j]=j;
        }

        if (type.equals(Float.class)) {
            for (int i = 0; i < n; i++) {
                int maxRow = i;
                int maxColumn = i;

                for (int j = i; j < matrix.rows; j++) {
                    for (int k = i; k < matrix.columns; k++) {
                        if (Math.abs(matrix.matrix[j][k].floatValue()) > Math.abs(matrix.matrix[maxRow][maxColumn].floatValue())) {
                            maxRow = j;
                            maxColumn = k;

                        }
                    }
                }

                    int tempInt = truePosition[i];
                    truePosition[i] = truePosition[maxColumn];

                    truePosition[maxColumn] = tempInt;


                for (int j = 0; j < matrix.rows; j++) {
                    T temp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[maxRow][j];
                    matrix.matrix[maxRow][j] = temp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T temp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[maxRow][j];
                    vector.matrix[maxRow][j] = temp;
                }

                for (int j = 0; j < matrix.rows; j++) {
                    T temp = matrix.matrix[j][i];
                    matrix.matrix[j][i] = matrix.matrix[j][maxColumn];
                    matrix.matrix[j][maxColumn] = temp;
                }


                for (int j = i + 1; j < n; j++) {
                    float factor = matrix.matrix[j][i].floatValue() / matrix.matrix[i][i].floatValue();
                    vector.matrix[j][0] = (T) (Float) (vector.matrix[j][0].floatValue() - factor * vector.matrix[i][0].floatValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Float) (matrix.matrix[j][k].floatValue() - factor * matrix.matrix[i][k].floatValue());
                    }
                }
            }

            for (int j = 0; j < n; j++) {
//                System.out.println(truePosition[j]);
            }

            for (int i = n - 1; i >= 0; i--) {
                float sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();
                }
                resultVector.matrix[i][0] = (T) (Float) ((vector.matrix[i][0].floatValue() - sum) / matrix.matrix[i][i].floatValue());
            }
            for (int j = 0; j < n; j++) {
                trueResultVector.matrix[truePosition[j]][0]= (T) (Float) (resultVector.matrix[j][0].floatValue());
            }

//            System.out.println("zmienone wyniki do sprawdzania \n" + trueResultVector);
//
//
//            System.out.println("zmienone wyniki do sprawdzania \n" + trueResultVector);
//            System.out.println("macierz bez zmian"+MatrixCopy);


            for (int i = 0; i < n; i++) {
                float sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].floatValue() * trueResultVector.matrix[j][0].floatValue();

                }
                VectorCheck.matrix[i][0] = (T) (Float) (sum) ;
            }

            System.out.println("sprawdzenie wektora "+VectorCheck);
        }

        if (type.equals(Double.class)) {


            for (int i = 0; i < n; i++) {
                int maxRow = i;
                int maxColumn = i;

                for (int j = i; j < matrix.rows; j++) {
                    for (int k = i; k < matrix.columns; k++) {
                        if (Math.abs(matrix.matrix[j][k].floatValue()) > Math.abs(matrix.matrix[maxRow][maxColumn].doubleValue())) {
                            maxRow = j;
                            maxColumn = k;

                        }
                    }
                }

                int tempInt = truePosition[i];
                truePosition[i] = truePosition[maxColumn];

                truePosition[maxColumn] = tempInt;


                for (int j = 0; j < matrix.rows; j++) {
                    T temp = matrix.matrix[i][j];
                    matrix.matrix[i][j] = matrix.matrix[maxRow][j];
                    matrix.matrix[maxRow][j] = temp;
                }

                for (int j = 0; j < vector.columns; j++) {
                    T temp = vector.matrix[i][j];
                    vector.matrix[i][j] = vector.matrix[maxRow][j];
                    vector.matrix[maxRow][j] = temp;
                }

                for (int j = 0; j < matrix.rows; j++) {
                    T temp = matrix.matrix[j][i];
                    matrix.matrix[j][i] = matrix.matrix[j][maxColumn];
                    matrix.matrix[j][maxColumn] = temp;
                }


                for (int j = i + 1; j < n; j++) {
                    double factor = matrix.matrix[j][i].doubleValue() / matrix.matrix[i][i].doubleValue();
                    vector.matrix[j][0] = (T) (Double) (vector.matrix[j][0].doubleValue() - factor * vector.matrix[i][0].doubleValue());

                    for (int k = i; k < n; k++) {
                        matrix.matrix[j][k] = (T) (Double) (matrix.matrix[j][k].doubleValue() - factor * matrix.matrix[i][k].doubleValue());
                    }
                }
            }

            for (int j = 0; j < n; j++) {
//                System.out.println(truePosition[j]);
            }

            for (int i = n - 1; i >= 0; i--) {
                double sum = 0;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();
                }
                resultVector.matrix[i][0] = (T) (Double) ((vector.matrix[i][0].floatValue() - sum) / matrix.matrix[i][i].doubleValue());
            }
            for (int j = 0; j < n; j++) {
                trueResultVector.matrix[truePosition[j]][0]= (T) (Double) (resultVector.matrix[j][0].doubleValue());
            }

//            System.out.println("zmienone wyniki do sprawdzania \n" + trueResultVector);
//            System.out.println("macierz bez zmian"+MatrixCopy);


            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += MatrixCopy.matrix[i][j].doubleValue() * trueResultVector.matrix[j][0].doubleValue();

                }
                VectorCheck.matrix[i][0] = (T) (Double) (sum) ;
            }

            System.out.println("sprawdzenie wektora "+VectorCheck);

        }

        return VectorCheck;
    }

    public float errorCalculationFloat(MyMatrix<T> vector){

        float sum=0;
        for(int i=0;i<vector.rows;i++){
            sum+=Math.pow(vector.matrix[i][0].floatValue(),2);
        }
        sum=(float) Math.sqrt(sum);
    return sum;
    }

    public double errorCalculationDouble(MyMatrix<T> vector){

        double sum=0;
        for(int i=0;i<vector.rows;i++){
            sum+=Math.pow(vector.matrix[i][0].doubleValue(),2);
        }
        sum= Math.sqrt(sum);
        return sum;
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
