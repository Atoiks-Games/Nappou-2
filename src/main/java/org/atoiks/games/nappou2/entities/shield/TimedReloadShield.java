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

public final class TimedReloadShield implements ShieldEntity {

    private static final long serialVersionUID = 5782188724080604520L;

    // see read/writeObject
    private transient ShieldEntity wrapper;
    private transient float reloadTime;

    private transient float timeRemaining;

    public TimedReloadShield(ShieldEntity wrapper, float reloadTime) {
        this.wrapper = wrapper;
        this.reloadTime = reloadTime;
    }

    public boolean isReloading() {
        return this.timeRemaining > 0;
    }

    public void restartReloadTimer() {
        this.timeRemaining = this.reloadTime;
    }

    @Override
    public void activate() {
        if (!this.isReloading()) {
            this.wrapper.activate();
        }
    }

    @Override
    public void deactivate() {
        this.wrapper.deactivate();
        this.restartReloadTimer();
    }

    @Override
    public boolean isActive() {
        if (this.isReloading()) {
            return false;
        }
        return this.wrapper.isActive();
    }

    @Override
    public boolean isReady() {
        if (this.isReloading()) {
            return false;
        }

        return this.wrapper.isReady();
    }

    @Override
    public void update(final float dt) {
        if (this.isReloading()) {
            this.timeRemaining -= dt;
            return;
        }

        if (this.wrapper.isActive()) {
            this.wrapper.update(dt);

            if (!this.wrapper.isActive()) {
                this.restartReloadTimer();
            }
        }
    }

    @Override
    public TimedReloadShield copy() {
        return new TimedReloadShield(wrapper.copy(), this.reloadTime);
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
        if (this.isReloading()) {
            return NullRenderer.INSTANCE;
        }
        return this.wrapper.getRenderer();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        // Have java serialize as much as possible
        s.defaultWriteObject();

        s.writeObject(this.wrapper);
        s.writeFloat(this.reloadTime);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        // Have java deserialize as much as possible
        s.defaultReadObject();

        this.wrapper = (ShieldEntity) s.readObject();
        this.reloadTime = s.readFloat();
    }
}
