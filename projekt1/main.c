#include <stdio.h>
#include <math.h>

#define FROM  -0.99
#define TO    1
#define STEP  0.01
#define N     100

double power(double base, int exponent);
double buildInFunctions(double x);
double logarithmFromTheBeginning(double x, int n);
double arctanFromTheBeginning(double x, int n);
double logarithmFromTheEnd(double x, int n);
double arctanFromTheEnd(double x, int n);

int main(){

    //sposób pierwszy, sumowanie od początku do końca bezpośrendnio ze wzoru Taylora
    for(double i = FROM; i <= TO; i = i + STEP) {
        printf("Moja: %.20lf \t Wbudowana: %.20lf \n", logarithmFromTheBeginning(i, N) * arctanFromTheBeginning(i, N), buildInFunctions(i));
    }

    printf("-----------------------------------------------------------\n");

    //sposób drugi, sumowanie od końca do początku bezpośrendnio ze wzoru Taylora
    for(double i = FROM; i <= TO; i = i + STEP) {
        printf("Moja: %.20lf \t Wbudowana: %.20lf \n", logarithmFromTheEnd(i, N) * arctanFromTheEnd(i, N), buildInFunctions(i));
    }

    return 0;
}

double power(double base, int exponent){
    double result = 1;

    for(int i = 0; i < exponent; i++) {
        result *= base;
    }

    return result;
}

double buildInFunctions(double x) {
    return atan(x) * log(x + 1);
}

double logarithmFromTheBeginning(double x, int n) {
    double result = 0;

    for(int i = 1; i <= N; i++) {
        result += power(-1, i + 1) * (power(x, i) / i);
    }

    return result;
}

double arctanFromTheBeginning(double x, int n) {
    double result = 0;

    for(int i = 0; i <= N; i++) {
        result += power(-1, i) * ((power(x, (2 * i) + 1)) / ((2 * i) + 1));
    }

    return result;
}

double logarithmFromTheEnd(double x, int n) {
    double result = 0;

    for(int i = N; i >= 1; i--) {
        result += power(-1, i + 1) * (power(x, i) / i);
    }

    return result;
}

double arctanFromTheEnd(double x, int n) {
    double result = 0;

    for(int i = N; i >= 0; i--) {
        result += power(-1, i) * ((power(x, (2 * i) + 1)) / ((2 * i) + 1));
    }

    return result;
}


