import javafx.scene.control.Tab;

public class Main {

    final static int N = 3;
    final static int YES = 2;
    final static int NO = 1;
    final static int Un = N-YES-NO;
    static int COMBINATIONS = 0;

    public static void main(String[] args) {
        countCombinations();
        double[][] matrix = new double[COMBINATIONS][COMBINATIONS];
        double[] results = new double[COMBINATIONS];
        State[] states = new State[COMBINATIONS];
        int k=-1;
        double pairsCount=0.0;
        pairsCount=(N*(N-1))/2;
        for (int i=0;i<COMBINATIONS;i++){
            results[i]=0;
        }
        for (int i=0;i<COMBINATIONS;i++){

            for (int j=0;j<COMBINATIONS;j++){
                matrix[i][j]=0;
            }
        }
        for(int i = 0; i <= N ; i++)
        {

            int no=0;
            int j = 0;
            do{
                k++;
                states[k] = new State();
                states[k].setNo(no);
                states[k].setYes(i);
//                System.out.println(i +"  "+ no);
                j=i+no;
                no++;

            } while(j < N);
        }
//        for (int i=0;i<COMBINATIONS;i++)
//        {
//            System.out.println(states[i].getYes() + " " +states[i].getNo());
//
//        }

        for (int i=0;i<COMBINATIONS;i++)
        {
            int y,n,u,changeRow,changeCol=0;
            double temp=0.0;
            y=states[i].getYes();
            n=states[i].getNo();
            u=N-y-n;
            temp=(y*(y-1)/2)+(n*(n-1)/2)+(u*(u-1)/2);

               matrix[i][i]=temp/pairsCount ;//pozostanie w tym samym stanie
            if(states[i].getYes()!=0 || states[i].getNo()!=0 && states[i].getNo()!=N) {
//                matrix[i][i] = matrix[i][i] - 1; // tutaj !!!- TU JEST PRZENOSZENIE NA DRUGĄ STRONĘ OGÓLNIE ZAKOMENTOWANE ŻEBY ZOBACZYĆ CZY MACIERZ SIĘ ZGADZA ALE DLA OBLICZEŃ BEDZIE TO TRZEBA ODKOMENTOWAC 
            }
            if(states[i].getYes()==N){
                results[i]=1;
                matrix[i][i] = 1;
            }
           temp=0;
           if(y!=0 && n!=0){            // zmiejszenie ilosci tak i nie  tak spotyka sie z nie Y,N -> U,U

               for (int m=0;m<COMBINATIONS;m++){
                   if (states[m].getYes()==y-1 && states[m].getNo()==n-1){
                       changeCol=m;

                       break;
                   }
               }
               temp=y*n/pairsCount;
               matrix[i][changeCol]=y*n/pairsCount;
//               System.out.println(changeCol+"!!!!!!");
//               System.out.println(temp);
           }

           if(y!=0 && N-y!=n ) {       //zwiekszenie tak
               for (int m = 0; m < COMBINATIONS; m++) {
                   if (states[m].getYes() ==y+1 && states[m].getNo() ==n) {
                       changeCol = m;

                       break;
                   }
               }
               temp = y * u / pairsCount;
               matrix[i][changeCol] = y * u / pairsCount;
               System.out.println(changeCol + "!!!!!!");
               System.out.println(temp);
           }

           if(n!=0 && N-n!=y){
               for (int m = 0; m < COMBINATIONS; m++) {
                   if (states[m].getYes() ==y && states[m].getNo() ==n+1) {
                       changeCol = m;

                       break;
                   }
               }
               temp = n * u / pairsCount;
               matrix[i][changeCol] = n * u / pairsCount;
//               System.out.println(changeCol + "!!!!!!");
//               System.out.println(temp);

           }
        }

        System.out.print( "zapis tak i nie   ");
        for (int i=0;i<COMBINATIONS;i++){

            System.out.print(states[i].getYes() + "." +states[i].getNo()+"  ");

        }
        System.out.println("\n");
        for (int i=0;i<COMBINATIONS;i++){

          System.out.print(states[i].getYes() + " " +states[i].getNo()+ "  ");

            System.out.print("    "+ results[i]+"    ");
            for (int j=0;j<COMBINATIONS;j++){
                System.out.print("  "+ matrix[i][j]);
            }
            System.out.print("\n");
        }
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N - 1; j++) {
//                System.out.println(j + " " + i);
//            }
//        }

    }

    public static void countCombinations() {
        for (int i = 0; i <= N; i++) {
            COMBINATIONS += (i + 1);
        }
    }
}
