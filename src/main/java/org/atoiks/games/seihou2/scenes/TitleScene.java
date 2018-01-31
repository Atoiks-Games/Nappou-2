package org.atoiks.games.seihou2.scenes;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework.Scene;

import static org.atoiks.games.seihou2.scenes.MainScene.HEIGHT;
import static org.atoiks.games.seihou2.scenes.MainScene.WIDTH;

public final class TitleScene extends Scene {

    // Conventionally, last scene is always Quit,
    // sceneDest is always one less than the selector{X, Y}
    private static final int[] selectorX = {270, 322, 175};
    private static final int[] selectorY = {293, 357, 502};
    private static final int[] sceneDest = {2, 3};

    private Image titleImg;
    private Clip bgm;
    private int selector;

	@Override
	public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(titleImg, 0, 0, null);
        g.setColor(Color.white);
        g.drawLine(65, selectorY[selector], selectorX[selector], selectorY[selector]);
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
            if (++selector >= selectorX.length) selector = 0;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (--selector < 0) selector = selectorX.length - 1;
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

        bgm.start();
        bgm.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void leave() {
        bgm.stop();
    }
}