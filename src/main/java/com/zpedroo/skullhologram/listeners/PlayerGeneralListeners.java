package com.zpedroo.skullhologram.listeners;

import com.zpedroo.skullhologram.managers.DataManager;
import com.zpedroo.skullhologram.objects.PlacedSkullHologram;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerGeneralListeners implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlace(BlockPlaceEvent event) {
        if (event.getItemInHand() == null || event.getItemInHand().getType().equals(Material.AIR)) return;

        ItemStack item = event.getItemInHand().clone();
        NBTItem nbt = new NBTItem(item);
        if (!nbt.hasKey("SkullHologram")) return;

        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();
        String text = nbt.getString("SkullHologram");

        PlacedSkullHologram skullHologram = new PlacedSkullHologram(location, text);
        skullHologram.cache();

        item.setAmount(1);
        player.getInventory().removeItem(item);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        PlacedSkullHologram placedSkullHologram = DataManager.getInstance().getPlacedSkullHologram(event.getBlock().getLocation());
        if (placedSkullHologram == null) return;

        placedSkullHologram.delete();
    }
}