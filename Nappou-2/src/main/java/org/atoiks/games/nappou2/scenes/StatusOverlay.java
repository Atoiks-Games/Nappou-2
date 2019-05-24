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
import java.awt.Image;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.entities.Game;

import static org.atoiks.games.nappou2.App.SANS_FONT;

import static org.atoiks.games.nappou2.scenes.RefittedGameScene.WIDTH;
import static org.atoiks.games.nappou2.scenes.RefittedGameScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.RefittedGameScene.GAME_BORDER;

/* package */ final class StatusOverlay {

    public static final Color BACKGROUND_COLOR = new Color(106, 106, 106);

    private Game game;
    private Image hpImg;

    public void attachGame(final Game game) {
        this.game = game;
    }

    public void attachHpImg(final Image hpImg) {
        this.hpImg = hpImg;
    }

    public void render(final IGraphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(GAME_BORDER, 0, WIDTH, HEIGHT);

        g.setColor(Color.white);
        g.setFont(SANS_FONT);
        g.drawString("HP Remaining", GAME_BORDER + 2, 16);
        g.drawString("Score", GAME_BORDER + 2, 58);

        final int hp = game.player.getHp();
        final int w = hpImg.getWidth(null);
        for (int i = 0; i < hp; ++i) {
            g.drawImage(hpImg, GAME_BORDER + 5 + i * w, 24);
        }

        final String str = game.getScore() == 0 ? "0" : Integer.toString(game.getScore()) + "000";
        g.drawString(str, GAME_BORDER + 5, 74);

        if (game.player.shield.isReady()) {
            g.drawString("Shield Ready", GAME_BORDER + 30, 96);
        }
    }
}
