package org.atoiks.games.seihou2.entities.enemy;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.Game;
import org.atoiks.games.seihou2.entities.IEnemy;

public abstract class AbstractEnemy extends IEnemy {

    private static final long serialVersionUID = 7192746L;

    protected float x, y, r;

    protected Game game;

    protected AbstractEnemy(int hp, float x, float y, float r) {
        super(hp);
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public final void attachGame(Game game) {
        this.game = game;
    }

    @Override
    public final float getX() {
        return this.x;
    }

    @Override
    public final float getY() {
        return this.y;
    }

    @Override
    public final float getR() {
        return this.r;
    }

    public final void setX(float x) {
        this.x = x;
    }

    public final void setY(float y) {
        this.y = y;
    }

    public final void setR(float r) {
        this.r = r;
    }
}