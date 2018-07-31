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

import org.atoiks.games.nappou2.GameConfig;
import org.atoiks.games.nappou2.Difficulty;
import org.atoiks.games.nappou2.entities.IShield;
import org.atoiks.games.nappou2.entities.shield.*;

public final class ShieldOptionScene extends Scene {

    private static final int[] shieldSelY = {356, 414, 498};
    private static final int OPT_HEIGHT = 37;

    private Image shieldOptImg;
    private Clip bgm;
    private int shieldSel;
    private Difficulty diff;

    @Override
    public void render(IGraphics g) {
        g.drawImage(shieldOptImg, 0, 0);
        g.setColor(Color.white);
        g.drawRect(90, shieldSelY[shieldSel], 94, shieldSelY[shieldSel] + OPT_HEIGHT);
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

        if (diff != Difficulty.CHALLENGE) {
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
                if (++shieldSel >= shieldSelY.length) shieldSel = 0;
            }
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
                if (--shieldSel < 0) shieldSel = shieldSelY.length - 1;
            }

            final int mouseY = scene.mouse().getLocalY();
            for (int i = 0; i < shieldSelY.length; ++i) {
                final int selBase = shieldSelY[i];
                if (mouseY > selBase && mouseY < (selBase + OPT_HEIGHT)) {
                    shieldSel = i;
                    break;
                }
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
        shieldOptImg = (Image) scene.resources().get("opt_shield.png");
        bgm = (Clip) scene.resources().get("Enter_The_Void.wav");
        diff = (Difficulty) scene.resources().get("difficulty");

        if (((GameConfig) scene.resources().get("game.cfg")).bgm) {
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }

        if (diff == Difficulty.CHALLENGE) {
            // Challenge mode does not allow a lumas
            shieldSel = shieldSelY.length - 1;
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
            case 0: return new FixedTimeShield(3.5f, 2, 50);
            case 1: return new TrackingTimeShield(2f, 3, 35);
            case 2: return new NullShield();
        }
    }
}
