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

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.graphics.Renderer;
import org.atoiks.games.nappou2.graphics.NullRenderer;
import org.atoiks.games.nappou2.graphics.OutlineRenderer;

import org.atoiks.games.nappou2.graphics.shapes.Circular;

/* package */ abstract class AbstractEnemy implements Enemy {

    private static final int SCREEN_EDGE_BUFFER = 16;

    private Renderer compRenderer = OutlineRenderer.DEFAULT;

    protected int hp;

    protected Game game;

    protected AbstractEnemy(int hp) {
        this.hp = hp;
    }

    @Override
    public boolean isDead() {
        return hp <= 0;
    }

    @Override
    public int changeHp(int delta) {
        return this.hp += delta;
    }

    @Override
    public final Renderer getRenderer() {
        return this.compRenderer;
    }

    public final void setRenderer(Renderer renderer) {
        this.compRenderer = renderer != null ? renderer : NullRenderer.INSTANCE;
    }

    @Override
    public boolean collidesWith(final Circular other) {
        return Circular.overlaps(this, other);
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        final Vector2 pos = this.getPosition();
        final float x = pos.getX();
        final float y = pos.getY();
        final float r = getRadius();
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }

    @Override
    public final void attachGame(Game game) {
        this.game = game;
    }

    @Override
    public final Game getAssocGame() {
        return this.game;
    }
}
