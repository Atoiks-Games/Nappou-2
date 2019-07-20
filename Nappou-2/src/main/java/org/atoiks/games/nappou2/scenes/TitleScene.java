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

import java.awt.Color;

import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.resource.Font;

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.NullState;

import org.atoiks.games.nappou2.entities.Player;

public final class TitleScene extends OptionSelectScene {

    private static final Entry[] ENTRIES = {
        new Entry("Continue", new Vector2(68, 232)),
        new Entry("New Game", new Vector2(68, 270)),
        new Entry("Highscore", new Vector2(68, 308)),
        new Entry("Settings", new Vector2(68, 346)),
        new Entry("Credits", new Vector2(68, 384)),
        new Entry("Quit", new Vector2(68, 469))
    };

    private static final int[] BLOCK_CONTINUE_INDICES = {
        1, 2, 3, 4, 5
    };
    private static final int[] STANDARD_INDICES = {
        0, 1, 2, 3, 4, 5
    };

    private final Font font80;
    private final Clip bgm;

    private SaveData saves;

    public TitleScene() {
        super(ResourceManager.get("/Logisoso.ttf"), ResourceManager.<GameConfig>get("./game.cfg").keymap, false);

        this.font80 = this.font16.deriveSize(80f);

        this.bgm = ResourceManager.get("/music/Enter_The_Void.wav");

        this.setOptions(STANDARD_INDICES, ENTRIES);
    }

    @Override
    public void enter(final Scene from) {
        // Enter only deals with scenes that play different songs!
        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }

        // Refetch in case SaveData was replaced
        this.saves = ResourceManager.get("./saves.dat");

        final boolean blockContinueOption = this.saves.getCheckpoint() instanceof NullState;
        this.updateSelectableIndices(blockContinueOption ? BLOCK_CONTINUE_INDICES : STANDARD_INDICES);
    }

    @Override
    public void leave() {
        bgm.stop();
    }

    @Override
    public void render(IGraphics g) {
        super.render(g);

        g.setColor(Color.white);
        this.font80.renderText(g, "Void Walker", 260, 178);

        this.font16.renderText(g, "      Made with love by Atoiks Games", 603, 540);
        this.font16.renderText(g, "In association with Harvard Game Devs", 600, 560);
    }

    @Override
    public boolean update(float dt) {
        super.update(dt);

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            final int selector = this.getSelectedIndex();
            switch (selector) {
                case 0:
                    GameLevelScene.unwindAndStartLevel(new Player(this.saves.getShieldCopy()), this.saves.getCheckpoint());
                    break;
                case 1:
                    SceneManager.pushScene(new DiffOptionScene());
                    break;
                case 2:
                    SceneManager.pushScene(new ScoreScene());
                    break;
                case 3:
                    SceneManager.pushScene(new ConfigScene());
                    break;
                case 4:
                    SceneManager.pushScene(new CreditsScene());
                    break;
                case 5:
                    return false;
                default:
                    System.err.println("Unhandled selector jump: " + selector);
                    return false;
            }
            return true;
        }
        return true;
    }
}
