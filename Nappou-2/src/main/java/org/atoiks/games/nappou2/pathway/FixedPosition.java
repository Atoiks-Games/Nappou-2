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
 * A fixed pathway that stays at one spot.
 * Can it really be a path if it stays at the same spot?
 */
public final class FixedPosition implements UnboundPathway {

    /**
     * A path that just stays fixed at (0, 0)
     */
    public static final FixedPosition DEFAULT = new FixedPosition(Vector2.ZERO);

    private final Vector2 position;

    public FixedPosition(float x, float y) {
        this(new Vector2(x, y));
    }

    public FixedPosition(final Vector2 position) {
        this.position = position;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void update(float dt) {
        // Do nothing
    }
}
