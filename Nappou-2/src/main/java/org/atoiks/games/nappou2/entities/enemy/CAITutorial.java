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

package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.bullet.Beam;
import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class CAITutorial extends AbstractEnemy {

    private static final long serialVersionUID = 5619264522L;

    private int bulletPattern;

    public CAITutorial(int hp, float x, float y, float r) {
        super(hp, x, y, r);
    }

    @Override
    public void update(float dt) {
        bulletPattern ++;

        if (getY() <= 150) {
            setY(getY() + 300 * dt);
        }

        final float x = getX();
        final float y = getY();

        if (bulletPattern % 200 == 0) {
            final double angle = Math.atan2(game.player.getY() - y, game.player.getX() - x);
            if (Math.random() >= 0.5) {
                game.addEnemyBullet(new PointBullet(x, y, 10, 1000 * (float) Math.cos(angle), 1000 * (float) Math.sin(angle)));
                game.addEnemyBullet(new Beam(x, y, 2, 10, (float) (angle + Math.PI / 4), 500));
                game.addEnemyBullet(new Beam(x, y, 2, 10, (float) (angle - Math.PI / 4), 500));
                game.addEnemyBullet(new Beam(x, y, 2, 10, (float) (angle - Math.PI / 6), 750));
                game.addEnemyBullet(new Beam(x, y, 2, 10, (float) (angle + Math.PI / 6), 750));
                game.addEnemyBullet(new Beam(x, y, 2, 10, (float) (angle - Math.PI / 12), 1000));
                game.addEnemyBullet(new Beam(x, y, 2, 10, (float) (angle + Math.PI / 12), 1000));
            } else {
                game.addEnemyBullet(new Beam(x, y, 10, 100, (float) (angle), 600));
                game.addEnemyBullet(new Beam(x, y, 10, 1000, (float) (angle + Math.PI / 4), 500));
                game.addEnemyBullet(new Beam(x, y, 10, 1000, (float) (angle - Math.PI / 4), 500));
                game.addEnemyBullet(new Beam(x, y, 5, 100, (float) (angle + Math.PI / 6), 750));
                game.addEnemyBullet(new Beam(x, y, 5, 100, (float) (angle - Math.PI / 6), 750));
            }
        }
    }

    @Override
    public int getScore() {
        return 1;
    }
}
