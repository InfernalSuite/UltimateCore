package mc.ultimatecore.helper.objects;

public class Placeholder {
    
    private final String key;
    
    private final String value;
    
    public Placeholder(String key, String value) {
        this.key = "%" + key + "%";
        this.value = value;
    }
    
    public String process(String line) {
        if (line == null)
            return "";
        return line.replace(this.key, this.value);
    }
}
