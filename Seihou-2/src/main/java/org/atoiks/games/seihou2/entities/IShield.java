package org.atoiks.games.seihou2.entities;

import java.io.Serializable;

import org.atoiks.games.framework2d.IRender;
import org.atoiks.games.framework2d.IUpdate;

public interface IShield extends ICollidable, IRender, IUpdate, Serializable {

    public float getX();
    public float getY();
    public float getR();

    public void setX(float x);
    public void setY(float y);

    public void activate();
    public void deactivate();
    public boolean isActive();
}