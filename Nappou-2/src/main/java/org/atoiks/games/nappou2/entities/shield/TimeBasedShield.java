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

import org.atoiks.games.nappou2.graphics.Renderer;
import org.atoiks.games.nappou2.graphics.NullRenderer;
import org.atoiks.games.nappou2.graphics.OutlineRenderer;

public abstract class TimeBasedShield implements IShieldEntity {

    private static final long serialVersionUID = 172635916L;
    private static final Renderer DEFAULT_RENDERER = new OutlineRenderer(Color.orange);

    // see read/writeObject
    private transient Color color = Color.orange;
    private transient float reloadTime;
    private transient float timeout;

    protected transient float r;

    protected transient boolean active;
    protected transient Vector2 position;
    protected transient float time;

    protected TimeBasedShield(final TimeBasedShield from) {
        this(from.timeout, from.reloadTime, from.r);
        this.color = from.color;
    }

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
    public float getRadius() {
        return r;
    }

    @Override
    public Renderer getRenderer() {
        return active ? DEFAULT_RENDERER : NullRenderer.INSTANCE;
    }

    @Override
    public void setPosition(Vector2 pos) {
        this.position = pos;
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

    public Color getColor() {
        return this.color;
    }

    protected final float getReloadTime() {
        return this.reloadTime;
    }

    protected final float getTimeout() {
        return this.timeout;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        // Have java serialize as much as possible
        s.defaultWriteObject();

        // Write color as packed bytes a, r, g, b
        s.writeInt(this.color.getRGB());

        s.writeFloat(this.r);
        s.writeFloat(this.reloadTime);
        s.writeFloat(this.timeout);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        // Have java deserialize as much as possible
        s.defaultReadObject();

        // Read color as packed bytes a, r, g, b
        this.color = new Color(s.readInt(), true);

        this.r = s.readFloat();
        this.reloadTime = s.readFloat();
        this.timeout = s.readFloat();
    }
}
