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

package org.atoiks.games.nappou2.levels;

import java.io.ObjectStreamException;

import org.atoiks.games.framework2d.IGraphics;

public final class NullState implements LevelState {

    private static final long serialVersionUID = 2023699955695331416L;

    public static final NullState INSTANCE = new NullState();

    private NullState() {
    }

    @Override
    public void updateLevel(LevelContext ctx, float dt) {
        // Do nothing
    }

    // Exists to make sure singleton works
    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}
