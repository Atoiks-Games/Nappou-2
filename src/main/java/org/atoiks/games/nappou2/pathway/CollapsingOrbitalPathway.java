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

package org.atoiks.games.nappou2.pathway;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.sizer.Sizer;

/**
 * Pathway that orbits with a decaying width around a singular point
 */
public final class CollapsingOrbitalPathway extends SizerOrbitalPathway<CollapsingThenExplodeSizer> {

    // Use if path is elliptical
    public CollapsingOrbitalPathway(float rx, float ry, float x, float y, int direction, float speedMod, double startPos) {
        this(new Vector2(rx, ry), new Vector2(x, y), direction, speedMod, startPos);
    }

    public CollapsingOrbitalPathway(Vector2 axis, Vector2 center, int direction, float speedMod, double startPos) {
        // Direction is applied on the Y component
        super(new Vector2(1, direction).mul(axis),
                center,
                speedMod / 500,
                (float) startPos,
                CollapsingThenExplodeSizer.INSTANCE);

        this.setOrbitalWidth(1);
    }
}

final class CollapsingThenExplodeSizer implements Sizer {

    public static final CollapsingThenExplodeSizer INSTANCE = new CollapsingThenExplodeSizer();

    private CollapsingThenExplodeSizer() {
    }

    @Override
    public float getNextSize(final float prev, float dt) {
        // by returning 100, the position will be really far away from the
        // center of the orbit, putting the enemy out of screen, causing it
        // to be removed from the game (so it *disappears* after collapsing
        // to one point)

        final float next = prev - dt;
        return next > 0 ? next : 100;
    }
}
