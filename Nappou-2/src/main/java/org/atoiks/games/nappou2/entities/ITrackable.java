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

public interface ITrackable {

    public float getX();
    public float getY();

    public default Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public default float distanceFrom(float x, float y) {
        final float dx = getX() - x;
        final float dy = getY() - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public default double angleWith(float x, float y) {
        return Math.atan2(getX() - x, getY() - y);
    }
}
