package org.atoiks.games.seihou2.equations;

import se.tube42.lib.tweeny.TweenEquation;

public final class Polynomial {

    public final TweenEquation easeIn;
    public final TweenEquation easeOut;
    public final TweenEquation easeInOut;
    public final TweenEquation easeOutIn;

    public Polynomial(int order) {
        if (order < 0) throw new IllegalArgumentException("Order of polynomial cannot be negative");
        this.easeIn = new TweenEquation() {
            @Override
            public float compute(float t) {
                return (float) Math.pow(t, order);
            }

            @Override
            public String toString() {
                return order + "-polynomial-in";
            }
        };
        this.easeOut = new TweenEquation() {
            @Override
            public float compute(float t) {
                return 1 - (float) Math.pow(1 - t, order);
            }
    
            @Override
            public String toString() {
                return order + "-polynomial-out";
            }
        };
        this.easeInOut = new TweenEquation() {
            @Override
            public float compute(float t) {
                if (t > 0.5) {
                    return 1 - 0.5f * (float) Math.pow(2 * (1 - t), order);
                }
                return 0.5f * (float) Math.pow(2 * t, order);
            }

            @Override
            public String toString() {
                return order + "-polynomial-inout";
            }
        };
        this.easeOutIn = new TweenEquation() {
            @Override
            public float compute(float t) {
                final float rem = 0.5f * (float) Math.pow(2 * (0.5 - t), order);
                if (order % 2 == 1 || t <= 0.5) {
                    return 1 - rem;
                }
                return 1 + rem;
            }

            @Override
            public String toString() {
                return order + "-polynomial-outin";
            }
        };
    }
}