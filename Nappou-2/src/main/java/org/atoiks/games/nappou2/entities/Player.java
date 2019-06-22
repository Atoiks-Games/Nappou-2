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

package org.atoiks.games.nappou2.entities;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.entities.shield.IShield;
import org.atoiks.games.nappou2.entities.shield.TrackingTimeShield;

public final class Player implements ITrackable {

    public static final int RADIUS = 8;
    public static final int COLLISION_RADIUS = 2;
    public static final int HINT_COL_RADIUS = COLLISION_RADIUS + 2;

    private final TrackingTimeShield respawnShield;
    private final HitpointCounter hpCounter;

    private IShield shield;

    private Vector2 position;
    private Vector2 velocity;
    private float speedScale = 1;

    public Player(IShield shield) {
        this.shield = shield;
        this.respawnShield = new TrackingTimeShield(3f, 0, Player.RADIUS);
        this.respawnShield.setColor(Color.red);
        this.hpCounter = new HitpointCounter();
        this.velocity = Vector2.ZERO;
        this.setPosition(Vector2.ZERO);
    }

    public void render(final IGraphics g) {
        g.setColor(Color.cyan);
        final float x = position.getX();
        final float y = position.getY();
        g.drawOval(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS);
        if (speedScale != 1) {
            g.setColor(Color.yellow);
            g.fillOval(x - HINT_COL_RADIUS, y - HINT_COL_RADIUS, x + HINT_COL_RADIUS, y + HINT_COL_RADIUS);
        }

        this.shield.render(g);
        this.respawnShield.render(g);
    }

    public void update(final float dt) {
        this.shield.update(dt);
        this.respawnShield.update(dt);

        this.setPosition(Vector2.muladd(this.speedScale * dt, this.velocity, this.position));
    }

    public void applyFreshShield() {
        this.shield = this.shield.copy();
    }

    public IShield getShield() {
        return this.shield;
    }

    public void activateRespawnShield() {
        this.respawnShield.activate();
    }

    public boolean isRespawnShieldActive() {
        return this.respawnShield.isActive();
    }

    public HitpointCounter getHpCounter() {
        return this.hpCounter;
    }

    public void setSpeedScale(float scale) {
        this.speedScale = scale;
    }

    public void resetSpeedScale() {
        this.speedScale = 1;
    }

    public void setVelocity(Vector2 v) {
        this.velocity = v;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    public float getX() {
        return this.position.getX();
    }

    public float getY() {
        return this.position.getY();
    }

    public void setPosition(final float x, final float y) {
        this.setPosition(new Vector2(x, y));
    }

    public void setPosition(final Vector2 pos) {
        this.position = pos;
        this.shield.setPosition(pos);
        this.respawnShield.setPosition(pos);
    }
}
