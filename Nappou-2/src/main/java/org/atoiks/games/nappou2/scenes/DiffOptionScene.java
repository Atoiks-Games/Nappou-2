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
import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;
import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.levels.level1.*;
import org.atoiks.games.nappou2.levels.ILevelState;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.shield.NullShield;

public final class DiffOptionScene extends CenteringScene {

    private static final int[] diffSelY = {274, 334, 393, 491};
    private static final int OPT_HEIGHT = 37;

    private int diffSel;

    private final Font font30;
    private final Font font80;

    public DiffOptionScene() {
        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        this.font30 = fnt.deriveFont(30f);
        this.font80 = fnt.deriveFont(80f);
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        g.setColor(Color.white);
        g.setFont(this.font80);
        g.drawString("Choose Your Difficulty", 80, 120);
        g.setFont(this.font30);
        for (int i = 0; i < Utils.DIFF_NAMES.length; ++i) {
            g.drawString(Utils.DIFF_NAMES[i], 98, diffSelY[i] + this.font30.getSize());
        }
        g.drawRect(90, diffSelY[diffSel], 94, diffSelY[diffSel] + OPT_HEIGHT);
    }

    @Override
    public boolean update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.popScene();
            return true;
        }
        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            final ILevelState level = getLevelFromOption();
            if (ResourceManager.<GameConfig>get("./game.cfg").challengeMode) {
                // Challenge mode forces you to use no shields,
                // so it there is no need to ever jump into shield-option-scene.
                //
                // but we also jump directly to the chosen stage since
                // there is a part of the tutorial that requires a shield!

                final NullShield shield = new NullShield();
                ResourceManager.<SaveData>get("./saves.dat").setShield(shield);
                GameLevelScene.unwindAndStartLevel(new Game(shield), level);
            } else {
                SceneManager.pushScene(new ShieldOptionScene(level));
            }
            return true;
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

    private Difficulty getDiffFromOption() {
        try {
            return Difficulty.values()[diffSel];
        } catch (IndexOutOfBoundsException ex) {
            return Difficulty.NORMAL;
        }
    }

    private ILevelState getLevelFromOption() {
        final Difficulty diff = getDiffFromOption();
        switch (diff) {
            case EASY:
                return new Easy();
            case NORMAL:
                return new Normal();
            case HARD:
                return new Hard();
            case INSANE:
                return new Insane();
            default:
                throw new AssertionError("Unhandled difficulty: " + diff);
        }
    }
}
