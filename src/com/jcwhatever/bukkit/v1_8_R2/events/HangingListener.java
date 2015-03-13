package com.jcwhatever.bukkit.v1_8_R2.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

public final class HangingListener implements Listener {

    @EventHandler
    private void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onHangingBreak(HangingBreakEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onHangingPlace(HangingPlaceEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

}
