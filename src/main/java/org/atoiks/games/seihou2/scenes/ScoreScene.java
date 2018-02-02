package org.atoiks.games.seihou2.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework.Scene;
import org.atoiks.games.seihou2.GameConfig;

import static org.atoiks.games.seihou2.scenes.LevelOneScene.WIDTH;
import static org.atoiks.games.seihou2.scenes.LevelOneScene.HEIGHT;

public final class ScoreScene extends Scene {

    private int[][] scoreDat = null;

    private Clip bgm;

	@Override
	public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (scoreDat == null) return;
        g.setColor(Color.white);
        for (int i = 0; i < scoreDat.length; ++i) {
            final int bh = 20 + 60 * i;
            g.drawString("Level " + (i + 1), 20, bh);
            final int[] p = scoreDat[i];
            for (int j = 0; j < p.length; ++j) {
                final int offset = p.length - 1 - j;
                final String str = p[offset] == 0 ? "0" : Integer.toString(p[offset]) + "000";
                g.drawString(str, 30, bh + (j + 1) * 12);
            }
        }
	}

	@Override
	public boolean update(float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ESCAPE) || scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
            scene.switchToScene(1);
            return true;
        }
		return true;
	}

	@Override
	public void resize(int x, int y) {
		// Screen size is fixed
    }
    
    @Override
    public void enter(int previousSceneId) {
        scoreDat = (int[][]) scene.resources().get("score.dat");
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