package org.atoiks.games.seihou2.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import org.atoiks.games.framework.Scene;
import org.atoiks.games.seihou2.entities.IShield;
import org.atoiks.games.seihou2.entities.shield.FixedTimeShield;
import org.atoiks.games.seihou2.entities.shield.TrackingTimeShield;

public final class PlayerOptionScene extends Scene {

    private static final int[] shieldSelX = {272, 272};
    private static final int[] shieldSelY = {393, 455};

    private Image shieldOptImg;
    private int shieldSel;

	@Override
	public void render(Graphics g) {
        g.drawImage(shieldOptImg, 0, 0, null);
        g.setColor(Color.white);
        g.drawLine(96, shieldSelY[shieldSel], shieldSelX[shieldSel], shieldSelY[shieldSel]);
    }

	@Override
	public boolean update(float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ESCAPE)) {
            scene.switchToScene(1);
            return true;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
            scene.gotoNextScene();
            return true;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
            if (++shieldSel >= shieldSelX.length) shieldSel = 0;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (--shieldSel < 0) shieldSel = shieldSelX.length - 1;
        }
		return true;
	}

	@Override
	public void resize(int x, int y) {
		// Screen size is fixed
    }
    
    @Override
    public void enter(int previousSceneId) {
        shieldOptImg = (Image) scene.resources().get("opt_shield.png");
    }

    @Override
    public void leave() {
        final IShield shield;
        switch (shieldSel) {
            case 0:
            default:
                shield = new FixedTimeShield(3.5f, 50);
                break;
            case 1:
                shield = new TrackingTimeShield(2f, 35);
                break;
        }
        scene.resources().put("shield", shield);
    }
}