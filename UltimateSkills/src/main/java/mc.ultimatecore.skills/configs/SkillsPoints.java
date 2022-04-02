package mc.ultimatecore.skills.configs;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.skills.HyperSkills;
import mc.ultimatecore.skills.objects.SkillType;
import mc.ultimatecore.skills.objects.xp.BlockXP;
import mc.ultimatecore.skills.objects.xp.MobXP;
import mc.ultimatecore.skills.objects.xp.SkillPoint;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class SkillsPoints extends YAMLFile {

    public Double fishingXP;
    public Double alchemyXP;
    public Double enchantingXP;
    public Double runeCraftingXP;
    public Double tamingXP;
    public Double farming_Cut_Wool;
    public Double carpentryXP;

    public Map<String, BlockXP> skillBlocksXP;

    public Map<String, MobXP> skillMobsXP;

    public Map<String, SkillPoint> skillEpicMobsXP;

    public Map<String, SkillPoint> fishingCaughtXP;

    public SkillsPoints(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        skillBlocksXP = new HashMap<>();
        skillMobsXP = new HashMap<>();
        skillEpicMobsXP = new HashMap<>();
        fishingCaughtXP = new HashMap<>();
        if(HyperSkills.getInstance().getConfiguration().useDefaultSkillsXP)
            loadDefaultSkillsPoints();
        fishingXP = getConfig().getDouble("fishingXP");
        alchemyXP = getConfig().getDouble("alchemyXP");
        enchantingXP = getConfig().getDouble("enchantingXP");
        runeCraftingXP = getConfig().getDouble("runeCraftingXP");
        tamingXP = getConfig().getDouble("tamingXP");
        farming_Cut_Wool = getConfig().getDouble("farming_Cut_Wool");
        carpentryXP = getConfig().getDouble("carpentryXP");
        for(String key : getConfig().getConfigurationSection("xpManager").getKeys(false))
            if(key.contains("blocks")) {
                for (String id : getConfig().getConfigurationSection("xpManager.blocks").getKeys(false)){
                    SkillType skillType = SkillType.valueOf(getConfig().getString("xpManager.blocks." + id + ".skillType"));
                    Double xp = getConfig().getDouble("xpManager.blocks." + id +".xp");
                    String material = getConfig().getString("xpManager.blocks." + id + ".material");
                    byte data;

                    var bytePath = "xpManager.blocks." + id +".materialData";

                    // Take byte or use material byte default
                    if(getConfig().contains(bytePath)) {
                        data = Byte.parseByte(Objects.requireNonNull(getConfig().getString(bytePath)));
                    } else {
                        data = XMaterial.matchXMaterial(Objects.requireNonNull(material)).map(XMaterial::getData).orElse((byte) 0);
                    }
                    BlockXP blockXP = new BlockXP(id, skillType, xp, data);
                    if(skillBlocksXP == null) skillBlocksXP = new HashMap<>();
                    skillBlocksXP.put(material, blockXP);
                }
            }else if(key.contains("mobs")){
                for (String id : getConfig().getConfigurationSection("xpManager.mobs").getKeys(false)){
                    SkillType skillType = SkillType.valueOf(getConfig().getString("xpManager.mobs." + id + ".skillType"));
                    Double xp = getConfig().getDouble("xpManager.mobs." + id +".xp");
                    String mobType = getConfig().getString("xpManager.mobs." + id + ".mobName");
                    int data = getConfig().contains("xpManager.mobs." + id + ".data") ? getConfig().getInt("xpManager.mobs." + id + ".data") : 0;
                    if(skillMobsXP == null) skillMobsXP = new HashMap<>();
                    skillMobsXP.put(mobType, new MobXP(id, skillType, xp, data));
                }
            }else if(key.contains("mythicMobs")){
                for (String id : getConfig().getConfigurationSection("xpManager.mythicMobs").getKeys(false)){
                    SkillType skillType = SkillType.valueOf(getConfig().getString("xpManager.mythicMobs." + id + ".skillType"));
                    Double xp = getConfig().getDouble("xpManager.mythicMobs." + id +".xp");
                    String mythicMobID = getConfig().getString("xpManager.mythicMobs." + id + ".id");
                    if(skillEpicMobsXP == null) skillEpicMobsXP = new HashMap<>();
                    skillEpicMobsXP.put(mythicMobID, new SkillPoint(mythicMobID, skillType, xp));
                }
            }else if(key.contains("caughtEntities")){
                for (String id : getConfig().getConfigurationSection("xpManager.caughtEntities").getKeys(false)){
                    SkillType skillType = SkillType.valueOf(getConfig().getString("xpManager.caughtEntities." + id + ".skillType"));
                    Double xp = getConfig().getDouble("xpManager.caughtEntities." + id +".xp");
                    String caughtID = getConfig().getString("xpManager.caughtEntities." + id + ".id");
                    if(fishingCaughtXP == null) fishingCaughtXP = new HashMap<>();
                    fishingCaughtXP.put(caughtID, new SkillPoint(caughtID, skillType, xp));
                }
            }



        //------------------------------------------------//
    }

    private void loadDefaultSkillsPoints(){
        skillBlocksXP = XMaterial.getVersion() < 14 ? new HashMap<String, BlockXP>(){{
            put("CROPS", new BlockXP("101", SkillType.Farming, 2D, (byte) 7));
            put("CARROT", new BlockXP("102", SkillType.Farming, 2D, (byte) 7));
            put("POTATO", new BlockXP("103", SkillType.Farming, 2D, (byte) 7));
            put("PUMPKIN", new BlockXP("104", SkillType.Farming, 2D, (byte) 0));
            put("MELON", new BlockXP("105", SkillType.Farming, 2D, (byte) 0));
            put("SUGAR_CANE", new BlockXP("106", SkillType.Farming, 2D, (byte) 0));
            put("NETHER_WARTS", new BlockXP("107", SkillType.Farming, 2D, (byte) 3));
                        //MINING
            put("IRON_ORE", new BlockXP("108", SkillType.Mining, 2D, (byte) 0));
            put("GOLD_ORE", new BlockXP("109", SkillType.Mining, 2D, (byte) 0));
            put("DIAMOND_ORE", new BlockXP("1010", SkillType.Mining, 2D, (byte) 0));
            put("REDSTONE_ORE", new BlockXP("1011", SkillType.Mining, 2D, (byte) 0));
            put("EMERALD_ORE", new BlockXP("1012", SkillType.Mining, 2D, (byte) 0));
            put("OBSIDIAN", new BlockXP("1013", SkillType.Mining, 2D, (byte) 0));
            put("COAL_ORE", new BlockXP("1014", SkillType.Mining, 2D, (byte) 0));
            put("COBBLESTONE", new BlockXP("1015", SkillType.Mining, 2D, (byte) 0));
            put("STONE", new BlockXP("1016", SkillType.Mining, 2D, (byte) 0));
            put("LOG", new BlockXP("1017", SkillType.Foraging, 2D, (byte) -1));
            put("LOG_2", new BlockXP("1018", SkillType.Foraging, 2D, (byte) -1));

            }} : new HashMap<String, BlockXP>(){{
                put("WHEAT", new BlockXP("101", SkillType.Farming, 2D, (byte) 7));
                put("CARROTS", new BlockXP("102", SkillType.Farming, 2D, (byte) 7));
                put("POTATOES", new BlockXP("103", SkillType.Farming, 2D, (byte) 7));
                put("PUMPKIN", new BlockXP("104", SkillType.Farming, 2D, (byte) 0));
                put("MELON", new BlockXP("105", SkillType.Farming, 2D, (byte) 0));
                put("SUGAR_CANE", new BlockXP("106", SkillType.Farming, 2D, (byte) 0));
                put("NETHER_WART", new BlockXP("107", SkillType.Farming, 2D, (byte) 3));

                //MINING
                put("IRON_ORE", new BlockXP("108", SkillType.Mining, 2D, (byte) 0));
                put("GOLD_ORE", new BlockXP("109", SkillType.Mining, 2D, (byte) 0));
                put("DIAMOND_ORE", new BlockXP("1001", SkillType.Mining, 2D, (byte) 0));
                put("REDSTONE_ORE", new BlockXP("1002", SkillType.Mining, 2D, (byte) 0));
                put("EMERALD_ORE", new BlockXP("1003", SkillType.Mining, 2D, (byte) 0));
                put("OBSIDIAN", new BlockXP("1004", SkillType.Mining, 2D, (byte) 0));
                put("COAL_ORE", new BlockXP("1005", SkillType.Mining, 2D, (byte) 0));
                put("COBBLESTONE", new BlockXP("1006", SkillType.Mining, 2D, (byte) 0));
                put("STONE", new BlockXP("1007", SkillType.Mining, 2D, (byte) 0));

                //FORAGING
                put("OAK_LOG", new BlockXP("1008", SkillType.Foraging, 2D, (byte) -1));
                put("SPRUCE_LOG", new BlockXP("1009", SkillType.Foraging, 2D, (byte) -1));
                put("BIRCH_LOG", new BlockXP("1010", SkillType.Foraging, 2D, (byte) -1));
                put("JUNGLE_LOG", new BlockXP("1011", SkillType.Foraging, 2D, (byte) -1));
                put("ACACIA_LOG", new BlockXP("1012", SkillType.Foraging, 2D, (byte) -1));
                put("DARK_OAK_LOG", new BlockXP("1314", SkillType.Foraging, 2D, (byte) -1));

            }};
        skillMobsXP = new HashMap<String, MobXP>(){{
            put("COW", new MobXP("1001", SkillType.Combat, 2D, 0));
            put("PIG", new MobXP("1002", SkillType.Combat, 2D, 0));
            put("CHICKEN", new MobXP("1003", SkillType.Combat, 2D, 0));
            put("SHEEP", new MobXP("1004", SkillType.Combat, 2D, 0));
            put("ZOMBIE", new MobXP("1005", SkillType.Combat, 2D, 0));
            put("ENDERMAN", new MobXP("1006", SkillType.Combat, 2D, 0));
            put("PIGMAN", new MobXP("1007", SkillType.Combat, 2D, 0));
            put("WITCH", new MobXP("1008", SkillType.Combat, 2D, 0));
            put("CREEPER", new MobXP("1009", SkillType.Combat, 2D, 0));
            put("BLAZE", new MobXP("1000", SkillType.Combat, 2D, 0));
            put("SPIDER", new MobXP("1011", SkillType.Combat, 2D, 0));
            put("CAVE_SPIDER", new MobXP("1012", SkillType.Combat, 2D, 0));
            put("MAGMA_CUBE", new MobXP("1013", SkillType.Combat, 2D, 0));
            put("GHAST", new MobXP("1014", SkillType.Combat, 2D, 0));
            put("SLIME", new MobXP("1015", SkillType.Combat, 2D, 0));
            put("WOLF", new MobXP("1016", SkillType.Combat, 2D, 0));
            }};
    }




}
