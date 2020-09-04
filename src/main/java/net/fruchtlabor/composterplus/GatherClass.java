package main.java.net.fruchtlabor.composterplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GatherClass {

    public static ItemStack itemgen(String name, Material material, List<String> lore, boolean ench){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        if(ench){ itemStack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);} //TODO: Enchantment noch verstecken (X)

        return itemStack;
    }
    public static Inventory getMainGui(){
        Inventory inventory = Bukkit.createInventory(null, 9, "CompostingPlus");
        inventory.setItem(4, GatherClass.itemgen(ChatColor.GREEN+"specialcompost", Material.matchMaterial(ComposterPlus.plugin.getConfig().get("Text.Compost_Gui")+""), new ArrayList<String>(){{add(ChatColor.WHITE+""+ComposterPlus.plugin.getConfig().get("Text.CompostGui_Text")+"");}}, false));
        inventory.setItem(6, GatherClass.itemgen(ChatColor.GOLD+"loot", Material.matchMaterial(ComposterPlus.plugin.getConfig().get("Text.Loot_Gui")+""), new ArrayList<String>(){{add(ChatColor.WHITE+""+ComposterPlus.plugin.getConfig().get("Text.LootGui_Text")+"");}}, false));
        inventory.setItem(2, GatherClass.itemgen(ChatColor.YELLOW+"MC_Compost", Material.OAK_SIGN, new ArrayList<>(){{add("Minecraft Standard Compost");}}, false));
        return inventory;
    }

}
