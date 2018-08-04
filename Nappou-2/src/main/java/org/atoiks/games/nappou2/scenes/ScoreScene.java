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
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.ScoreData;
import org.atoiks.games.nappou2.Difficulty;
import org.atoiks.games.nappou2.GameConfig;

import static org.atoiks.games.nappou2.App.SANS_FONT;
import static org.atoiks.games.nappou2.scenes.LevelOneScene.WIDTH;
import static org.atoiks.games.nappou2.scenes.LevelOneScene.HEIGHT;

public final class ScoreScene extends Scene {

    private static final String[] PLANE_MSG = {
        "Highscore", "Highscore (Challenge Mode)"
    };

    private ScoreData score = null;
    private int plane = 0;

    private Clip bgm;

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        if (score == null) return;

        g.setColor(Color.white);
        g.setFont(TitleScene.OPTION_FONT);
        g.drawString(PLANE_MSG[plane], 10, 30);

        g.setFont(SANS_FONT);
        final int size = SANS_FONT.getSize();
        final int[][][] splane = score.data[plane];
        for (int i = 0; i < splane.length; ++i) {
            final int bh = 55 + 8 * size * i;
            g.drawString("Level " + (i + 1), 20, bh);
            for (Difficulty diff : Difficulty.values()) {
                final int bw = 60 + diff.ordinal() * 200;
                final int[] p = splane[i][diff.ordinal()];
                g.drawString(diff.toString(), bw, bh + size);
                for (int j = 0; j < p.length; ++j) {
                    final int offset = p.length - 1 - j;
                    final String str = p[offset] == 0 ? "0" : Integer.toString(p[offset]) + "000";
                    g.drawString(str, bw + 10, bh + (j + 2) * size);
                }
            }
        }

        g.drawString("Switch score mode with left and right arrows", 14, 560);
        g.drawString("Hit Escape or Enter to return to title screen", 14, 580);
    }

    @Override
    public boolean update(float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ESCAPE) || scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
            scene.switchToScene(1);
            return true;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_RIGHT)) {
            plane = (plane + 1) % PLANE_MSG.length;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_LEFT)) {
            if (--plane < 0) plane = PLANE_MSG.length - 1;
        }
        return true;
    }

    @Override
    public void resize(int x, int y) {
        // Screen size is fixed
    }

    @Override
    public void enter(int previousSceneId) {
        score = (ScoreData) scene.resources().get("score.dat");
        bgm = (Clip) scene.resources().get("Enter_The_Void.wav");

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
