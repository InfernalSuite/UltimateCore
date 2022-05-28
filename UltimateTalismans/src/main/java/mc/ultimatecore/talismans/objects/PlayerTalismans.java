package mc.ultimatecore.talismans.objects;

import lombok.Getter;
import mc.ultimatecore.talismans.HyperTalismans;
import mc.ultimatecore.talismans.objects.implementations.*;
import mc.ultimatecore.talismans.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerTalismans {
    @Getter
    private final UUID uuid;
    private int emeraldTask;
    private int countTask;
    @Getter
    private Set<String> normalTalismans;
    @Getter
    private final Set<String> bagTalismans;
    private final HyperTalismans plugin = HyperTalismans.getInstance();

    public PlayerTalismans(Player player) {
        this.uuid = player.getUniqueId();
        this.normalTalismans = new HashSet<>();
        this.bagTalismans = new HashSet<>();
        startCountingMovement(player);
        startCountingTimers(player);
        startCounting(player);
    }

    private void startCountingTimers(Player player) {
        Map<String, Integer> timers = new HashMap<>();
        emeraldTask = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            if (!checkTimers(player, timers))
                Bukkit.getScheduler().cancelTask(countTask);
        }, 20, 20);
    }

    private void startCountingMovement(Player player) {
        countTask = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            if (!checkMovement(player))
                Bukkit.getScheduler().cancelTask(countTask);
        }, 0, 20);
    }

    private void startCounting(Player player) {
        countTask = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            if (!checkTalismans(player))
                Bukkit.getScheduler().cancelTask(countTask);
        }, 0, 20);
    }

    private boolean checkMovement(Player player){
        if(player == null || !player.isOnline()) return false;
        /*
        Checking all region Talismans and then make the execution
         */
        normalTalismans
                .stream()
                .map(talisman -> HyperTalismans.getInstance().getTalismans().getTalisman(talisman))
                .filter(talisman -> talisman.getTalismanType() == TalismanType.ABILITY_REGION || talisman.getTalismanType() == TalismanType.PERK_REGION)
                .forEach(inventoryTalisman -> checkMove(player, inventoryTalisman));
        return true;
    }

    private boolean checkTalismans(Player player){
        if(player == null || !player.isOnline()) return false;
        Set<String> oldTalismans = normalTalismans;
        normalTalismans = Utils.getInventoryTalismans(player);
        for(String name : normalTalismans) {
            if (!oldTalismans.contains(name)) {
                Talisman talisman = HyperTalismans.getInstance().getTalismans().getTalismans().get(name);
                if(talisman instanceof StatsTalisman && !(talisman instanceof RegionTalisman))
                    talisman.execute(player, 1);
            }
        }
        oldTalismans.forEach(inventoryTalisman -> {
            if(!normalTalismans.contains(inventoryTalisman)){
                TalismanImpl talisman = HyperTalismans.getInstance().getTalismans().getTalismans().get(inventoryTalisman);
                if(talisman instanceof StatsTalisman){
                    if(talisman instanceof RegionTalisman){
                        RegionTalisman regionTalisman = (RegionTalisman) talisman;
                        if(regionTalisman.getRegionPlayers().contains(uuid)){
                            regionTalisman.getRegionPlayers().remove(uuid);
                            talisman.stop(player, 1);
                        }
                    }else{
                        talisman.stop(player, 1);
                    }
                }
            }
        });
        return true;
    }


    private boolean checkTimers(Player player, Map<String, Integer> timers){
        if(player == null || !player.isOnline()) return false;
        /*
        Check Timer Talismans
         */
        normalTalismans
                .stream()
                .map(talisman -> plugin.getTalismans().getTalisman(talisman))
                .filter(talisman -> talisman.getTalismanType().equals(TalismanType.TIMER))
                .forEach(inventoryTalisman -> checkTimer(player, inventoryTalisman, timers));
        return true;
    }

    private void checkTimer(Player player, Talisman talisman, Map<String, Integer> timers){
        TimerTalisman timerTalisman = (TimerTalisman) talisman;
        String key = talisman.getName();
        if(!timers.containsKey(key)) timers.put(key, 0);
        int time = timers.get(key);
        if(time >= timerTalisman.getSeconds()){
            timerTalisman.execute(player, 1);
            timers.put(key, 0);
        }else{
            timers.put(key, time+1);
        }
    }

    private void checkMove(Player player, Talisman talisman){
        RegionTalisman regionTalisman = (RegionTalisman) talisman;
        UUID uuid = player.getUniqueId();
        if(regionTalisman.getRegionPlayers().contains(uuid)) {
            if (!isInRegion(player, regionTalisman)) {
                talisman.stop(player, 1);
                regionTalisman.getRegionPlayers().remove(uuid);
            }
        }else if(isInRegion(player, regionTalisman)) {
            talisman.execute(player, 1);
            regionTalisman.getRegionPlayers().add(uuid);
        }
    }

    private boolean isInRegion(Player player, RegionTalisman talisman){
        if(HyperTalismans.getInstance().getAddonsManager().getRegionPlugin() != null) {
            return HyperTalismans.getInstance().getAddonsManager().getRegionPlugin().isInRegion(player.getLocation(), talisman.getRegions());
        }else{
            return false;
        }
    }

    public Optional<String> hasTalisman(String name){
        return normalTalismans.stream().filter(talisman -> talisman.equals(name)).findFirst();
    }

    public Optional<String> hasTalisman(TalismanType talismanType){
        return normalTalismans.stream()
                .map(talisman -> plugin.getTalismans().getTalisman(talisman))
                .filter(talisman -> talisman.getTalismanType().equals(talismanType))
                .map(Talisman::getName)
                .findFirst();
    }


    public Optional<String> hasTalisman(ImmunityType immunityType){
        return normalTalismans.stream()
                .map(talisman -> plugin.getTalismans().getTalisman(talisman))
                .filter(talisman -> talisman.getTalismanType().equals(TalismanType.IMMUNITY))
                .filter(talisman -> ((ImmunityTalisman) talisman).getImmunities().containsKey(immunityType))
                .map(Talisman::getName)
                .findFirst();
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(emeraldTask);
        Bukkit.getScheduler().cancelTask(countTask);
    }



}
