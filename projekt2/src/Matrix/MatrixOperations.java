package Matrix;

public interface MatrixOperations<Type1, Type2> extends Comparable<Type1> {
    Type2 getValue();

//    T1 setValue(int value);
//    T1 add(T1 t1);
//    T1 substract(T1 t1);
//    T1 multiply(T1 t1);
//    T1 divide(T1 t1);
//    T1 clone();
    int compareTo(Type1 t1);
}

