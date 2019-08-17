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

public abstract class TimeBasedShield implements ShieldEntity {

    private static final long serialVersionUID = 1607481798186246352L;
    private static final Renderer DEFAULT_RENDERER = new OutlineRenderer(Color.orange);

    // see read/writeObject
    private transient float timeout;

    protected transient float r;

    protected transient boolean active;
    protected transient Vector2 position;
    protected transient float time;

    protected TimeBasedShield(final TimeBasedShield from) {
        this(from.timeout, from.r);
    }

    protected TimeBasedShield(final float timeout, final float r) {
        this.timeout = timeout;
        this.r = r;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public float getRadius() {
        return this.r;
    }

    @Override
    public Renderer getRenderer() {
        return this.active ? DEFAULT_RENDERER : NullRenderer.INSTANCE;
    }

    @Override
    public void setPosition(Vector2 pos) {
        this.position = pos;
    }

    @Override
    public void update(float dt) {
        if (!this.active) {
            return;
        }

        this.time += dt;
        if (this.time >= this.timeout) {
            this.deactivate();
        }
    }

    @Override
    public void activate() {
        if (!this.active && this.isReady()) {
            this.active = true;
        }
    }

    @Override
    public void deactivate() {
        this.active = false;
        this.time = 0;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public boolean isReady() {
        return this.time == 0;
    }

    protected final float getTimeout() {
        return this.timeout;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        // Have java serialize as much as possible
        s.defaultWriteObject();

        s.writeFloat(this.r);
        s.writeFloat(this.timeout);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        // Have java deserialize as much as possible
        s.defaultReadObject();

        this.r = s.readFloat();
        this.timeout = s.readFloat();
    }
}
