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

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.sizer.Sizer;
import org.atoiks.games.nappou2.sizer.FixedSizer;

import org.atoiks.games.nappou2.pattern.NullPattern;
import org.atoiks.games.nappou2.pattern.AttackPattern;

import org.atoiks.games.nappou2.pathway.Pathway;
import org.atoiks.games.nappou2.pathway.FixedPosition;

public final class PathwayEnemy extends AbstractEnemy {

    private Pathway path;
    private AttackPattern attack;
    private Sizer sizer;

    private float r;

    private boolean driftFlag = true;
    private Vector2 displacement = Vector2.ZERO;

    private final int score;

    public PathwayEnemy(int hp, int score) {
        this(hp, score, FixedPosition.DEFAULT, NullPattern.INSTANCE, FixedSizer.INSTANCE);
    }

    public PathwayEnemy(int hp, int score, final Pathway path, final AttackPattern attack) {
        this(hp, score, path, attack, FixedSizer.INSTANCE);
    }

    public PathwayEnemy(int hp, int score, final Pathway path, final AttackPattern attack, final Sizer sizer) {
        super(hp);
        this.score = score;

        setPathway(path);
        setAttackPattern(attack);
        setSizer(sizer);
    }

    public Pathway getPathway() {
        return path;
    }

    public void setPathway(Pathway p) {
        this.path = p != null ? p : FixedPosition.DEFAULT;
    }

    public AttackPattern getUpdateListener() {
        return attack;
    }

    public void setAttackPattern(AttackPattern lis) {
        this.attack = lis != null ? lis : NullPattern.INSTANCE;
    }

    public Sizer getSizer() {
        return sizer;
    }

    public void setSizer(Sizer f) {
        this.sizer = f != null ? f : FixedSizer.INSTANCE;
    }

    public void ignoreDrift(boolean flag) {
        driftFlag = !flag;
    }

    public void resetDriftFactor() {
        this.displacement = Vector2.ZERO;
    }

    @Override
    public void update(float dt) {
        path.update(dt);
        attack.onFireUpdate(this, dt);
        this.r = sizer.getNextSize(this.r, dt);
    }

    @Override
    public void drift(Vector2 d) {
        if (driftFlag) {
            this.displacement = this.displacement.add(d);
        }
    }

    @Override
    public Vector2 getPosition() {
        return this.path.getPosition().add(this.displacement);
    }

    @Override
    public float getRadius() {
        return this.r;
    }

    public void setRadius(float r) {
        this.r = r;
    }

    @Override
    public int getScore() {
        return score;
    }
}
