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

import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.framework2d.resource.Font;
import org.atoiks.games.framework2d.resource.Texture;

import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.ScoreCounter;

import org.atoiks.games.nappou2.entities.Player;

import org.atoiks.games.nappou2.entities.shield.*;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.WIDTH;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

/* package */ final class StatusOverlay {

    public static final Color BACKGROUND_COLOR = new Color(106, 106, 106);

    private final Font font;
    private final ScoreCounter scoreCounter;
    private final Shield shield;
    private final SaveData save;

    public StatusOverlay(Font font, final Player player) {
        this.font = font.deriveSize(16f);
        this.scoreCounter = player.getScoreCounter();
        this.shield = player.getShield();
        this.save = ResourceManager.get("./saves.dat");
    }

    public void render(final IGraphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(GAME_BORDER, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);
        g.drawLine(GAME_BORDER, 0, GAME_BORDER, HEIGHT);

        g.setColor(Color.white);
        this.font.renderText(g, "Score", GAME_BORDER + 2, 16);

        final int rawScore = this.scoreCounter.getScore();
        final String str = rawScore == 0 ? "0" : Integer.toString(rawScore) + "000";
        this.font.renderText(g, str, GAME_BORDER + 5, 32);

        if (this.save.isChallengeMode()) {
            this.font.renderText(g, "Challenge Mode", GAME_BORDER + 28, 136);
        }

        g.translate(GAME_BORDER, 56);
        this.shield.drawStatus(g, this.font);
        g.translate(-GAME_BORDER, -56);
    }
}
