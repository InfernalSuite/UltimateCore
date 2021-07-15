package mc.ultimatecore.anvil.nms;

import mc.ultimatecore.anvil.utils.StringUtils;
import org.bukkit.Bukkit;

import java.util.function.Supplier;

/**
 * Matches the server's NMS version to its {@link VersionWrapper}
 *
 * @author Wesley Smith
 * @since 1.2.1
 */
public enum VersionMatcher {
    
    /**
     * IntelliJ will recommend you to replace these with method reference.
     * However, this would break the plugins on some machines running the HotSpot VM.
     * Just leave this as it is and add new versions down below in the same way.
     */
    V1_8_R3(mc.ultimatecore.anvil.nms.v1_8_R3::new),
    V1_12_R1(mc.ultimatecore.anvil.nms.v1_12_R1::new),
    V1_14_R1(mc.ultimatecore.anvil.nms.v1_14_R1::new),
    V1_15_R1(mc.ultimatecore.anvil.nms.v1_15_R1::new),
    V1_16_R3(mc.ultimatecore.anvil.nms.v1_16_R3::new),
    V1_17_R1(mc.ultimatecore.anvil.nms.v1_17_R1::new);
    
    private final Supplier<VersionWrapper> nmsSupplier;
    
    VersionMatcher(Supplier<VersionWrapper> nmsSupplier) {
        this.nmsSupplier = nmsSupplier;
    }
    
    public VersionWrapper getNms() {
        return nmsSupplier.get();
    }
    
    
    public static VersionWrapper byName() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        for (VersionMatcher minecraftVersion : values()) {
            if (minecraftVersion.name().equalsIgnoreCase(version))
                return minecraftVersion.getNms();
        }
        Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperAnvil] &cNo Compatibility found for version " + version));
        return null;
    }
    
    
}