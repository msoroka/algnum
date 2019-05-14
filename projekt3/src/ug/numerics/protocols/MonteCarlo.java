package ug.numerics.protocols;

import ug.numerics.protocols.assets.AgentState;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class MonteCarlo {
    private final int N;
    private final int MONTE_CARLO_TESTS;
    private final double gaussResult;
    private final double jacobiResult;
    private final double gaussSeidelResult;

    private int yesResults = 0;
    private int YES = 0;
    private int NO = 0;
    private int U = 0;

    private AgentState agents[];
    private double voting[];

    static int yesVotes = 0, noVotes = 0, uVotes = 0;

    String fileName;

    public MonteCarlo(int N, int MONTE_CARLO_TESTS, int YES, int NO, double gaussResult, double jacobiResult, double gaussSeidelResult, String fileName) throws FileNotFoundException {
        this.N = N;
        this.MONTE_CARLO_TESTS = MONTE_CARLO_TESTS;
        this.YES = YES;
        this.NO = NO;
        this.gaussResult = gaussResult;
        this.jacobiResult = jacobiResult;
        this.gaussSeidelResult = gaussSeidelResult;
        this.fileName = fileName;

        this.U = N - YES - NO;

        assignArraysSizes();
        runMonteCarloTest();
    }

    public void runMonteCarloTest() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(fileName));
        StringBuilder sb = new StringBuilder();

        sb.append("ITER");
        sb.append(',');
        sb.append("PROBABILITY FOR YES");
        sb.append(',');
        sb.append("GAUSS");
        sb.append(',');
        sb.append("GAUSS-SEIDEL");
        sb.append(',');
        sb.append("JACOBI");
        sb.append(',');
        sb.append("ABS GAUSS");
        sb.append(',');
        sb.append("ABS GAUSS-SEIDEL");
        sb.append(',');
        sb.append("ABS JACOBI");
        sb.append('\n');


        for (int i = 0; i < MONTE_CARLO_TESTS; i++) {
            generateArray();
            simulateVoting();

            if (yesVotes == N) {
                yesResults++;
            }

            voting[i] = ((double) yesResults) / ((double) (i + 1));

            sb.append(i+1);
            sb.append(',');
            sb.append(voting[i]);
            sb.append(',');
            sb.append(gaussResult);
            sb.append(',');
            sb.append(gaussSeidelResult);
            sb.append(',');
            sb.append(jacobiResult);
            sb.append(',');
            sb.append(error(voting[i], gaussResult));
            sb.append(',');
            sb.append(error(voting[i], gaussSeidelResult));
            sb.append(',');
            sb.append(error(voting[i], jacobiResult));
            sb.append('\n');
        }

        pw.write(sb.toString());
        pw.close();

        System.out.println(yesResults);

        System.out.println(voting[MONTE_CARLO_TESTS - 1] + " number: " + MONTE_CARLO_TESTS);
    }

    public void assignArraysSizes() {
        agents = new AgentState[N];
        voting = new double[MONTE_CARLO_TESTS];
    }

    public void generateArray() {
        for (int i = 0; i < N; i++) {
            agents[i] = null;
        }

        int tempYes = YES;
        int tempNo = NO;
        int tempU = U;

        for (int i = 0; i < N; i++) {
            AgentState vote = AgentState.getRandom();

            if (vote.equals(AgentState.Yes) && tempYes > 0) {
                agents[i] = AgentState.Yes;
                tempYes--;
            } else if (vote.equals(AgentState.No) && tempNo > 0) {
                agents[i] = AgentState.No;
                tempNo--;
            } else {
                if (tempU > 0) {
                    agents[i] = AgentState.U;
                    tempU--;
                } else if (tempNo > 0) {
                    agents[i] = AgentState.No;
                    tempNo--;
                } else {
                    agents[i] = AgentState.Yes;
                    tempYes--;
                }
            }
        }
    }

    public void simulateVoting() {
        yesVotes = 0;
        noVotes = 0;
        uVotes = 0;

        do {
            yesVotes = 0;
            noVotes = 0;
            uVotes = 0;
            changeStates();

            for (int i = 0; i < N; i++) {
                if (agents[i].equals(AgentState.Yes)) {
                    yesVotes++;
                } else if (agents[i].equals(AgentState.No)) {
                    noVotes++;
                } else {
                    uVotes++;
                }
            }

            if (yesVotes == N) break;
            if (noVotes == N) break;
            if (uVotes == N) break;
        } while (true);
    }

    public void changeStates() {
        int randomNum;

        for (int i = 0; i < N; i++) {
            randomNum = ThreadLocalRandom.current().nextInt(0, N);
            while (randomNum == i) {
                randomNum = ThreadLocalRandom.current().nextInt(0, N);
            }


            if (agents[i].equals(AgentState.Yes)) {
                if (agents[randomNum].equals(AgentState.No)) {
                    agents[i] = AgentState.U;
                    agents[randomNum] = AgentState.U;
                } else if (agents[randomNum].equals(AgentState.U)) {
                    agents[randomNum] = AgentState.Yes;
                }
            } else if (agents[i].equals(AgentState.No)) {
                if (agents[randomNum].equals(AgentState.Yes)) {
                    agents[i] = AgentState.U;
                    agents[randomNum] = AgentState.U;
                } else if (agents[randomNum].equals(AgentState.U)) {
                    agents[randomNum] = AgentState.No;
                }
            } else if (agents[i].equals(AgentState.U)) {
                if (agents[randomNum].equals(AgentState.Yes)) {
                    agents[i] = AgentState.Yes;
                } else if (agents[randomNum].equals(AgentState.No)) {
                    agents[i] = AgentState.No;
                }
            }
        }
    }

    public static double error(double length, double length2) {
        return Math.abs(length - length2);
    }
}
