package mc.ultimatecore.skills.utils;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.messages.ActionBar;
import de.tr7zw.changeme.nbtapi.NBTItem;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.Item;
import mc.ultimatecore.skills.api.HyperSkillsAPI;
import mc.ultimatecore.skills.gui.ArmorEditGUI;
import mc.ultimatecore.skills.gui.AttributeEditGUI;
import mc.ultimatecore.skills.objects.DamageIndicator;
import mc.ultimatecore.skills.objects.PlayerSkills;
import mc.ultimatecore.skills.objects.SkillType;
import mc.ultimatecore.skills.objects.abilities.Ability;
import mc.ultimatecore.skills.objects.item.UltimateItem;
import mc.ultimatecore.skills.objects.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.*;

public final class Utils {
    private static final TreeMap<Integer, String> map = new TreeMap<>();
    private static final List<String> COLOR = new ArrayList<>();

    static {
        map.put(1000000, "m");
        map.put(900000, "cm");
        map.put(500000, "d");
        map.put(100000, "c");
        map.put(90000, "xc");
        map.put(50000, "l");
        map.put(10000, "x");
        map.put(9000, "Mx");
        map.put(5000, "v");
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
        map.put(0, "");
        COLOR.add("&f");
        COLOR.add("&e");
        COLOR.add("&6");
        COLOR.add("&c");
        COLOR.add("&c");
    }

    public static void sendOfflineMessage(UUID uuid, String message) {
        try {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(StringUtils.color(message.replace("%prefix%", HyperSkills.getInstance().getConfiguration().prefix)));
        } catch (Exception ignored) {

        }
    }

    public static Double getMultiplier(Player player, SkillType skill) {
        String skillName = skill.getName().toUpperCase();
        if (player.hasPermission("hyperskills.multiplier." + skillName + ".5") || player.hasPermission("hyperskills.multiplier.all.5"))
            return 4d;
        if (player.hasPermission("hyperskills.multiplier." + skillName + ".4") || player.hasPermission("hyperskills.multiplier.all.4"))
            return 4d;
        if (player.hasPermission("hyperskills.multiplier." + skillName + ".3.5") || player.hasPermission("hyperskills.multiplier.all.3.5"))
            return 3.5d;
        if (player.hasPermission("hyperskills.multiplier." + skillName + ".3") || player.hasPermission("hyperskills.multiplier.all.3"))
            return 3d;
        if (player.hasPermission("hyperskills.multiplier." + skillName + ".2.5") || player.hasPermission("hyperskills.multiplier.all.2.5"))
            return 2.5d;
        if (player.hasPermission("hyperskills.multiplier." + skillName + ".2") || player.hasPermission("hyperskills.multiplier.all.2"))
            return 2d;
        if (player.hasPermission("hyperskills.multiplier." + skillName + ".1.5") || player.hasPermission("hyperskills.multiplier.all.1.5"))
            return 1.5d;
        if (player.hasPermission("hyperskills.multiplier." + skillName + ".1.1") || player.hasPermission("hyperskills.multiplier.all.1.1"))
            return 1.1d;
        return 1d;
    }


    public static Item getItemFromConfig(YamlConfiguration yamlConfig, String path) {
        Item item = new Item();
        if (yamlConfig.contains(path + ".material"))
            item.material = XMaterial.valueOf(yamlConfig.getString(path + ".material"));
        if (yamlConfig.contains(path + ".title")) item.title = yamlConfig.getString(path + ".title");
        if (yamlConfig.contains(path + ".lore")) item.lore = yamlConfig.getStringList(path + ".lore");
        if (yamlConfig.contains(path + ".slot")) item.slot = yamlConfig.getInt(path + ".slot");
        if (yamlConfig.contains(path + ".isGlowing")) item.isGlowing = yamlConfig.getBoolean(path + ".isGlowing");
        if (yamlConfig.contains(path + ".headData")) item.headData = yamlConfig.getString(path + ".headData");
        if (yamlConfig.contains(path + ".headOwner")) item.headOwner = yamlConfig.getString(path + ".headOwner");
        if (yamlConfig.contains(path + ".command")) item.command = yamlConfig.getString(path + ".command");
        if (yamlConfig.contains(path + ".amount")) item.amount = yamlConfig.getInt(path + ".amount");
        return item;
    }

