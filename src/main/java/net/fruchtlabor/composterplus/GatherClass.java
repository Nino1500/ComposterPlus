package main.java.net.fruchtlabor.composterplus;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

}
