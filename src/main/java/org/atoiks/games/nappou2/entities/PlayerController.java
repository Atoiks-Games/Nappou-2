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

import org.atoiks.games.nappou2.Keymap;
import org.atoiks.games.nappou2.Vector2;
import org.atoiks.games.nappou2.Drifter;

import org.atoiks.games.nappou2.entities.Border;
import org.atoiks.games.nappou2.entities.bullet.factory.BulletFactory;
import org.atoiks.games.nappou2.entities.bullet.factory.LegacyPointBulletInfo;

public final class PlayerController {

    private static final float PLAYER_SPEED = 300;
    private static final BulletFactory PLAYER_BULLET_INFO = new LegacyPointBulletInfo(5, 1350);
    private static final float MIN_FIRE_DELAY = 0.2f;
    private static final Vector2 PLAYER_CENTER = new Vector2(Player.RADIUS, Player.RADIUS);

    private final Player player;
    private final Game game;
    private final Border border;
    private final Drifter drifter;
    private final Keymap keymap;

    private boolean ignoreUpdateFlag;
    private float playerFireLimiter;

    public PlayerController(final Game game, Border border, final Keymap keymap) {
        this.game = game;
        this.player = this.game.player;
        this.drifter = this.game.drifter;
        this.border = border;
        this.keymap = keymap;
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
        final Vector2 velocity = Vector2.muladd(
                PLAYER_SPEED,
                keymap.getMovementDirection(),
                drifter.getDrift());

        final boolean focusedMode = this.keymap.shouldSlowDown();
        final Vector2 newPos = Vector2.clamp(
                Vector2.muladd((focusedMode ? 0.55f : 1) * dt, velocity, player.getPosition()),
                PLAYER_CENTER,
                this.border.toVector().sub(PLAYER_CENTER));

        player.setPosition(newPos);
        player.setFocusedMode(focusedMode);
        player.update(dt);
    }

    private void processPlayerAttack(final float dt) {
        if ((playerFireLimiter -= dt) <= 0 && this.keymap.shouldFire()) {
            game.addPlayerBullet(PLAYER_BULLET_INFO.createBullet(player.getPosition(), (float) (-Math.PI / 2)));
            playerFireLimiter = MIN_FIRE_DELAY;
        }
    }

    private void processPlayerShield(final float dt) {
        if (this.keymap.shouldActivateShield()) {
            player.getShield().activate();
        }
    }
}
