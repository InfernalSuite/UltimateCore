package mc.ultimatecore.dragon.configs;

import mc.ultimatecore.dragon.objects.event.DragonBallEvent;
import mc.ultimatecore.dragon.objects.event.FireBallEvent;
import mc.ultimatecore.dragon.objects.event.GuardianEvent;
import mc.ultimatecore.dragon.objects.event.LightningEvent;
import mc.ultimatecore.dragon.objects.guardian.RandomGuardian;
import mc.ultimatecore.dragon.objects.implementations.IDragonEvent;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.dragon.utils.Utils;
import mc.ultimatecore.helper.UltimatePlugin;
import mc.ultimatecore.helper.files.YAMLFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class DragonEvents extends YAMLFile {
    private Map<String, Map<Integer, IDragonEvent>> events;

    public DragonEvents(UltimatePlugin plugin, String name, boolean defaults, boolean save) {
        super(plugin, name, defaults, save);
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        events = new HashMap<>();
        //------------------------------------------------//
        ConfigurationSection section = getConfig().getConfigurationSection("events");
        if(section == null) return;
        for(String dragon : section.getKeys(false)){
            ConfigurationSection newSection = getConfig().getConfigurationSection("events."+dragon);
            if(newSection == null) continue;
            for(String key : newSection.getKeys(false)){
                try {
                    String type = getConfig().getString("events."+dragon+"."+key+".general.type");
                    if(type.equals("GUARDIANS"))
                        addGuardian(dragon, key);
                    else if(type.equals("DRAGON_FIREBALLS"))
                        addDragonBall(dragon, key);
                    else if(type.equals("NORMAL_FIREBALLS"))
                        addNormalBall(dragon, key);
                    else if(type.equals("LIGHTNING"))
                        addLightning(dragon, key);
                }catch (Exception e){
                    Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperDragon] &cError Adding event " + key + " for Dragon " + dragon+" | Invalid Type"));
                }
            }
        }
        //------------------------------------------------//
    }

    private void addDragonBall(String dragon, String key){
        Map<Integer, IDragonEvent> events = this.events.getOrDefault(dragon, new HashMap<>());
        int position = events.size();
        try {
            int duration = getConfig().getInt("events."+dragon+"."+key+".general.duration");
            int repeat = getConfig().getInt("events."+dragon+"."+key+".general.repeatEvery");
            double speed = getConfig().getDouble("events."+dragon+"."+key+".general.dragonSpeed");
            boolean freeze = getConfig().getBoolean("events."+dragon+"."+key+".general.freezeDragon");
            int amount = getConfig().getInt("events."+dragon+"."+key+".settings.ballsPerSecond");
            boolean particle = getConfig().getBoolean("events."+dragon+"."+key+".settings.particle", true);
            double damage = getConfig().getDouble("events."+dragon+"."+key+".settings.damage", 50);
            DragonBallEvent dragonBallEvent = new DragonBallEvent(duration, repeat, speed, freeze, amount, particle, damage);
            events.put(position, dragonBallEvent);
            this.events.put(dragon, events);
        }catch (Exception e){
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperDragon] &cError Adding event " + key + " for Dragon " + dragon));
        }
    }

    private void addNormalBall(String dragon, String key){
        Map<Integer, IDragonEvent> events = this.events.getOrDefault(dragon, new HashMap<>());
        int position = events.size();
        try {
            int duration = getConfig().getInt("events."+dragon+"."+key+".general.duration");
            int repeat = getConfig().getInt("events."+dragon+"."+key+".general.repeatEvery");
            double speed = getConfig().getDouble("events."+dragon+"."+key+".general.dragonSpeed");
            boolean freeze = getConfig().getBoolean("events."+dragon+"."+key+".general.freezeDragon");
            int amount = getConfig().getInt("events."+dragon+"."+key+".settings.ballsPerSecond");
            boolean particle = getConfig().getBoolean("events."+dragon+"."+key+".settings.particle");
            double damage = getConfig().getDouble("events."+dragon+"."+key+".settings.damage");
            FireBallEvent dragonBallEvent = new FireBallEvent(duration, repeat, speed, freeze, amount, particle, damage);
            events.put(position, dragonBallEvent);
            this.events.put(dragon, events);
        }catch (Exception e){
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperDragon] &cError Adding event " + key + " for Dragon " + dragon));
        }
    }

    private void addLightning(String dragon, String key){
        Map<Integer, IDragonEvent> events = this.events.getOrDefault(dragon, new HashMap<>());
        int position = events.size();
        try {
            int duration = getConfig().getInt("events."+dragon+"."+key+".general.duration");
            int repeat = getConfig().getInt("events."+dragon+"."+key+".general.repeatEvery");
            double speed = getConfig().getDouble("events."+dragon+"."+key+".general.dragonSpeed");
            boolean freeze = getConfig().getBoolean("events."+dragon+"."+key+".general.freezeDragon");
            double damage = getConfig().getDouble("events."+dragon+"."+key+".settings.damage");
            LightningEvent dragonBallEvent = new LightningEvent(duration, repeat, speed, freeze, damage);
            events.put(position, dragonBallEvent);
            this.events.put(dragon, events);
        }catch (Exception e){
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperDragon] &cError Adding event " + key + " for Dragon " + dragon));
        }
    }


    private void addGuardian(String dragon, String key){
        Map<Integer, IDragonEvent> events = this.events.getOrDefault(dragon, new HashMap<>());
        int position = events.size();
        try {
            int duration = getConfig().getInt("events."+dragon+"."+key+".general.duration");
            int repeat = getConfig().getInt("events."+dragon+"."+key+".general.repeatEvery");
            double speed = getConfig().getDouble("events."+dragon+"."+key+".general.dragonSpeed");
            boolean freeze = getConfig().getBoolean("events."+dragon+"."+key+".general.freezeDragon");
            int amount = getConfig().getInt("events."+dragon+"."+key+".settings.amount");
            RandomGuardian randomGuardian = Utils.getRandomGuardian(getConfig().getStringList("events."+dragon+"."+key+".settings.guardians"));
            GuardianEvent guardianEvent = new GuardianEvent(duration, repeat, speed, freeze, amount, randomGuardian);
            events.put(position, guardianEvent);
            this.events.put(dragon, events);
        }catch (Exception e){
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperDragon] &cError Adding event " + key + " for Dragon " + dragon));
        }
    }

    public Map<Integer, IDragonEvent> getEvents(String dragon){
        return events.getOrDefault(dragon, new HashMap<>());
    }
}
