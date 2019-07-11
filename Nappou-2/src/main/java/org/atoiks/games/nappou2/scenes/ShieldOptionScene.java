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
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;

import org.atoiks.games.nappou2.entities.Player;

import org.atoiks.games.nappou2.entities.shield.*;

public final class ShieldOptionScene extends OptionSelectScene {

    private static final String[] OPT_NAMES = {
        "Bonfire", "Firefly", "None"
    };
    private static final Vector2[] OPT_POS = {
        new Vector2(98, 356),
        new Vector2(98, 414),
        new Vector2(98, 498)
    };

    private final LevelState nextState;

    private final Font font80;

    public ShieldOptionScene(LevelState nextState) {
        super(ResourceManager.get("/Logisoso.ttf"), ResourceManager.<GameConfig>get("./game.cfg").keymap);

        this.nextState = nextState;
        this.font80 = this.font30.deriveFont(80f);

        this.setOptions(OPT_NAMES, OPT_POS);
    }

    @Override
    public void render(IGraphics g) {
        super.render(g);

        g.setFont(this.font80);
        g.drawString("Choose Your Shield", 130, 120);
    }

    @Override
    public boolean update(float dt) {
        super.update(dt);

        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            final ShieldEntity shield = getShieldFromOption();
            ResourceManager.<SaveData>get("./saves.dat").setShield(shield);
            GameLevelScene.unwindAndStartLevel(new Player(shield.copy()), this.nextState);
        }
        return true;
    }

    private ShieldEntity getShieldFromOption() {
        switch (this.getSelectorIndex()) {
            default:
            case 0: return new FixedTimeShield(3.5f, 2, 50);
            case 1: return new TrackingTimeShield(2f, 3, 35);
            case 2: return new NullShield();
        }
    }
}
