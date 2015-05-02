
package com.jcwhatever.v1_8_R2.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public final class WorldListener implements Listener {

    @EventHandler
    private void onChunkLoad(ChunkLoadEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onChunkPopulate(ChunkPopulateEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onChunkUnload(ChunkUnloadEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPortalCreate(PortalCreateEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onSpawnChange(SpawnChangeEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onStructureGrow(StructureGrowEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onWorldInit(WorldInitEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onWorldSave(WorldSaveEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onWorldUnload(WorldUnloadEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }
}
