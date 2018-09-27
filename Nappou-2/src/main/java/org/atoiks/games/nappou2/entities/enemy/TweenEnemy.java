/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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

import java.awt.Color;

import se.tube42.lib.tweeny.Item;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.IEnemy;

public abstract class TweenEnemy extends IEnemy {

    private static final long serialVersionUID = 7192746L;

    public static final int FIELD_X = 0;
    public static final int FIELD_Y = 1;
    public static final int FIELD_R = 2;

    protected final Item xyr;

    private float dspX;
    private float dspY;

    protected Game game;

    protected TweenEnemy(int hp, float x, float y, float r) {
        this(hp, new Item(3));
        this.xyr.setImmediate(FIELD_X, x);
        this.xyr.setImmediate(FIELD_Y, y);
        this.xyr.setImmediate(FIELD_R, r);
    }

    protected TweenEnemy(int hp, Item tween) {
        super(hp);
        this.xyr = tween;
    }

    @Override
    public final void attachGame(Game game) {
        this.game = game;
    }


    @Override
    public void drift(float dx, float dy) {
        dspX += dx;
        dspY += dy;
    }

    @Override
    public final float getX() {
        return this.xyr.get(FIELD_X) + dspX;
    }

    @Override
    public final float getY() {
        return this.xyr.get(FIELD_Y) + dspY;
    }

    @Override
    public final float getR() {
        return this.xyr.get(FIELD_R);
    }

    public final void setR(float r) {
        this.xyr.setImmediate(FIELD_R, r);
    }

    public Item tween() {
        return xyr;
    }
}
