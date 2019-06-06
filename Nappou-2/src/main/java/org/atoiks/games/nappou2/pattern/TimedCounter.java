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

package org.atoiks.games.nappou2.pattern;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.enemy.IEnemy;

public abstract class TimedCounter implements IAttackPattern {

    public enum InitialState {
        COUNTER_RESET, DO_PAUSE, DO_ACTION;
    }

    public final int limit;
    public final float pause;
    public final float delay;

    private int counter;
    private float time;

    public TimedCounter(final int limit, final float pause, final float delay) {
        this(limit, pause, delay, InitialState.COUNTER_RESET);
    }

    /**
     * Cycle:
     * [1] counter = 0 --> wait(delay) --> [3] onTimerUpdate --> ...
     * --> counter = limit - 1 --> wait(delay) --> onTimerUpdate
     * --> [2] wait(pause) --> restarting at [1]
     *
     * InitialState parameter will change the starting point of the 1st cycle:
     * - COUNTER_RESET: start at [1]
     * - DO_PAUSE:      start at [2]
     * - DO_ACTION:     start at [3]
     */
    public TimedCounter(final int limit, final float pause, final float delay, InitialState initState) {
        this.limit = limit;
        this.pause = pause;
        this.delay = delay;

        switch (initState) {
            case COUNTER_RESET:
                // First update starts at 1
                break;
            case DO_PAUSE:
                // First update starts at 2
                this.counter = limit;
                break;
            case DO_ACTION:
                // First update starts at 3
                this.time = this.delay;
                break;
            default:
                throw new AssertionError("Unhandled starting state: " + initState);
        }
    }

    @Override
    public final void onFireUpdate(IEnemy enemy, float dt) {
        this.time += dt;

        // 1
        if (this.counter >= this.limit) {
            // 2
            if (this.time >= this.pause) this.counter = 0;
        } else if (this.time > this.delay) {
            // 3
            onTimerUpdate(enemy, dt);
            this.counter++;
            this.time = 0;
        }
    }

    public final int getCount() {
        return this.counter;
    }

    protected abstract void onTimerUpdate(IEnemy enemy, float dt);
}
