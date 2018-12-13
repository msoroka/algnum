package ug.numerics.protocols.assets;

import java.util.Random;

public enum AgentState {
    Yes,
    No,
    U;

    public static AgentState getRandom() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

