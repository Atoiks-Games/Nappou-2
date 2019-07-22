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

package org.atoiks.games.nappou2.graphics.shapes;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.framework2d.resource.Texture;

import org.atoiks.games.nappou2.Vector2;

public final class ImmutableRectangle implements Rectangular {

    private final Vector2 pos;
    private final float w;
    private final float h;

    public ImmutableRectangle(Vector2 position, float width, float height) {
        if (position == null) {
            position = Vector2.ZERO;
        }

        if (width < 0 || height < 0) {
            position = position.add(Vector2.min(new Vector2(width, height), Vector2.ZERO));
            width = Math.abs(width);
            height = Math.abs(height);
        }

        this.pos = position;
        this.w = width;
        this.h = height;
    }

    public static Rectangular formedBetween(Vector2 u, Vector2 v) {
        final Vector2 sides = v.sub(u);
        return new ImmutableRectangle(u, sides.getX(), sides.getY());
    }

    @Override
    public float getWidth() {
        return this.w;
    }

    @Override
    public float getHeight() {
        return this.h;
    }

    @Override
    public Vector2 getPosition() {
        return this.pos;
    }

    @Override
    public void draw(final IGraphics g) {
        final float x = this.pos.getX();
        final float y = this.pos.getY();

        g.drawRect(x, y, x + this.w, y + this.h);
    }

    @Override
    public void fill(final IGraphics g) {
        final float x = this.pos.getX();
        final float y = this.pos.getY();

        g.fillRect(x, y, x + this.w, y + this.h);
    }

    @Override
    public void renderTexture(IGraphics g, Texture img) {
        final float x = this.pos.getX();
        final float y = this.pos.getY();

        g.drawTexture(img, x, y, x + this.w, y + this.h);
    }
}
