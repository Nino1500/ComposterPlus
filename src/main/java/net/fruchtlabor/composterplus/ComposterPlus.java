package main.java.net.fruchtlabor.composterplus;

import java.io.IOException;
import java.util.Iterator;

import main.java.net.fruchtlabor.composterplus.commands.DefaultCommand;
import main.java.net.fruchtlabor.composterplus.listeners.FermentingListener;
import main.java.net.fruchtlabor.composterplus.listeners.GUIManagemant;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import java.io.File;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;
import java.util.Properties;

import org.bukkit.plugin.java.JavaPlugin;

public class ComposterPlus extends JavaPlugin
{
    public static ArrayList<Loot> lootList;
    public static ArrayList<SpecialCompost> sclist;
    public static Plugin plugin;

    public void onEnable() { //TODO: Wenn level von specialcompost > als noch benötigte -> error

        plugin = this;

        //Startup Section
        setupConfig();
        setupLootlist();
        setupSclist();

        //Listener Section
        final FermentingListener fermentingListener = new FermentingListener(this);
        this.getServer().getPluginManager().registerEvents((Listener)fermentingListener,this);
        this.getServer().getPluginManager().registerEvents(new GUIManagemant(this), this);

        //Command Section
        this.getCommand("cp").setExecutor(new DefaultCommand());

    }

    public static void setupConfig() {
        final File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            plugin.saveDefaultConfig();
        }
        else {
            plugin.reloadConfig();
        }
    }

    public static void setupLootlist() {

        File data = new File(plugin.getDataFolder()+File.separator+"loot.yml");
        FileConfiguration dataconfig = YamlConfiguration.loadConfiguration(data);

        if(!data.exists()){
            plugin.saveResource("loot.yml", false);
        }
        else{
            try {
                dataconfig.save(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        ComposterPlus.lootList = new ArrayList<Loot>();
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(data);
        ConfigurationSection section = yamlConfiguration.getConfigurationSection("loot");

        for (final String key : section.getKeys(false)) {
            ComposterPlus.lootList.add(new Loot(section.getConfigurationSection(key)));
        }

    }
    public static void setupSclist() {

        File data = new File(plugin.getDataFolder()+File.separator+"specialcompost.yml");
        FileConfiguration dataconfig = YamlConfiguration.loadConfiguration(data);

        if(!data.exists()){
            plugin.saveResource("specialcompost.yml", false);
        }
        else{
            try {
                dataconfig.save(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        ComposterPlus.sclist = new ArrayList<SpecialCompost>();
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(data);
        ConfigurationSection section = yamlConfiguration.getConfigurationSection("SpecialCompost");

        for (final String key : section.getKeys(false)) {
            ComposterPlus.sclist.add(new SpecialCompost(section.getConfigurationSection(key)));
        }

    }

    public static boolean addSc(SpecialCompost specialCompost, int index) {

        File data = new File(plugin.getDataFolder()+File.separator+"specialcompost.yml");
        FileConfiguration dataconfig = YamlConfiguration.loadConfiguration(data);

        if(!data.exists()){
            plugin.saveResource("specialcompost.yml", false);
        }
        else{
            try {
                dataconfig.save(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        dataconfig.set("SpecialCompost."+index+".material", specialCompost.getMaterial());
        dataconfig.set("SpecialCompost."+index+".level", specialCompost.getLevel());

        try {
            dataconfig.save(data);
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }
    public static boolean addLoot(Loot loot, int index){

        File data = new File(plugin.getDataFolder()+File.separator+"loot.yml");
        FileConfiguration dataconfig = YamlConfiguration.loadConfiguration(data);

        if(!data.exists()){
            plugin.saveResource("loot.yml", false);
        }
        else{
            try {
                dataconfig.save(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        dataconfig.set("loot."+index+".material", loot.getMat());
        dataconfig.set("loot."+index+".amount", loot.getAmount());
        dataconfig.set("loot."+index+".chance", loot.getChance());
        dataconfig.set("loot."+index+".xp", loot.getXp());

        try {
            dataconfig.save(data);
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }
}

