package mc.ultimatecore.reforge.utils;

import com.cryptomorin.xseries.XSound;
import mc.ultimatecore.enchantment.EnchantmentsPlugin;
import mc.ultimatecore.enchantment.enchantments.HyperEnchant;
import mc.ultimatecore.reforge.HyperReforge;
import mc.ultimatecore.reforge.Item;
import mc.ultimatecore.reforge.enums.ReforgeState;
import mc.ultimatecore.reforge.managers.GUIManager;
import mc.ultimatecore.reforge.objects.ChanceObject;
import mc.ultimatecore.reforge.objects.EnchantmentProbability;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Utils {

    public static void playSound(Player player, String sound){
        try{
            player.playSound(player.getLocation(), XSound.valueOf(sound).parseSound(), 1, 1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path) {
        Item item = new Item();
        if (yamlConfig.contains(path + ".material"))
            item.material = com.cryptomorin.xseries.XMaterial.valueOf(yamlConfig.getString(path + ".material"));
        if (yamlConfig.contains(path + ".title")) item.title = yamlConfig.getString(path + ".title");
        if (yamlConfig.contains(path + ".lore")) item.lore = yamlConfig.getStringList(path + ".lore");
        if (yamlConfig.contains(path + ".slot")) item.slot = yamlConfig.getInt(path + ".slot");
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");

        if (yamlConfig.contains(path + ".command")) item.command = yamlConfig.getString(path + ".command");
        if (yamlConfig.contains(path + ".amount")) item.amount = yamlConfig.getInt(path + ".amount");
        return item;
    }


    public static ReforgeState manageReforge(GUIManager manager){
        ItemStack itemStack = manager.getFirstItem();
        if(itemStack != null){
            String type = itemStack.getType().toString();
            if(type.contains("SWORD"))
                return ReforgeState.NO_ERROR_WEAPON;
            if(type.contains("HELMET") || type.contains("LEGGINGS") || type.contains("CHESTPLATE") || type.contains("BOOTS"))
                return ReforgeState.NO_ERROR_ARMOR;
            if(type.contains("BOW"))
                return ReforgeState.NO_ERROR_BOW;
        }
        return ReforgeState.INCOMPATIBLE_ITEMS;
    }


    public static double getCost(GUIManager manager){
        if(manager.getReforgeState() == null) return 0;
        Optional<ChanceObject> chanceObject = HyperReforge.getInstance().getChancesManager().getChanceObject(manager.getReforgeState().itemType);

        if(!chanceObject.isPresent()) return 0;
        ItemStack itemStack = manager.getFirstItem();

        if(itemStack == null) return 0;
        Map<HyperEnchant, Integer> hyperEnchants = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(itemStack);
        int reforgedEnch = 0;
        for(HyperEnchant hyperEnchant : hyperEnchants.keySet()){
            if(EnchantmentsPlugin.getInstance().getApi().hasReforgedEnchantment(itemStack, hyperEnchant)) reforgedEnch++;
        }
        double cost = chanceObject.get().getCostPerEnchant() * (hyperEnchants.keySet().size() - reforgedEnch);
        return cost > 0 ? cost : chanceObject.get().getCostPerEnchant();
    }

    public static ItemStack getReforgedItem(GUIManager manager){
        ItemStack itemStack = manager.getFirstItem().clone();
        Optional<ChanceObject> chanceObject = HyperReforge.getInstance().getChancesManager().getChanceObject(manager.getReforgeState().itemType);
        if(!chanceObject.isPresent()) return itemStack;
        Map<HyperEnchant, Integer> hyperEnchants = EnchantmentsPlugin.getInstance().getApi().getItemEnchantments(itemStack);
        List<HyperEnchant> reforgedEnch = new ArrayList<>();
        for(HyperEnchant hyperEnchant : hyperEnchants.keySet())
            if(EnchantmentsPlugin.getInstance().getApi().hasReforgedEnchantment(itemStack, hyperEnchant)) reforgedEnch.add(hyperEnchant);

        //REMOVING OLD REFORG ENCHANTS
        for(HyperEnchant hyperEnchant : reforgedEnch){
            itemStack = hyperEnchant.removeEnchant(itemStack);
            hyperEnchants.remove(hyperEnchant);
        }

        int newEnchants = hyperEnchants.keySet().size() * chanceObject.get().getNewEnchantsPerEnchant();
        if(newEnchants <= 0) newEnchants = chanceObject.get().getNewEnchantsPerEnchant();
        HashMap<HyperEnchant, Integer> toAddHyperEnchants = new HashMap<>();
        int antiInfinity = 0;
        List<EnchantmentProbability> enchantmentProbabilities = chanceObject.get().getEnchantmentProbabilities();

        for(int i = 0; i<newEnchants;){
            antiInfinity++;
            if(antiInfinity == 100) break;
            int random = new Random().nextInt(enchantmentProbabilities.size());
            EnchantmentProbability enchantmentProbability = enchantmentProbabilities.get(random);
            random = new Random().nextInt(enchantmentProbabilities.size());
            enchantmentProbability = enchantmentProbability.getChance() < enchantmentProbabilities.get(random).getChance() ? enchantmentProbability : enchantmentProbabilities.get(random);
            HyperEnchant toAddEnchant = enchantmentProbability.getHyperEnchant();
            Optional<Enchantment> optionalEnchantment = toAddEnchant.hasEnchantmentConflicts(itemStack);
            if(optionalEnchantment.isPresent()) continue;
            if(!toAddEnchant.itemCanBeEnchanted(itemStack)) continue;
            if(toAddHyperEnchants.containsKey(toAddEnchant) || hyperEnchants.containsKey(toAddEnchant)) continue;
            toAddHyperEnchants.put(toAddEnchant, enchantmentProbability.getLevel());
            i--;
        }

        for(HyperEnchant hyperEnchant : toAddHyperEnchants.keySet())
            itemStack = EnchantmentsPlugin.getInstance().getApi().enchantItem(itemStack, hyperEnchant, toAddHyperEnchants.get(hyperEnchant), true);

        return itemStack;
    }

}
