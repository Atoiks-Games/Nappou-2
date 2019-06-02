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

import java.awt.Image;

import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;

import org.atoiks.games.nappou2.entities.enemy.ShieldTesterEnemy;

import static org.atoiks.games.nappou2.scenes.DialogOverlay.alignVertical;
import static org.atoiks.games.nappou2.scenes.DialogOverlay.alignHorizontal;

import static org.atoiks.games.nappou2.scenes.RefittedGameScene.GAME_BORDER;

import static org.atoiks.games.nappou2.entities.Message.VerticalAlignment;
import static org.atoiks.games.nappou2.entities.Message.HorizontalAlignment;

/* package */ final class ShieldTesterWave implements ILevelState {

    public static final ShieldTesterWave INSTANCE = new ShieldTesterWave();

    private Image img;
    private int imgY;
    private int imgX;

    private boolean firstRun;

    private ShieldTesterWave() {
    }

    @Override
    public void enter(final ILevelContext ctx) {
        final Image image = ResourceManager.get("/image/x.png");
        this.img = image;
        this.imgY = alignVertical(VerticalAlignment.CENTER, image);
        this.imgX = alignHorizontal(HorizontalAlignment.CENTER, image);

        this.firstRun = true;
    }

    @Override
    public void renderBackground(final IGraphics g) {
        g.drawImage(img, imgX, imgY);
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        final Game game = ctx.getGame();
        if (game.noMoreEnemies()) {
            if (firstRun) {
                firstRun = false;
                game.addEnemy(new ShieldTesterEnemy(200, 0, -10, 8, false));
                game.addEnemy(new ShieldTesterEnemy(200, GAME_BORDER, -10, 8, false));
            } else {
                ctx.setState(PrebossDialog.INSTANCE);
                return;
            }
        }
    }
}
