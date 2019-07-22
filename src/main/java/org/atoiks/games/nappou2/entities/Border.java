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

import org.atoiks.games.nappou2.Vector2;

public final class Border {

    private int width;
    private int height;

    public Border() {
    }

    public Border(int w, int h) {
        this.clip(w, h);
    }

    public void clip(int w, int h) {
        this.width = Math.max(0, w);
        this.height = Math.max(0, h);
    }

    public void clipWidth(int w) {
        this.width = Math.max(0, w);
    }

    public void clipHeight(int h) {
        this.height = Math.max(0, h);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Vector2 toVector() {
        return new Vector2(this.width, this.height);
    }

    public boolean containsCollidable(final Collidable entity) {
        return !entity.isOutOfScreen(this.width, this.height);
    }

    @Override
    public String toString() {
        return "{width=" + width + ", height=" + height + "}";
    }
}
