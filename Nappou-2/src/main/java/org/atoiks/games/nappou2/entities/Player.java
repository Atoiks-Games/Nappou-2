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

public final class Player implements ITrackable {

    private static final float RESPAWN_SHIELD_TIME = 3f;
    private static final float RESPAWN_SHIELD_OFF = -1f;

    public static final int RADIUS = 8;
    public static final int COLLISION_RADIUS = 2;
    public static final int HINT_COL_RADIUS = COLLISION_RADIUS + 2;

    private IShield shield;

    private Vector2 position;
    private Vector2 velocity;
    private float speedScale = 1;
    private int hp = 5;
    private float respawnShieldTime = RESPAWN_SHIELD_OFF;

    private boolean ignoreHpChange;

    public Player(float x, float y, IShield shield) {
        this(new Vector2(x, y), shield);
    }

    public Player(Vector2 position, IShield shield) {
        this.position = position;
        this.shield = shield;
    }

    public void render(final IGraphics g) {
        this.shield.render(g);
        if (isRespawnShieldActive()) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.cyan);
        }

        final float x = position.getX();
        final float y = position.getY();
        g.drawOval(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS);
        if (speedScale != 1) {
            g.setColor(Color.yellow);
            g.fillOval(x - HINT_COL_RADIUS, y - HINT_COL_RADIUS, x + HINT_COL_RADIUS, y + HINT_COL_RADIUS);
        }
    }

    public void update(final float dt) {
        this.position = Vector2.muladd(this.speedScale * dt, this.velocity, this.position);

        this.shield.update(dt);
        this.shield.setPosition(position);

        if (respawnShieldTime >= 0) {
            if ((respawnShieldTime += dt) >= RESPAWN_SHIELD_TIME) respawnShieldTime = RESPAWN_SHIELD_OFF;
        }
    }

    public void applyFreshShield() {
        this.shield = this.shield.copy();
    }

    public IShield getShield() {
        return this.shield;
    }

    public void deactivateRespawnShield() {
        respawnShieldTime = RESPAWN_SHIELD_OFF;
    }

    public void activateRespawnShield() {
        respawnShieldTime = 0;
    }

    public boolean isRespawnShieldActive() {
        return respawnShieldTime >= 0;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int changeHpBy(int delta) {
        if (!ignoreHpChange) {
            this.hp += delta;
        }
        return this.hp;
    }

    public void setIgnoreHpChange(boolean flag) {
        ignoreHpChange = flag;
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
}
