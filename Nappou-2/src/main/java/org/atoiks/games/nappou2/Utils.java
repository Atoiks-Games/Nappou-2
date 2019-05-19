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

package org.atoiks.games.nappou2;

import java.util.List;
import java.util.Collections;

import org.atoiks.games.nappou2.equations.*;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.attack.*;
import org.atoiks.games.nappou2.entities.bullet.*;
import org.atoiks.games.nappou2.entities.pathway.*;

import org.atoiks.games.nappou2.entities.bullet.factory.*;

public final class Utils {

    private Utils() {
    }

    public static void tweenRadialGroupPattern(final Game game, final float[] xrangeInv, final float[] radOffset) {
        final PointBulletInfo info = new PointBulletInfo(15, 300);
        for (int idx = 0; idx < radOffset.length; ++idx) {
            final int i = idx;  // Lambda captures must be effectively final
            game.addEnemySpawner(EnemySpawner.createImmediateGroup(0.17f, 5, () -> {
                final PathwayEnemy enemy = new PathwayEnemy(2, 2);
                enemy.setUpdateListener(new RadialPattern(0.5f, true, 0, radOffset[i], 3, (float) (2 * Math.PI / 3), info));

                final List<LerpPathway.LerpEquation> fx = Collections.singletonList(
                    new LerpPathway.LerpEquation(xrangeInv[i], xrangeInv[i ^ 1], 2.8f * 2.1f, EaseInOutQuad.INSTANCE));
                final List<LerpPathway.LerpEquation> fy = Collections.singletonList(
                    new LerpPathway.LerpEquation(10, 640, 1.4f * 2.1f, Linear.INSTANCE));

                enemy.setPathway(new LerpPathway(fx.iterator(), fy.iterator()));
                enemy.setR(8);
                return enemy;
            }));
        }
    }

    public static PathwayEnemy circularPathEnemy(int hp, float x, float y, float r, float radius, int direction, float speedMod, int startPos, float bulletSpeed) {
        final PathwayEnemy enemy = new PathwayEnemy(hp, 1);
        enemy.setR(r);
        enemy.setPathway(new OrbitalPathway(radius, x, y, direction, speedMod, startPos));
        enemy.setUpdateListener(new SineFireGate(bulletSpeed, 0, 0.01, new SingleShot()));
        return enemy;
    }

    public static PathwayEnemy singleShotEnemy(int hp, float x, float y, float r, boolean inverted) {
        final PathwayEnemy enemy = new PathwayEnemy(hp, 1);
        enemy.setR(r);
        enemy.setPathway(new FixedVelocity(x, y, 0, 300 * (inverted ? -1 : 1)));
        enemy.setUpdateListener(new SineFireGate(6, 0, 0.5, new SingleShot()));
        return enemy;
    }

    public static PathwayEnemy starShotEnemy(int hp, float x, float y, float r, boolean inverted) {
        final PathwayEnemy enemy = new PathwayEnemy(hp, 1);
        enemy.setR(r);
        enemy.setPathway(new FixedVelocity(x, y, 0, 300 * (inverted ? -1 : 1)));
        enemy.setUpdateListener(new SineFireGate(6, 0, 0.5, new StarPattern()));
        return enemy;
    }

    public static PathwayEnemy miniBomberEnemy(int hp, float x, float y, float r, int direction, float speed) {
        final PathwayEnemy enemy = new PathwayEnemy(hp, 1);
        enemy.setR(r);
        enemy.setPathway(new FixedVelocity(x, y, direction * 300, 0));
        enemy.setUpdateListener(new SineFireGate(speed, 0, 0.5, new MiniBomber()));
        return enemy;
    }

    public static PathwayEnemy advancedMiniBomberEnemy(int hp, float x, float y, float r, int direction, float speed) {
        final PathwayEnemy enemy = new PathwayEnemy(hp, 1);
        enemy.setR(r);
        enemy.setPathway(new FixedVelocity(x, y, direction * 300, 0));
        enemy.setUpdateListener(new SineFireGate(speed, 0, 0.5, new AdvancedMiniBomber()));
        return enemy;
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

    public static boolean isPtOutOfScreen(final float x, final float y, final int w, final int h) {
        return !(x > 0 && y > 0 && x < w && y < h);
    }

    public static float lerp(final float start, final float end, final float frac) {
        return (end - start) * frac + start;
    }

    /**
     * Clamps a value within [0, 1]
     *
     * @param val the value being clamped
     *
     * @return the clamped value
     */
    public static float clamp01(final float val) {
        return val < 0 ? 0 : (val > 1 ? 1 : val);
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
