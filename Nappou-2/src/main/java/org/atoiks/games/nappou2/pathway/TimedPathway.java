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

/**
 * Used to put a time limit on unbounded pathways (like FixedPathway)
 */
public final class TimedPathway implements IPathway {

    private final UnboundPathway path;
    private final float limit;

    private float elapsed;

    public TimedPathway(final UnboundPathway path, final float limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Time limit must be greater than zero: " + limit);
        }
        if (path == null) {
            throw new IllegalArgumentException("Pathway cannot be null");
        }

        this.path = path;
        this.limit = limit;
    }

    @Override
    public float getX() {
        return path.getX();
    }

    @Override
    public float getY() {
        return path.getY();
    }

    @Override
    public void update(final float dt) {
        if (elapsed < limit) {
            path.update(Math.min(Math.abs(limit - (elapsed += dt)), dt));
        }
    }

    @Override
    public boolean hasFinished() {
        return elapsed > limit;
    }
}
