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

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.NullState;

import org.atoiks.games.nappou2.entities.Player;

public final class TitleScene extends OptionSelectScene {

    private static final String[] OPT_MSG = {
        "Continue", "New Game", "Highscore", "Settings", "Credits", "Quit"
    };
    private static final Vector2[] OPT_POS = {
        new Vector2(68, 232),
        new Vector2(68, 270),
        new Vector2(68, 308),
        new Vector2(68, 346),
        new Vector2(68, 384),
        new Vector2(68, 469)
    };

    private final Font font80;
    private final Clip bgm;

    private SaveData saves;
    private boolean blockContinueOption;

    public TitleScene() {
        super(ResourceManager.get("/Logisoso.ttf"), ResourceManager.<GameConfig>get("./game.cfg").keymap, false);

        this.font80 = this.font16.deriveFont(80f);

        this.bgm = ResourceManager.get("/music/Enter_The_Void.wav");

        this.setOptions(OPT_MSG, OPT_POS);
    }

    @Override
    protected int getMinimumIndex() {
        return this.blockContinueOption ? 1 : 0;
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
        this.blockContinueOption = this.saves.getCheckpoint() instanceof NullState;

        if (this.blockContinueOption) {
            // selector index will wrap back to last entry if we do not do this!
            this.setSelectorIndex(this.getMinimumIndex());
        }
    }

    @Override
    public void leave() {
        bgm.stop();
    }

    @Override
    public void render(IGraphics g) {
        super.render(g);

        g.setColor(Color.white);
        g.setFont(font80);
        g.drawString("Void Walker", 260, 178);

        g.setFont(this.font16);
        g.drawString("      Made with love by Atoiks Games", 603, 540);
        g.drawString("In association with Harvard Game Devs", 600, 560);
    }

    @Override
    public boolean update(float dt) {
        super.update(dt);

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            final int selector = this.getSelectorIndex();
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
