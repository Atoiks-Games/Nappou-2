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

package org.atoiks.games.nappou2.entities.shield;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Vector2;

public abstract class TimeBasedShield implements IShieldEntity {

    private static final long serialVersionUID = 172635916L;

    protected final float reloadTime;
    protected final float timeout;

    protected transient Color color = Color.orange; // see read/writeObject
    protected float r;

    protected transient boolean active;
    protected transient Vector2 position;
    protected transient float time;

    protected TimeBasedShield(final float timeout, final float reloadTime, final float r) {
        this.timeout = timeout;
        this.reloadTime = reloadTime;
        this.time = timeout + reloadTime;
        this.r = r;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public float getR() {
        return r;
    }

    @Override
    public void setPosition(Vector2 pos) {
        this.position = pos;
    }

    @Override
    public void render(IGraphics g) {
        if (active) {
            g.setColor(this.color);

            final float x = position.getX();
            final float y = position.getY();
            // x, y are the center of the shield
            g.drawOval(x - r, y - r, x + r, y + r);
        }
    }

    @Override
    public void update(float dt) {
        time += dt;
        if (active && time >= timeout) {
            deactivate();
        }
    }

    @Override
    public void activate() {
        if (!active && isReady()) {
            active = true;
            time = 0;
        }
    }

    @Override
    public void deactivate() {
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isReady() {
        return time > timeout + reloadTime;
    }

    public void setColor(Color c) {
        this.color = c != null ? c : Color.orange;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        // Have java serialize as much as possible
        s.defaultWriteObject();
        // Write color as packed bytes a, r, g, b
        s.writeInt(this.color.getRGB());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        // Have java deserialize as much as possible
        s.defaultReadObject();
        // Read color as packed bytes a, r, g, b
        this.color = new Color(s.readInt(), true);
    }
}
