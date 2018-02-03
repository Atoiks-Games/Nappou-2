package org.atoiks.games.seihou2.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework.Scene;
import org.atoiks.games.seihou2.GameConfig;
import org.atoiks.games.seihou2.entities.IShield;
import org.atoiks.games.seihou2.entities.shield.*;

public final class PlayerOptionScene extends Scene {

    private static final int[] shieldSelY = {356, 414, 498};

    private Image shieldOptImg;
    private Clip bgm;
    private int shieldSel;

	@Override
	public void render(Graphics g) {
        g.drawImage(shieldOptImg, 0, 0, null);
        g.setColor(Color.white);
        g.drawRect(90, shieldSelY[shieldSel], 4, 37);
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
            if (++shieldSel >= shieldSelY.length) shieldSel = 0;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (--shieldSel < 0) shieldSel = shieldSelY.length - 1;
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
        bgm = (Clip) scene.resources().get("title.wav");

        if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void leave() {
        scene.resources().put("shield", getShieldFromOption());

        bgm.stop();
    }

    private IShield getShieldFromOption() {
        switch (shieldSel) {
            default:
            case 0: return new FixedTimeShield(3.5f, 50);
            case 1: return new TrackingTimeShield(2f, 35);
            case 2: return new NullShield();
        }
    }
}