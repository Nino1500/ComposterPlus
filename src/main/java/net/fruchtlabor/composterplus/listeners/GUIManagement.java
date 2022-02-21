package net.fruchtlabor.composterplus.listeners;

import net.fruchtlabor.composterplus.ComposterPlus;
import net.fruchtlabor.composterplus.GatherClass;
import net.fruchtlabor.composterplus.Loot;
import net.fruchtlabor.composterplus.SpecialCompost;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class GUIManagement implements Listener {

    Plugin plugin;

    public GUIManagement(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCP(InventoryClickEvent event){
        if(event.getView().getTitle().equalsIgnoreCase("CompostingPlus") || event.getView().getTitle().equalsIgnoreCase("MC_Compost") || event.getView().getTitle().equalsIgnoreCase("loot") || event.getView().getTitle().equalsIgnoreCase("specialcompost")){
            if(event.getCurrentItem() != null){
                ItemStack itemStack = event.getCurrentItem();
                if(itemStack.getItemMeta()!=null){
                    ItemMeta meta = itemStack.getItemMeta();
                    if(meta.getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"specialcompost")){
                        ArrayList<SpecialCompost> sc = ComposterPlus.sclist;
                        Inventory inv = Bukkit.createInventory(null, 54, "specialcompost");
                        for (int i = 0; i < sc.size(); i++) {
                            int finalI = i;
                            inv.addItem(GatherClass.itemgen(sc.get(i).getMaterial(), Material.matchMaterial(sc.get(i).getMaterial()), new ArrayList<String>(){{add("lvl: "+sc.get(finalI).getLevel());}}, false));
                        }
                        inv.setItem(45, GatherClass.itemgen("<-", Material.BARRIER, new ArrayList<>(), false));
                        event.getWhoClicked().openInventory(inv);
                    }
                    if(meta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"loot")){
                        ArrayList<Loot> loot = ComposterPlus.lootList;
                        Inventory inv = Bukkit.createInventory(null, 54, "loot");
                        for (int i = 0; i < loot.size(); i++) {
                            int finalI = i;
                            inv.addItem(GatherClass.itemgen(loot.get(i).getMat(), Material.matchMaterial(loot.get(i).getMat()), new ArrayList<String>(){{add("Chance: "+loot.get(finalI).getChance()); add("Amount: "+loot.get(finalI).getAmount()); add("Exp: "+loot.get(finalI).getXp());}}, false));
                        }
                        inv.setItem(45, GatherClass.itemgen("<-", Material.BARRIER, new ArrayList<>(), false));
                        event.getWhoClicked().openInventory(inv);
                    }
                    if(meta.getDisplayName().equalsIgnoreCase("<-")){
                        event.getWhoClicked().openInventory(GatherClass.getMainGui());
                    }
                    if(meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW+"MC_Compost")){
                        Inventory inv = Bukkit.createInventory(null, 54, "MC_Compost");
                        inv.addItem(GatherClass.itemgen("Beetroot Seeds", Material.BEETROOT_SEEDS, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Dried Kelp", Material.DRIED_KELP, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Grass", Material.GRASS, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Kelp", Material.KELP, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Leaves", Material.OAK_LEAVES, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Melon Seeds", Material.MELON_SEEDS, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Nether Wart", Material.NETHER_WART, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Pumpkin Seeds", Material.PUMPKIN_SEEDS, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Saplings", Material.OAK_SAPLING, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Seagrass", Material.SEAGRASS, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Sweet Berries", Material.SWEET_BERRIES, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Wheat Seeds", Material.WHEAT_SEEDS, new ArrayList<String>(){{add("lvl: ~0.3");}}, false));
                        inv.addItem(GatherClass.itemgen("Cactus", Material.CACTUS, new ArrayList<String>(){{add("lvl: ~0.5");}}, false));
                        inv.addItem(GatherClass.itemgen("Dried Kelp Block", Material.DRIED_KELP_BLOCK, new ArrayList<String>(){{add("lvl: ~0.5");}}, false));
                        inv.addItem(GatherClass.itemgen("Melon Slice", Material.MELON_SLICE, new ArrayList<String>(){{add("lvl: ~0.5");}}, false));
                        inv.addItem(GatherClass.itemgen("Sugar Cane", Material.SUGAR_CANE, new ArrayList<String>(){{add("lvl: ~0.5");}}, false));
                        inv.addItem(GatherClass.itemgen("Tall Grass", Material.TALL_GRASS, new ArrayList<String>(){{add("lvl: ~0.5");}}, false));
                        inv.addItem(GatherClass.itemgen("Vines", Material.VINE, new ArrayList<String>(){{add("lvl: ~0.5");}}, false));
                        inv.addItem(GatherClass.itemgen("Weeping Vines", Material.WEEPING_VINES, new ArrayList<String>(){{add("lvl: ~0.5");}}, false));
                        inv.addItem(GatherClass.itemgen("Twisted Vines", Material.TWISTING_VINES, new ArrayList<String>(){{add("lvl: ~0.5");}}, false));
                        inv.addItem(GatherClass.itemgen("Apple", Material.APPLE, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Beetroot", Material.BEETROOT, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Carrot", Material.CARROT, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Cocoa Beans", Material.COCOA_BEANS, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Ferns", Material.FERN, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Flowers", Material.SUNFLOWER, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Lily Pad", Material.LILY_PAD, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Melon", Material.MELON, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Mushrooms", Material.RED_MUSHROOM, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Mushroom Stem", Material.MUSHROOM_STEM, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Potato", Material.POTATO, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Pumpkins", Material.PUMPKIN, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Seapickle", Material.SEA_PICKLE, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Wheat", Material.WHEAT, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Fungi", Material.CRIMSON_FUNGUS, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Roots", Material.CRIMSON_ROOTS, new ArrayList<String>(){{add("lvl: ~0.65");}}, false));
                        inv.addItem(GatherClass.itemgen("Baked Potato", Material.BAKED_POTATO, new ArrayList<String>(){{add("lvl: ~0.85");}}, false));
                        inv.addItem(GatherClass.itemgen("Bread", Material.BREAD, new ArrayList<String>(){{add("lvl: ~0.85");}}, false));
                        inv.addItem(GatherClass.itemgen("Cookie", Material.COOKIE, new ArrayList<String>(){{add("lvl: ~0.85");}}, false));
                        inv.addItem(GatherClass.itemgen("Hay Bale", Material.HAY_BLOCK, new ArrayList<String>(){{add("lvl: ~0.85");}}, false));
                        inv.addItem(GatherClass.itemgen("Mushroom Blocks", Material.RED_MUSHROOM_BLOCK, new ArrayList<String>(){{add("lvl: ~0.85");}}, false));
                        inv.addItem(GatherClass.itemgen("Nether Wart Block", Material.NETHER_WART_BLOCK, new ArrayList<String>(){{add("lvl: ~0.85");}}, false));
                        inv.addItem(GatherClass.itemgen("Warped Wart Block", Material.WARPED_WART_BLOCK, new ArrayList<String>(){{add("lvl: ~0.85");}}, false));
                        inv.addItem(GatherClass.itemgen("Cake", Material.CAKE, new ArrayList<String>(){{add("lvl: ~1.0");}}, false));
                        inv.addItem(GatherClass.itemgen("Pumpkin Pie", Material.PUMPKIN_PIE, new ArrayList<String>(){{add("lvl: ~1.0");}}, false));
                        inv.setItem(45, GatherClass.itemgen("<-", Material.BARRIER, new ArrayList<>(), false));
                        event.getWhoClicked().openInventory(inv);
                    }
                }
            }
            event.setCancelled(true);
        }
    }
}
