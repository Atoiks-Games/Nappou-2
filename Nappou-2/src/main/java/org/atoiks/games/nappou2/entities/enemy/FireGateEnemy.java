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

package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.nappou2.entities.IUpdateListener;

public class FireGateEnemy extends PathwayEnemy {

    private static final long serialVersionUID = 6503566228630955824L;

    private final float spd;
    private final double limit;

    private float time;
    private boolean fireGate;

    public FireGateEnemy(int hp, int score, float r, float spd) {
        this(hp, score, r, spd, 0.5);
    }

    public FireGateEnemy(int hp, int score, float r, float spd, double limit) {
        super(hp, score);
        this.spd = spd;
        this.limit = limit;
        this.setR(r);
    }

    @Override
    public final void update(float dt) {
        path.update(dt);

        // but what if the following fenced block was yet another listener?
        // could we simply use PathwayEnemy directly?
        // {
        time += dt;
        final double cosSpdTime = Math.cos(spd * time);
        if (!fireGate && cosSpdTime < limit) {
            fireGate = true;
            return;
        }

        if (fireGate && cosSpdTime > limit) {
            fireGate = false;
            listener.onFireUpdate(this, dt);
        }
        // }
    }
}
