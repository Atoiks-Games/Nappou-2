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

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.bullet.*;

public final class Utils {

    private Utils() {
    }

    public static void tweenRadialGroupPattern(final Game game, final float[] xrangeInv, final float[] radOffset) {
        for (int idx = 0; idx < radOffset.length; ++idx) {
            final int i = idx;  // Lambda captures must be effectively final
            game.addEnemy(EnemyGroup.createLazyGroup(0.17f, 5, () -> {
                final Item tweenInfo = new Item(3);
                tweenInfo.set(0, xrangeInv[i], xrangeInv[i ^ 1]).configure(28000, TweenEquation.QUAD_INOUT);
                tweenInfo.set(1, 10, 600 + 40).configure(14000, TweenEquation.LINEAR);
                tweenInfo.setImmediate(2, 8);
                return new RadialPointEnemy(2, 2, tweenInfo, 0.5f, true, 0, radOffset[i], 3, (float) (2 * Math.PI / 3), 15f, 500f);
            }));
        }
    }

    public static boolean intersectSegmentCircle(float x1, float y1, float x2, float y2,
                                                 float cx, float cy, float cr) {
        // Taken from https://stackoverflow.com/a/10392860
        final float acx = cx - x1;
        final float acy = cy - y1;

        final float abx = x2 - x1;
        final float aby = y2 - y1;

        final float ab2 = abx * abx + aby * aby;
        final float acab = acx * abx + acy * aby;
        float t = acab / ab2;

        if (t < 0) {
            t = 0;
        } else if (t > 1) {
            t = 1;
        }

        final float hx = (abx * t) + x1 - cx;
        final float hy = (aby * t) + y1 - cy;

        return hx * hx + hy * hy <= cr * cr;
    }

    public static boolean centerSquareCollision(float x1, float y1, float h1,
                                                float x2, float y2, float h2) {
        // +------+
        // |      | x, y = the center
        // |      | h    = side length / 2 = apothem
        // +------+
        //
        // squares do not rotate just check distance between

        // return x1 - h1 < x2 + h2
        //     && x1 + h1 > x2 - h2
        //     && y1 - h1 < y2 + h2
        //     && y1 + h1 > y2 - h1;
        final float dist = h1 + h2;
        return Math.abs(x1 - x2) < dist
            && Math.abs(y1 - y2) < dist;
    }

    public static boolean fastCircleCollision(float x1, float y1, float r1,
                                              float x2, float y2, float r2) {
        // Only perform accurate collision if both circles collide as squares
        if (centerSquareCollision(x1, y1, r1, x2, y2, r2)) {
            // Accurate collision check
            final float dx = x1 - x2;
            final float dy = y1 - y2;
            final float dr = r1 + r2;
            return dx * dx + dy * dy < dr * dr;
        }
        return false;
    }
}
