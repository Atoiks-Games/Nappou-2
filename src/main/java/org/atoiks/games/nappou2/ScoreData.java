/**
 *  Nappou-2
 *  Copyright (C) 2017-2019  Atoiks-Games <atoiks-games@outlook.com>
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
import java.util.Comparator;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Externalizable;

public final class ScoreData implements Externalizable {

    private static final long serialVersionUID = -912732385246733L;

    public static class Pair implements Externalizable, Comparable<Pair> {

        private static final long serialVersionUID = -1251012937L;

        private String name;
        private int score;

        public Pair() {
            // Exists solely for readExternal to work
        }

        public Pair(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getProcessedName() {
            return name == null ? "" : name;
        }

        public String getProcessedScore() {
            return score == 0 ? "0" : score + "0000";
        }

        public boolean isValid() {
            // see ScoreData's writeExternal:
            // writing null will crash, so instead (for now)
            // they will write a pair with a negative score
            return score >= 0;
        }

        @Override
        public int compareTo(final Pair other) {
            return Integer.compare(score, other.score);
        }

        @Override
        public void readExternal(final ObjectInput stream) throws IOException {
            this.name = stream.readUTF();
            this.score = stream.readInt();
        }

        @Override
        public void writeExternal(final ObjectOutput stream) throws IOException {
            stream.writeUTF(this.name);
            stream.writeInt(this.score);
        }
    }

    // Only keep level 1 for now?
    public static final int LEVELS = 1;
    public static final int KEPT_SCORES = 5;

    private static final int PANELS = 2;

    private static final Comparator<ScoreData.Pair> NULLS_FIRST_CMP =
            Comparator.nullsFirst(Comparator.naturalOrder());

    private Pair[][][] data = makeScoreBuffer(PANELS, LEVELS, KEPT_SCORES);

    public void clear() {
        for (final Pair[][] p : data) {
            for (final Pair[] pp : p) {
                Arrays.fill(pp, null);
            }
        }
    }

    public void clear(int plane) {
        final Pair[][] p = data[plane];
        for (final Pair[] pp : p) {
            Arrays.fill(pp, null);
        }
    }

    public Pair[][] getScoreForPlane(int plane) {
        return data[plane];
    }

    public void updateScores(boolean challengeMode, int level, Pair newEntry) {
        final Pair[] alias = data[challengeMode ? 1 : 0][level];
        final Pair[] acopy = Arrays.copyOf(alias, alias.length + 1);
        acopy[acopy.length - 1] = newEntry;
        Arrays.sort(acopy, NULLS_FIRST_CMP);
        System.arraycopy(acopy, 1, alias, 0, alias.length);
    }

    private static Pair[][][] makeScoreBuffer(int panels, int levels, int keptScores) {
        return new Pair[panels][levels][keptScores];
    }

    @Override
    public void readExternal(final ObjectInput stream) throws IOException {
        // Read array size
        final int panels = stream.readInt();
        final int levels = stream.readInt();
        final int keptScores = stream.readInt();

        // Check if the data written out has the same size
        // as the current one
        if (panels != PANELS || levels != LEVELS
            || keptScores != KEPT_SCORES)
        {
            // If any size mismatches, we do not try to read from the stream.
            // Since constructor would have been called already, we would have
            // a blank score data array initialized already.
            return;
        }

        // Read the actual scores
        for (final Pair[][] p : data) {
            for (final Pair[] pp : p) {
                for (int i = 0; i < pp.length; ++i) {
                    final Pair pair = new Pair();
                    pair.readExternal(stream);
                    pp[i] = pair;
                }
            }
        }
    }

    @Override
    public void writeExternal(final ObjectOutput stream) throws IOException {
        // Write out array size
        stream.writeInt(PANELS);
        stream.writeInt(LEVELS);
        stream.writeInt(KEPT_SCORES);

        // Allocate dummy score pair just in case some pairs are null
        final Pair dummy = new Pair("", -1);

        // Write out actual data
        for (final Pair[][] p : data) {
            for (final Pair[] pp : p) {
                for (final Pair ppp : pp) {
                    (ppp != null ? ppp : dummy).writeExternal(stream);
                }
            }
        }
    }
}
