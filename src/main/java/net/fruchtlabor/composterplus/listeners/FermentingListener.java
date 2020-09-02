package main.java.net.fruchtlabor.composterplus.listeners;

import main.java.net.fruchtlabor.composterplus.ComposterPlus;
import main.java.net.fruchtlabor.composterplus.Loot;
import main.java.net.fruchtlabor.composterplus.SpecialCompost;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.BlockState;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.block.data.Levelled;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;

public class FermentingListener implements Listener
{
    private Plugin plugin;
    
    public FermentingListener(final Plugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void Fermenting(final PlayerInteractEvent event) {

        final Player player = event.getPlayer();

        if (event.getClickedBlock() != null) {
            final Block b = event.getClickedBlock();

            if (b.getType().equals(Material.COMPOSTER)) {
                final Levelled levelled = (Levelled)b.getBlockData();

                if (levelled.getLevel() == levelled.getMaximumLevel()) {
                    this.giveExtraBounty(b.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void FermentingHopper(final InventoryMoveItemEvent event) {

        if (event.getSource().getType().name().equalsIgnoreCase("HOPPER") && this.plugin.getConfig().getBoolean("Hopperinteraction")) {

            final Location loc = event.getSource().getLocation();
            assert loc != null;

            loc.setY(loc.getY() - 1.0);

            if (loc.getBlock() != null && loc.getBlock().getType().equals(Material.COMPOSTER)) {

                final Levelled levelled = (Levelled)loc.getBlock().getBlockData();

                if (levelled.getLevel() == 6) {

                    loc.setY(loc.getY() - 1.0);

                    this.giveExtraBountyHopper(loc);
                }
            }
        }
    }
    
    private void giveExtraBountyHopper(final Location location) {

        Loot loot = chooseOnWeight(ComposterPlus.lootList);

        try {
            final Material material = Material.matchMaterial(loot.getMat());
            assert material != null;
            final ItemStack itemStack = new ItemStack(material, loot.getAmount());
            final Block block = location.getBlock();
            if (!block.getType().equals(Material.HOPPER)) {
                return;
            }
            if (block.getState() instanceof Hopper) {
                final Hopper hopper = (Hopper)block.getState();
                hopper.getInventory().addItem(itemStack);
                block.getState().update();
            }
            if (loot.getXp() > 0) {
                final int orbs = loot.getXp();
                ((ExperienceOrb)location.getWorld().spawn(location, ExperienceOrb.class)).setExperience(orbs);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Seems like you failed to write the correct Material-name in the config! " + ChatColor.RED + loot.getMat());
        }
    }
    
    private void giveExtraBounty(final Location location) {

        Loot loot = chooseOnWeight(ComposterPlus.lootList);

        try {
            final Material material = Material.matchMaterial(loot.getMat());
            final ItemStack itemStack = new ItemStack(material, loot.getAmount());
            location.getWorld().dropItemNaturally(location, itemStack);
            if (loot.getXp() > 0) {
                final int orbs = loot.getXp();
                ((ExperienceOrb)location.getWorld().spawn(location, (Class)ExperienceOrb.class)).setExperience(orbs);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Seems like you failed to write the correct Material name in the config!" + ChatColor.RED + loot.getMat());
        }



    }

    public Loot chooseOnWeight(List<Loot> items) {
        double completeWeight = 0.0;
        for (Loot item : items)
            completeWeight += item.getChance();
        double r = Math.random() * completeWeight;
        double countWeight = 0.0;
        for (Loot item : items) {
            countWeight += item.getChance();
            if (countWeight >= r)
                return item;
        }
        throw new RuntimeException("Should never be shown.");
    }
    
    @EventHandler
    public void fillSpecials(final PlayerInteractEvent event) {

        if (event.getClickedBlock() != null && event.getClickedBlock().getType().equals((Object)Material.COMPOSTER)) {

            final Block block = event.getClickedBlock();
            final BlockState state = block.getState();
            final BlockData data = block.getBlockData();
            final Levelled lev = (Levelled)data;

            for (final SpecialCompost compost : ComposterPlus.sclist) {

                if (compost.getMaterial().equalsIgnoreCase(event.getMaterial().name())) {

                    if (lev.getLevel() < lev.getMaximumLevel()) {
                        lev.setLevel(compost.getLevel() + lev.getLevel());
                    }

                    state.setBlockData(data);
                    state.update();

                }
            }
        }
    }
}