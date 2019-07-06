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
import org.atoiks.games.nappou2.levels.LevelState;

import org.atoiks.games.nappou2.entities.shield.NullShield;
import org.atoiks.games.nappou2.entities.shield.ShieldEntity;

public final class SaveData implements Externalizable {

    private static final long serialVersionUID = -6315543815579288169L;

    private LevelState checkpoint = NullState.INSTANCE;
    private ShieldEntity shield = new NullShield();
    private boolean challengeMode = false;

    public void setCheckpoint(final LevelState p, boolean m) {
        this.setCheckpoint(p);
        this.challengeMode = m;
    }

    public void setCheckpoint(final LevelState p) {
        this.checkpoint = p != null ? p : NullState.INSTANCE;
    }

    public void setShield(final ShieldEntity s) {
        this.shield = s != null ? s : new NullShield();
    }

    public boolean isChallengeMode() {
        return this.challengeMode;
    }

    public LevelState getCheckpoint() {
        return this.checkpoint;
    }

    public ShieldEntity getShieldCopy() {
        return this.shield.copy();
    }

    @Override
    public void readExternal(final ObjectInput stream) throws IOException, ClassNotFoundException {
        this.setCheckpoint((LevelState) stream.readObject());
        this.setShield((ShieldEntity) stream.readObject());
        this.challengeMode = stream.readBoolean();
    }

    @Override
    public void writeExternal(final ObjectOutput stream) throws IOException {
        stream.writeObject(this.checkpoint);
        stream.writeObject(this.shield);
        stream.writeBoolean(this.challengeMode);
    }
}
