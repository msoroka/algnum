#include <stdio.h>
#include <math.h>
#include <stdlib.h>

#define FROM  -0.99
#define TO    1
#define STEP  0.001
#define N     100

const int range = (int)((-1) * (FROM - TO) / STEP);

double power(double base, int exponent);
double builtInFunctions(double x);
double logarithmFromTheBeginning(double x, int n);
double arctanFromTheBeginning(double x, int n);
double logarithmFromTheEnd(double x, int n);
double arctanFromTheEnd(double x, int n);
double arctanPreviousFromTheBeginning(double x, int n);
double logarithmPreviousFromTheBeginning(double x, int n);
double arctanPreviousFromTheEnd(double x, int n);
double logarithmPreviousFromTheEnd(double x, int n);
double absoluteError(double x, double y);
double relativeError(double x, double y);
void saveToFile(char name[], double myValues[], double builtInValues[], double absoluteErrors[], double relativeErrors[]);

int main()
    int iteration = 0;
    double myValues[range];
    double builtInValues[range];
    double absoluteErrors[range];
    double relativeErrors[range];

    //zapisanie do tablicy wartości obliczone z funkcji wbudowanych
    iteration = 0;
    for(double i = FROM; i <= TO; i = i + STEP) {
        builtInValues[iteration] = builtInFunctions(i);
        iteration++;
    }

    //sposób pierwszy, sumowanie od początku do końca bezpośrendnio ze wzoru Taylora
    iteration = 0;
    for(double i = FROM; i <= TO; i = i + STEP) {
        myValues[iteration]         = logarithmFromTheBeginning(i, N) * arctanFromTheBeginning(i, N);
        absoluteErrors[iteration]   = absoluteError(builtInValues[iteration], myValues[iteration]);
        relativeErrors[iteration]   = relativeError(builtInValues[iteration], myValues[iteration]);
        iteration++;
    }
    saveToFile("sposob-1.csv", myValues, builtInValues, absoluteErrors, relativeErrors);

    //sposób drugi, sumowanie od końca do początku bezpośrendnio ze wzoru Taylora
    iteration = 0;
    for(double i = FROM; i <= TO; i = i + STEP) {
        myValues[iteration]         = logarithmFromTheEnd(i, N) * arctanFromTheEnd(i, N);
        absoluteErrors[iteration]   = absoluteError(builtInValues[iteration], myValues[iteration]);
        relativeErrors[iteration]   = relativeError(builtInValues[iteration], myValues[iteration]);
        iteration++;
    }
    saveToFile("sposob-2.csv", myValues, builtInValues, absoluteErrors, relativeErrors);

    //sposób trzeci, sumowanie od początku do końca na podstawie poprzedniego wyrazu
    iteration = 0;
    for(double i = FROM; i <= TO; i = i + STEP) {
        myValues[iteration]         = logarithmPreviousFromTheBeginning(i, N) * arctanPreviousFromTheBeginning(i, N);
        absoluteErrors[iteration]   = absoluteError(builtInValues[iteration], myValues[iteration]);
        relativeErrors[iteration]   = relativeError(builtInValues[iteration], myValues[iteration]);
        iteration++;
    }
    saveToFile("sposob-3.csv", myValues, builtInValues, absoluteErrors, relativeErrors);

    //sposób czwarty, sumowanie od końca do początku na podstawie poprzedniego wyrazu
    iteration = 0;
    for(double i = FROM; i <= TO; i = i + STEP) {
        myValues[iteration]         = logarithmPreviousFromTheEnd(i, N) * arctanPreviousFromTheEnd(i, N);
        absoluteErrors[iteration]   = absoluteError(builtInValues[iteration], myValues[iteration]);
        relativeErrors[iteration]   = relativeError(builtInValues[iteration], myValues[iteration]);
        iteration++;
    }
    saveToFile("sposob-4.csv", myValues, builtInValues, absoluteErrors, relativeErrors);

    return 0;
}

double power(double base, int exponent){
    double result = 1;

    for(int i = 0; i < exponent; i++) {
        result *= base;
    }

    return result;
}

double builtInFunctions(double x) {
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

double logarithmPreviousFromTheBeginning(double x, int n) {
    double first = x;
    double result = x;

    for(int i = 2; i <= n; i++) {
        first *= ((-1) * ((i - 1) * x / i));
        result += first;
    }

    return result;
}

double arctanPreviousFromTheBeginning(double x, int n) {
    double first = x;
    double result = x;

    for(int i = 0; i <= n; i++) {
        first *= (-1) * (power(x, 2) * ((2 * i) + 1)) / ((2 * n) - 3);
        result += first;
    }

    return result;
}

double logarithmPreviousFromTheEnd(double x, int n) {
    double first = x;
    double result = x;
    double countedValues[n];
    int iteration = 0;

    for(int i = 2; i <= n; i++) {
        first *= ((-1) * ((i - 1) * x / i));
        countedValues[iteration] = first;
        iteration++;
    }

    for(int i = iteration; i >= 0; i--) {
        result += countedValues[i];
    }

    return result;
}

double arctanPreviousFromTheEnd(double x, int n) {
    double first = x;
    double result = x;
    double countedValues[n];
    int iteration = 0;

    for(int i = 0; i <= n; i++) {
        first *= (-1) * (power(x, 2) * ((2 * i) + 1)) / ((2 * n) - 3);
        countedValues[iteration] = first;
        iteration++;
    }

    for(int i = iteration; i >= 0; i--) {
        result += countedValues[i];
    }

    return result;
}

double absoluteError(double x, double y) {
    return fabs(x-y);
}

double relativeError(double x, double y) {
    return (absoluteError(x, y)/fabs(x));
}

void saveToFile(char name[], double myValues[], double builtInValues[], double absoluteErrors[], double relativeErrors[]){
    int iteration = 0;

    FILE *file = fopen(name, "w+");
    if (file == NULL) {
        printf("Błąd podczas otwierania pliku %s\n.", name);
        exit(1);
    } else {
        printf("Pomyślnie otworzono plik %s.\n", name);
        fprintf(file, "%s, %s, %s, %s, %s \n", "Wartość x", "Mój wynik", "Funkcja wbudowana", "Błąd bezwzględny", "Błąd względny");
    }

    for(double i = FROM; i <= TO; i = i + STEP) {
        fprintf(
            file,
            "%.6lf, %.20lf, %.20lf, %.20lf, %.20lf\n",
            i,
            myValues[iteration],
            builtInValues[iteration],
            absoluteErrors[iteration],
            relativeErrors[iteration]
        );
        iteration++;
    }

    printf("Pomyślnie zapisano dane do pliku %s.\n", name);
    fclose(file);
}
