package mc.ultimatecore.enchantment.enums;

import mc.ultimatecore.enchantment.EnchantmentsPlugin;


public enum EnchantState {
    READY_TO_ENCHANT(null),
    NO_ENCHANTS_AVAILABLE(EnchantmentsPlugin.getInstance().getMessages().getMessage("placeholderCannot")),
    REACH_STACK_SIZE(EnchantmentsPlugin.getInstance().getMessages().getMessage("placeholderStackError"));

    public final String error;

    EnchantState(String error){
        this.error = error;
    }
}
