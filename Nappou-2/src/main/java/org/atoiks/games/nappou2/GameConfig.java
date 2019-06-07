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

public final class GameConfig implements Externalizable {

    private static final long serialVersionUID = -2916105168250234328L;

    // Default values = default settings
    public boolean bgm = true;
    public boolean challengeMode = false;
    public boolean fullscreen = false;

    @Override
    public void readExternal(final ObjectInput stream) throws IOException {
        this.bgm = stream.readBoolean();
        this.challengeMode = stream.readBoolean();
        this.fullscreen = stream.readBoolean();
    }

    @Override
    public void writeExternal(final ObjectOutput stream) throws IOException {
        stream.writeBoolean(this.bgm);
        stream.writeBoolean(this.challengeMode);
        stream.writeBoolean(this.fullscreen);
    }
}
