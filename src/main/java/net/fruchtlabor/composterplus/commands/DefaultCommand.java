package net.fruchtlabor.composterplus.commands;

import net.fruchtlabor.composterplus.ComposterPlus;
import net.fruchtlabor.composterplus.GatherClass;
import net.fruchtlabor.composterplus.Loot;
import net.fruchtlabor.composterplus.SpecialCompost;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DefaultCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(strings.length>0){
                if(player.hasPermission("cp.reload") && strings[0].equalsIgnoreCase("reload")){
                    reload();
                    player.sendMessage("Reloaded!");
                    return true;
                }
                if(player.hasPermission("cp.create") && strings[0].equalsIgnoreCase("create")){
                    if(strings.length > 1){
                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        if(itemStack.getType().equals(Material.AIR)){
                            player.sendMessage("Put an Item in your hand!");
                        }
                        else{
                            if(strings[1].equalsIgnoreCase("loot")){
                                if(strings.length>3) {
                                    int am = Integer.parseInt(strings[2]);
                                    double chance = Double.parseDouble(strings[3]);
                                    int xp = Integer.parseInt(strings[4]);
                                    if(am > 0 && chance > 0.0 && xp >= 0){
                                        int id = ComposterPlus.lootList.size()+2;
                                        if(id>53){
                                            player.sendMessage("CompostPlus currently does not support more than 53 Items on Specialcompost or on Loot-GUI!");
                                            player.sendMessage("If you want to see more (I dont know why) let me know on spigot!");
                                        }
                                        ComposterPlus.addLoot(new Loot(itemStack.getType().name(), am, chance, xp), id);
                                        player.sendMessage(ChatColor.RED+"Added "+ChatColor.GREEN+itemStack.getType().name()+ChatColor.RED+" to loot, with ID: "+ChatColor.GOLD+id);
                                        reload();
                                    }
                                }
                            }
                            if(strings[1].equalsIgnoreCase("specialcompost")){
                                int lvl = Integer.parseInt(strings[2]);
                                int id = ComposterPlus.sclist.size()+2;
                                if(id>53){
                                    player.sendMessage("CompostPlus currently does not support more than 53 Items on Specialcompost or on Loot-GUI!");
                                    player.sendMessage("If you want to see more (I dont know why) let me know on spigot!");
                                }
                                ComposterPlus.addSc(new SpecialCompost(itemStack.getType().name(), lvl), id);
                                player.sendMessage(ChatColor.RED+"Added "+ChatColor.GREEN+itemStack.getType().name()+ChatColor.RED+" to specialcompost, with ID: "+ChatColor.GOLD+id);
                                reload();
                            }
                        }
                    }
                }
            }
            else{
                if(player.hasPermission("cp.use")){
                    player.openInventory(GatherClass.getMainGui());
                }
            }
        }
        return false;
    }
    public void reload(){
        ComposterPlus.setupConfig();
        ComposterPlus.setupLootlist();
        ComposterPlus.setupSclist();
    }
}