    public static String getUpperValue(String str) {
        return str.toUpperCase().replaceAll(" ", "_");
    }

    public static boolean hasSkillTouch(Player player) {
        return player.getInventory().getItemInHand() != null && player.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH);
    }

    public static List<Placeholder> getMainStatsPlaceholders(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        HyperSkillsAPI api = HyperSkills.getInstance().getApi();
        return new ArrayList<>(Arrays.asList(
                new Placeholder("defense", roundStr(api.getTotalAbility(uuid, Ability.DEFENSE))),
                new Placeholder("crit_chance", roundStr(api.getTotalAbility(uuid, Ability.CRIT_CHANCE))),
                new Placeholder("crit_damage", roundStr(api.getTotalAbility(uuid, Ability.CRIT_DAMAGE))),
                new Placeholder("health", roundStr(api.getTotalAbility(uuid, Ability.HEALTH))),
                new Placeholder("pet_luck", roundStr(api.getTotalAbility(uuid, Ability.PET_LUCK))),
                new Placeholder("speed", roundStr(api.getTotalAbility(uuid, Ability.SPEED))),
                new Placeholder("strength", roundStr(api.getTotalAbility(uuid, Ability.STRENGTH))),
                new Placeholder("player", offlinePlayer.getName()),
                new Placeholder("max_intelligence", roundStr(api.getTotalAbility(uuid, Ability.MAX_INTELLIGENCE))),
                new Placeholder("intelligence", roundStr(api.getTotalAbility(uuid, Ability.INTELLIGENCE)))));
    }

    public static String roundStr(Double d) {
        return new DecimalFormat("#.#").format(d);
    }

    public static float calculateSpeed(double speed) {
        if (!HyperSkills.getInstance().getAddonsManager().isMMOItems())
            return (float) Math.min((200.0D + (speed + 0.1)) * 0.0010000000474974513D, 1.0D);
        else
            return (float) Math.min((200.0D + speed) * 0.0010000000474974513D, 1.0D);

    }

    public static List<Placeholder> getStatsPlaceholders(UUID uuid) {
        return new ArrayList<Placeholder>() {{
            add(new Placeholder("player", Bukkit.getOfflinePlayer(uuid).getName()));
            for (Ability ability : Ability.values()) {
                double base = HyperSkills.getInstance().getApi().getSimpleAbility(uuid, ability);
                double extra = HyperSkills.getInstance().getApi().getExtraAbility(uuid, ability);
                double total = HyperSkills.getInstance().getApi().getTotalAbility(uuid, ability);
                String abilityName = formattedName(ability.toString());
                add(new Placeholder("total_" + abilityName, roundStr(total)));
                add(new Placeholder("simple_" + abilityName, roundStr(base)));
                add(new Placeholder("bonus_" + abilityName, roundStr(extra)));
            }

            for (Perk perk : Perk.values()) {
                double simple = HyperSkills.getInstance().getApi().getSimplePerk(uuid, perk);
                double total = HyperSkills.getInstance().getApi().getTotalPerk(uuid, perk);
                double armorValue = total - simple;
                String abilityName = formattedName(perk.toString());
                add(new Placeholder("total_" + abilityName, roundStr(total)));
                add(new Placeholder("simple_" + abilityName, roundStr(simple)));
                add(new Placeholder("bonus_" + abilityName, roundStr(armorValue)));
            }
        }};
    }

    private static String formattedName(String text) {
        return text.toLowerCase().replaceAll(" ", "_");
    }

    public static String getKey(String string) {
        return string.toUpperCase().replace(" ", "_");
    }

    public static int getBlockQuantity(Block bl, Material material) {
        int numBroken = 0;
        Block blockUP = bl.getRelative(BlockFace.UP);
        Block secondBlockUP = blockUP.getRelative(BlockFace.UP);
        if (!bl.hasMetadata("placeBlock"))
            numBroken++;
        if (blockUP.getType() == material && !blockUP.hasMetadata("placeBlock"))
            numBroken++;
        if (secondBlockUP.getType() == material && !secondBlockUP.hasMetadata("placeBlock"))
            numBroken++;
        return numBroken;
    }

    public static List<Placeholder> getSkillsPlaceHolders(UUID uuid, SkillType skill) {
        PlayerSkills playerSkills = HyperSkills.getInstance().getSkillManager().getPlayerSkills(uuid);
        int level = playerSkills.getLevel(skill);
        Double xp = playerSkills.getXP(skill);
        Double maxXP = HyperSkills.getInstance().getRequirements().getLevelRequirement(skill, level);
        double currentPercentage = getCurrentPercentage(xp, maxXP);
        return new ArrayList<>(Arrays.asList(new Placeholder("current_percentage", String.valueOf(round(currentPercentage))),
                new Placeholder("next_level", Utils.toRoman(level + 1)),
                new Placeholder("current_xp", String.valueOf(xp)),
                new Placeholder("level", Utils.toRoman(level)),
                new Placeholder("displayname", HyperSkills.getInstance().getSkills().getAllSkills().get(skill).getName()),
                new Placeholder("money_reward", "100"),
                new Placeholder("progress_bar", getProgressBar((int) currentPercentage)),
                new Placeholder("max_xp", String.valueOf(maxXP))));
    }

    public static List<Placeholder> getTopPlaceHolders(UUID uuid, SkillType skill) {
        PlayerSkills playerSkills = HyperSkills.getInstance().getSkillManager().getPlayerSkills(uuid);
        int level = playerSkills.getLevel(skill);
        Double xp = playerSkills.getXP(skill);
        Double maxXP = HyperSkills.getInstance().getRequirements().getLevelRequirement(skill, level);
        double currentPercentage = getCurrentPercentage(xp, maxXP);
        int rankPosition = playerSkills.getRankPosition(skill);
        int playersQuantity = HyperSkills.getInstance().getSkillManager().playersQuantity == 0 ? HyperSkills.getInstance().getSkillManager().getOptionalPlayers() : HyperSkills.getInstance().getSkillManager().playersQuantity;
        return new ArrayList<>(Arrays.asList(new Placeholder("current_percentage", String.valueOf(round(currentPercentage))),
                new Placeholder("next_level", Utils.toRoman(level + 1)),
                new Placeholder("current_xp", String.valueOf(xp)),
                new Placeholder("level", Utils.toRoman(level)),
                new Placeholder("displayname", HyperSkills.getInstance().getSkills().getAllSkills().get(skill).getName()),

                new Placeholder("rank_position", String.format("%,d", rankPosition)),
                new Placeholder("total_players", String.format("%,d", playersQuantity)),
                new Placeholder("rank_percentage", String.valueOf(round((rankPosition / playersQuantity) * 100))),


                new Placeholder("money_reward", "100"),
                new Placeholder("progress_bar", getProgressBar((int) currentPercentage)),
                new Placeholder("max_xp", String.valueOf(maxXP))));
    }

    public static List<Placeholder> getSkillsPlaceHolders(UUID uuid, SkillType skill, int level) {
        PlayerSkills playerSkills = HyperSkills.getInstance().getSkillManager().getPlayerSkills(uuid);
        Double xp = playerSkills.getXP(skill);
        Double maxXP = HyperSkills.getInstance().getRequirements().getLevelRequirement(skill, level);
        double currentPercentage = getCurrentPercentage(xp, maxXP);
        return new ArrayList<>(Arrays.asList(new Placeholder("current_percentage", String.valueOf(round(currentPercentage))),
                new Placeholder("next_level", Utils.toRoman(level + 1)),
                new Placeholder("current_xp", String.valueOf(xp)),
                new Placeholder("level", Utils.toRoman(level)),
                new Placeholder("displayname", HyperSkills.getInstance().getSkills().getAllSkills().get(skill).getName()),
                new Placeholder("progress_bar", getProgressBar((int) currentPercentage)),
                new Placeholder("max_xp", String.valueOf(maxXP))));
    }

    public static Double round(double value) {
        return Math.round(value * 10) / 10D;
    }


    public static double getCurrentPercentage(Double xp, Double maxXP) {
        if (xp > 0)
            return (xp * 100) / maxXP;
        return 0D;
    }

    public static int getPercentageQuantity(int percentage) {
        if (percentage > 0)
            return (percentage * 20) / 100;
        return 0;
    }

    public static boolean isInBlockedWorld(String worldName, SkillType skill) {
        for (String name : HyperSkills.getInstance().getSkills().getAllSkills().get(skill).getBlockedWorlds()) {
            if (name.equalsIgnoreCase(worldName))
                return true;
        }
        return false;
    }

    public static String translatedDegradeText(DamageIndicator damageIndicator, double damage) {
        if (!damageIndicator.isDegrade())
            return StringUtils.color(damageIndicator.getIndicator().replace("%damage%", roundToInt(damage)));
        return getDegradedText(damageIndicator, roundToInt(damage));
    }

    public static String roundToInt(double value) {
        return String.valueOf((int) value);
    }

    private static String getDegradedText(DamageIndicator damageIndicator, String damage) {
        String[] split = damage.split("");
        StringBuilder finalText = new StringBuilder();
        int index = 0;
        for (int i = 0; i < split.length; i++, index++) {
            if (index == COLOR.size()) index = 0;
            finalText.append(COLOR.get(index)).append(split[i]);
        }
        return StringUtils.color(damageIndicator.getIndicator().replace("%damage%", finalText.toString()));
    }

    public static int getRandom(int max) {
        return new Random().nextInt(max);
    }


    public static boolean isInBlockedRegion(Location location, SkillType skill) {
        if (HyperSkills.getInstance().getAddonsManager().getRegionPlugin() == null) return false;
        return HyperSkills.getInstance().getAddonsManager().getRegionPlugin().isInRegion(location, HyperSkills.getInstance().getSkills().getAllSkills().get(skill).getBlockedRegions());
    }

    public static String toRoman(int number) {
        if (number >= 0) {
            int l = map.floorKey(number);
            if (number == l)
                return map.get(number);
            return map.get(l) + toRoman(number - l);
        }
        return String.valueOf(number);
    }


    public static String getProgressBar(int percentage) {
        StringBuilder bar = new StringBuilder();
        int current = getPercentageQuantity(percentage);
        for (int i = 0; i < current; i++)
            bar.append("&a-");
        for (int i = 0; i < 20 - current; i++)
            bar.append("&f-");
        return bar.toString();
    }

    public static String getProgressBar(UUID uuid, SkillType skill) {
        PlayerSkills playerSkills = HyperSkills.getInstance().getSkillManager().getPlayerSkills(uuid);
        int level = playerSkills.getLevel(skill);
        Double xp = playerSkills.getXP(skill);
        Double maxXP = HyperSkills.getInstance().getRequirements().getLevelRequirement(skill, level);
        double currentPercentage = getCurrentPercentage(xp, maxXP);
        StringBuilder bar = new StringBuilder();
        int current = getPercentageQuantity((int) currentPercentage);
        for (int i = 0; i < current; i++)
            bar.append("&a-");
        for (int i = 0; i < 20 - current; i++)
            bar.append("&f-");
        return bar.toString();
    }

    public static boolean hasInventoryFull(Player player) {
        Inventory inv = player.getInventory();
        return inv.firstEmpty() == -1;
    }

    public static double getHealth(Player player) {
        double totalHealth = HyperSkills.getInstance().getApi().getTotalAbility(player.getUniqueId(), Ability.HEALTH);
        double perHeart = totalHealth / player.getMaxHealth();
        double health = player.getHealth();
        return Math.nextUp(Math.min(health * perHeart, totalHealth));
    }

    public static double getDamage(double defense, double health, double damage) {
        return damage / (defense * 0.009D + 1.0D + Math.max(0, health - 100) * 0.007D);
    }

    public static double getScale(double health) {
        return 20 + Math.max(1.5 + ((health - 100) * 0.021D), 0);
    }

    public static void sendActionBar(Player player, String actionBar) {
        if (HyperSkills.getInstance().getConfiguration().translatePAPIPlaceholders)
            ActionBar.sendActionBar(player, PAPIUtils.translatePAPIPlaceholders(player, actionBar));
        else
            ActionBar.sendActionBar(player, actionBar);
    }

    public static boolean hasEffectInHand(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("inHand");
    }


    public static void openGUIAsync(Player player, UltimateItem ultimateItem) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSkills.getInstance(), () -> player.openInventory(new ArmorEditGUI(ultimateItem).getInventory()), 3L);
    }

    public static void openGUIAttributeAsync(Player player, UltimateItem ultimateItem) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSkills.getInstance(), () -> player.openInventory(new AttributeEditGUI(ultimateItem).getInventory()), 3L);
    }


}
