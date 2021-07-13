package mc.ultimatecore.farm.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.ultimatecore.farm.objects.blocks.ChanceBlock;
import mc.ultimatecore.farm.objects.blocks.RegionBlock;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
public class ChanceBlocks {
    private final List<ChanceBlock> chanceBlocks;

    public Optional<ChanceBlock> getHighestBlock(){
        chanceBlocks.sort(Comparator.comparingInt(ChanceBlock::getChance));
        return chanceBlocks.stream().findFirst();
    }

    public ChanceBlock getRandomBlock() {
        if (chanceBlocks.size() == 0) return null;
        int random = new Random().nextInt(100);
        ChanceBlock chanceBlock = null;
        chanceBlocks.sort(Comparator.comparingInt(ChanceBlock::getChance));
        for (ChanceBlock block : chanceBlocks) {
            if (random < block.getChance()) {
                chanceBlock = block;
                break;
            }
        }
        if (chanceBlock == null)
            chanceBlock = chanceBlocks.get(0);
        return chanceBlock;
    }

}
