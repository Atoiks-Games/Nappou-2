package org.atoiks.games.seihou2.scenes;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.GameConfig;
import org.atoiks.games.seihou2.entities.Player;
import org.atoiks.games.seihou2.entities.enemy.*;
import org.atoiks.games.seihou2.entities.shield.*;

public final class TutorialScene extends AbstractGameScene {

    private int waveCounter;
    private float time;
    private Clip bgm;
    private Image tutorialImg;
    private Image controlsImg;
    private Image talkImg;
    private boolean controlsGone;
    private boolean bossMode;

    public TutorialScene() {
        // -1 scene id means the score is not saved
        super(-1);
    }

    @Override
    public void enter(final int prevSceneId) {
        super.enter(prevSceneId);

        talkImg = null;
        tutorialImg = (Image) scene.resources().get("z.png");
        controlsImg = (Image) scene.resources().get("controls.png");

        bgm = (Clip) scene.resources().get("Awakening.wav");

        if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }

        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5, new FixedTimeShield(3.5f, 2, 50));

        game.player.setHp(5);
        game.setScore(0);
        controlsGone = false;
        bossMode = false;
        waveCounter = 0;
    }

    @Override
    public void renderBackground(final IGraphics g) {
        super.renderBackground(g);
        if (tutorialImg != null) {
            g.drawImage(tutorialImg, (GAME_BORDER - tutorialImg.getWidth(null)) / 2, (HEIGHT - tutorialImg.getHeight(null)) / 2);
        }
        if (controlsImg != null) {
            g.drawImage(controlsImg, 0, 0);
        }
    }

    @Override
    public void renderStats(final IGraphics g) {
        super.renderStats(g);
        if (talkImg != null) {
            g.drawImage(talkImg, 0, HEIGHT - 200);
        }
    }

    @Override
    public void leave() {
        bgm.stop();
        super.leave();
    }

    @Override
    public boolean postUpdate(float dt) {
        if (!controlsGone && scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
            game.addEnemy(new DummyEnemy(1, -10, 50, 8, true));
            controlsGone = true;
            controlsImg = null;
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
                    if (controlsGone) {
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
                    talkImg = (Image) scene.resources().get("tutorial_preboss_1.png");
                    disableInput = true;
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        waveCounter = 3;
                    }
                    break;
                case 3:
                    bgm = (Clip) scene.resources().get("Broken_Soul.wav");
                    bgm.setMicrosecondPosition(0);
                    bgm.start();
                    bgm.loop(Clip.LOOP_CONTINUOUSLY);
                    talkImg = null;
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
                    talkImg = (Image) scene.resources().get("tutorial_postboss_1.png");
                    disableInput = true;
                    if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
                        scene.switchToScene(1);
                    }
                    break;

            }
        }
        return true;
    }
}
