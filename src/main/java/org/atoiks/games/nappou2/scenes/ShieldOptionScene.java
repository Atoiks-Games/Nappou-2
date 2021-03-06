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

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.KeyCode;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.resource.Font;

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;

import org.atoiks.games.nappou2.entities.Player;

import org.atoiks.games.nappou2.entities.shield.*;

public final class ShieldOptionScene extends OptionSelectScene {

    private static final Entry[] ENTRIES = {
        new Entry("Bonfire", new Vector2(98, 356)),
        new Entry("Firefly", new Vector2(98, 414))
    };

    private final LevelState nextState;

    private final Font font80;

    public ShieldOptionScene(LevelState nextState) {
        super(ResourceManager.get("/Logisoso.ttf"), ResourceManager.<GameConfig>get("./game.cfg").keymap);

        this.nextState = nextState;
        this.font80 = this.font30.deriveSize(80f);

        this.setOptions(ENTRIES);
    }

    @Override
    public void render(IGraphics g) {
        super.render(g);

        this.font80.renderText(g, "Choose Your Shield", 130, 120);
    }

    @Override
    public boolean update(float dt) {
        super.update(dt);

        if (Input.isKeyPressed(KeyCode.KEY_ENTER)) {
            final ShieldEntity shield = getShieldFromOption();
            ResourceManager.<SaveData>get("./saves.dat").setShield(shield);
            GameLevelScene.unwindAndStartLevel(new Player(shield.copy()), this.nextState, true);
        }
        return true;
    }

    private ShieldEntity getShieldFromOption() {
        switch (this.getSelectedIndex()) {
            default:
            case 0: return new CounterBasedShield(new FixedTimeShield(3.5f, 50), 3);
            case 1: return new CounterBasedShield(new TrackingTimeShield(2f, 35), 3);
        }
    }
}
