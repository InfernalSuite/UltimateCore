package mc.ultimatecore.talismans.objects;

import lombok.Getter;
import lombok.Setter;
import mc.ultimatecore.talismans.HyperTalismans;

import java.util.UUID;

@Getter
@Setter
public class InventoryTalisman {
    private UUID uuid;
    private final String id;
    private Integer amount;
    private final boolean executable;
    private final TalismanType talismanType;

    public InventoryTalisman(UUID uuid, String id, Integer amount, boolean executable, TalismanType talismanType) {
        this.uuid = uuid;
        this.id = id;
        this.amount = amount;
        this.executable = executable;
        this.talismanType = talismanType;
        HyperTalismans.getInstance().getTalismanManager().register(uuid, this);
    }
}
