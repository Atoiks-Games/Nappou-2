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

public final class ScoreCounter {

    // The lowest value is capped at zero
    private int score;

    public int getScore() {
        return this.score;
    }

    public void restoreTo(int score) {
        this.score = Math.max(0, score);
    }

    public void reset() {
        this.score = 0;
    }

    public ScoreCounter changeBy(int delta) {
        this.score = Math.max(0, this.score + delta);
        return this;
    }
}
