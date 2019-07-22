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

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.KeyCode;

public final class Keymap implements Externalizable {

    private static final long serialVersionUID = -5418643969162122158L;

    // Default values = default settings
    private KeyCode kcUp = KeyCode.KEY_UP;
    private KeyCode kcDown = KeyCode.KEY_DOWN;
    private KeyCode kcLeft = KeyCode.KEY_LEFT;
    private KeyCode kcRight = KeyCode.KEY_RIGHT;

    private KeyCode kcSlow = KeyCode.KEY_LSHIFT;
    private KeyCode kcFire = KeyCode.KEY_Z;
    private KeyCode kcShield = KeyCode.KEY_X;

    public boolean shouldSelectPrevious() {
        return Input.isKeyPressed(this.kcUp);
    }

    public boolean shouldSelectNext() {
        return Input.isKeyPressed(this.kcDown)
            || Input.isKeyPressed(KeyCode.KEY_TAB);
    }

    public boolean shouldSelectLeft() {
        return Input.isKeyPressed(this.kcLeft);
    }

    public boolean shouldSelectRight() {
        return Input.isKeyPressed(this.kcRight);
    }

    public Vector2 getMovementDirection() {
        int signX = 0;
        int signY = 0;

        if (Input.isKeyDown(this.kcDown))  ++signY;
        if (Input.isKeyDown(this.kcUp))    --signY;
        if (Input.isKeyDown(this.kcRight)) ++signX;
        if (Input.isKeyDown(this.kcLeft))  --signX;

        return new Vector2(signX, signY);
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

    public void changeMoveUpKeycode(KeyCode kc) {
        this.kcUp = kc;
    }

    public void changeMoveDownKeycode(KeyCode kc) {
        this.kcDown = kc;
    }

    public void changeMoveLeftKeycode(KeyCode kc) {
        this.kcLeft = kc;
    }

    public void changeMoveRightKeycode(KeyCode kc) {
        this.kcRight = kc;
    }

    public void changeSlowDownKeycode(KeyCode kc) {
        this.kcSlow = kc;
    }

    public void changeFireKeycode(KeyCode kc) {
        this.kcFire = kc;
    }

    public void changeActivateShieldKeycode(KeyCode kc) {
        this.kcShield = kc;
    }

    public boolean keyIsAlreadyAssigned(final KeyCode kc) {
        return kc == KeyCode.KEY_ESCAPE || kc == KeyCode.KEY_ENTER
            || kc == this.kcUp
            || kc == this.kcDown
            || kc == this.kcLeft
            || kc == this.kcRight
            || kc == this.kcSlow
            || kc == this.kcFire
            || kc == this.kcShield;
    }

    public KeyCode getKeycodeOfIndex(int index) {
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

    public void changeKeycodeOfIndex(int index, KeyCode kc) {
        if (kc == KeyCode.KEY_UNDEFINED) {
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

    public void reset() {
        this.copyFrom(new Keymap());
    }

    public void copyFrom(final Keymap map) {
        this.kcUp = map.kcUp;
        this.kcDown = map.kcDown;
        this.kcLeft = map.kcLeft;
        this.kcRight = map.kcRight;
        this.kcSlow = map.kcSlow;
        this.kcFire = map.kcFire;
        this.kcShield = map.kcShield;
    }

    public String[][] getInfoMessage() {
        return new String[][] {
            { "Move Up:", keycodeToString(this.kcUp) },
            { "     Down:", keycodeToString(this.kcDown) },
            { "     Left:", keycodeToString(this.kcLeft) },
            { "     Right:", keycodeToString(this.kcRight) },
            {"Focus:", keycodeToString(this.kcSlow) },
            {"Shoot:", keycodeToString(this.kcFire) },
            {"Shield:", keycodeToString(this.kcShield) },
            {"Pause:", keycodeToString(KeyCode.KEY_ESCAPE) },
            {"Select:", keycodeToString(KeyCode.KEY_ENTER) },
        };
    }

    public String getFireKeystr() {
        return keycodeToString(this.kcFire);
    }

    public String getShieldKeystr() {
        return keycodeToString(this.kcShield);
    }

    public static String keycodeToString(final KeyCode kc) {
        return kc.getKeyString();
    }

    @Override
    public void readExternal(final ObjectInput stream) throws IOException {
        final KeyCode[] array = KeyCode.values();
        this.kcUp = array[stream.readInt()];
        this.kcDown = array[stream.readInt()];
        this.kcLeft = array[stream.readInt()];
        this.kcRight = array[stream.readInt()];

        this.kcSlow = array[stream.readInt()];
        this.kcFire = array[stream.readInt()];
        this.kcShield = array[stream.readInt()];
    }

    @Override
    public void writeExternal(final ObjectOutput stream) throws IOException {
        stream.writeInt(this.kcUp.ordinal());
        stream.writeInt(this.kcDown.ordinal());
        stream.writeInt(this.kcLeft.ordinal());
        stream.writeInt(this.kcRight.ordinal());

        stream.writeInt(this.kcSlow.ordinal());
        stream.writeInt(this.kcFire.ordinal());
        stream.writeInt(this.kcShield.ordinal());
    }
}
