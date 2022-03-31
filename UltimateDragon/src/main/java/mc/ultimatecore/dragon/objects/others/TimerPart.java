package mc.ultimatecore.dragon.objects.others;

import com.cryptomorin.xseries.XSound;
import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.objects.DragonGame;
import mc.ultimatecore.dragon.objects.implementations.IHyperDragon;
import mc.ultimatecore.dragon.objects.title.TitleObject;
import mc.ultimatecore.dragon.objects.title.TitleType;
import mc.ultimatecore.dragon.utils.Placeholder;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.dragon.utils.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TimerPart {

    private final DragonGame dragonGame;
    private int time;
    private Integer taskID;

    public TimerPart(DragonGame hyperDragonEntity, int time){
        this.dragonGame = hyperDragonEntity;
        this.time = time;
    }

    public CompletableFuture<Void> startTimer() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Location spawn = dragonGame.getSpawn();
        if (spawn == null) return future;
        HyperDragons plugin = dragonGame.getPlugin();
        taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (time <= 0) {
                getNearby(spawn).forEach(player -> update(player, TitleType.SPAWNED));
                future.complete(null);
                Bukkit.getScheduler().cancelTask(taskID);
            }
            getNearby(spawn).forEach(player -> update(player, TitleType.SPAWNING));
            time--;
        }, 20, 20);
        return future;
    }


    private void playSound(Player player){
        player.playSound(player.getLocation(), XSound.ENTITY_ENDER_DRAGON_GROWL.parseSound(), 10.0F, 10.0F);
    }

    private void sendTitle(Player player, TitleType titleType){
        TitleObject titleObject = dragonGame.getPlugin().getMessages().getTitles().get(titleType);
        IHyperDragon dragon = dragonGame.getHyperDragon();
        String dragonName = dragon == null ? "" : dragonGame.getHyperDragon().getDisplayName();
        String title = StringUtils.processMultiplePlaceholders(titleObject.getTitle(), Arrays.asList(new Placeholder("dragon", dragonName), new Placeholder("time", String.valueOf(time))));
        String subTitle = StringUtils.processMultiplePlaceholders(titleObject.getSubtitle(), Arrays.asList(new Placeholder("dragon", dragonName), new Placeholder("time", String.valueOf(time))));
        TitleAPI.sendTitle(player, 0, 30, 0, title, subTitle);
    }

    private Collection<Player> getNearby(Location location){
        return location.getWorld().getNearbyEntities(location, 100, 100, 100).stream()
                .filter(en -> en instanceof Player)
                .map(entity -> (Player) entity)
                .collect(Collectors.toList());
    }

    private void update(Player player, TitleType titleType){
        sendTitle(player, titleType);
        playSound(player);
    }
}
