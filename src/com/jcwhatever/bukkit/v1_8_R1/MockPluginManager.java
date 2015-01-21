package com.jcwhatever.bukkit.v1_8_R1;

import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A mock implementation of {@link org.bukkit.plugin.PluginManager}
 */
public class MockPluginManager implements PluginManager {

    private Map<String, Plugin> _plugins = new HashMap<>(10);
    private SimplePluginManager _pluginManager;

    /**
     * Constructor.
     *
     * @param server      The current Bukkit server.
     * @param commandMap  A command map.
     */
    public MockPluginManager(Server server, SimpleCommandMap commandMap) {
        _pluginManager = new SimplePluginManager(server, commandMap);
    }

    /**
     * Register a plugin with the plugin manager.
     *
     * @param plugin  The plugin to register.
     */
    public void registerPlugin(Plugin plugin) {
        _plugins.put(plugin.getName().toLowerCase(), plugin);
    }

    @Override
    public void registerInterface(Class<? extends PluginLoader> aClass) throws IllegalArgumentException {
        _pluginManager.registerInterface(aClass);
    }

    @Override
    public Plugin getPlugin(String s) {
        return _plugins.get(s.toLowerCase());
    }

    @Override
    public Plugin[] getPlugins() {
        return _plugins.values().toArray(new Plugin[_plugins.size()]);
    }

    @Override
    public boolean isPluginEnabled(String s) {
        Plugin plugin = getPlugin(s);
        return plugin != null && plugin.isEnabled();
    }

    @Override
    public boolean isPluginEnabled(Plugin plugin) {
        return plugin != null && plugin.isEnabled();
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException,
            UnknownDependencyException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Plugin[] loadPlugins(File file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disablePlugins() {
        for (Plugin plugin : _plugins.values()) {
            disablePlugin(plugin);
        }
    }

    @Override
    public void clearPlugins() {
        disablePlugins();
        _plugins.clear();
    }

    @Override
    public void callEvent(Event event) throws IllegalStateException {
        _pluginManager.callEvent(event);
    }

    @Override
    public void registerEvents(Listener listener, Plugin plugin) {
        _pluginManager.registerEvents(listener, plugin);
    }

    @Override
    public void registerEvent(Class<? extends Event> aClass,
                              Listener listener,
                              EventPriority eventPriority,
                              EventExecutor eventExecutor,
                              Plugin plugin) {

        _pluginManager.registerEvent(aClass, listener, eventPriority, eventExecutor, plugin);
    }

    @Override
    public void registerEvent(Class<? extends Event> aClass,
                              Listener listener,
                              EventPriority eventPriority,
                              EventExecutor eventExecutor,
                              Plugin plugin,
                              boolean b) {
        _pluginManager.registerEvent(aClass, listener, eventPriority, eventExecutor, plugin, b);
    }

    @Override
    public void enablePlugin(Plugin plugin) {

        if (!_plugins.containsKey(plugin.getName().toLowerCase()))
            registerPlugin(plugin);

        setPluginEnabled(plugin, true);
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        setPluginEnabled(plugin, false);
    }

    public void setPluginEnabled(Plugin plugin, boolean isEnabled) {
        // enable plugin
        try {
            Method method = JavaPlugin.class.getDeclaredMethod("setEnabled", boolean.class);
            method.setAccessible(true);

            method.invoke(plugin, isEnabled);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Permission getPermission(String s) {
        return _pluginManager.getPermission(s);
    }

    @Override
    public void addPermission(Permission permission) {
        _pluginManager.addPermission(permission);
    }

    @Override
    public void removePermission(Permission permission) {
        _pluginManager.removePermission(permission);
    }

    @Override
    public void removePermission(String s) {
        _pluginManager.removePermission(s);
    }

    @Override
    public Set<Permission> getDefaultPermissions(boolean b) {
        return _pluginManager.getDefaultPermissions(b);
    }

    @Override
    public void recalculatePermissionDefaults(Permission permission) {
        _pluginManager.recalculatePermissionDefaults(permission);
    }

    @Override
    public void subscribeToPermission(String s, Permissible permissible) {
        _pluginManager.subscribeToPermission(s, permissible);
    }

    @Override
    public void unsubscribeFromPermission(String s, Permissible permissible) {
        _pluginManager.unsubscribeFromPermission(s, permissible);
    }

    @Override
    public Set<Permissible> getPermissionSubscriptions(String s) {
        return _pluginManager.getPermissionSubscriptions(s);
    }

    @Override
    public void subscribeToDefaultPerms(boolean b, Permissible permissible) {
        _pluginManager.subscribeToDefaultPerms(b, permissible);
    }

    @Override
    public void unsubscribeFromDefaultPerms(boolean b, Permissible permissible) {
        _pluginManager.unsubscribeFromDefaultPerms(b, permissible);
    }

    @Override
    public Set<Permissible> getDefaultPermSubscriptions(boolean b) {
        return _pluginManager.getDefaultPermSubscriptions(b);
    }

    @Override
    public Set<Permission> getPermissions() {
        return _pluginManager.getPermissions();
    }

    @Override
    public boolean useTimings() {
        return _pluginManager.useTimings();
    }
}
