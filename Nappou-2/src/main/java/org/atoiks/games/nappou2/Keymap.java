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

import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Input;

public final class Keymap implements Externalizable {

    private static final long serialVersionUID = -5418643969162122158L;

    // Default values = default settings
    private int kcUp = KeyEvent.VK_UP;
    private int kcDown = KeyEvent.VK_DOWN;
    private int kcLeft = KeyEvent.VK_LEFT;
    private int kcRight = KeyEvent.VK_RIGHT;

    private int kcSlow = KeyEvent.VK_SHIFT;
    private int kcFire = KeyEvent.VK_Z;
    private int kcShield = KeyEvent.VK_X;

    public boolean shouldMoveUp() {
        return Input.isKeyDown(this.kcUp);
    }

    public boolean shouldMoveDown() {
        return Input.isKeyDown(this.kcDown);
    }

    public boolean shouldMoveLeft() {
        return Input.isKeyDown(this.kcLeft);
    }

    public boolean shouldMoveRight() {
        return Input.isKeyDown(this.kcRight);
    }

    public boolean shouldSlowDown() {
        return Input.isKeyDown(this.kcSlow);
    }

    public boolean shouldFire() {
        return Input.isKeyDown(this.kcFire);
    }

    public boolean shouldActivateShield() {
        return Input.isKeyDown(this.kcShield);
    }

    @Override
    public void readExternal(final ObjectInput stream) throws IOException {
        this.kcUp = stream.readInt();
        this.kcDown = stream.readInt();
        this.kcLeft = stream.readInt();
        this.kcRight = stream.readInt();

        this.kcSlow = stream.readInt();
        this.kcFire = stream.readInt();
        this.kcShield = stream.readInt();
    }

    @Override
    public void writeExternal(final ObjectOutput stream) throws IOException {
        stream.writeInt(this.kcUp);
        stream.writeInt(this.kcDown);
        stream.writeInt(this.kcLeft);
        stream.writeInt(this.kcRight);

        stream.writeInt(this.kcSlow);
        stream.writeInt(this.kcFire);
        stream.writeInt(this.kcShield);
    }
}
