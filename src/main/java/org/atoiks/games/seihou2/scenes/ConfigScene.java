package org.atoiks.games.seihou2.scenes;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework.Scene;
import org.atoiks.games.seihou2.GameConfig;

public final class ConfigScene extends Scene {

	private Image configImg;
	private Clip bgm;

	@Override
	public void render(Graphics g) {
		g.drawImage(configImg, 0, 0, null);
	}

	@Override
	public boolean update(float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ESCAPE)) {
            scene.switchToScene(1);
        }
		return true;
	}

	@Override
	public void resize(int x, int y) {
        // Screen size is fixed
	}
	
	@Override
	public void enter(int previousSceneId) {
		configImg = (Image) scene.resources().get("config.png");
		bgm = (Clip) scene.resources().get("title.wav");
		
		if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
	}

	@Override
	public void leave() {
		bgm.stop();
	}
}