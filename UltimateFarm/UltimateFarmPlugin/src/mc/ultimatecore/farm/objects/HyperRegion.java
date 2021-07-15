package mc.ultimatecore.farm.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.farm.objects.blocks.RegionBlock;

import java.util.Set;

@Getter
@AllArgsConstructor
public class HyperRegion {
    @Setter
    private Set<String> regions;
    @Setter
    private RegenTime regenTime;
    private final boolean isTripleBlock;

    private final RegionBlock blockBroken;
    private final RegionBlock blockWhileRegen;
    private final ChanceBlocks chanceBlocks;
    private final String texture;

    public String getTexture() {
        if (texture == null)
            return Textures.DEFAULT_TEXTURE.getTexture();
        return texture;
    }
}
