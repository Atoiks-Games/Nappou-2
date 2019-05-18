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

package org.atoiks.games.nappou2.entities.pathway;

import java.util.Iterator;

import org.atoiks.games.nappou2.entities.IPathway;

import org.atoiks.games.nappou2.equations.IEquation;

import static org.atoiks.games.nappou2.Utils.lerp;
import static org.atoiks.games.nappou2.Utils.clamp01;

/**
 * Uses linear interpolation
 */
public final class LerpPathway implements IPathway {

    public static class LerpEquation {

        public final float start;
        public final float end;
        public final float limit;

        public final IEquation eqn;

        private float elapsed;

        public LerpEquation(float start, float end, float limit, IEquation eqn) {
            this.start = start;
            this.end = end;
            this.limit = limit;
            this.eqn = eqn;
        }

        public float compute() {
            return lerp(start, end, eqn.compute(clamp01(elapsed / limit)));
        }

        public void addTime(float dt) {
            elapsed += dt;
        }

        public boolean hasCompleted() {
            return elapsed > limit;
        }
    }

    private final Iterator<LerpEquation> fxs;
    private final Iterator<LerpEquation> fys;

    private LerpEquation fx;
    private LerpEquation fy;

    public LerpPathway(Iterator<LerpEquation> fxs, Iterator<LerpEquation> fys) {
        this.fxs = fxs;
        this.fys = fys;

        if (fxs.hasNext()) fx = fxs.next();
        if (fys.hasNext()) fy = fys.next();
    }

    @Override
    public float getX() {
        return fx.compute();
    }

    @Override
    public float getY() {
        return fy.compute();
    }

    @Override
    public void update(final float dt) {
        if (fx.hasCompleted() && fxs.hasNext()) fx = fxs.next();
        if (fy.hasCompleted() && fys.hasNext()) fy = fys.next();

        fx.addTime(dt);
        fy.addTime(dt);
    }

    @Override
    public boolean canFinish() {
        return true;
    }

    @Override
    public boolean hasFinished() {
        return fx.hasCompleted() && !fxs.hasNext()
            && fy.hasCompleted() && !fys.hasNext();
    }
}
