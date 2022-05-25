package mc.ultimatecore.helper.utils;

import com.cryptomorin.xseries.*;
import lombok.*;
import org.bukkit.block.*;
import org.bukkit.block.data.*;
import org.bukkit.material.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlockUtils {
    public static boolean needsPlacementTagging(Block block) {
        if (XMaterial.supports(13)) {
            return needsPlacementTaggingAquatic(block);
        } else {
            return needsPlacementTaggingLegacy(block);
        }
    }

    private static boolean needsPlacementTaggingAquatic(Block block) {
        return !(block.getBlockData() instanceof Ageable) || block.getType().toString().contains("SUGAR_CANE");
    }

    private static boolean needsPlacementTaggingLegacy(Block block) {
        if (block.getState().getData() instanceof Crops) {
            return false;
        }
        if (block.getState().getData() instanceof NetherWarts) {
            return false;
        }
        return true;
    }
}
