package main.java.net.fruchtlabor.composterplus.commands;

import main.java.net.fruchtlabor.composterplus.ComposterPlus;
import main.java.net.fruchtlabor.composterplus.GatherClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class DefaultCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(strings.length>0){
                if(player.hasPermission("cp.reload") && strings[0].equalsIgnoreCase("reload")){
                    ComposterPlus.setupConfig();
                    ComposterPlus.setupLootlist();
                    ComposterPlus.setupSclist();
                    player.sendMessage("Reloaded!");
                    return true;
                }
            }
            else{
                if(player.hasPermission("cp.use")){
                    Inventory inventory = Bukkit.createInventory(null, 9, "CompostingPlus");
                    inventory.setItem(4, GatherClass.itemgen(ChatColor.GREEN+"specialcompost", Material.matchMaterial(ComposterPlus.plugin.getConfig().get("Text.Compost_Gui")+""), new ArrayList<String>(){{add(ChatColor.WHITE+""+ComposterPlus.plugin.getConfig().get("Text.CompostGui_Text")+"");}}, false));
                    inventory.setItem(6, GatherClass.itemgen(ChatColor.GOLD+"loot", Material.matchMaterial(ComposterPlus.plugin.getConfig().get("Text.Loot_Gui")+""), new ArrayList<String>(){{add(ChatColor.WHITE+""+ComposterPlus.plugin.getConfig().get("Text.LootGui_Text")+"");}}, false));
                    inventory.setItem(2, GatherClass.itemgen(ChatColor.YELLOW+"MC_Compost", Material.OAK_SIGN, new ArrayList<>(){{add("Minecraft Standard Compost");}}, false));
                    player.openInventory(inventory);
                }
            }
        }
        return false;
    }
}
