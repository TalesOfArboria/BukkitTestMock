package com.jcwhatever.v1_8_R3.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;

public final class BlockListener implements Listener{

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockBurn(BlockBurnEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockCanBuildEvent(BlockCanBuildEvent event) {
        BukkitEventTester.notify(event);

    }

    @EventHandler
    private void onBlockDamage(BlockDamageEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockDispense(BlockDispenseEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockExp(BlockExpEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onBlockFade(BlockFadeEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockForm(BlockFormEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockFromTo(BlockFromToEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockGrow(BlockGrowEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockIgnite(BlockIgniteEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockMultiPlace(BlockMultiPlaceEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockPhysics(BlockPhysicsEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockRedstone(BlockRedstoneEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onBlockSpread(BlockSpreadEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onEntityBlockForm(EntityBlockFormEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onLeavesDecay(LeavesDecayEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onNotePlay(NotePlayEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }

    @EventHandler
    private void onSignChange(SignChangeEvent event) {
        if (!BukkitEventTester.notify(event))
            event.setCancelled(true);
    }
}
