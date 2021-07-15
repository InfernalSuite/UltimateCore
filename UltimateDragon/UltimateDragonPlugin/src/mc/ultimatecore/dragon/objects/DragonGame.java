package mc.ultimatecore.dragon.objects;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.dragon.HyperDragons;
import mc.ultimatecore.dragon.implementations.SchematicImpl;
import mc.ultimatecore.dragon.objects.event.SwitchableEvent;
import mc.ultimatecore.dragon.objects.implementations.IDragonEvent;
import mc.ultimatecore.dragon.objects.implementations.IHyperDragon;
import mc.ultimatecore.dragon.objects.implementations.UCEnderDragon;
import mc.ultimatecore.dragon.objects.others.BossBarObj;
import mc.ultimatecore.dragon.objects.others.FinalPart;
import mc.ultimatecore.dragon.objects.others.TimerPart;
import mc.ultimatecore.dragon.objects.structures.DragonAltar;
import mc.ultimatecore.dragon.objects.structures.DragonStructure;
import mc.ultimatecore.dragon.utils.StringUtils;
import mc.ultimatecore.dragon.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class DragonGame {
    private IHyperDragon hyperDragon;
    private UCEnderDragon enderDragon;
    private Location spawn;
    private final List<EventPlayer> eventPlayers = new ArrayList<>();
    private final List<Entity> crystals = new ArrayList<>();
    private boolean active = false;
    private final HyperDragons plugin = HyperDragons.getInstance();

    private Integer moveDragonTask;
    private Integer eventTask;

    private SwitchableEvent dragonEvent;

    private ArmorStand stand;
    private BossBarObj bossBar;
    private UUID last;

    public void init(){
        DragonStructure dragonStructure = plugin.getDragonManager().getDragonStructure();
        if(spawn == null) spawn = dragonStructure.getSpawnLocation();
        hyperDragon = plugin.getConfiguration().randomDragon.getDragon();
        if(hyperDragon == null){
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&e[HyperDragon] &cInvalid dragon, check dragon to spawn!"));
            return;
        }
        startEvent(spawn);
        this.active = true;
        this.bossBar = new BossBarObj(this);
    }

    public void finish(){
        if(enderDragon != null) {
            enderDragon.setPhase(EnderDragon.Phase.HOVER);
            enderDragon.setPhase(EnderDragon.Phase.DYING);
        }
        cancelTask();
        crystals.forEach(Entity::remove);
        plugin.getDragonManager().setPlayMode();
        new FinalPart(plugin, eventPlayers, last).sendFinishMessage(hyperDragon);
        active = false;
    }

    public void managePlayerDamage(Player player, Double damage){
        Optional<EventPlayer> eventPlayer = eventPlayers.stream()
                .filter(evPlayer -> evPlayer.getUuid().equals(player.getUniqueId()))
                .findFirst();
        if(eventPlayer.isPresent())
            eventPlayer.get().setDamage(eventPlayer.get().getDamage() + damage);
        else
            eventPlayers.add(new EventPlayer(player.getUniqueId(), damage));
    }

    public void removePlayersDamage(Double damage){
        eventPlayers.forEach(eventPlayer -> eventPlayer.setDamage(eventPlayer.getDamage() - damage));
    }

    public void sendMessage(String msg){
        if(msg.equals("")) return;
        eventPlayers.forEach(player -> player.getPlayer().sendMessage(StringUtils.color(msg.replace("%prefix%", plugin.getConfiguration().prefix))));
    }

    private void switchEvents(){
        dragonEvent = new SwitchableEvent(plugin.getDragonEvents().getEvents(hyperDragon.getId()));
        eventTask = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
            if (dragonEvent.getCurrentEvent() == null || !dragonEvent.getCurrentEvent().isActive()) {
                IDragonEvent nextEvent = dragonEvent.getNext();
                if(nextEvent != null) {
                    nextEvent.setEnderDragon(enderDragon);
                    nextEvent.start();
                    sendMessage(Utils.getStartMessage(nextEvent));
                }
            }
        }, 0, 20);
    }


    private void moveDragon(){
        int maxDistance = plugin.getConfiguration().maxDistance;
        BukkitScheduler scheduler = Bukkit.getScheduler();
        moveDragonTask = scheduler.scheduleAsyncRepeatingTask(plugin, () -> {
            if(!enderDragon.stillAlive()) return;
            double distance = enderDragon.getLocation().distance(spawn);
            if(distance > maxDistance) enderDragon.teleport(spawn);
        }, 0, 20);
    }



    private void makeBlockExplosion() {
        DragonStructure dragonStructure = plugin.getDragonManager().getDragonStructure();
        World w = spawn.getWorld();
        enderDragon = hyperDragon.getEnderDragon(spawn);
        dragonStructure.getCrystals().forEach(l -> crystals.add(l.getWorld().spawn(l.clone().add(0.0D, 1.0D, 0.0D), EnderCrystal.class)));
        enderDragon.getNearby(100).forEach(entity -> eventPlayers.add(new EventPlayer(entity.getUniqueId(), 0)));
        final List<FallingBlock> falling = new ArrayList<>();
        Utils.getCuboid(spawn, plugin).thenAccept(blocks -> {
            List<Block> newBlocks = new ArrayList<>(blocks.getBlocks());
            Bukkit.getScheduler().runTask(HyperDragons.getInstance(), () -> {
                for (Block block : newBlocks) {
                    Material m = block.getType();
                    byte data = block.getData();
                    block.setType(Material.AIR);
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        FallingBlock fb = w.spawnFallingBlock(block.getLocation(), m, data);
                        fb.setVelocity(new Vector(random(), 0.95D, random()));
                        fb.setDropItem(false);
                        falling.add(fb);
                    }
                }
            });
        }).thenRun(() -> Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                falling.forEach(this::checkFallingBlock);
                falling.clear();
                dragonStructure.getAltars().forEach(DragonAltar::clearEye);
            }, 60)).thenRun(this::initTasks);
    }

    private void initTasks(){
        moveDragon();
        switchEvents();
    }

    private void spawnStand(){
        stand = spawn.getWorld().spawn(spawn, ArmorStand.class);
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setInvulnerable(true);
    }

    private void cancelTask(){
        BukkitScheduler scheduler = Bukkit.getScheduler();
        if(moveDragonTask != null) scheduler.cancelTask(moveDragonTask);
        if(eventTask != null) scheduler.cancelTask(eventTask);
        if(dragonEvent.getCurrentEvent() != null) dragonEvent.getCurrentEvent().end();
        if(bossBar != null) bossBar.stop();
    }

    private void checkFallingBlock(FallingBlock fallingBlock){
        if (fallingBlock.isOnGround()) fallingBlock.getLocation().getBlock().setType(Material.AIR);
        if (!fallingBlock.isDead()) fallingBlock.remove();
    }


    protected double random() {
        return -0.5D + ThreadLocalRandom.current().nextDouble();
    }

    private void startEvent(Location location){
        Optional<SchematicImpl> worldEditSchematic = HyperDragons.getInstance().getSchematicManager().getSchematic(plugin.getConfiguration().schematic);
        if(!worldEditSchematic.isPresent()) {
            Bukkit.getConsoleSender().sendMessage(StringUtils.color("&c[HyperDragon] Schematic "+plugin.getConfiguration().schematic+" wasn't found!"));
            startTimer();
            return;
        }
        worldEditSchematic.get().pasteSchematic(location).thenRun(this::startTimer);
    }


    public void startTimer() {
        spawnStand();
        new TimerPart(this, plugin.getConfiguration().spawnTime).startTimer().thenRun(this::makeBlockExplosion);
    }
}
