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

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.GameConfig;
import org.atoiks.games.nappou2.entities.Player;
import org.atoiks.games.nappou2.entities.Message;
import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.shield.*;

import static org.atoiks.games.nappou2.App.SANS_FONT;
import static org.atoiks.games.nappou2.entities.Message.VerticalAlignment;
import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

public final class TutorialScene extends AbstractGameScene {

    private static final String[][] INFO_MSG = {
        { "Arrow Keys", "= Move" },
        { "Shift", "= Focus" },
        { "Z", "= Shoot" },
        { "X", "= Activate shield" },
        { "Escape", "= Pause" },
        { "Enter", "= Select" }
    };

    private static final Message MSG_BTN_Z = new Message(
            "z.png", HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    private static final Message MSG_BTN_X = new Message(
            "x.png", HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    private static final Message[] PREBOSS_MSG = {
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "Good morning! You're dead!"),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "What?"),
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "Just kidding! You're just in the void. Which is arguably worse."),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "What?!"),
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "Yep, the humans threw us in just like that."),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Cai, you don't understand, we have to get out of here."),
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "Not before you finish your daily combat exercises!"),
        new Message("LUMA.png", HorizontalAlignment.LEFT, "LUMA", "Cai, now's not that time."),
        new Message("CAI.png", HorizontalAlignment.RIGHT, "CAI", "There's always time for senseless violence!"),
    };

    private static final Message POSTBOSS_MSG = new Message(
            "CAI.png", HorizontalAlignment.RIGHT, "CAI", "Alright now we are ready for whomever we come across!");

    private int waveCounter;
    private float time;
    private Clip bgm;
    private boolean renderControls;
    private boolean bossMode;

    public TutorialScene() {
        // -1 scene id means the score is not saved
        super(-1);
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        displayMessage(null);
        renderControls = true;

        bgm = (Clip) scene.resources().get("Awakening.wav");

        if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, new FixedTimeShield(3.5f, 2, 50));

        game.player.setHp(5);
        game.setScore(0);
        bossMode = false;
        waveCounter = 0;
    }

    @Override
    public void renderBackground(final IGraphics g) {
        super.renderBackground(g);
        if (renderControls) {
            g.setColor(Color.white);
            g.setFont(TitleScene.OPTION_FONT);
            g.drawString("Controls", 25, 70);
            g.setFont(SANS_FONT);
            for (int i = 0; i < INFO_MSG.length; ++i) {
                final int h = 90 + i * (SANS_FONT.getSize() + 5);
                g.drawString(INFO_MSG[i][0], 40, h);
                g.drawString(INFO_MSG[i][1], 120, h);
            }

            g.drawString("Survive the void", 25, 550);
            g.drawString("Press Enter to continue", 25, 580);
        }
    }

    @Override
    public void leave() {
        bgm.stop();
        super.leave();
    }

    @Override
    public boolean postUpdate(float dt) {
        if (renderControls && Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            renderControls = false;
            displayMessage(MSG_BTN_Z);
        }

        if (game.noMoreEnemies()) {
            switch (waveCounter) {
                case 0:
                    if (!renderControls) {
                        ++waveCounter;
                    }
                    break;
                case 1:
                    if (game.getScore() < 6) {
                        game.addEnemy(new SingleShotEnemy(1, 250, -10, 8, false));
                        game.addEnemy(new SingleShotEnemy(1, 500, -10, 8, false));
                    } else {
                        ++waveCounter;
                        displayMessage(MSG_BTN_X);
                        game.addEnemy(new ShieldTesterEnemy(200, 0, -10, 8, false));
                        game.addEnemy(new ShieldTesterEnemy(200, GAME_BORDER, -10, 8, false));
                    }
                    break;

                case 2:
                    disableDamage();
                    disableInput();
                    game.clearBullets();
                    bgm.stop();
                    // fallthrough!!
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    displayMessage(PREBOSS_MSG[waveCounter - 2]);
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        ++waveCounter;
                    }
                    break;
                case 11:
                    bgm = (Clip) scene.resources().get("Unlocked.wav");
                    if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
                        bgm.setMicrosecondPosition(0);
                        bgm.start();
                        bgm.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                    enableDamage();
                    enableInput();
                    displayMessage(null);
                    //bossMode = true;
                    game.addEnemy(new CAITutorial(50, 375, -10, 20));
                    ++waveCounter;
                    break;
                case 12:
                    disableDamage();
                    disableInput();
                    game.clearBullets();
                    bgm.stop();

                    displayMessage(POSTBOSS_MSG);
                    if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
                        // Score in tutorial does not get saved
                        // Jump to title scene directly
                        return scene.switchToScene(1);
                    }
                    break;
            }
        }
        return true;
    }
}
