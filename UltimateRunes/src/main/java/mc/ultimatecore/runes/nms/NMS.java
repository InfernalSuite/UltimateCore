package mc.ultimatecore.runes.nms;

import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;


public interface NMS {

    boolean inverted();

    Vector vectorName();

    Vector vectorStand();

    double addName();

    double addStand();

    EulerAngle rightArm();

}
