import Matrix.MatrixOperations;

public class MyMatrix<Type extends MatrixOperations> {

    private Type[][] matrix;

    public MyMatrix(Type[][] matrix) {
        this.matrix = matrix;
    }

    public String toString() {
        String result = "";

        for (int i = 0; i < matrix.length; i++) {
            result += "|";
            for (int j = 0; j < matrix[i].length; j++) {
                result = result + matrix[i][j].getValue();
                if(j < matrix.length - 1) {
                    result+= "  ";
                }
            }
            result += "|\n";
        }
        result += "\n";

        return result;
    }
}
