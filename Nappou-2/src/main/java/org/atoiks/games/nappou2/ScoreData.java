package org.atoiks.games.nappou2;

import java.io.Serializable;

public final class ScoreData implements Serializable {

    private static final long serialVersionUID = -912732385246734L;

    public static final int LEVELS = 2;
    public static final int DIFFICULTIES = Difficulty.values().length;
    public static final int KEPT_SCORES = 5;

    public int[][][] data = new int[LEVELS][DIFFICULTIES][KEPT_SCORES];

    public ScoreData validate() {
        if (data.length != LEVELS) {
            final int[][][] newData = new int[LEVELS][DIFFICULTIES][KEPT_SCORES];
            for (int i = 0; i < data.length; ++i) {
                newData[i] = data[i];
            }
            data = newData;
        }
        return this;
    }
}