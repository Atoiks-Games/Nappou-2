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
    private boolean renderControls;
    private boolean bossMode;

    // Modify these two with resetDialogue or updateDialogue
    private String speaker;
    private String[] talkMsg;

    public TutorialScene() {
        // -1 scene id means the score is not saved
        super(-1);
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        resetDialogue();

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

    private void resetDialogue() {
        speaker = null;
        talkMsg = null;
    }

    private void updateDialogue(final String speaker, final String... msg) {
        this.speaker = speaker + ":";
        this.talkMsg = msg;
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
        if (speaker != null) {
            drawDialog(g, speaker, talkMsg);
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
                        ++waveCounter;
                    }
                    break;
                case 1:
                    if (game.getScore() < 6) {
                        game.addEnemy(new SingleShotEnemy(1, 250, -10, 8));
                        game.addEnemy(new SingleShotEnemy(1, 500, -10, 8));
                    } else {
                        ++waveCounter;
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
                    disableInput = true;

                    updateDialogue("CAI", "Good morning! You're dead!");
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        ++waveCounter;
                    }
                    break;
                case 3:
                    updateDialogue("Player", "What?");
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        ++waveCounter;
                    }
                    break;
                case 4:
                    updateDialogue("CAI", "Just kidding, you're just in the void. Which is arguably worse.");
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        ++waveCounter;
                    }
                    break;
                case 5:
                    updateDialogue("Player", "What?!");
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        ++waveCounter;
                    }
                    break;
                case 6:
                    updateDialogue("CAI", "Yep, the humans threw us in just like that.");
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        ++waveCounter;
                    }
                    break;
                case 7:
                    updateDialogue("Player", "Cai, you don't understand, we have to get out of here.");
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        ++waveCounter;
                    }
                    break;
                case 8:
                    updateDialogue("CAI", "Not before you finish your daily combat exercises!");
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        ++waveCounter;
                    }
                    break;
                case 9:
                    updateDialogue("Player", "Cai, now's not that time.");
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        ++waveCounter;
                    }
                    break;
                case 10:
                    updateDialogue("CAI", "There's always time for senseless violence!");
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
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
                    resetDialogue();
                    enableDamage();
                    disableInput = false;
                    //bossMode = true;
                    game.addEnemy(new CAITutorial(100, 375, -10, 20));
                    ++waveCounter;
                    break;
                case 12:
                    disableDamage();
                    game.clearBullets();
                    bgm.stop();
                    updateDialogue("CAI",
                            "I guess it won't be that easy. If you really are determined to escape the void, We will meet again soon.",
                            "See ya pal!");
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
