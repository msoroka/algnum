public class Main {

    final static int N = 3;
    final static int YES = 2;
    final static int NO = 1;
    static int COMBINATIONS = 0;

    public static void main(String[] args) {
        countCombinations();

        State[] states = new State[COMBINATIONS];

        for(int i = 0; i <= N ; i++)
        {
            int no=0;
            int j = 0;
            do{
//                states[i] = new State();
                System.out.println(i +"  "+ no);
                j=i+no;
                no++;
            } while(j < N);
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
