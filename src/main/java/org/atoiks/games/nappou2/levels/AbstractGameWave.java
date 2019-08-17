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

package org.atoiks.games.nappou2.levels;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Externalizable;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.SaveData;
import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.DefaultRestoreData;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public abstract class AbstractGameWave implements LevelState, Externalizable {

    private static final long serialVersionUID = -6227625815801486593L;

    protected final DefaultRestoreData restoreData = new DefaultRestoreData();
    protected int cycles;

    private String bgmPath;
    private Clip bgm;

    protected AbstractGameWave(String bgmPath) {
        this.bgmPath = bgmPath;
    }

    @Override
    public void restore(final LevelContext ctx) {
        this.defaultRestore(ctx);

        // Restart music if we resume
        if (this.bgmPath != null) {
            this.bgm = ResourceManager.get(this.bgmPath);
            this.bgm.setMicrosecondPosition(0);
        }
    }

    @Override
    public void respawn(final LevelContext ctx) {
        this.defaultRestore(ctx);

        // Fetch the music, but no not start from beginning
        if (this.bgmPath != null) {
            this.bgm = ResourceManager.get(this.bgmPath);
        }
    }

    private void defaultRestore(final LevelContext ctx) {
        final Game game = ctx.getGame();
        game.player.setPosition(GAME_BORDER / 2, HEIGHT / 6 * 5);
        this.restoreData.restore(game);
    }

    @Override
    public void enter(final LevelContext ctx) {
        this.cycles = 0;

        this.restoreData.fetch(ctx.getGame());

        if (this.bgmPath != null) {
            if (this.bgm == null) {
                this.bgm = ResourceManager.get(this.bgmPath);
            }

            if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
                this.configureBgm(this.bgm);
                this.bgm.start();
            }
        }

        final SaveData saveData = ResourceManager.get("./saves.dat");
        if (!saveData.isChallengeMode()) saveData.setCheckpoint(this);
    }

    protected void configureBgm(Clip bgm) {
        this.bgm.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void exit() {
        this.bgm.stop();
    }

    @Override
    public void writeExternal(final ObjectOutput s) throws IOException {
        this.restoreData.writeExternal(s);
        s.writeUTF(this.bgmPath);
    }

    @Override
    public void readExternal(final ObjectInput s) throws IOException, ClassNotFoundException {
        this.restoreData.readExternal(s);
        this.bgmPath = s.readUTF();
    }
}
