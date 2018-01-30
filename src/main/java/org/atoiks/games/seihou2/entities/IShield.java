package org.atoiks.games.seihou2.entities;

import java.io.Serializable;

import org.atoiks.games.framework.IRender;
import org.atoiks.games.framework.IUpdate;

public interface IShield extends ICollidable, IRender, IUpdate, Serializable {

    public float getX();
    public float getR();
    public float getY();

    public void setX(float x);
    public void setY(float y);

    public void activate();
    public void deactivate();
    public boolean isActive();
}