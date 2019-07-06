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

import org.atoiks.games.nappou2.entities.enemy.Enemy;

public final class SineFireGate implements AttackPattern {

    private final AttackPattern delegate;

    private final float afreq;
    private final float phase;
    private final double limit;

    private float time;
    private boolean fireGate;

    public SineFireGate(float afreq, float phase, double limit, AttackPattern delegate) {
        this.afreq = afreq;
        this.phase = phase;
        this.limit = limit;

        this.delegate = delegate;
    }

    @Override
    public void onFireUpdate(Enemy enemy, float dt) {
        time += dt;
        final double cosSpdTime = Math.cos(afreq * time + phase);
        if (!fireGate && cosSpdTime < limit) {
            fireGate = true;
            return;
        }

        if (fireGate && cosSpdTime > limit) {
            fireGate = false;
            delegate.onFireUpdate(enemy, dt);
        }
    }
}
