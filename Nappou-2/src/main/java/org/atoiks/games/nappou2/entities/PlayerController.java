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

package org.atoiks.games.nappou2.entities;

import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Input;

import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.Drifter;

import org.atoiks.games.nappou2.entities.Border;
import org.atoiks.games.nappou2.entities.bullet.factory.BulletFactory;
import org.atoiks.games.nappou2.entities.bullet.factory.LegacyPointBulletInfo;

public final class PlayerController {

    public static final float DEFAULT_DX = 300f;
    public static final float DEFAULT_DY = 300f;

    private static final BulletFactory PLAYER_BULLET_INFO = new LegacyPointBulletInfo(5, DEFAULT_DY * 4.5f);
    private static final float MIN_FIRE_DELAY = 0.2f;
    private static final Vector2 PLAYER_CENTER = new Vector2(Player.RADIUS, Player.RADIUS);

    private final Player player;
    private final Game game;
    private final Border border;
    private final Drifter drifter;

    private boolean ignoreUpdateFlag;
    private float playerFireLimiter;

    public PlayerController(final Game game, Border border) {
        this.game = game;
        this.player = this.game.player;
        this.drifter = this.game.drifter;
        this.border = border;
    }

    public Player getAssociatedPlayer() {
        return this.player;
    }

    public boolean isUpdateIgnored() {
        return this.ignoreUpdateFlag;
    }

    public void setIgnoreUpdate(boolean flag) {
        this.ignoreUpdateFlag = flag;
    }

    public void update(final float dt) {
        if (ignoreUpdateFlag) {
            return;
        }

        processPlayerMovement(dt);
        processPlayerAttack(dt);
        processPlayerShield(dt);
    }

    private void processPlayerMovement(final float dt) {
        int signX = 0;
        int signY = 0;

        if (Input.isKeyDown(KeyEvent.VK_DOWN))  ++signY;
        if (Input.isKeyDown(KeyEvent.VK_UP))    --signY;
        if (Input.isKeyDown(KeyEvent.VK_RIGHT)) ++signX;
        if (Input.isKeyDown(KeyEvent.VK_LEFT))  --signX;

        final Vector2 velocity = new Vector2(signX * DEFAULT_DX, signY * DEFAULT_DY).add(drifter.getDrift());

        final boolean focusedMode = Input.isKeyDown(KeyEvent.VK_SHIFT);
        final Vector2 newPos = Vector2.clamp(
                Vector2.muladd((focusedMode ? 0.55f : 1) * dt, velocity, player.getPosition()),
                PLAYER_CENTER,
                this.border.toVector().sub(PLAYER_CENTER));

        player.setPosition(newPos);
        player.setFocusedMode(focusedMode);
        player.update(dt);
    }

    private void processPlayerAttack(final float dt) {
        if ((playerFireLimiter -= dt) <= 0 && Input.isKeyDown(KeyEvent.VK_Z)) {
            game.addPlayerBullet(PLAYER_BULLET_INFO.createBullet(player.getPosition(), (float) (-Math.PI / 2)));
            playerFireLimiter = MIN_FIRE_DELAY;
        }
    }

    private void processPlayerShield(final float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_X)) {
            player.getShield().activate();
        }
    }
}
