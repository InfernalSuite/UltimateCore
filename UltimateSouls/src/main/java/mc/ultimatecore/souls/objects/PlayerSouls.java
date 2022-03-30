package mc.ultimatecore.souls.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class PlayerSouls {
    
    private Set<Integer> collectedSouls;
    
    @Getter
    @Setter
    private int soulsExchanged;
    
    public PlayerSouls() {
        this.collectedSouls = new HashSet<>();
    }
    
    public String getSoulsStr() {
        return collectedSouls.toString().replace("]", "").replace("[", "").replace(" ", "");
    }
    
    public void setSoulsStr(String str) {
        Set<Integer> set = new HashSet<>();
        String[] inventorySplit = str.split(",");
        for (String id : inventorySplit) {
            try {
                set.add(Integer.parseInt(id));
            } catch (Exception ignored) {
            }
        }
        this.collectedSouls = set;
    }
    
    public boolean hasSoul(int id) {
        return collectedSouls.contains(id);
    }
    
    public void addSoul(int id) {
        collectedSouls.add(id);
    }
    
    public void removeSoul(int id) {
        collectedSouls.remove(id);
    }
    
    public int getAmount() {
        return collectedSouls.size();
    }
    
    public void addSoulsExchanged(int exchanged) {
        soulsExchanged += exchanged;
    }
    
    public void resetData() {
        soulsExchanged = 0;
        collectedSouls.clear();
    }
}
