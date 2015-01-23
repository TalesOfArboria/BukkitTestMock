package com.jcwhatever.bukkit.v1_8_R1.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.event.server.ServiceUnregisterEvent;

public class ServerListener implements Listener {

    @EventHandler
    private void onMapInitialize(MapInitializeEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onPluginEnable(PluginEnableEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onRemoteServerCommand(RemoteServerCommandEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onServerCommand(ServerCommandEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onServerListPing(ServerListPingEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onServiceRegister(ServiceRegisterEvent event) {
        BukkitEventTester.notify(event);
    }

    @EventHandler
    private void onServiceUnregister(ServiceUnregisterEvent event) {
        BukkitEventTester.notify(event);
    }
}
