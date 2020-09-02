package main.java.net.fruchtlabor.composterplus.commands;

import main.java.net.fruchtlabor.composterplus.ComposterPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DefaultCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(player.hasPermission("cp.reload") && strings[0].equalsIgnoreCase("reload")){
                ComposterPlus.setupConfig();
                ComposterPlus.setupLootlist();
                ComposterPlus.setupSclist();
                player.sendMessage("Reloaded!");
                return true;
            }
        }
        return false;
    }
}
