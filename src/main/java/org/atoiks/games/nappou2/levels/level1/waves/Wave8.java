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

package org.atoiks.games.nappou2.levels.level1.waves;

import javax.sound.sampled.Clip;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.levels.LevelContext;
import org.atoiks.games.nappou2.levels.AbstractGameWave;

import org.atoiks.games.nappou2.levels.level1.Stage;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.PathwayEnemy;

import org.atoiks.games.nappou2.entities.bullet.StutterBullet;

import org.atoiks.games.nappou2.graphics.shapes.ImmutableEllipses;

import org.atoiks.games.nappou2.pathway.CollapsingOrbitalPathway;

import org.atoiks.games.nappou2.pattern.NullPattern;

import static org.atoiks.games.nappou2.levels.level1.Data.LEVEL_LOOP;

public class Wave8 extends AbstractGameWave {

    private static final long serialVersionUID = -5144665572565203146L;

    private static final ImmutableEllipses BOUNDARY_1 = new ImmutableEllipses(new Vector2(375, 300), new Vector2(385, 385));

    public Wave8() {
        super("/music/Level_One.wav");
    }

    @Override
    protected void configureBgm(Clip bgm) {
        super.configureBgm(bgm);
        bgm.setLoopPoints(LEVEL_LOOP, -1);
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        final Game game = ctx.getGame();

        ++cycles;
        switch (cycles) {
            case 40:
                for (int i = 0; i < 24; ++i) {
                    game.addEnemyBullet(new StutterBullet(0, 0, 8, 250, i * (float) Math.PI / 12, 500, 10));
                    game.addEnemyBullet(new StutterBullet(750, 0, 8, 250, i * (float) Math.PI / 12, 500, 10));
                }
                break;
            case 50:
                PathwayEnemy e = new PathwayEnemy(3, 3, new CollapsingOrbitalPathway(BOUNDARY_1, 1, 1, 0), NullPattern.INSTANCE);
                e.setRadius(20);
                game.addEnemy(e);
                PathwayEnemy f = new PathwayEnemy(3, 3, new CollapsingOrbitalPathway(BOUNDARY_1, -1, 1, Math.PI), NullPattern.INSTANCE);
                f.setRadius(20);
                game.addEnemy(f);
                break;
        }
        if (cycles > 50 && game.noMoreEnemies()) {
            ctx.setState(new Wave9());
            return;
        }
    }

    @Override
    public Stage getAssociatedLevel() {
        return new Stage();
    }
}
