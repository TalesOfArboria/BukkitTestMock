
package com.jcwhatever.v1_8_R3.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public final class EnchantmentListener implements Listener {

    @EventHandler
    private void onEnchantItem(EnchantItemEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

}
