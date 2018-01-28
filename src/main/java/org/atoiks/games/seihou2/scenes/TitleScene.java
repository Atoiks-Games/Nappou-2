package org.atoiks.games.seihou2.scenes;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import org.atoiks.games.framework.Scene;

import static org.atoiks.games.seihou2.scenes.MainScene.HEIGHT;
import static org.atoiks.games.seihou2.scenes.MainScene.WIDTH;

public final class TitleScene extends Scene {

    private Image titleImg;

	@Override
	public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(titleImg, 0, 0, null);
	}

	@Override
	public boolean update(float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
            scene.gotoNextScene();
        }
		return true;
	}

	@Override
	public void resize(int x, int y) {
		// Screen size is fixed
    }
    
    @Override
    public void enter() {
        titleImg = (Image) scene.resources().get("title.png");
    }
}