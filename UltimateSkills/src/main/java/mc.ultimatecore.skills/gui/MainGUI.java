package mc.ultimatecore.skills.gui;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.Item;
import mc.ultimatecore.skills.objects.Skill;
import mc.ultimatecore.skills.objects.SkillType;
import mc.ultimatecore.skills.utils.InventoryUtils;
import mc.ultimatecore.skills.utils.StringUtils;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MainGUI implements GUI {

    private final UUID uuid;

    private final HyperSkills plugin = HyperSkills.getInstance();

    public MainGUI(UUID uuid) {
        this.uuid = uuid;
    }

    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            if (e.getSlot() == plugin.getInventories().getCloseButton().slot) {
                player.closeInventory();
            }else if(e.getSlot() == plugin.getInventories().getShowRanking().slot){
                player.openInventory(new TopGUI(player.getUniqueId()).getInventory());
            }else if(e.getSlot() == plugin.getInventories().getMainMenuStatsItem().slot) {
                player.openInventory(new ProfileGUI(player).getInventory());
            }else if(e.getSlot() == plugin.getInventories().getMainMenuBack().slot && HyperSkills.getInstance().getInventories().isMainMenuBackEnabled()){
                player.closeInventory();
                String command = plugin.getInventories().getMainMenuBack().command;
                if(command.contains("%player%"))
                    Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())), 3);
                else
                    Bukkit.getScheduler().runTaskLater(plugin, () -> player.performCommand(command), 3);
            } else {
                for (Skill skill : plugin.getSkills().getAllSkills().values()) {
                    if (skill.getSlot() == e.getSlot() && skill.isEnabled())
                        player.openInventory(new SubGUI(player.getUniqueId(), skill.getSkillType(), 1, Utils.getMultiplier(player, skill.getSkillType())).getInventory());
                }
            }
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, plugin.getInventories().getMainMenuSize(), StringUtils.color(plugin.getInventories().getMainMenuTitle()));

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, InventoryUtils.makeItem(plugin.getInventories().getBackground()));
        }

        for (SkillType skillType : SkillType.values()) {
            Skill skill = plugin.getSkills().getAllSkills().get(skillType);
            if(!skill.isEnabled()) continue;
            int level = plugin.getSkillManager().getLevel(uuid, skillType);
            inventory.setItem(skill.getSlot(), InventoryUtils.makeItem(plugin.getInventories().getMainMenuSkillItem(), Utils.getSkillsPlaceHolders(uuid, skillType), skillType, level+1));
        }

        if(plugin.getInventories().isMainMenuBackEnabled()) {
            inventory.setItem(plugin.getInventories().getMainMenuBack().slot, InventoryUtils.makeItem(plugin.getInventories().getMainMenuBack()));
        }
        
        inventory.setItem(plugin.getInventories().getShowRanking().slot, InventoryUtils.makeItem(plugin.getInventories().getShowRanking()));
        Item item = new Item(plugin.getInventories().getMainMenuStatsItem());
        item.headOwner = Bukkit.getOfflinePlayer(uuid).getName();
        inventory.setItem(item.slot, InventoryUtils.makeItem(plugin.getInventories().getMainMenuStatsItem(), Utils.getMainStatsPlaceholders(uuid)));
        inventory.setItem(plugin.getInventories().getCloseButton().slot, InventoryUtils.makeItem(plugin.getInventories().getCloseButton()));
        return inventory;
    }
}
