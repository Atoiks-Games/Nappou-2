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

import java.io.Serializable;

import org.atoiks.games.framework2d.IGraphics;

public interface ILevelState extends Serializable {

    /**
     * Only called when starting game with "Continue".
     * If called, it will happen before the enter method.
     */
    public default void restore(ILevelContext ctx) {
    }

    // Maybe pass in the previous state?
    public default void enter(ILevelContext ctx) {
    }

    public default void exit() {
    }

    public default void renderBackground(final IGraphics g) {
    }

    public void updateLevel(ILevelContext ctx, float dt);
}
