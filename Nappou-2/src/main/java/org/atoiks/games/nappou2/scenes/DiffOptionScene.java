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

import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Utils;
import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;
import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.levels.level1.*;
import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.tutorial.Preface;

import org.atoiks.games.nappou2.entities.Player;

import org.atoiks.games.nappou2.entities.shield.NullShield;

public final class DiffOptionScene extends OptionSelectScene {

    private static final Entry[] ENTRIES = {
        new Entry("", new Vector2(98, 274)),
        new Entry("", new Vector2(98, 334)),
        new Entry("", new Vector2(98, 393)),
        new Entry("", new Vector2(98, 491))
    };

    static {
        if (ENTRIES.length != Utils.DIFF_NAMES.length) {
            // Sanity check: these two must be the same!
            throw new IllegalStateException("ENTRIES.length != Utils.DIFF_NAMES.length");
        }

        for (int i = 0; i < ENTRIES.length; ++i) {
            ENTRIES[i].setText(Utils.DIFF_NAMES[i]);
        }
    }

    private final Font font80;

    public DiffOptionScene() {
        super(ResourceManager.get("/Logisoso.ttf"), ResourceManager.<GameConfig>get("./game.cfg").keymap);

        this.font80 = this.font30.deriveFont(80f);

        this.setOptions(ENTRIES);
    }

    @Override
    public void render(IGraphics g) {
        super.render(g);

        g.setFont(this.font80);
        g.drawString("Choose Your Difficulty", 80, 120);
    }

    @Override
    public boolean update(float dt) {
        super.update(dt);
        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            final LevelState level = new Preface(getLevelFromOption());
            if (ResourceManager.<GameConfig>get("./game.cfg").challengeMode) {
                // Challenge mode forces you to use no shields,
                // so there is no need to jump into shield-option-scene.

                final NullShield shield = new NullShield();
                ResourceManager.<SaveData>get("./saves.dat").setShield(shield);
                GameLevelScene.unwindAndStartLevel(new Player(shield), level);
            } else {
                SceneManager.pushScene(new ShieldOptionScene(level));
            }
            return true;
        }
        return true;
    }

    private Difficulty getDiffFromOption() {
        try {
            return Difficulty.values()[this.getSelectedIndex()];
        } catch (IndexOutOfBoundsException ex) {
            return Difficulty.NORMAL;
        }
    }

    private LevelState getLevelFromOption() {
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
