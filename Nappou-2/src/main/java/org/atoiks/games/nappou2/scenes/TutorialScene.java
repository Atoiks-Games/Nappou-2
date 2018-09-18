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

package org.atoiks.games.nappou2.scenes;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.GameConfig;
import org.atoiks.games.nappou2.entities.Player;
import org.atoiks.games.nappou2.entities.enemy.*;
import org.atoiks.games.nappou2.entities.shield.*;

import static org.atoiks.games.nappou2.App.SANS_FONT;

public final class TutorialScene extends AbstractGameScene {

    private static final String[][] INFO_MSG = {
        { "Arrow Keys", "= Move" },
        { "Shift", "= Focus" },
        { "Z", "= Shoot" },
        { "X", "= Shield using Lumas" },
        { "Escape", "= Pause" },
        { "Enter", "= Select" }
    };

    private int waveCounter;
    private float time;
    private Clip bgm;
    private Image tutorialImg;
    private String[] talkMsg;
    private boolean renderControls;
    private boolean bossMode;

    public TutorialScene() {
        // -1 scene id means the score is not saved
        super(-1);
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        talkMsg = null;
        tutorialImg = (Image) scene.resources().get("z.png");
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
        if (tutorialImg != null) {
            g.drawImage(tutorialImg, (GAME_BORDER - tutorialImg.getWidth(null)) / 2, (HEIGHT - tutorialImg.getHeight(null)) / 2);
        }
        if (renderControls) {
            g.setColor(Color.black);
            g.fillRect(0, 0, GAME_BORDER, HEIGHT);

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
    public void renderStats(final IGraphics g) {
        super.renderStats(g);
        if (talkMsg != null) {
            drawDialog(g, "CAI:", talkMsg);
        }
    }

    @Override
    public void leave() {
        bgm.stop();
        super.leave();
    }

    @Override
    public boolean postUpdate(float dt) {
        if (renderControls && scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
            game.addEnemy(new DummyEnemy(1, -10, 50, 8, true));
            renderControls = false;
        }

/*
        if(bossMode){
          time ++;
            if(time%25000 == 0){
              game.addEnemy(new SingleShotEnemy(1, 250, -10, 8));
              game.addEnemy(new SingleShotEnemy(1, 500, -10, 8));
            }
        }
*/


        if (game.enemies.isEmpty()) {
            switch (waveCounter) {
                case 0:
                    if (!renderControls) {
                        waveCounter = 1;
                    }
                    break;
                case 1:
                    if (game.getScore() < 6) {
                        game.addEnemy(new SingleShotEnemy(1, 250, -10, 8));
                        game.addEnemy(new SingleShotEnemy(1, 500, -10, 8));
                    } else {
                        waveCounter = 2;
                        tutorialImg = (Image) scene.resources().get("x.png");
                        game.addEnemy(new ShieldTesterEnemy(200, 0, -10, 8));
                        game.addEnemy(new ShieldTesterEnemy(200, GAME_BORDER, -10, 8));
                    }
                    break;
                case 2:
                    game.clearBullets();
                    tutorialImg = null;
                    disableDamage();
                    bgm.stop();
                    talkMsg = new String[] {
                        "Oh hello there, Didn't expect you to wake up so soon. Why don't I put you back to sleep?"
                    };
                    disableInput = true;
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        waveCounter = 3;
                    }
                    break;
                case 3:
                    bgm = (Clip) scene.resources().get("Unlocked.wav");
                    if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
                        bgm.setMicrosecondPosition(0);
                        bgm.start();
                        bgm.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                    talkMsg = null;
                    enableDamage();
                    disableInput = false;
                    //bossMode = true;
                    game.addEnemy(new CAITutorial(100, 375, -10, 20));
                    waveCounter = 4;
                    break;

                case 4:
                    disableDamage();
                    game.clearBullets();
                    bgm.stop();
                    talkMsg = new String[] {
                        "I guess it won't be that easy. If you really are determined to escape the void, We will meet again soon.",
                        "See ya pal!"
                    };
                    disableInput = true;
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        scene.switchToScene(0);
                    }
                    break;

            }
        }
        return true;
    }
}
