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
                return new RadialPointEnemy(2, 2, tweenInfo, 0.50f, true, 0, radOffset[i], 3, (float) (2 * Math.PI / 3), 15f, 500f);
            }));
        }
    }
}
