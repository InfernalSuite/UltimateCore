package com.infernalsuite.ultimatecore.farm.managers;

import com.infernalsuite.ultimatecore.farm.HyperRegions;
import mc.ultimatecore.farm.objects.*;
import com.infernalsuite.ultimatecore.farm.objects.blocks.BlockAfter;
import com.infernalsuite.ultimatecore.farm.objects.blocks.ChanceBlock;
import com.infernalsuite.ultimatecore.farm.objects.blocks.RegionBlock;
import com.infernalsuite.ultimatecore.helper.files.YAMLFile;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class RegionsManager extends YAMLFile {

    private final HyperRegions plugin;
    public Map<String, HyperRegion> hyperRegions;
    public Set<BlockRegen> blockRegenCache = new HashSet<>();

    public RegionsManager(HyperRegions plugin, String name, boolean defaults) {
        super(plugin, name, defaults, false);
        this.plugin = plugin;
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults() {
        hyperRegions = new HashMap<>();
        ConfigurationSection configurationSection = getConfig().getConfigurationSection("regRegions");
        if (configurationSection == null) return;
        for (String regRegion : configurationSection.getKeys(false)) {
            Set<String> regions = new HashSet<>(getConfig().getStringList("regRegions." + regRegion + ".regions"));
            RegenTime regenTime = new RegenTime(getConfig().getInt("regRegions." + regRegion + ".regenTime.max"), getConfig().getInt("regRegions." + regRegion + ".regenTime.min"));
            boolean isTripleBlock = getConfig().getBoolean("regRegions." + regRegion + ".isTripleBlock");
            byte blockBrokenData = Byte.parseByte(Objects.requireNonNull(getConfig().getString("regRegions." + regRegion + ".blockBroken.data")));
            RegionBlock blockBroken = new RegionBlock(getConfig().getString("regRegions." + regRegion + ".blockBroken.material"), blockBrokenData);
            byte blockWhileRegenData = Byte.parseByte(Objects.requireNonNull(getConfig().getString("regRegions." + regRegion + ".blockWhileRegen.data")));
            RegionBlock blockWhileRegen = new RegionBlock(getConfig().getString("regRegions." + regRegion + ".blockWhileRegen.material"), blockWhileRegenData);
            List<ChanceBlock> chanceBlocksList = new ArrayList<>();
            ConfigurationSection section = getConfig().getConfigurationSection("regRegions." + regRegion + ".blocksAfter");
            if (section != null) {
                for (String type : section.getKeys(false)) {
                    byte data = getConfig().contains("regRegions." + regRegion + ".blocksAfter." + type + ".data") ? Byte.valueOf(Objects.requireNonNull(getConfig().getString("regRegions." + regRegion + ".blocksAfter." + type + ".data"))) : 0;
                    int chance = getConfig().contains("regRegions." + regRegion + ".blocksAfter." + type + ".chance") ? Byte.valueOf(Objects.requireNonNull(getConfig().getString("regRegions." + regRegion + ".blocksAfter." + type + ".chance"))) : 100;
                    ChanceBlock chanceBlock = new ChanceBlock(getConfig().getString("regRegions." + regRegion + ".blocksAfter." + type + ".material"), data, chance);
                    chanceBlocksList.add(chanceBlock);
                }
            }
            ChanceBlocks chanceBlocks = new ChanceBlocks(chanceBlocksList);
            String texture;
            try {
                Textures textures = Textures.valueOf(blockBroken.getMaterial());
                texture = textures.getTexture();
            } catch (Exception e) {
                texture = getConfig().contains("regRegions." + regRegion + ".texture") ? getConfig().getString("regRegions." + regRegion + ".texture") : Textures.DEFAULT_TEXTURE.getTexture();
            }
            HyperRegion farmRegion = new HyperRegion(regions, regenTime, isTripleBlock, blockBroken, blockWhileRegen, chanceBlocks, texture);
            hyperRegions.put(regRegion, farmRegion);
        }
    }

    public boolean regenBlock(Block block, String material, WrappedBlockData data) {
        Location location = block.getLocation();
        Optional<HyperRegion> hyperRegion = hyperRegions.values().stream()
                .filter(region -> region.getBlockBroken().getMaterial().equals(material))
                .filter(region -> data.getLegacyData() == region.getBlockBroken().getData() || region.getBlockBroken().getData() == -1)
                .filter(region -> region.getRegions().size() > 0).findFirst();

        if (!hyperRegion.isPresent()) return false;

        Optional<String> regionName = hyperRegion.get().getRegions().stream()
                .filter(region -> plugin.getAddonsManager().getRegionPlugin().isInRegion(location, region)).findFirst();

        if (!regionName.isPresent()) return false;

        new BlockRegen(location, hyperRegion.get(), regionName.get(), new BlockAfter(data, block, hyperRegion.get())).startRegen();

        return true;
    }


    public void removeCache(BlockRegen blockRegen) {
        if (blockRegen == null) return;
        blockRegenCache.remove(blockRegen);
    }

    public void addCache(BlockRegen blockRegen) {
        if (blockRegen == null) return;
        blockRegenCache.add(blockRegen);
    }
}
