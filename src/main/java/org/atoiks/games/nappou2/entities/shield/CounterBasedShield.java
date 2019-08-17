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

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.graphics.Renderer;
import org.atoiks.games.nappou2.graphics.NullRenderer;

public final class CounterBasedShield implements ShieldEntity {

    private static final long serialVersionUID = 5782188724080604520L;

    // see read/writeObject
    private transient ShieldEntity wrapper;
    private transient int maxTimes;

    private transient int count = -1;

    public CounterBasedShield(ShieldEntity wrapper, int maxTimes) {
        this.wrapper = wrapper;
        this.maxTimes = maxTimes;
    }

    @Override
    public void activate() {
        if (this.isReady()) {
            ++this.count;
            this.wrapper.activate();
        }
    }

    @Override
    public void deactivate() {
        this.wrapper.deactivate();
    }

    @Override
    public boolean isActive() {
        return this.wrapper.isActive();
    }

    @Override
    public boolean isReady() {
        if (this.count + 1 < this.maxTimes) {
            return this.wrapper.isReady();
        }

        return false;
    }

    @Override
    public void update(final float dt) {
        this.wrapper.update(dt);
    }

    @Override
    public CounterBasedShield copy() {
        return new CounterBasedShield(wrapper.copy(), this.maxTimes);
    }

    @Override
    public void setPosition(Vector2 vec) {
        this.wrapper.setPosition(vec);
    }

    @Override
    public Vector2 getPosition() {
        return this.wrapper.getPosition();
    }

    @Override
    public float getRadius() {
        return this.wrapper.getRadius();
    }

    @Override
    public Renderer getRenderer() {
        if (this.count < maxTimes) {
            return this.wrapper.getRenderer();
        }
        return NullRenderer.INSTANCE;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        // Have java serialize as much as possible
        s.defaultWriteObject();

        s.writeObject(this.wrapper);
        s.writeInt(this.maxTimes);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        // Have java deserialize as much as possible
        s.defaultReadObject();

        this.wrapper = (ShieldEntity) s.readObject();
        this.maxTimes = s.readInt();
    }
}
