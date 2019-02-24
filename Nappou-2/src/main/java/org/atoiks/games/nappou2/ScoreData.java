/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2;

import java.util.Arrays;

import java.io.Serializable;

public final class ScoreData implements Serializable {

    private static final long serialVersionUID = -912732385246733L;

    public static class Pair implements Serializable, Comparable<Pair> {

        private static final long serialVersionUID = -1251012937L;

        public final String name;
        public final int score;

        public Pair(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(final Pair other) {
            return Integer.compare(score, other.score);
        }
    }

    // Only keep level 1 for now?
    public static final int LEVELS = 1;
    public static final int DIFFICULTIES = Difficulty.values().length;
    public static final int KEPT_SCORES = 5;

    public final Pair[][][][] data = new Pair[2][LEVELS][DIFFICULTIES][KEPT_SCORES];

    public void clear() {
        for (final Pair[][][] p : data) {
            for (final Pair[][] pp : p) {
                for (final Pair[] ppp : pp) {
                    Arrays.fill(ppp, null);
                }
            }
        }
    }

    public void clear(int plane) {
        final Pair[][][] p = data[plane];
        for (final Pair[][] pp : p) {
            for (final Pair[] ppp : pp) {
                Arrays.fill(ppp, null);
            }
        }
    }
}
