package mc.ultimatecore.skills.listener;

import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.item.UltimateItem;
import mc.ultimatecore.skills.objects.perks.Perk;
import mc.ultimatecore.skills.utils.StringUtils;
import mc.ultimatecore.skills.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ArmorSetupListener implements Listener {

    @EventHandler
    public void checkChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        Map<String, String> setupMode = HyperSkills.getInstance().getUltimateItems().getSetupMode(uuid);
        if (setupMode != null && !setupMode.isEmpty()) {
            Optional<String> key = setupMode.keySet().stream().findFirst();
            String edit = setupMode.get(key.get());
            event.setCancelled(true);
            String message = event.getMessage();
            UltimateItem ultimateItem = HyperSkills.getInstance().getUltimateItems().ultimateItems.getOrDefault(key.get(), null);
            final boolean b = edit.equals("ManaCost") || edit.equals("Lore") || edit.equals("Name");
            if(message.contains("leave") || message.contains("exit") || message.contains("stop")){
                if (b)
                    Utils.openGUIAsync(player, ultimateItem);
                else
                    Utils.openGUIAttributeAsync(player, ultimateItem);
            }else{
                try{
                    switch (edit) {
                        case "ManaCost":
                            double manaCost = Double.parseDouble(message);
                            ultimateItem.setManaCost(manaCost);
                            break;
                        case "Lore":
                            ultimateItem.setItemStack(getNewItem(ultimateItem.getItemStack(), message.contains("%EMPTY%") ? "" : message));
                            break;
                        case "Name":
                            ultimateItem.setDisplayName(StringUtils.color(message));
                            break;
                        default:
                            Ability.getAbilities().stream()
                                    .filter(edit::equalsIgnoreCase)
                                    .forEach(ability -> ultimateItem.getAbilitiesMap().put(Ability.valueOf(ability), Double.parseDouble(message)));
                            Perk.getPerks().stream()
                                    .filter(edit::equalsIgnoreCase)
                                    .forEach(perk -> ultimateItem.getPerksMap().put(Perk.valueOf(perk), Double.parseDouble(message)));
                            break;
                    }
                    if (b)
                        Utils.openGUIAsync(player, ultimateItem);
                    else
                        Utils.openGUIAttributeAsync(player, ultimateItem);
                } catch (NumberFormatException e) {
                    player.sendMessage(StringUtils.color(HyperSkills.getInstance().getMessages().getMessage("invalidNumber").replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
                }
                HyperSkills.getInstance().getUltimateItems().removeSetupMode(uuid);
            }
            HyperSkills.getInstance().getUltimateItems().removeSetupMode(uuid);
        }
    }

    private ItemStack getNewItem(ItemStack itemStack, String newLine){
        ItemMeta meta = itemStack.getItemMeta();
        List<String> list = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        list.add(StringUtils.color(newLine));
        meta.setLore(list);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        HyperSkills.getInstance().getUltimateItems().removeSetupMode(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerLeave(PlayerKickEvent event){
        HyperSkills.getInstance().getUltimateItems().removeSetupMode(event.getPlayer().getUniqueId());
    }
}
