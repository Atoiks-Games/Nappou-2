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
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Difficulty;
import org.atoiks.games.nappou2.GameConfig;
import org.atoiks.games.nappou2.entities.IShield;
import org.atoiks.games.nappou2.entities.shield.*;

public final class DiffOptionScene extends Scene {

    private static final int[] diffSelY = {274, 334, 393, 491};
    private static final int OPT_HEIGHT = 37;

    private Image diffOptImg;
    private Clip bgm;
    private int diffSel;
    private Difficulty difficulty;
    private boolean challengeModeLocked;

    @Override
    public void render(IGraphics g) {
        g.drawImage(diffOptImg, 0, 0);
        g.setColor(Color.white);
        g.drawRect(90, diffSelY[diffSel], 94, diffSelY[diffSel] + OPT_HEIGHT);
    }

    @Override
    public boolean update(float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ESCAPE)) {
            scene.switchToScene(1);
            return true;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER) || scene.mouse().isButtonClicked(1, 2)) {
            scene.gotoNextScene();
            return true;
        }


        if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
            ++diffSel;
            if (diffSel >= diffSelY.length || (challengeModeLocked && diffSel == diffSelY.length - 1)) diffSel = 0;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (--diffSel < 0) diffSel = diffSelY.length - (challengeModeLocked ? 2 : 1);
        }

        final int mouseY = scene.mouse().getLocalY();
        for (int i = 0; i < diffSelY.length; ++i) {
            if (challengeModeLocked && i == diffSelY.length - 1) continue;

            final int selBase = diffSelY[i];
            if (mouseY > selBase && mouseY < (selBase + OPT_HEIGHT)) {
                diffSel = i;
                break;
            }
        }
        return true;
    }

    @Override
    public void resize(int x, int y) {
        // Screen size is fixed
    }

    @Override
    public void enter(int previousSceneId) {
        diffOptImg = (Image) scene.resources().get("opt_diff.png");
        bgm = (Clip) scene.resources().get("Enter_The_Void.wav");
        difficulty = (Difficulty) scene.resources().getOrDefault("difficulty", Difficulty.NORMAL);

        final GameConfig cfg = (GameConfig) scene.resources().get("game.cfg");
        challengeModeLocked = cfg.challengeModeLocked;

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
        switch (diffSel) {
            case 0: return Difficulty.EASY;
            default:    // default is normal
            case 1: return Difficulty.NORMAL;
            case 2: return Difficulty.HARD;
            case 3: return Difficulty.CHALLENGE;
        }
    }
}
