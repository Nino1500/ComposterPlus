package net.fruchtlabor.composterplus.listeners;

import net.fruchtlabor.composterplus.ComposterPlus;
import net.fruchtlabor.composterplus.Loot;
import net.fruchtlabor.composterplus.SpecialCompost;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Objects;

public class FermentingListener implements Listener
{
    private Plugin plugin;
    
    public FermentingListener(final Plugin plugin) {
        this.plugin = plugin;
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
                Objects.requireNonNull(location.getWorld(), "World is null!").spawn(location, ExperienceOrb.class).setExperience(orbs);
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
            Objects.requireNonNull(material, "Material is null!");
            final ItemStack itemStack = new ItemStack(material, loot.getAmount());
            Objects.requireNonNull(location.getWorld(), "World is null!")
                    .dropItemNaturally(location, itemStack);
            if (loot.getXp() > 0) {
                final int orbs = loot.getXp();
                location.getWorld().spawn(location, ExperienceOrb.class).setExperience(orbs);
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
    public void Fermenting(final PlayerInteractEvent event) {

        if (event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.COMPOSTER)) {

            final Block block = event.getClickedBlock();
            final BlockState state = block.getState();
            final BlockData data = block.getBlockData();
            final Levelled lev = (Levelled) data;

            if(lev.getMaximumLevel()>lev.getLevel()){

                for (final SpecialCompost compost : ComposterPlus.sclist) {

                    if (compost.getMaterial().equalsIgnoreCase(event.getMaterial().name())) {

                        lev.setLevel(Math.min(lev.getLevel() + compost.getLevel(), lev.getMaximumLevel()));
                        event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    }

                    state.setBlockData(data);
                    state.update();
                }
            }
            else {
                this.giveExtraBounty(block.getLocation());
            }
        }
    }

    @EventHandler
    public void FermentingHopper(final InventoryMoveItemEvent event) {

        if (event.getSource().getType().name().equalsIgnoreCase("HOPPER") && this.plugin.getConfig().getBoolean("Hopperinteraction")) {

            final Location loc = event.getSource().getLocation();
            assert loc != null;

            loc.setY(loc.getY() - 1.0);

            if(loc.getBlock().getType().equals(Material.COMPOSTER)){

                final Block block = loc.getBlock();
                final BlockState state = block.getState();
                final BlockData data = block.getBlockData();
                final Levelled lev = (Levelled) data;

                for (SpecialCompost compost : ComposterPlus.sclist){
                    if(compost.getMaterial().equalsIgnoreCase(event.getItem().getType().name())){

                        lev.setLevel(Math.min(lev.getLevel() + compost.getLevel(), lev.getMaximumLevel()));

                        event.setItem(new ItemStack(Material.AIR));
                    }
                }
                state.setBlockData(data);
                state.update();

                if(lev.getLevel() == lev.getMaximumLevel()){
                    loc.setY(loc.getY() - 1.0);
                    this.giveExtraBountyHopper(loc);
                }
            }
        }
    }
    @EventHandler
    public void onTopPlace(BlockPlaceEvent event){
        Location location = event.getBlock().getLocation();
        location.setY(location.getY()-1);
        Block block = location.getBlock();
        if(block.getType() == Material.COMPOSTER){
            for (int i = 0; i < ComposterPlus.sclist.size(); i++) {
                if(ComposterPlus.sclist.get(i).getMaterial().equalsIgnoreCase(event.getBlock().getType().name()) && !(event.getBlock().getType() == Material.HOPPER)){
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
