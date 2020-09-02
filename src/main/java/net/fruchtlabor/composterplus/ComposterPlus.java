package main.java.net.fruchtlabor.composterplus;

import java.util.Iterator;

import main.java.net.fruchtlabor.composterplus.commands.DefaultCommand;
import main.java.net.fruchtlabor.composterplus.listeners.FermentingListener;
import main.java.net.fruchtlabor.composterplus.listeners.GUIManagemant;
import org.bukkit.configuration.ConfigurationSection;
import java.io.File;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;

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
        ComposterPlus.lootList = new ArrayList<Loot>();
        final ConfigurationSection section = plugin.getConfig().getConfigurationSection("ExtraLoot.loots");
        for (final String key : section.getKeys(false)) {
            ComposterPlus.lootList.add(new Loot(section.getConfigurationSection(key)));
        }
    }

    public static void setupSclist() {
        ComposterPlus.sclist = new ArrayList<SpecialCompost>();
        final ConfigurationSection section = plugin.getConfig().getConfigurationSection("SpecialCompost.composts");
        for (final String key : section.getKeys(false)) {
            ComposterPlus.sclist.add(new SpecialCompost(section.getConfigurationSection(key)));
        }
    }
}

