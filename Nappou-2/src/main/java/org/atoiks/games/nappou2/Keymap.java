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

    public void changeMoveUpKeycode(int kc) {
        this.kcUp = kc;
    }

    public void changeMoveDownKeycode(int kc) {
        this.kcDown = kc;
    }

    public void changeMoveLeftKeycode(int kc) {
        this.kcLeft = kc;
    }

    public void changeMoveRightKeycode(int kc) {
        this.kcRight = kc;
    }

    public void changeSlowDownKeycode(int kc) {
        this.kcSlow = kc;
    }

    public void changeFireKeycode(int kc) {
        this.kcFire = kc;
    }

    public void changeActivateShieldKeycode(int kc) {
        this.kcShield = kc;
    }

    public boolean keyIsAlreadyAssigned(final int kc) {
        return kc == KeyEvent.VK_ESCAPE || kc == KeyEvent.VK_ENTER
            || kc == this.kcUp
            || kc == this.kcDown
            || kc == this.kcLeft
            || kc == this.kcRight
            || kc == this.kcSlow
            || kc == this.kcFire
            || kc == this.kcShield;
    }

    public int getKeycodeOfIndex(int index) {
        // Index is based on info message format!
        switch (index) {
            case 0: return this.kcUp;
            case 1: return this.kcDown;
            case 2: return this.kcLeft;
            case 3: return this.kcRight;
            case 4: return this.kcSlow;
            case 5: return this.kcFire;
            case 6: return this.kcShield;
            default:
                throw new IndexOutOfBoundsException(index);
        }
    }

    public void changeKeycodeOfIndex(int index, int kc) {
        if (kc == KeyEvent.VK_UNDEFINED) {
            throw new IllegalArgumentException("Keycode cannot be undefined!");
        }

        // Index is based on info message format!
        switch (index) {
            case 0: this.kcUp = kc; break;
            case 1: this.kcDown = kc; break;
            case 2: this.kcLeft = kc; break;
            case 3: this.kcRight = kc; break;
            case 4: this.kcSlow = kc; break;
            case 5: this.kcFire = kc; break;
            case 6: this.kcShield = kc; break;
            default:
                throw new IndexOutOfBoundsException(index);
        }
    }

    public String[][] getInfoMessage() {
        return new String[][] {
            { "Move up:", keycodeToString(this.kcUp) },
            { "     down:", keycodeToString(this.kcDown) },
            { "     left:", keycodeToString(this.kcLeft) },
            { "     right:", keycodeToString(this.kcRight) },
            {"Focus:", keycodeToString(this.kcSlow) },
            {"Shoot:", keycodeToString(this.kcFire) },
            {"Activate shield:", keycodeToString(this.kcShield) },
            {"Pause game:", keycodeToString(KeyEvent.VK_ESCAPE) },
            {"Select:", keycodeToString(KeyEvent.VK_ENTER) },
        };
    }

    public String getFireKeystr() {
        return keycodeToString(this.kcFire);
    }

    public String getShieldKeystr() {
        return keycodeToString(this.kcShield);
    }

    public static String keycodeToString(final int kc) {
        switch (kc) {
            case KeyEvent.VK_TAB:           return "<Tab>";
            case KeyEvent.VK_SPACE:         return "<Space>";
            case KeyEvent.VK_ENTER:         return "<Enter>";
            case KeyEvent.VK_SHIFT:         return "<Shift>";
            case KeyEvent.VK_CONTROL:       return "<Ctrl>";
            case KeyEvent.VK_ALT:           return "<Alt>";
            case KeyEvent.VK_WINDOWS:       return "<Windows>";
            case KeyEvent.VK_META:          return "<Meta>";
            case KeyEvent.VK_ESCAPE:        return "<Escape>";
            case KeyEvent.VK_BACK_SPACE:    return "<Backspace>";
            case KeyEvent.VK_DELETE:        return "<Delete>";
            default:                        return KeyEvent.getKeyText(kc);
        }
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
