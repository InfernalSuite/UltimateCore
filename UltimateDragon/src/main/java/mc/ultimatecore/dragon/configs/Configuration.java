package mc.ultimatecore.dragon.configs;

import com.cryptomorin.xseries.XMaterial;
import mc.ultimatecore.dragon.objects.others.RandomDragon;
import mc.ultimatecore.dragon.utils.Item;
import mc.ultimatecore.dragon.utils.Utils;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;

import java.util.Arrays;


public class Configuration extends YAMLFile {
    public String prefix;
    public String schematic;
    public int maxDistance;
    public int sizeX;
    public int sizeY;
    public int sizeZ;
    public int spawnTime;
    public Item item = new Item(XMaterial.PLAYER_HEAD, 1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ==", 1, "&aEpic Ender Eye", Arrays.asList("&eLeft-Click at an altar to place.", "", "&lEPIC"));
    public boolean inmortalCrystals;
    public boolean debug;
    public RandomDragon randomDragon;

    public Configuration(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        //------------------------------------------------//
        prefix = getConfig().getString("prefix");
        schematic = String.valueOf(getConfig().getString("eventSettings.schematic.id")).replace(".schem", "").replace(".schematic", "");
        maxDistance = getConfig().getInt("eventSettings.maxDistance");
        sizeX = getConfig().getInt("eventSettings.schematic.size.X");
        sizeY = getConfig().getInt("eventSettings.schematic.size.Y");
        sizeZ = getConfig().getInt("eventSettings.schematic.size.Z");
        item = Utils.getItemFromConfig(getConfig(), "enderEyeItem");
        spawnTime = getConfig().getInt("eventSettings.spawnTime");
        inmortalCrystals = getConfig().getBoolean("eventSettings.inmortalCrystals");
        randomDragon = new RandomDragon(getConfig().getBoolean("eventSettings.dragon.useRandom"), getConfig().getString("eventSettings.dragon.name"), getConfig().getBoolean("eventSettings.dragon.isFromMythicMobs"), getConfig().getInt("eventSettings.dragon.mythicMobLevel"));

        debug = getConfig().getBoolean("DEBUG");
        //------------------------------------------------//
    }
}
