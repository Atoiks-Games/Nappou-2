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


import org.atoiks.games.nappou2.pattern.NullPattern;
import org.atoiks.games.nappou2.pattern.IAttackPattern;

import org.atoiks.games.nappou2.pathway.IPathway;
import org.atoiks.games.nappou2.pathway.FixedPathway;

public final class PathwayEnemy extends AbstractEnemy {

    private static final long serialVersionUID = -4022004468618519339L;

    private IPathway path;
    private IAttackPattern attack;

    private float r;

    private boolean driftFlag = true;
    private float dx;
    private float dy;

    private final int score;

    public PathwayEnemy(int hp, int score) {
        this(hp, score, FixedPathway.DEFAULT, NullPattern.INSTANCE);
    }

    public PathwayEnemy(int hp, int score, final IPathway path, final IAttackPattern attack) {
        super(hp);
        this.score = score;

        setPathway(path);
        setAttackPattern(attack);
    }

    public IPathway getPathway() {
        return path;
    }

    public void setPathway(IPathway p) {
        this.path = p != null ? p : FixedPathway.DEFAULT;
    }

    public IAttackPattern getUpdateListener() {
        return attack;
    }

    public void setAttackPattern(IAttackPattern lis) {
        this.attack = lis != null ? lis : NullPattern.INSTANCE;
    }

    public void ignoreDrift(boolean flag) {
        driftFlag = !flag;
    }

    public void resetDriftFactor() {
        this.dx = 0;
        this.dy = 0;
    }

    @Override
    public void update(float dt) {
        path.update(dt);
        attack.onFireUpdate(this, dt);
    }

    @Override
    public void drift(float dx, float dy) {
        if (driftFlag) {
            this.dx += dx;
            this.dy += dy;
        }
    }

    @Override
    public float getX() {
        return this.path.getX() + this.dx;
    }

    @Override
    public float getY() {
        return this.path.getY() + this.dy;
    }

    @Override
    public float getR() {
        return this.r;
    }

    public void setR(float r) {
        this.r = r;
    }

    @Override
    public int getScore() {
        return score;
    }
}
