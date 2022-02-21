package net.fruchtlabor.composterplus;

import net.fruchtlabor.composterplus.commands.DefaultCommand;
import net.fruchtlabor.composterplus.listeners.FermentingListener;
import net.fruchtlabor.composterplus.listeners.GUIManagement;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ComposterPlus extends JavaPlugin
{
    public static ArrayList<Loot> lootList;
    public static ArrayList<SpecialCompost> sclist;
    public static Plugin plugin;

    public void onEnable() {

        plugin = this;

        //Startup Section
        setupConfig();
        setupLootlist();
        setupSclist();

        //Listener Section
        final FermentingListener fermentingListener = new FermentingListener(this);
        this.getServer().getPluginManager().registerEvents(fermentingListener,this);
        this.getServer().getPluginManager().registerEvents(new GUIManagement(this), this);

        //Command Section
        Objects.requireNonNull(this.getCommand("cp"), "Command cp has not been initialized!")
                .setExecutor(new DefaultCommand());
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

        ComposterPlus.lootList = new ArrayList<>();
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(data);
        ConfigurationSection section = yamlConfiguration.getConfigurationSection("loot");
        if(section != null){
            for (final String key : section.getKeys(false)) {
                ComposterPlus.lootList.add(new Loot(Objects.requireNonNull(section.getConfigurationSection(key))));
            }
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

        ComposterPlus.sclist = new ArrayList<>();
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(data);
        ConfigurationSection section = yamlConfiguration.getConfigurationSection("SpecialCompost");
        if(section != null){
            for (final String key : section.getKeys(false)) {
                ComposterPlus.sclist.add(new SpecialCompost(Objects.requireNonNull(section.getConfigurationSection(key))));
            }
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
    public static boolean removeLoot(){
        //TODO do stuff
        return false;
    }
    public static boolean removeSC(){
        //TODO do stuff
        return false;
    }
}

