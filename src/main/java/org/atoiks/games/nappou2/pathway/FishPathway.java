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

public final class FishPathway implements UnboundPathway {

    private final float amplitude;
    private final float afreq;

    private final Vector2 baseVel;
    private final Vector2 acaVector;

    private float time;

    private Vector2 position;

    public FishPathway(float speed, final float direction, float amplitude, float afreq) {
        this(Vector2.ZERO, speed, direction, amplitude, afreq);
    }

    public FishPathway(Vector2 pos, float speed, final float direction, float amplitude, float afreq) {
        this.position = pos;
        this.amplitude = amplitude;
        this.afreq = afreq;

        this.baseVel = Vector2.fromPolar(speed, direction);
        this.acaVector = Vector2.fromPolar(1, direction - (float) (Math.PI / 2));
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void update(final float dt) {
        this.time += dt;

        final float aca = this.amplitude * (float) Math.cos(afreq * this.time);

        this.position = Vector2.muladd(
                dt,
                Vector2.muladd(aca, this.acaVector, this.baseVel),
                this.position);
    }
}
