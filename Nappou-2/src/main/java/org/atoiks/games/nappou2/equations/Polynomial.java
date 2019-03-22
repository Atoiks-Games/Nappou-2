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

package org.atoiks.games.nappou2.equations;

import se.tube42.lib.tweeny.TweenEquation;

public final class Polynomial {

    public final TweenEquation easeIn;
    public final TweenEquation easeOut;
    public final TweenEquation easeInOut;
    public final TweenEquation easeOutIn;

    public Polynomial(int order) {
        if (order < 0) throw new IllegalArgumentException("Order of polynomial cannot be negative");
        this.easeIn = new TweenEquation() {
            @Override
            public float compute(float t) {
                return (float) Math.pow(t, order);
            }

            @Override
            public String toString() {
                return order + "-polynomial-in";
            }
        };
        this.easeOut = new TweenEquation() {
            @Override
            public float compute(float t) {
                return 1 - (float) Math.pow(1 - t, order);
            }

            @Override
            public String toString() {
                return order + "-polynomial-out";
            }
        };
        this.easeInOut = new TweenEquation() {
            @Override
            public float compute(float t) {
                if (t > 0.5) {
                    return 1 - 0.5f * (float) Math.pow(2 * (1 - t), order);
                }
                return 0.5f * (float) Math.pow(2 * t, order);
            }

            @Override
            public String toString() {
                return order + "-polynomial-inout";
            }
        };
        this.easeOutIn = new TweenEquation() {
            @Override
            public float compute(float t) {
                final float rem = 0.5f * (float) Math.pow(2 * (0.5 - t), order);
                if (order % 2 == 1 || t <= 0.5) {
                    return 1 - rem;
                }
                return 1 + rem;
            }

            @Override
            public String toString() {
                return order + "-polynomial-outin";
            }
        };
    }
}
