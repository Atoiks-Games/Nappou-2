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

public abstract class FireGateEnemy extends ManualEnemy {

    private static final long serialVersionUID = 6503566228630955824L;

    protected final float spd;

    protected float time;

    private boolean fireGate;

    protected FireGateEnemy(int hp, float x, float y, float r, float spd) {
        super(hp, x, y, r);
        this.spd = spd;
    }

    @Override
    public final void update(float dt) {
        time += dt;

        customUpdate(dt);

        final double cosSpdTime = Math.cos(spd * time);
        if (!fireGate && cosSpdTime < 0.5) {
            fireGate = true;
            return;
        }

        if (fireGate && cosSpdTime > 0.5) {
            fireGate = false;
            customFireAction(dt);
        }
    }

    protected abstract void customUpdate(float dt);
    protected abstract void customFireAction(float dt);
}
