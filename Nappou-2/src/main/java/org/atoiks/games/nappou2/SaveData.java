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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Externalizable;

import org.atoiks.games.nappou2.levels.NullState;
import org.atoiks.games.nappou2.levels.ILevelState;

import org.atoiks.games.nappou2.entities.shield.NullShield;
import org.atoiks.games.nappou2.entities.shield.IShieldEntity;

public final class SaveData implements Externalizable {

    private static final long serialVersionUID = -6315543815579288169L;

    private ILevelState checkpoint = NullState.INSTANCE;
    private IShieldEntity shield = new NullShield();
    private boolean challengeMode = false;

    public void setCheckpoint(final ILevelState p, boolean m) {
        this.setCheckpoint(p);
        this.challengeMode = m;
    }

    public void setCheckpoint(final ILevelState p) {
        this.checkpoint = p != null ? p : NullState.INSTANCE;
    }

    public void setShield(final IShieldEntity s) {
        this.shield = s != null ? s : new NullShield();
    }

    public boolean isChallengeMode() {
        return this.challengeMode;
    }

    public ILevelState getCheckpoint() {
        return this.checkpoint;
    }

    public IShieldEntity getShieldCopy() {
        return this.shield.copy();
    }

    @Override
    public void readExternal(final ObjectInput stream) throws IOException, ClassNotFoundException {
        this.checkpoint = (ILevelState) stream.readObject();
        this.shield = (IShieldEntity) stream.readObject();
        this.challengeMode = stream.readBoolean();
    }

    @Override
    public void writeExternal(final ObjectOutput stream) throws IOException {
        stream.writeObject(this.checkpoint);
        stream.writeObject(this.shield);
        stream.writeBoolean(this.challengeMode);
    }
}
