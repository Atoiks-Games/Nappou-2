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

package org.atoiks.games.nappou2;

import java.io.Serializable;

import org.atoiks.games.nappou2.levels.NullState;
import org.atoiks.games.nappou2.levels.ILevelState;

import org.atoiks.games.nappou2.entities.shield.*;

public final class SaveData implements Serializable {

    private static final long serialVersionUID = -6315543815579288169L;

    private ILevelState checkpoint = NullState.INSTANCE;
    private IShield shield = new NullShield();

    public void setCheckpoint(final ILevelState p) {
        this.checkpoint = p != null ? p : NullState.INSTANCE;
    }

    public void setShield(final IShield s) {
        this.shield = s != null ? s : new NullShield();
    }

    public ILevelState getCheckpoint() {
        return this.checkpoint;
    }

    public IShield getShieldCopy() {
        return this.shield.copy();
    }
}
