/**
 *  Nappou-2
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>
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

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.GameScene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Difficulty;
import org.atoiks.games.nappou2.GameConfig;

public final class DiffOptionScene extends GameScene {

    private static final Difficulty[] DIFFS = Difficulty.values();
    private static final int[] diffSelY = {274, 334, 393, 491};
    private static final int OPT_HEIGHT = 37;

    private Clip bgm;
    private int diffSel;
    private Difficulty difficulty;

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        g.setColor(Color.white);
        g.setFont(TitleScene.TITLE_FONT);
        g.drawString("Choose Your Difficulty", 80, 120);
        g.setFont(TitleScene.OPTION_FONT);
        for (int i = 0; i < DIFFS.length; ++i) {
            g.drawString(DIFFS[i].toString(), 98, diffSelY[i] + TitleScene.OPTION_FONT.getSize());
        }
        g.drawRect(90, diffSelY[diffSel], 94, diffSelY[diffSel] + OPT_HEIGHT);
    }

    @Override
    public boolean update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            return scene.switchToScene(1);
        }
        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            return scene.gotoNextScene();
        }

        if (Input.isKeyPressed(KeyEvent.VK_DOWN)) {
            ++diffSel;
            if (diffSel >= diffSelY.length) diffSel = 0;
        }
        if (Input.isKeyPressed(KeyEvent.VK_UP)) {
            if (--diffSel < 0) diffSel = diffSelY.length - 1;
        }
        return true;
    }

    @Override
    public void resize(int x, int y) {
        // Screen size is fixed
    }

    @Override
    public void init() {
        bgm = (Clip) scene.resources().get("Enter_The_Void.wav");
    }

    @Override
    public void enter(int previousSceneId) {
        difficulty = (Difficulty) scene.resources().getOrDefault("difficulty", Difficulty.NORMAL);

        final GameConfig cfg = (GameConfig) scene.resources().get("game.cfg");

        if (cfg.bgm) {
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void leave() {
        scene.resources().put("difficulty", getDiffFromOption());

        bgm.stop();
    }

    private Difficulty getDiffFromOption() {
        try {
            return Difficulty.values()[diffSel];
        } catch (IndexOutOfBoundsException ex) {
            return Difficulty.NORMAL;
        }
    }
}
