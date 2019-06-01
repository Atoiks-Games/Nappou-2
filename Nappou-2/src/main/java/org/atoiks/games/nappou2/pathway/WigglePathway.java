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

/**
 * Pathway that follows a trackable entity. The path it takes is linear.
 */
public final class WigglePathway implements UnboundPathway {

    private final Vector2 velocity;
    private final Vector2 amplitude;
    private final float afreq;
    private final float phase;
    private Vector2 position;

    private float time;

    public WigglePathway(Vector2 pos, Vector2 velocity, Vector2 amplitude, float afreq, float phase) {
        this.velocity = velocity;
        this.amplitude = amplitude;
        this.afreq = afreq;
        this.phase = phase;
        this.position = pos;
    }

    public WigglePathway(Vector2 velocity, Vector2 amplitude, float afreq, float phase) {
        this(Vector2.ZERO, velocity, amplitude, afreq, phase);
    }

    public void setPosition(Vector2 pos) {
        this.position = pos;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void update(final float dt) {
        this.time += dt;

        // position += dt * (velocity + amplitude * cos(afreq * time + phase))
        final float cosVal = (float) Math.cos(this.afreq * this.time + this.phase);
        this.position = this.position.add(velocity.add(amplitude.mul(cosVal)).mul(dt));
    }

    @Override
    public boolean hasFinished() {
        return false;
    }

    public static WigglePathway horizontal(Vector2 position, Vector2 velocity, float m, float s) {
        final WigglePathway pathway = new WigglePathway(velocity, Vector2.UNIT_X.mul(m), s, 0);
        pathway.setPosition(position);
        return pathway;
    }

    public static WigglePathway vertical(Vector2 position, Vector2 velocity, float m, float s) {
        final WigglePathway pathway = new WigglePathway(velocity, Vector2.UNIT_Y.mul(m), s, 0);
        pathway.setPosition(position);
        return pathway;
    }
}
