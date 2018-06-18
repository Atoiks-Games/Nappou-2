package org.atoiks.games.seihou2.entities.enemy;

import java.awt.Color;

import se.tube42.lib.tweeny.Item;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.seihou2.entities.Game;
import org.atoiks.games.seihou2.entities.IEnemy;

public abstract class TweenEnemy extends IEnemy {

    private static final long serialVersionUID = 7192746L;

    public static final int FIELD_X = 0;
    public static final int FIELD_Y = 1;
    public static final int FIELD_R = 2;

    protected final Item xyr;

    protected Game game;

    protected TweenEnemy(int hp, float x, float y, float r) {
        this(hp, new Item(3));
        this.xyr.setImmediate(FIELD_X, x);
        this.xyr.setImmediate(FIELD_Y, y);
        this.xyr.setImmediate(FIELD_R, r);
    }

    protected TweenEnemy(int hp, Item tween) {
        super(hp);
        this.xyr = tween;
    }

    @Override
    public final void attachGame(Game game) {
        this.game = game;
    }

    @Override
    public final float getX() {
        return this.xyr.get(FIELD_X);
    }
    
    @Override
    public final float getY() {
        return this.xyr.get(FIELD_Y);
    }

    @Override
    public final float getR() {
        return this.xyr.get(FIELD_R);        
    }

    public final void setX(float x) {
        this.xyr.setImmediate(FIELD_X, x);
    }

    public final void setY(float y) {
        this.xyr.setImmediate(FIELD_Y, y);
    }

    public final void setR(float r) {
        this.xyr.setImmediate(FIELD_R, r);
    }

    public Item tween() {
        return xyr;
    }
}