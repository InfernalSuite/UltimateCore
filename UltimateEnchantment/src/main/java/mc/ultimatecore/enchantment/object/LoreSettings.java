package mc.ultimatecore.enchantment.object;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LoreSettings {
    private final LoreAddition loreAddition;
    private final int line;

    public List<String> getToAddLore(List<String> itemLore, List<String> toAddLore){
        if(itemLore == null) return toAddLore;
        if(loreAddition == LoreAddition.BOTTOM && !itemLore.isEmpty()) {
            itemLore.addAll(toAddLore);
        }else if(loreAddition == LoreAddition.TOP && !itemLore.isEmpty()) {
            itemLore.addAll(0, toAddLore);
        }else if(loreAddition == LoreAddition.SPECIFIC_LINE && line < itemLore.size()) {
            itemLore.addAll(line, toAddLore);
        }else{
            return toAddLore;
        }
        return itemLore;
    }
}
