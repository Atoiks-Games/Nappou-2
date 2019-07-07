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
import org.atoiks.games.nappou2.ScoreCounter;
import org.atoiks.games.nappou2.HitpointCounter;

import org.atoiks.games.nappou2.graphics.Renderer;
import org.atoiks.games.nappou2.graphics.FillRenderer;
import org.atoiks.games.nappou2.graphics.NullRenderer;
import org.atoiks.games.nappou2.graphics.OutlineRenderer;

import org.atoiks.games.nappou2.graphics.shapes.Circular;

import org.atoiks.games.nappou2.entities.shield.IShield;
import org.atoiks.games.nappou2.entities.shield.IShieldEntity;
import org.atoiks.games.nappou2.entities.shield.RespawnShield;

public final class Player implements Drawable, Circular {

    public static final int RADIUS = 8;
    public static final int COLLISION_RADIUS = 2;

    private static final Renderer CYAN_RENDERER = new OutlineRenderer(Color.cyan);

    private final ScoreCounter scoreCounter = new ScoreCounter();
    private final HitpointCounter hpCounter = new HitpointCounter();
    private final SpeedHintCircle speedHint = new SpeedHintCircle(this);

    private final RespawnShield respawnShield = new RespawnShield();
    private final IShieldEntity shield;

    private Vector2 position;
    private boolean focusedMode;

    public Player(IShieldEntity shield) {
        this.shield = shield;
        this.setPosition(Vector2.ZERO);
    }

    public void render(final IGraphics g) {
        Drawable.render(g, this);
        Drawable.render(g, this.speedHint);
        Drawable.render(g, this.shield);
        Drawable.render(g, this.respawnShield);
    }

    @Override
    public Renderer getRenderer() {
        return CYAN_RENDERER;
    }

    @Override
    public float getRadius() {
        return RADIUS;
    }

    public void update(final float dt) {
        this.shield.update(dt);
        this.respawnShield.update(dt);
    }

    public IShield getShield() {
        return this.shield;
    }

    public IShield getRespawnShield() {
        return this.respawnShield;
    }

    public ScoreCounter getScoreCounter() {
        return this.scoreCounter;
    }

    public HitpointCounter getHpCounter() {
        return this.hpCounter;
    }

    public void setFocusedMode(boolean flag) {
        this.focusedMode = flag;
    }

    public boolean isInFocusedMode() {
        return this.focusedMode;
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

    public boolean collidesWith(ICollidable col) {
        return col.collidesWith(this.position, Player.COLLISION_RADIUS);
    }
}

final class SpeedHintCircle implements Drawable, Circular {

    private static final int HINT_COL_RADIUS = Player.COLLISION_RADIUS + 2;

    private static final Renderer YELLOW_RENDERER = new FillRenderer(Color.yellow);

    private Player player;

    public SpeedHintCircle(Player player) {
        this.player = player;
    }

    @Override
    public float getRadius() {
        return HINT_COL_RADIUS;
    }

    @Override
    public Renderer getRenderer() {
        return this.player.isInFocusedMode() ? YELLOW_RENDERER : NullRenderer.INSTANCE;
    }

    @Override
    public Vector2 getPosition() {
        return this.player.getPosition();
    }
}
