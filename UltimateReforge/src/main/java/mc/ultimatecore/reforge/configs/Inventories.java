package mc.ultimatecore.reforge.configs;

import lombok.Getter;
import mc.ultimatecore.reforge.HyperReforge;
import mc.ultimatecore.reforge.Item;
import mc.ultimatecore.reforge.utils.Utils;

import java.util.List;

@Getter
public class Inventories extends YAMLFile{

    //ANVIL
    private int reforgeMenuSize;
    private String reforgeMenuTitle;

    private Item emptyAnvil;
    private Item toReforgeAnvil;

    private Item successItem;
    private Item emptyItem;

    private Item errorItem;

    List<Integer> successSlots;
    //BUTTONS
    private Item background;
    private Item closeButton;
    private boolean backButtons;

    public Inventories(HyperReforge hyperSkills, String name) {
        super(hyperSkills, name);
    }

    @Override
    public void enable(){
        super.enable();
        loadDefaults();
    }

    @Override
    public void reload(){
        super.reload();
        loadDefaults();
    }

    private void loadDefaults(){
        successSlots = getConfig().get().getIntegerList("reforgeGUI.successSlots");

        //RUNETABLE
        reforgeMenuTitle = getConfig().get().getString("reforgeGUI.title");
        reforgeMenuSize = getConfig().get().getInt("reforgeGUI.size");
        emptyAnvil = Utils.getItemFromConfig(getConfig().get(), "reforgeGUI.items.emptyAnvil");
        toReforgeAnvil = Utils.getItemFromConfig(getConfig().get(), "reforgeGUI.items.toReforgeAnvil");
        successItem = Utils.getItemFromConfig(getConfig().get(), "reforgeGUI.items.successItem");
        errorItem = Utils.getItemFromConfig(getConfig().get(), "reforgeGUI.items.errorItem");
        emptyItem = Utils.getItemFromConfig(getConfig().get(), "reforgeGUI.items.emptyItem");

        //BUTTONS
        background = Utils.getItemFromConfig(getConfig().get(), "buttons.background");
        closeButton = Utils.getItemFromConfig(getConfig().get(), "buttons.closeButton");
        backButtons = getConfig().get().getBoolean("buttons.backButtons");

    }
}
