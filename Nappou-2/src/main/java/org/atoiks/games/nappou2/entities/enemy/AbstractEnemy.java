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

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.IEnemy;

import org.atoiks.games.nappou2.physics.CollisionSquare;

import org.atoiks.games.nappou2.graphics.IEnemyRenderer;
import org.atoiks.games.nappou2.graphics.ColorEnemyRenderer;

import static org.atoiks.games.nappou2.Utils.fastCircleCollision;

/* package */ abstract class AbstractEnemy implements IEnemy {

    private static final long serialVersionUID = 8123472652L;

    private static final int SCREEN_EDGE_BUFFER = 16;

    protected int hp;

    protected Game game;

    public IEnemyRenderer compRenderer = ColorEnemyRenderer.DEFAULT;

    protected AbstractEnemy(int hp) {
        this.hp = hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public int changeHp(int delta) {
        return this.hp += delta;
    }

    @Override
    public final void render(IGraphics g) {
        compRenderer.render(g, this);
    }

    @Override
    public boolean collidesWith(final float x1, final float y1, final float r1) {
        return fastCircleCollision(getX(), getY(), getR(), x1, y1, r1);
    }

    @Override
    public boolean isOutOfScreen(final int w, final int h) {
        final float x = getX();
        final float y = getY();
        final float r = getR();
        return (x + r < -SCREEN_EDGE_BUFFER)
            || (x - r > w + SCREEN_EDGE_BUFFER)
            || (y + r < -SCREEN_EDGE_BUFFER)
            || (y - r > h + SCREEN_EDGE_BUFFER);
    }

    @Override
    public CollisionSquare makeCollisionSquare() {
        return new CollisionSquare(getX(), getY(), getR());
    }

    @Override
    public final void attachGame(Game game) {
        this.game = game;
    }
}
