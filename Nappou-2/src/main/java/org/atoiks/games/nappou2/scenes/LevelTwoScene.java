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

package org.atoiks.games.nappou2.scenes;

import se.tube42.lib.tweeny.Item;
import se.tube42.lib.tweeny.TweenEquation;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.bullet.*;

import org.atoiks.games.nappou2.GameConfig;

import java.util.Random;

public final class LevelTwoScene extends AbstractGameScene {

    private int cycles;
    private int wave;

    private final Random rnd = new Random();

    public LevelTwoScene() {
        super(1);
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        cycles = 0;
        wave = 0;

        final GameConfig cfg = (GameConfig) scene.resources().get("game.cfg");

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, (IShield) scene.resources().get("shield"));
        game.player.setHp(cfg.challengeMode ? 1 : 5);
        game.setScore(0);
    }

    @Override
    public boolean postUpdate(float dt) {
        ++cycles;
        switch (difficulty) {
            case EASY:
            switch (wave) {
                case 0:
                    switch (cycles) {
                        case 2000:
                            game.addEnemy(new SingleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new DummyEnemy(2, -10, 20, 20, 500, 1, true));
                            game.addEnemy(new DummyEnemy(2, 760, 20, 20, 500, 1, false));
                            game.addEnemy(new DummyEnemy(2, -10, 120, 20, 500, 1, true));
                            game.addEnemy(new DummyEnemy(2, 760, 120, 20, 500, 1, false));
                            game.addEnemy(new DummyEnemy(2, -10, 220, 20, 500, 1, true));
                            game.addEnemy(new DummyEnemy(2, 760, 220, 20, 500, 1, false));
                            game.addEnemy(new DummyEnemy(2, -10, 320, 20, 500, 1, true));
                            game.addEnemy(new DummyEnemy(2, 760, 320, 20, 500, 1, false));
                            break;
                        case 12000:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DummyEnemy(2, -10, 280, 20, 500, 1, true));
                            game.addEnemy(new DummyEnemy(2, 760, 280, 20, 500, 1, false));
                            game.addEnemy(new DummyEnemy(2, -10, 380, 20, 500, 1, true));
                            game.addEnemy(new DummyEnemy(2, 760, 380, 20, 500, 1, false));
                            game.addEnemy(new DummyEnemy(2, -10, 480, 20, 500, 1, true));
                            game.addEnemy(new DummyEnemy(2, 760, 480, 20, 500, 1, false));
                            game.addEnemy(new DummyEnemy(2, -10, 580, 20, 500, 1, true));
                            game.addEnemy(new DummyEnemy(2, 760, 580, 20, 500, 1, false));
                            break;
                        case 22000:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DummyEnemy(1, -10, 30, 10, 500, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 30, 10, 500, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 70, 10, 500, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 70, 10, 500, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 110, 10, 500, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 110, 10, 500, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 150, 10, 500, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 150, 10, 500, 1, false));
                            break;
                        case 32000:
                            game.addEnemy(new SingleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new DummyEnemy(1, -10, 590, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 590, 10, 1000, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 550, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 550, 10, 1000, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 510, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 510, 10, 1000, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 470, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 470, 10, 1000, 1, false));
                            break;
                        case 60000:
                            game.addEnemy(new DropEnemy(1, 30, -10, 8, false));
                            game.addEnemy(new DropEnemy(1, 720, -10, 8, false));
                            game.addEnemy(new DummyEnemy(1, -10, 200, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 200, 10, 1000, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 240, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 240, 10, 1000, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 280, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 280, 10, 1000, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 320, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 320, 10, 1000, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 360, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 360, 10, 1000, 1, false));
                            game.addEnemy(new DummyEnemy(1, -10, 400, 10, 1000, 1, true));
                            game.addEnemy(new DummyEnemy(1, 760, 400, 10, 1000, 1, false));
                            break;
                    }
                    if (cycles > 60000) {
                        if (game.enemies.isEmpty()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 2000:
                            game.addEnemy(new SingleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new ChargerEnemy(1, 0, 0, 5, 5, 500));
                            game.addEnemy(new ChargerEnemy(1, 750, 0, 5, 5, 500));
                            break;
                        case 12000:
                            game.addEnemy(new SingleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new ChargerEnemy(1, 0, 600, 10, 6, 500));
                            game.addEnemy(new ChargerEnemy(1, 750, 600, 10, 6, 500));
                            break;
                        case 22000:
                            game.addEnemy(new SingleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new ChargerEnemy(1, 0, 300, 15, 7, 500));
                            game.addEnemy(new ChargerEnemy(1, 750, 300, 15, 7, 500));
                            break;
                        case 32000:
                            game.addEnemy(new SingleShotEnemy(5, 375, -10, 15, false));
                            break;
                        }
                    if (cycles > 32000) {
                        if (game.enemies.isEmpty()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 2:
                    switch (cycles) {
                        case 1000:
                            game.addEnemy(new DropEnemy(1, 750, -10, 8, false));
                            game.addEnemy(new ShiftEnemy(5, 0, 10, 10, 0.325f, false));
                            break;
                        case 10000:
                            game.addEnemy(new ShiftEnemy(5, 0, 10, 10, 0.325f, true));
                            break;
                        case 51000:
                            game.addEnemy(new DropEnemy(1, 0, 610, 8, true));
                            game.addEnemy(new ShiftEnemy(5, 0, 10, 10, 0.325f, false));
                            break;
                        case 60000:
                            game.addEnemy(new ShiftEnemy(5, 0, 10, 10, 0.325f, true));
                            break;
                        }
                    break;
            }
            break;
        }
        return true;
    }

    @Override
    public void leave() {
        super.leave();
    }
}
