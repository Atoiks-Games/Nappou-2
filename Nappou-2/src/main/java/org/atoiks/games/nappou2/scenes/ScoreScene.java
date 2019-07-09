/**
 *  Nappou-2
 *  Copyright (C) 2017-2019  Atoiks-Games <atoiks-games@outlook.com>
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

import java.awt.Font;
import java.awt.Color;

import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Utils;
import org.atoiks.games.nappou2.Keymap;
import org.atoiks.games.nappou2.ScoreData;
import org.atoiks.games.nappou2.GameConfig;

public final class ScoreScene extends CenteringScene {

    private static final String[] PLANE_MSG = {
        "Highscore", "Highscore (Challenge Mode)"
    };

    private ScoreData score = null;
    private int plane = 0;
    private boolean showName = true;

    private final Font font16;
    private final Font font30;
    private final Keymap keymap;

    private int ticks = 0;

    public ScoreScene() {
        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        this.font16 = fnt.deriveFont(16f);
        this.font30 = fnt.deriveFont(30f);

        this.score = ResourceManager.get("./score.dat");
        this.keymap = ResourceManager.<GameConfig>get("./game.cfg").keymap;
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        if (score == null) return;

        g.setColor(Color.white);
        g.setFont(this.font30);
        g.drawString(PLANE_MSG[plane], 10, 30);

        g.setFont(this.font16);
        final int size = this.font16.getSize();
        final ScoreData.Pair[][][] splane = score.getScoreForPlane(plane);
        for (int i = 0; i < splane.length; ++i) {
            final int bh = 55 + 8 * size * i;
            g.drawString("Level " + (i + 1), 20, bh);
            for (int diffId = 0; diffId < Utils.DIFF_NAMES.length; ++diffId) {
                final int bw = 60 + diffId * 200;
                final ScoreData.Pair[] p = splane[i][diffId];
                g.drawString(Utils.DIFF_NAMES[diffId], bw, bh + size);
                for (int j = 0; j < p.length; ++j) {
                    final int offset = p.length - 1 - j;
                    final ScoreData.Pair pair = p[offset];

                    if (pair != null && pair.isValid()) {
                        g.drawString(showName ? pair.getProcessedName() : pair.getProcessedScore(),
                                bw + 10, bh + (j + 2) * size);
                    }
                }
            }
        }

        g.drawString("Hit Escape to return to title screen", 14, 540);
        g.drawString("Switch score mode with left and right arrows", 14, 560);
    }

    @Override
    public boolean update(float dt) {
        if (++ticks % 225 == 0) {
            ticks = 0;
            showName = !showName;
        }

        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.popScene();
            return true;
        }
        if (this.keymap.shouldSelectRight()) {
            plane = (plane + 1) % PLANE_MSG.length;
        }
        if (this.keymap.shouldSelectLeft()) {
            if (--plane < 0) plane = PLANE_MSG.length - 1;
        }
        return true;
    }
}
