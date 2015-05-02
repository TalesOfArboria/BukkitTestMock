package com.jcwhatever.v1_8_R2.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public final class PlayerListener implements Listener {

    @EventHandler
    private void onPlayerAchievmentAwarded(PlayerAchievementAwardedEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerAnimation(PlayerAnimationEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerChat(PlayerChatTabCompleteEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerEditBook(PlayerEditBookEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerEggThrow(PlayerEggThrowEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerExpChange(PlayerExpChangeEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerFish(PlayerFishEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerItemBreak(PlayerItemBreakEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerItemHeld(PlayerItemHeldEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerKick(PlayerKickEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerLogin(PlayerLoginEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerPortal(PlayerPortalEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerRegisterChannel(PlayerRegisterChannelEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerShearEntity(PlayerShearEntityEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerUnleashEntity(PlayerUnleashEntityEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerUnregisterChannel(PlayerUnregisterChannelEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPlayerVelocity(PlayerVelocityEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }
}
