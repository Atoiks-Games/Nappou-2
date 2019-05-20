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

package org.atoiks.games.nappou2.entities.pathway;

import java.util.Iterator;

import org.atoiks.games.nappou2.entities.IPathway;

/**
 * Sequences a bunch of pathways. Note: canFinish always true, but whether it
 * can actually finish depends on the sequence of pathways. If an unlimited
 * pathway is passed to this, it will never return true for hasFinished!
 */
public final class ChainedPathway implements IPathway {

    private final Iterator<? extends IPathway> it;

    private IPathway currentPath;

    public ChainedPathway(Iterator<? extends IPathway> it) {
        this.it = it;
        if (it.hasNext()) {
            currentPath = it.next();
        } else {
            throw new IllegalArgumentException("Iterator is already empty!");
        }
    }

    @Override
    public float getX() {
        return currentPath.getX();
    }

    @Override
    public float getY() {
        return currentPath.getY();
    }

    @Override
    public void update(final float dt) {
        currentPath.update(dt);
        if (currentPath.hasFinished() && it.hasNext()) {
            currentPath = it.next();
        }
    }

    @Override
    public boolean hasFinished() {
        return currentPath.hasFinished() && !it.hasNext();
    }
}
