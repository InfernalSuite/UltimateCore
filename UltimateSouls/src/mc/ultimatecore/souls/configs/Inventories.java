package mc.ultimatecore.souls.configs;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;
import mc.ultimatecore.souls.Item;
import mc.ultimatecore.souls.objects.SoulParticle;
import mc.ultimatecore.souls.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

public class Inventories extends YAMLFile {
    
    //BUTTONS
    public Item nextPage = new Item(XMaterial.ARROW, 50, 1, "&eNext Page", new ArrayList<>());
    public Item previousPage = new Item(XMaterial.ARROW, 48, 1, "&ePrevious Page", new ArrayList<>());
    public Item closeButton = new Item(XMaterial.BARRIER, 49, 1, "&cClose Menu", new ArrayList<>());
    
    //SELECT SOUL GUI
    public String allSoulsGUITitle = "&8Select a Soul";
    public Item soulItem = new Item(XMaterial.PLAYER_HEAD, 1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjk2OTIzYWQyNDczMTAwMDdmNmFlNWQzMjZkODQ3YWQ1Mzg2NGNmMTZjMzU2NWExODFkYzhlNmIyMGJlMjM4NyJ9fX0=", 1, "&eSoul #%soul_id%", new ArrayList<>());
    public Item background = new Item(XMaterial.BLACK_STAINED_GLASS_PANE, 1, " ", new ArrayList<>());
    public String soulEditGUITitle = "&8Edit Soul:";
    
    //SELECT AN ATRIBUTTE GUI
    public Item soulLocation = new Item(XMaterial.BEACON, 4, 1, "&6&nSoul Location", Arrays.asList("", "&7World: &6%world%", "&7X: &6%x%", "&7Y: &6%y%", "&7Z: &6%z%", "", "&eClick to teleport!"));
    public Item soulEffect = new Item(XMaterial.SPAWNER, 21, 1, "&6&nSoul Effect", Arrays.asList("", "&eClick to Change Soul Effect!"));
    public Item soulDelete = new Item(XMaterial.REDSTONE_TORCH, 23, 1, "&6&nDelete", Arrays.asList("", "&eClick to delete Soul #%soul_id%"));
    public Item addCommand = new Item(XMaterial.MAP, 38, 1, "&6&nAdd Command Rewards", Arrays.asList("", "&eClick to add Commands as Reward!"));
    public Item addMoney = new Item(XMaterial.GOLD_NUGGET, 42, 1, "&6&nAdd Vault Money", Arrays.asList("", "&eClick to add money as Reward!"));
    
    //ADD MONEY GUI
    public String addMoneyGUITitle = "&8Add Money";
    public Item moneyGUIRemoveMoney = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, "&e-100$", Arrays.asList("", "&eClick to remove 100$!"));
    public Item moneyGUIChangeMoney = new Item(XMaterial.GOLD_BLOCK, 1, "&6&nCurrent Money: %money%", Arrays.asList("", "&eClick to Change this manually!"));
    public Item moneyGUIAddMoney = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&e+100$", Arrays.asList("", "&eClick to add 100$!"));
    
    //ADD COMMAND GUI
    public String commandGUITitle = "&8Add Command";
    public Item commandGUIcurrentCommand = new Item(XMaterial.PAPER, 1, "%command%", Arrays.asList("", "&eRight-Click to remove this command!"));
    public Item commandGUIaddCommand = new Item(XMaterial.LIME_DYE, 1, "&6&nAdd New Command", Arrays.asList("", "&eClick to add new command!"));
    
    //PARTICLE GUI
    public String particleGUITitle = "&8Add Effect";
    public Map<Item, SoulParticle> soulParticleGUI = new HashMap<Item, SoulParticle>() {{
        put(new Item(XMaterial.ENDER_EYE, 1, "&6&nEnder", Arrays.asList("", "&7Status: &a%status%")), SoulParticle.ENDER);
        put(new Item(XMaterial.DRAGON_EGG, 1, "&6&nDragon", Arrays.asList("", "&7Status: &a%status%")), SoulParticle.DRAGON);
        put(new Item(XMaterial.SPAWNER, 1, "&6&nFlames", Arrays.asList("", "&7Status: &a%status%")), SoulParticle.FLAMES);
        put(new Item(XMaterial.FIREWORK_ROCKET, 1, "&6&nFirework", Arrays.asList("", "&7Status: &a%status%")), SoulParticle.FIREWORK);
        put(new Item(XMaterial.NETHER_STAR, 1, "&6&nRainbow", Arrays.asList("", "&7Status: &a%status%")), SoulParticle.RAINBOW);
        put(new Item(XMaterial.REDSTONE, 1, "&6&nHearts", Arrays.asList("", "&7Status: &e%status%")), SoulParticle.HEARTS);
    }};
    
    //CONFIRM DELETE GUI
    public String confirmDeleteGUI = "&8Confirm Delete";
    public Item confirmDelete = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&aYes", Arrays.asList("", "&eClick to delete Soul #%soul_id% "));
    public Item cancelDelete = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, "&cNo", Arrays.asList("", "&eClick to cancel!"));


    /*
    PLAYER GUIS
     */
    
    public String tiaTitle;
    public int tiaSize;
    public Set<Integer> tiaSlots;
    public Item tiaDecoration;
    public Item tiaClose;
    public Item tiaClaim;
    
    public Inventories(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }
    
    
    @Override
    public void reload() {
        super.reload();
        loadDefaults();
    }
    
    private void loadDefaults() {
        YamlConfiguration cf = getConfig();
        tiaTitle = cf.getString("tiaGUI.title");
        tiaSize = cf.getInt("tiaGUI.size");
        tiaSlots = new HashSet<>(cf.getIntegerList("tiaGUI.decorationSlots"));
        tiaDecoration = Utils.getItemFromConfig(cf, "tiaGUI.items.decoration");
        tiaClose = Utils.getItemFromConfig(cf, "tiaGUI.items.closeButton");
        tiaClaim = Utils.getItemFromConfig(cf, "tiaGUI.items.claimItem");
    }
}
