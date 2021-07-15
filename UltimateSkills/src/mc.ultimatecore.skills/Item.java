package mc.ultimatecore.skills;

import com.cryptomorin.xseries.XMaterial;
import lombok.NoArgsConstructor;
import mc.ultimatecore.skills.objects.SkillType;

import java.util.List;

/**
 * Class which represents an item in an inventory.
 * Uses {@link XMaterial} for multi-version support.
 */
@NoArgsConstructor
public class Item {

    public XMaterial material;
    public int amount;
    public String title;
    public String headData;
    public String headOwner;
    public List<String> lore;
    public int slot;
    public SkillType skillType;
    public boolean isGlowing;
    public String command;

    public Item(Item item) {
        this.material = item.material;
        this.amount = item.amount;
        this.lore = item.lore;
        this.title = item.title;
        this.slot = item.slot;
    }

    public Item(XMaterial material, int amount, String title, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
    }

    public Item(XMaterial material, int slot, int amount, String title, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.slot = slot;
    }

    public Item(XMaterial material, int slot, int amount, String title, List<String> lore, SkillType category) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.slot = slot;
        this.skillType = category;
    }

    public Item(XMaterial material, int slot, int amount, String title, List<String> lore, SkillType skillType, boolean isGlowing) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.slot = slot;
        this.skillType = skillType;
        this.isGlowing = isGlowing;
    }

    public Item(XMaterial material, int slot, int amount, String title, List<String> lore, SkillType skillType, String command) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.slot = slot;
        this.skillType = skillType;
        this.command = command;
    }

    public Item(XMaterial material, int slot, int amount, String title, List<String> lore, SkillType skillType, boolean isGlowing, String command) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.slot = slot;
        this.skillType = skillType;
        this.isGlowing = isGlowing;
        this.command = command;
    }

    public Item(XMaterial material, int slot, String headData, int amount, String title, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.slot = slot;
        this.headData = headData;
    }

    public Item(XMaterial material, int slot, int amount, String title, String headOwner, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.headOwner = headOwner;
        this.slot = slot;
    }

    public Item(XMaterial material, int amount, String title, String headOwner, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.title = title;
        this.headOwner = headOwner;
    }

}