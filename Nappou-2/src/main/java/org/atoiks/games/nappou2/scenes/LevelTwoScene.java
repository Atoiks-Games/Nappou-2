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

package org.atoiks.games.nappou2.scenes;

import java.awt.event.KeyEvent;

import java.util.Random;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.bullet.*;

import org.atoiks.games.nappou2.GameConfig;

import static org.atoiks.games.nappou2.Utils.leapEnemy;
import static org.atoiks.games.nappou2.Utils.starShotEnemy;
import static org.atoiks.games.nappou2.Utils.singleShotEnemy;

public final class LevelTwoScene extends AbstractGameScene {

    private static final Message[] PREBOSS_MSG = {
        new Message("Cryo", "*Player*?! What are you doing here?!?!"),
        new Message("Pyro", "Clearly the humans betrayed her like she betrayed us. Do you even remember us, traitor?"),
        new Message("CAI", "Nope. Not at all."),
        new Message("Cryo", "WE HAVE BEEN TRAPPED HERE FOR YEARS BECAUSE OF YOU TWO!!!!"),
        new Message("CAI", "Oh right, you are the one that yells."),
        new Message("Player", "Yeah, I remember. Once I fight the humans, everything will be back to normal."),
        new Message("Cryo", "YOU POWER HUNGRY IDIOT!!!!"),
        new Message("Pyro", "I concur."),
        new Message("Player", "Alright, I'm sorry! Is that what you wanted?"),
        new Message("Pyro", "Apology..."),
        new Message("Cryo", "DENIED!!!"),
    };

    private static final Message[] POSTBOSS_MSG = {
        new Message("Cryo", "IS THAT ALL YOU DO?!?! SHOOT AT ANYTHING THAT MOVES?!?!"),
        new Message("CAI", "Pretty much!"),
        new Message("Pyro", "Come brother, they are not worth our time."),
    };

    private int cycles;
    private int wave;
    private Clip bgm;
    private int phase;

    private int msgPhase;

    // loop frame for level
    private static final int LEVEL_LOOP = 1229110;

    private final Random rnd = new Random();

    public LevelTwoScene() {
        super(1);
    }

    @Override
    public void enter(final String prevSceneId) {
        super.enter(prevSceneId);

        drift.clampSpeed(0,0,0,0);

        clearMessage();
        cycles = 0;
        wave = 0;
        phase = 0;

        msgPhase = -1;

        final GameConfig cfg = (GameConfig) scene.resources().get("game.cfg");

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, (IShield) scene.resources().get("shield"));
        game.player.setHp(cfg.challengeMode ? 1 : 5);
        game.setScore(0);

