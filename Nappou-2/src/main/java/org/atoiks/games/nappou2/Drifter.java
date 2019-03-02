/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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

public class Drifter {

    private float lowerX = Float.NEGATIVE_INFINITY;
    private float upperX = Float.POSITIVE_INFINITY;

    private float lowerY = Float.NEGATIVE_INFINITY;
    private float upperY = Float.POSITIVE_INFINITY;

    private float dx;
    private float dy;

    /**
     * The acceleration factor for the drifting speed on x axis
     */
    public float accelX;

    /**
     * The acceleration factor for the drifting speed on y axis
     */
    public float accelY;

    /**
     * Set an initial drifting speed.
     *
     * @param dx amount of drifting in x
     * @param dy amount of drifting in y
     */
    public void setDriftSpeed(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Resets the speed clamping for both x and y components. Same thing as
     * setting the clamping factor to negative and positive infinity.
     */
    public void resetSpeedClamp() {
        resetDxClamp();
        resetDyClamp();
    }

    /**
     * Resets the clamping for the x component.
     */
    public void resetDxClamp() {
        lowerX = Float.NEGATIVE_INFINITY;
        upperX = Float.POSITIVE_INFINITY;
    }

    /**
     * Resets the clamping for the y component.
     */
    public void resetDyClamp() {
        lowerY = Float.NEGATIVE_INFINITY;
        upperY = Float.POSITIVE_INFINITY;
    }

    /**
     * Sets the clamping factor for both x and y components. Shorthand for
     * calling {@link this#clampDx(float, float)} and
     * {@link this#clampDy(float, float)}.
     *
     * @param lowerX lower limit of dx
     * @param upperX upper limit of dx
     * @param lowerY lower limit of dy
     * @param upperY upper limit of dy
     */
    public void clampSpeed(float lowerX, float upperX, float lowerY, float upperY) {
        clampDx(lowerX, upperX);
        clampDy(lowerY, upperY);
    }

    /**
     * Sets the clamping factor for the x component. Does not update the factor
     * if the lower limit is greater than the upper limit.
     *
     * @param lower lower limit of dx
     * @param upper upper limit of dx
     */
    public void clampDx(final float lower, final float upper) {
        if (lower <= upper) {
            this.lowerX = lower;
            this.upperX = upper;
        }
    }

    /**
     * Sets the clamping factor for the y component. Does not update the factor
     * if the lower limit is greater than the upper limit.
     *
     * @param lower lower limit of dy
     * @param upper upper limit of dy
     */
    public void clampDy(final float lower, final float upper) {
        if (lower <= upper) {
            this.lowerY = lower;
            this.upperY = upper;
        }
    }

    /**
     * Updates dx and dy based on dv then clamps them
     *
     * @param dt time elapsed. used as time multipler for dv
     */
    public void update(final float dt) {
        dx = clamp(dx + accelX * dt, lowerX, upperX);
        dy = clamp(dy + accelY * dt, lowerY, upperY);
    }

    /**
     * @return dx
     */
    public float getDx() {
        return dx;
    }


    /**
     * @return dy
     */
    public float getDy() {
        return dy;
    }

    /**
     * Clamps a value within the value range.
     *
     * @param val the value being clamped
     * @param low the lower limit
     * @param high the upper limit
     *
     * @return the clamped value
     */
    public static float clamp(final float val, final float low, final float high) {
        return val < low ? low : (val > high ? high : val);
    }
}
