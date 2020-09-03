package main.java.net.fruchtlabor.composterplus;

import org.bukkit.configuration.ConfigurationSection;

public class Loot
{
    private String mat;
    private int amount;
    private double chance;
    private int xp;
    
    public Loot(final ConfigurationSection section) {
        this.mat = section.getString("material");
        this.amount = section.getInt("amount");
        this.chance = section.getDouble("chance");
        this.xp = section.getInt("xp");
    }

    public Loot(String material, int amount, double chance, int exp){
        this.mat=material;
        this.amount=amount;
        this.chance=chance;
        this.xp=exp;
    }
    
    public String getMat() {
        return this.mat;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public double getChance() {
        return this.chance;
    }
    
    public int getXp() {
        return this.xp;
    }
}
