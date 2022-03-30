package com.infernalsuite.ultimatecore.skills.listener.perks;

import com.infernalsuite.ultimatecore.skills.api.events.SkillsFortuneEvent;
import com.infernalsuite.ultimatecore.skills.HyperSkills;
import com.infernalsuite.ultimatecore.skills.enums.FortuneType;
import com.infernalsuite.ultimatecore.skills.objects.SkillType;
import com.infernalsuite.ultimatecore.skills.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Collection;
import java.util.Random;

public class DoubleItemPerks {
    public static void multiplyRewards(Player player, SkillType skillType, Block bl, double percentage, boolean telekinesis) {
        Location location = bl.getLocation();
        Collection<ItemStack> drops = bl.getDrops();
        int r = new Random().nextInt(100);
        //DOUBLE
        SkillsFortuneEvent doubleEvent = new SkillsFortuneEvent(player, skillType, drops, r, percentage, FortuneType.DOUBLE_DROPS, location);
        Bukkit.getPluginManager().callEvent(doubleEvent);
        if (doubleEvent.getRandom() < doubleEvent.getPercentage() && !doubleEvent.isCancelled())
            dropItem(player, doubleEvent.getDrops(), doubleEvent.getDropLocation(), telekinesis);
        //TRIPLE
        if(percentage > 100){
            SkillsFortuneEvent tripleEvent = new SkillsFortuneEvent(player, skillType, drops, r, percentage, FortuneType.TRIPLE_DROPS, location);
            Bukkit.getPluginManager().callEvent(tripleEvent);
            if (tripleEvent.getRandom() > tripleEvent.getPercentage() - 100 || tripleEvent.isCancelled()) return;
            dropItem(player, tripleEvent.getDrops(), tripleEvent.getDropLocation(), telekinesis);
        }
    }

    private static void dropItem(Player player, Collection<ItemStack> itemStacks, Location location, boolean telekinesis){
        if(itemStacks != null && itemStacks.size() > 0)
            for(ItemStack itemStack : itemStacks)
                if(telekinesis && !Utils.hasInventoryFull(player))
                    player.getInventory().addItem(itemStack);
                else
                    location.getWorld().dropItem(location, itemStack).setMetadata("placeBlock", new FixedMetadataValue(HyperSkills.getInstance(), "placeBlock"));
    }


}
