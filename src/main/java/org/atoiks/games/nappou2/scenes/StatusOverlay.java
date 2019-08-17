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
    private final Texture circImg;
    private final SaveData save;

    public StatusOverlay(Font font, final Player player) {
        this.font = font.deriveSize(16f);
        this.scoreCounter = player.getScoreCounter();
        this.shield = player.getShield();
        this.circImg = ResourceManager.get("/image/circ.png");
        this.save = ResourceManager.get("./saves.dat");
    }

    public void render(final IGraphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(GAME_BORDER, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);
        g.drawLine(GAME_BORDER, 0, GAME_BORDER, HEIGHT);

        g.setColor(Color.white);
        this.font.renderText(g, "Score", GAME_BORDER + 2, 58);

        final int rawScore = this.scoreCounter.getScore();
        final String str = rawScore == 0 ? "0" : Integer.toString(rawScore) + "000";
        this.font.renderText(g, str, GAME_BORDER + 5, 74);

        if (this.shield instanceof CounterBasedShield) {
            final CounterBasedShield cbs = (CounterBasedShield) this.shield;

            final int activationsRemaining = cbs.getTimesRemaining();

            this.font.renderText(g, "Shields Remaining", GAME_BORDER + 2, 96);

            final int w = circImg.getWidth();
            for (int i = 0; i < activationsRemaining; ++i) {
                g.drawTexture(circImg, GAME_BORDER + 5 + i * w, 100);
            }

            if (cbs.isReady()) {
                this.font.renderText(g, "Ready", GAME_BORDER + 78, 114);
            }
        } else if (this.shield instanceof TimedReloadShield) {
            final TimedReloadShield trs = (TimedReloadShield) this.shield;

            final float secondsRemaining = trs.getRemainingReloadTime();
            if (secondsRemaining > 0) {
                this.font.renderText(g, "Shield Reloading", GAME_BORDER + 2, 96);
                this.font.renderText(g, String.format("%.1fs left", secondsRemaining), GAME_BORDER + 5, 112);
            } else if (trs.isReady()) {
                this.font.renderText(g, "Shield Ready", GAME_BORDER + 2, 96);
            } else if (trs.isActive()) {
                this.font.renderText(g, "Shield Active", GAME_BORDER + 2, 96);
            }
        } else if (this.shield.isReady()) {
            // Default way of notifying a shield that is ready
            this.font.renderText(g, "Shield Ready", GAME_BORDER + 30, 96);
        }

        if (this.save.isChallengeMode()) {
            this.font.renderText(g, "Challenge Mode", GAME_BORDER + 28, 136);
        }
    }
}
