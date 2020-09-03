package main.java.net.fruchtlabor.composterplus;

import org.bukkit.configuration.ConfigurationSection;

public class SpecialCompost
{
    private String material;
    private int level;
    
    public SpecialCompost(final ConfigurationSection section) {
        this.material = section.getString("material");
        this.level = section.getInt("level");
    }
    public SpecialCompost(String material, int level){
        this.material=material;
        this.level=level;
    }

    public String getMaterial() {
        return this.material;
    }
    
    public int getLevel() {
        return this.level;
    }
}
