
package com.jcwhatever.v1_8_R2.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public final class InventoryListener implements Listener {

    @EventHandler
    private void onBrew(BrewEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onCraftItem(CraftItemEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onFurnaceBurn(FurnaceBurnEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onFurnaceExtract(FurnaceExtractEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onFurnaceSmelt(FurnaceSmeltEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onInventoryCreative(InventoryCreativeEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryDrag(InventoryDragEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryPickupItem(InventoryPickupItemEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPrepareItemCraft(PrepareItemCraftEvent event) {
        BukkitEventTester.notify(event);
    }

}
