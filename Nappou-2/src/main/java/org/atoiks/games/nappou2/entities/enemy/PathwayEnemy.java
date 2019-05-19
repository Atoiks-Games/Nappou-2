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

package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.nappou2.entities.IPathway;
import org.atoiks.games.nappou2.entities.IUpdateListener;

import org.atoiks.games.nappou2.entities.attack.NullPattern;
import org.atoiks.games.nappou2.entities.pathway.FixedPathway;

public class PathwayEnemy extends AbstractEnemy {

    private static final long serialVersionUID = 4632259879769823818L;

    protected IPathway path;
    protected IUpdateListener listener;

    protected float r;

    private float dx;
    private float dy;

    private final int score;

    public PathwayEnemy(int hp, int score) {
        this(hp, score, FixedPathway.DEFAULT, NullPattern.INSTANCE);
    }

    public PathwayEnemy(int hp, int score, final IPathway path, final IUpdateListener listener) {
        super(hp);
        this.score = score;

        setPathway(path);
        setUpdateListener(listener);
    }

    public final IPathway getPathway() {
        return path;
    }

    public final void setPathway(IPathway p) {
        this.path = p != null ? p : FixedPathway.DEFAULT;
    }

    public final IUpdateListener getUpdateListener() {
        return listener;
    }

    public final void setUpdateListener(IUpdateListener lis) {
        this.listener = lis != null ? lis : NullPattern.INSTANCE;
    }

    @Override
    public void update(float dt) {
        path.update(dt);
        listener.onFireUpdate(this, dt);
    }

    @Override
    public void drift(float dx, float dy) {
        this.dx += dx;
        this.dy += dy;
    }

    @Override
    public final float getX() {
        return this.path.getX() + this.dx;
    }

    @Override
    public final float getY() {
        return this.path.getY() + this.dy;
    }

    @Override
    public final float getR() {
        return this.r;
    }

    public final void setR(float r) {
        this.r = r;
    }

    @Override
    public int getScore() {
        return score;
    }
}
