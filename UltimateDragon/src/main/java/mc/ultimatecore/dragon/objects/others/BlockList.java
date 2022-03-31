package mc.ultimatecore.dragon.objects.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BlockList {
    private final List<Block> blocks;
}
