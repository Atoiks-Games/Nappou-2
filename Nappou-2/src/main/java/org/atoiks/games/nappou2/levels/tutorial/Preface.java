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

package org.atoiks.games.nappou2.levels.tutorial;

import java.awt.Font;
import java.awt.Color;

import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.Player;

import org.atoiks.games.nappou2.entities.shield.FixedTimeShield;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public final class Preface implements ILevelState {

    private static final long serialVersionUID = 4928495316273389216L;

    private static final String[][] INFO_MSG = {
        { "Arrow Keys", "= Move" },
        { "Shift", "= Focus" },
        { "Z", "= Shoot" },
        { "X", "= Activate shield" },
        { "Escape", "= Pause" },
        { "Enter", "= Select" }
    };

    private transient SaveData saveData;

    private transient Font font16;
    private transient Font font30;

    @Override
    public void restore(final ILevelContext ctx) {
        final Game game = ctx.getGame();
        game.player = new Player(GAME_BORDER / 2, HEIGHT / 6 * 5,
                ResourceManager.<SaveData>get("./saves.dat").getShieldCopy());
        game.player.setHp(5);
        game.setScore(0);
    }

    @Override
    public void enter(final ILevelContext ctx) {
        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        this.font16 = fnt.deriveFont(16f);
        this.font30 = fnt.deriveFont(30f);

        ctx.clearMessage();

        this.saveData = ResourceManager.get("./saves.dat");
        this.saveData.setCheckpoint(this);

        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
            final Clip bgm = ResourceManager.get("/music/Awakening.wav");
            bgm.setMicrosecondPosition(0);
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void renderBackground(final IGraphics g) {
        g.setColor(Color.white);
        g.setFont(font30);
        g.drawString("Controls", 25, 70);
        g.setFont(font16);
        for (int i = 0; i < INFO_MSG.length; ++i) {
            final int h = 90 + i * (font16.getSize() + 5);
            g.drawString(INFO_MSG[i][0], 40, h);
            g.drawString(INFO_MSG[i][1], 120, h);
        }

        g.drawString("Survive the void", 25, 550);
        g.drawString("Press Enter to continue", 25, 580);
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            ctx.setState(new SingleShotWave());
            return;
        }
    }
}
