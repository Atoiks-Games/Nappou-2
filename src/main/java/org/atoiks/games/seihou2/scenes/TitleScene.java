package org.atoiks.games.seihou2.scenes;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework.Scene;
import org.atoiks.games.seihou2.GameConfig;

public final class TitleScene extends Scene {

    // Conventionally, last scene is always Quit,
    // sceneDest is always one less than the selectorY
    private static final int[] selectorY = {235, 276, 318, 357, 469};
    private static final int[] sceneDest = {2, 5, 3, 4};

    private Image titleImg;
    private Clip bgm;
    private int selector;

	@Override
	public void render(Graphics g) {
        g.drawImage(titleImg, 0, 0, null);
        g.setColor(Color.white);
        g.fillRect(61, selectorY[selector], 4, 30);
	}

	@Override
	public boolean update(float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
            if (selector < sceneDest.length) {
                scene.switchToScene(sceneDest[selector]);
                return true;
            }

            // Quit was chosen
            return false;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
            if (++selector >= selectorY.length) selector = 0;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (--selector < 0) selector = selectorY.length - 1;
        }
		return true;
	}

	@Override
	public void resize(int x, int y) {
		// Screen size is fixed
    }
    
    @Override
    public void enter(final int prevSceneId) {
        titleImg = (Image) scene.resources().get("title.png");
        bgm = (Clip) scene.resources().get("title.wav");

        if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
            // ScoreScene and ConfigScene continues to play music
            switch (prevSceneId) {
                case 3:
                case 4:
                    break;
                default:
                    bgm.setMicrosecondPosition(0);
                    break;
            }
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void leave() {
        bgm.stop();
    }
}