        bgm = (Clip) scene.resources().get("Level_One.wav");
        if (cfg.bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.setLoopPoints(LEVEL_LOOP, -1);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    private boolean displayNextDialogue(Message[] s) {
        if (++msgPhase < s.length) {
            displayMessage(s[msgPhase]);
            return true;
        }
        clearMessage();
        return false;
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
                            game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
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
                            game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
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
                            game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new ChargerEnemy(1, 0, 0, 5, 5, 500));
                            game.addEnemy(new ChargerEnemy(1, 750, 0, 5, 5, 500));
                            break;
                        case 120:
                            game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new ChargerEnemy(1, 0, 600, 10, 6, 500));
                            game.addEnemy(new ChargerEnemy(1, 750, 600, 10, 6, 500));
                            break;
                        case 220:
                            game.addEnemy(singleShotEnemy(1, 375, -10, 8, false));
                            game.addEnemy(new ChargerEnemy(1, 0, 300, 15, 7, 500));
                            game.addEnemy(new ChargerEnemy(1, 750, 300, 15, 7, 500));
                            break;
                        case 310:
                            game.addEnemy(singleShotEnemy(5, 375, -10, 15, false));
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
                            game.addEnemy(starShotEnemy(1, 600, 0, 5, false));
                            game.addEnemy(starShotEnemy(1, 450, 0, 5, false));
                            game.addEnemy(starShotEnemy(1, 600, -10, 5, false));
                            game.addEnemy(starShotEnemy(1, 450, -10, 5, false));
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
                        game.addEnemy(leapEnemy(1, 375, 600, 8, 75, 1, 0.25f, 2, 1, 5));
                        break;
                    case 510:
                        game.addEnemy(leapEnemy(1, 300, 600, 9, 75, -1, 0.25f, 4, 1, 4));
                        game.addEnemy(leapEnemy(1, 450, 600, 9, 75, 1, 0.25f, 2, 1, 6.25f));
                        game.addEnemy(starShotEnemy(1, 300, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 450, 0, 5, false));
                        break;
                    case 1010:
                        game.addEnemy(leapEnemy(1, 200, 600, 10, 75, -1, 0.25f, 4, 2, 3));
                        game.addEnemy(leapEnemy(1, 350, 600, 10, 75, 1, 0.25f, 2, 0.5f, 7));
                        game.addEnemy(leapEnemy(1, 400, 600, 10, 75, -1, 0.25f, 4, 3, 2));
                        game.addEnemy(leapEnemy(1, 550, 600, 10, 75, 1, 0.25f, 2, 1, 6.25f));
                        game.addEnemy(starShotEnemy(1, 350, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 400, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 550, 0, 5, false));
                        game.addEnemy(starShotEnemy(1, 200, 0, 5, false));
                        break;
                    case 1510:
                        game.addEnemy(leapEnemy(1, 150, 600, 8, 75, -1, 0.25f, 4, 2, 0.75f));
                        game.addEnemy(leapEnemy(1, 276, 600, 9, 75, -1, 0.25f, 4, 0.5f, 4.5f));
                        game.addEnemy(leapEnemy(1, 367, 600, 10, 75, -1, 0.25f, 4, 3, 2));
                        game.addEnemy(leapEnemy(1, 675, 600, 8, 75, 1, 0.25f, 2, 0.345f, 1.3f));
                        game.addEnemy(leapEnemy(1, 293, 600, 9, 75, 1, 0.25f, 2, 0.23f, 0.5f));
                        game.addEnemy(leapEnemy(1, 700, 600, 10, 75, 1, 0.25f, 2, 5, 7));
                        game.addEnemy(leapEnemy(1, 100, 600, 12, 75, -1, 0.25f, 4, 3, 10));
                        game.addEnemy(leapEnemy(1, 545, 600, 12, 75, 1, 0.25f, 2, 1, 6.25f));
                        break;
                    }
                    if (cycles > 1510) {
                        if (game.noMoreEnemies()) {
                            wave++;
                            cycles = 0;
                        }
                    }
                    break;
                case 4:
                    wave++;
                    cycles = 0;
                    bgm.stop();
                    disableDamage();
                    disableInput();
                    game.clearBullets();

                    displayNextDialogue(PREBOSS_MSG);
                    break;
                case 5:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextDialogue(PREBOSS_MSG)) {
                            wave++;
                            enableDamage();
                            enableInput();
                            cycles = 0;
                            bgm = (Clip) scene.resources().get("Broken_Soul.wav");
                            if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
                                bgm.setMicrosecondPosition(0);
                                bgm.start();
                                bgm.loop(Clip.LOOP_CONTINUOUSLY);
                            }
                            game.addEnemy(new Level2Easy1(12, 500, -10, 20));
                            game.addEnemy(new Level2Easy2(12, 250, -10, 20));
                        }
                    }
                    break;
                case 6:
                    if (cycles > 40 && game.noMoreEnemies()) {
                        bgm.stop();
                        disableDamage();
                        msgPhase = -1;
                        displayNextDialogue(POSTBOSS_MSG);
                        disableInput();
                        game.clearBullets();
                        wave++;
                    }
                    break;
                case 7:
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        if (!displayNextDialogue(POSTBOSS_MSG)) {
                            // Ask for name, and have PromptNameScene switch scene for us to $prompt.trans
                            // scene.resources().put("prompt.trans", /* whatever the next scene id should be... */);
                            scene.resources().put("prompt.trans", "TitleScene");
                            return scene.switchToScene("SaveHighscoreScene");
                        }
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
