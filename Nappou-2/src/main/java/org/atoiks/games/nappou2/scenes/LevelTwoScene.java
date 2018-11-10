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
                        case 20:
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
                        case 120:
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
                        case 220:
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
                        case 320:
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
                        case 600:
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
                    if (cycles > 600) {
                        if (game.noMoreEnemies()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 1:
                    switch (cycles) {
                        case 20:
                            game.addEnemy(new SingleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new ChargerEnemy(1, 0, 0, 5, 5, 500));
                            game.addEnemy(new ChargerEnemy(1, 750, 0, 5, 5, 500));
                            break;
                        case 120:
                            game.addEnemy(new SingleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new ChargerEnemy(1, 0, 600, 10, 6, 500));
                            game.addEnemy(new ChargerEnemy(1, 750, 600, 10, 6, 500));
                            break;
                        case 220:
                            game.addEnemy(new SingleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new ChargerEnemy(1, 0, 300, 15, 7, 500));
                            game.addEnemy(new ChargerEnemy(1, 750, 300, 15, 7, 500));
                            break;
                        case 310:
                            game.addEnemy(new SingleShotEnemy(5, 375, -10, 15, false));
                            break;
                        }
                    if (cycles > 310) {
                        if (game.noMoreEnemies()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 2:
                    switch (cycles) {
                        case 10:
                            game.addEnemy(new DropEnemy(1, 750, -10, 8, false));
                            game.addEnemy(new ShiftEnemy(5, 0, 10, 10, 0.325f, false));
                            break;
                        case 100:
                            game.addEnemy(new ShiftEnemy(5, 0, 10, 10, 0.325f, true));
                            break;
                        case 200:
                        case 300:
                        case 400:
                        case 500:
                            game.addEnemy(new StarShotEnemy(1, 600, 0, 5, false));
                            game.addEnemy(new StarShotEnemy(1, 450, 0, 5, false));
                            game.addEnemy(new StarShotEnemy(1, 600, -10, 5, false));
                            game.addEnemy(new StarShotEnemy(1, 450, -10, 5, false));
                            break;
                        case 510:
                            game.addEnemy(new DropEnemy(1, 0, 610, 8, true));
                            game.addEnemy(new ShiftEnemy(5, 0, 10, 10, 0.325f, false));
                            break;
                        case 600:
                            game.addEnemy(new ShiftEnemy(5, 0, 10, 10, 0.325f, true));
                            break;
                        }
                    if (cycles > 600) {
                        if (game.noMoreEnemies()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 3:
                switch (cycles) {
                    case 10:
                        game.addEnemy(new LeapEnemy(1, 375, 600, 8, 75, 1, 0.25f, 2, 1, 5));
                        break;
                    case 510:
                        game.addEnemy(new LeapEnemy(1, 300, 600, 9, 75, -1, 0.25f, 4, 1, 4));
                        game.addEnemy(new LeapEnemy(1, 450, 600, 9, 75, 1, 0.25f, 2, 1, 6.25f));
                        game.addEnemy(new StarShotEnemy(1, 300, 0, 5, false));
                        game.addEnemy(new StarShotEnemy(1, 450, 0, 5, false));
                        break;
                    case 1010:
                        game.addEnemy(new LeapEnemy(1, 200, 600, 10, 75, -1, 0.25f, 4, 2, 3));
                        game.addEnemy(new LeapEnemy(1, 350, 600, 10, 75, 1, 0.25f, 2, 0.5f, 7));
                        game.addEnemy(new LeapEnemy(1, 400, 600, 10, 75, -1, 0.25f, 4, 3, 2));
                        game.addEnemy(new LeapEnemy(1, 550, 600, 10, 75, 1, 0.25f, 2, 1, 6.25f));
                        game.addEnemy(new StarShotEnemy(1, 350, 0, 5, false));
                        game.addEnemy(new StarShotEnemy(1, 400, 0, 5, false));
                        game.addEnemy(new StarShotEnemy(1, 550, 0, 5, false));
                        game.addEnemy(new StarShotEnemy(1, 200, 0, 5, false));
                        break;
                    case 1510:
                        game.addEnemy(new LeapEnemy(1, 150, 600, 8, 75, -1, 0.25f, 4, 2, 0.75f));
                        game.addEnemy(new LeapEnemy(1, 276, 600, 9, 75, -1, 0.25f, 4, 0.5f, 4.5f));
                        game.addEnemy(new LeapEnemy(1, 367, 600, 10, 75, -1, 0.25f, 4, 3, 2));
                        game.addEnemy(new LeapEnemy(1, 675, 600, 8, 75, 1, 0.25f, 2, 0.345f, 1.3f));
                        game.addEnemy(new LeapEnemy(1, 293, 600, 9, 75, 1, 0.25f, 2, 0.23f, 0.5f));
                        game.addEnemy(new LeapEnemy(1, 700, 600, 10, 75, 1, 0.25f, 2, 5, 7));
                        game.addEnemy(new LeapEnemy(1, 100, 600, 12, 75, -1, 0.25f, 4, 3, 10));
                        game.addEnemy(new LeapEnemy(1, 545, 600, 12, 75, 1, 0.25f, 2, 1, 6.25f));
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
