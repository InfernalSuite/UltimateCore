package mc.ultimatecore.runes.enums;

import com.cryptomorin.xseries.XMaterial;

public enum RuneEffect {
    GOLD("REDSTONE", "COLOURED_DUST"),
    SMOKE("SMOKE_LARGE", "PARTICLE_SMOKE"),
    MAGICAL("SPELL_WITCH", "WITCH_MAGIC"),
    LAVA("DRIP_LAVA", "LAVADRIP"),
    GEM("VILLAGER_HAPPY", "HAPPY_VILLAGER"),
    BLOOD("STEP_SOUND", "COLOURED_DUST"),
    LIGHTNING("", ""),
    HEARTS("HEART", "HEART"),
    MUSIC("NOTE", "NOTE");

    private final String v1_14;
    private final String legacy;

    RuneEffect(String v1_14, String legacy){
        this.v1_14 = v1_14;
        this.legacy = legacy;
    }

    public String getParticle(){
        if(XMaterial.isNewVersion())
            return v1_14;
        else
            return legacy;
    }

}
