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

public final class TrigConstants {

    // A mini trig-identity cheatsheet:
    //   sin(a + b) = sin a cos b + cos a sin b
    //   cos(a + b) = cos a cos b − sin a sin b

    // pi / k constants
    public static final float PI_DIV_1 = (float) Math.PI;
    public static final float PI_DIV_2 = (float) Math.PI / 2;
    public static final float PI_DIV_3 = (float) Math.PI / 3;
    public static final float PI_DIV_4 = (float) Math.PI / 4;
    public static final float PI_DIV_6 = (float) Math.PI / 6;

    // cos(k pi) constants
    public static final float COS_4_PI_DIV_3 = -0.5f;
    public static final float COS_3_PI_DIV_2 = 0;
    public static final float COS_5_PI_DIV_3 = 0.5f;

    // sin(k pi) constants
    public static final float SIN_4_PI_DIV_3 = -0.8660254038f;
    public static final float SIN_3_PI_DIV_2 = -1;
    public static final float SIN_5_PI_DIV_3 = -0.8660254038f;

    public static final float SIN_PI_DIV_2 = 1;
    public static final float SIN_PI_DIV_3 = 0.8660254038f; // sqrt(3) / 2
    public static final float SIN_PI_DIV_6 = 0.5f;

    private TrigConstants() {
    }
}
