package org.atoiks.games.seihou2.entities.shield;

public final class FixedTimeShield extends TimeBasedShield {

    private static final long serialVersionUID = 259728713501591561L;
    
    private boolean relocateX = false;
    private boolean relocateY = false;

    public FixedTimeShield(float timeout, float r) {
        super(timeout, r);
    }

    @Override
    public void activate() {
        if (!active) {
            super.activate();
            relocateX = relocateY = true;
        }
    }

    @Override
    public void setX(float x) {
        if (relocateX) {
            super.setX(x);
            relocateX = false;
        }
    }

    @Override
    public void setY(float y) {
        if (relocateY) {
            super.setY(y);
            relocateY = false;
        }
    }
}