package com.infernalsuite.ultimatecore.runes.nms;

import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class v1_8_R3 implements NMS {

    @Override
    public boolean inverted() {
        return false;
    }

    @Override
    public Vector vectorName() {
        return new Vector(0.0D, 0.6D, 0.0D);
    }

    @Override
    public Vector vectorStand() {
        return new Vector(0.0D, 0.5D, 0.0D);
    }

    @Override
    public double addName() {
        return -0.7D;
    }

    @Override
    public double addStand() {
        return -1.2D;
    }

    @Override
    public EulerAngle rightArm() {
        return new EulerAngle(0.05207085800252904D, 0.8784299702830227D, 0.0042994904876079865D);
    }
}

