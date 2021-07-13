package mc.ultimatecore.souls.objects;

public enum SoulParticle {
    
    ENDER("ENDER_SIGNAL"),
    DRAGON("DRAGON_BREATH"),
    FLAMES("MOBSPAWNER_FLAMES"),
    FIREWORK("FIREWORKS_SPARK"),
    RAINBOW("SPELL_MOB"),
    HEARTS("HEART");
    
    private final String particle;
    
    SoulParticle(String particle) {
        this.particle = particle;
    }
    
    public String particleName() {
        return particle;
    }
    
}
