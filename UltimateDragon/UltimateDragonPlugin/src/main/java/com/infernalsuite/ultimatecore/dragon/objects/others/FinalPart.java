package com.infernalsuite.ultimatecore.dragon.objects.others;

import lombok.RequiredArgsConstructor;
import com.infernalsuite.ultimatecore.dragon.HyperDragons;
import com.infernalsuite.ultimatecore.dragon.objects.DragonPlayer;
import com.infernalsuite.ultimatecore.dragon.objects.EventPlayer;
import com.infernalsuite.ultimatecore.dragon.objects.implementations.IHyperDragon;
import com.infernalsuite.ultimatecore.dragon.objects.rewards.DragonReward;
import com.infernalsuite.ultimatecore.dragon.utils.StringUtils;
import com.infernalsuite.ultimatecore.dragon.utils.Utils;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class FinalPart {
    private final HyperDragons plugin;
    private final List<EventPlayer> eventPlayers;
    private final UUID last;

    public void sendFinishMessage(IHyperDragon hyperDragon) {
        eventPlayers.sort((o1, o2) -> (int) (o2.getDamage() - o1.getDamage()));
        EventPlayer top1 = eventPlayers.size() > 0 ? eventPlayers.get(0) : null;
        EventPlayer top2 = eventPlayers.size() > 1 ? eventPlayers.get(1) : null;
        EventPlayer top3 = eventPlayers.size() > 2 ? eventPlayers.get(2) : null;
        String noPlayer = plugin.getMessages().getMessage("noPlayer");
        String recordMessage = plugin.getMessages().getMessage("newRecordMessage");
        String lastDamageName = getLast().map(eventPlayer -> eventPlayer.getPlayer().getDisplayName()).orElse(noPlayer);
        int rank = 0;
        for(EventPlayer eventPlayer : eventPlayers){
            Optional<DragonPlayer> dragonPlayer = plugin.getDatabaseManager().getDragonPlayer(eventPlayer.getUuid());
            double oldRecord = dragonPlayer.map(DragonPlayer::getRecord).orElse(0.0);
            for(String line : plugin.getMessages().getFinishGameMessage()){
                String message = StringUtils.color(line
                        .replace("%top_1%", top1 == null ? noPlayer : top1.getName())
                        .replace("%top_2%", top2 == null ? noPlayer : top2.getName())
                        .replace("%top_3%", top3 == null ? noPlayer : top3.getName())
                        .replace("%damage_1%", top1 == null ? "0" : Utils.doubleToStr(top1.getDamage()))
                        .replace("%damage_2%", top2 == null ? "0" : Utils.doubleToStr(top2.getDamage()))
                        .replace("%damage_3%", top3 == null ? "0" : Utils.doubleToStr(top3.getDamage()))
                        .replace("%rank%", String.valueOf(rank))
                        .replace("%is_record%", eventPlayer.getDamage() > oldRecord ? recordMessage : "")
                        .replace("%damage%", Utils.doubleToStr(eventPlayer.getDamage()))
                        .replace("%xp%", Utils.doubleToStr(hyperDragon.getXp()))
                        .replace("%last_damager%", lastDamageName));
                if(eventPlayer.getPlayer() != null)
                    eventPlayer.getPlayer().sendMessage(message);
            }
            Optional<DragonReward> reward = getPlayerReward(eventPlayer);
            reward.ifPresent(dragonReward -> dragonReward.getCommands().forEach(command -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("%dragon_xp%", Utils.doubleToStr(hyperDragon.getXp()))
                    .replace("%player%", eventPlayer.getName()))));
            rank++;
        }
        eventPlayers.clear();
    }


    private Optional<DragonReward> getPlayerReward(EventPlayer eventPlayer){
        List<DragonReward> dragonRewards = new ArrayList<>(plugin.getRewards().getDragonRewards());
        dragonRewards.sort((o1, o2) -> o2.getDamageDone() - o1.getDamageDone());
        return dragonRewards.stream()
                .filter(dragonReward -> eventPlayer.getDamage() >= dragonReward.getDamageDone())
                .findFirst();
    }

    private Optional<EventPlayer> getLast(){
        return last != null ? eventPlayers.stream()
                .filter(eventPlayer -> eventPlayer.getUuid().equals(last))
                .findFirst() : Optional.empty();
    }
}
