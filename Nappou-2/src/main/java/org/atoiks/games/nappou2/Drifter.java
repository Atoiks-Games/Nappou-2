/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2;

import org.atoiks.games.framework2d.IUpdate;

public class Drifter implements IUpdate {

    private float lowerX = Float.NEGATIVE_INFINITY;
    private float upperX = Float.POSITIVE_INFINITY;

    private float lowerY = Float.NEGATIVE_INFINITY;
    private float upperY = Float.POSITIVE_INFINITY;

    private float dx;
    private float dy;

    public float dv;

    public void setDriftSpeed(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void resetSpeedClamp() {
        resetDxClamp();
        resetDyClamp();
    }

    public void resetDxClamp() {
        lowerX = Float.NEGATIVE_INFINITY;
        upperX = Float.POSITIVE_INFINITY;
    }

    public void resetDyClamp() {
        lowerY = Float.NEGATIVE_INFINITY;
        upperY = Float.POSITIVE_INFINITY;
    }

    public void clampSpeed(float lowerX, float upperX, float lowerY, float upperY) {
        clampDx(lowerX, upperX);
        clampDy(lowerY, upperY);
    }

    public void clampDx(final float lower, final float upper) {
        if (lower <= upper) {
            this.lowerX = lower;
            this.upperX = upper;
        }
    }

    public void clampDy(final float lower, final float upper) {
        if (lower <= upper) {
            this.lowerY = lower;
            this.upperY = upper;
        }
    }

    @Override
    public void update(final float dt) {
        final float v = dv * dt;
        dx = clamp(dx + v, lowerX, upperX);
        dy = clamp(dy + v, lowerY, upperY);
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public static float clamp(final float val, final float low, final float high) {
        return val < low ? low : (val > high ? high : val);
    }
}