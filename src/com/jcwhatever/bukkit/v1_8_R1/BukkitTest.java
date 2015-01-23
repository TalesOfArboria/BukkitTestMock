package com.jcwhatever.bukkit.v1_8_R1;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.scheduler.CraftScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Static utilities for testing Bukkit plugins.
 */
public class BukkitTest {

    public static final String NMS_TEST_VERSION = "v1_8_R1";

    static boolean _isInit;
    static long _nextHeartBeat;
    static int _currentTick = 0;
    static MockServer _server;

    /**
     * Initialize the Bukkit server. This needs to be called
     * before running any tests that may potentially use Bukkit.
     *
     * @return  True if initialized, false if already initialized.
     */
    public static boolean init() {
        if (_isInit) {
            return false;
        }

        _isInit = true;

        if (_server == null)
            _server = new MockServer();

        try {
            Bukkit.setServer(_server);
        }
        catch (UnsupportedOperationException ignore) {
            return false;
        }

        return true;
    }

    /**
     * Get the mock Bukkit server.
     */
    public static MockServer getServer() {
        init();

        return _server;
    }

    /**
     * Invoke from within loops to send a heart beat signal to the scheduler service.
     *
     * <p>Required when testing components that use the Bukkit scheduler.</p>
     */
    public static void heartBeat() {
        if (!_isInit || System.currentTimeMillis() < _nextHeartBeat)
            return;

        ((CraftScheduler)Bukkit.getServer().getScheduler()).mainThreadHeartbeat(_currentTick);
        _currentTick++;

        _nextHeartBeat = System.currentTimeMillis() + 50;
    }

    /**
     * Invoke to pause for the specified number of ticks.
     * A heart beat is sent to the Bukkit scheduler during this time.
     */
    public static void pause(int ticks) {
        long end = System.currentTimeMillis() + (ticks * 50);
        while (System.currentTimeMillis() < end) {

            heartBeat();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                return;
            }
        }
    }


    /**
     * Login a mock player and return the {@code MockPlayer}
     * instance. If the player is already logged in, the current
     * player is returned.
     *
     * @param playerName  The name of the player.
     */
    public static MockPlayer login(String playerName) {
        return getServer().login(playerName);
    }

    /**
     * Logout a player by name.
     *
     * @param playerName  The name of the player to logout.
     *
     * @return  True if the player was found and removed.
     */
    public static boolean logout(String playerName) {
        return logout(playerName, "Disconnected");
    }

    /**
     * Logout a player by name.
     *
     * @param playerName  The name of the player to logout.
     * @param message     The logout message.
     *
     * @return  True if the player was found and removed.
     */
    public static boolean logout(String playerName, String message) {
        return getServer().logout(playerName, message);
    }

    /**
     * Kick a player by name.
     *
     * @param playerName  The name of the player to kick.
     *
     * @return  True if the player was found and kicked.
     */
    public static boolean kick(String playerName) {
        return getServer().kick(playerName, "test", "kicked.");
    }

    /**
     * Kick a player by name.
     *
     * @param playerName  The name of the player to kick.
     * @param reason      The reason the player is being kicked.
     * @param message     The message to show the player.
     *
     * @return  True if the player was found and kicked.
     */
    public static boolean kick(String playerName, String reason, String message) {
        return getServer().kick(playerName, reason, message);
    }

    /**
     * Add a world to the server or get an existing one.
     *
     * @param name  The name of the world.
     *
     * @return  The world.
     */
    public static MockWorld world(String name) {
        return getServer().world(name);
    }

    /**
     * Create a new instance of a mock plugin. The plugin
     * returned is already enabled.
     *
     * <p>The plugins version is "v0".</p>
     *
     * @param name  The name of the plugin.
     *
     * @return  The {@code MockPlugin} instance.
     */
    public static MockPlugin mockPlugin(String name) {
        return new MockPlugin(name).enable();
    }

    /**
     * Instantiate and init a non-mock Bukkit plugin.
     *
     * <p>The plugin is initialized without commands. The returned
     * instance is not yet enabled.</p>
     *
     * <p>The plugin requires a constructor with private or greater access
     * with the following arguments:</p>
     *
     * <ul>
     *     <li>{@link org.bukkit.plugin.java.JavaPluginLoader}</li>
     *     <li>{@link org.bukkit.plugin.PluginDescriptionFile}</li>
     *     <li>{@link java.io.File}</li>
     *     <li>{@link java.io.File}</li>
     * </ul>
     *
     * <p>The arguments passed into the constructor should be forwarded to
     * the protected superclass {@link org.bukkit.plugin.java.JavaPlugin}
     * constructor of the same signature.</p>
     *
     * @param name         The name to initialize the plugin with.
     * @param version      The version to initialize the plugin with.
     * @param pluginClass  The plugin class.
     *
     * @param <T>  The plugin type.
     *
     * @return  The plugin instance.
     */
    public static <T extends JavaPlugin> T initPlugin(String name, String version, Class<T> pluginClass) {

        init();

        PluginDescriptionFile descriptionFile = new PluginDescriptionFile(name, version, "");

        Map<String, Object> descriptionMap = new HashMap<>(10);
        descriptionMap.put("version", version);
        descriptionMap.put("name", name);
        descriptionMap.put("main", pluginClass.getName());
        descriptionMap.put("commands", new HashMap(0));

        try {
            Method method = descriptionFile.getClass().getDeclaredMethod("loadMap", Map.class);
            method.setAccessible(true);

            method.invoke(descriptionFile, descriptionMap);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        T plugin;
        Constructor<T> constructor;

        // get test constructor.
        try {

            constructor = pluginClass.getDeclaredConstructor(
                    JavaPluginLoader.class, PluginDescriptionFile.class, File.class, File.class);
            constructor.setAccessible(true);

        } catch (NoSuchMethodException e) {
            throw new AssertionError("Required test constructor not found in plugin. Unable to init for test.");
        }

        // instantiate plugin.
        try {
            plugin = constructor.newInstance(new JavaPluginLoader(Bukkit.getServer()),
                    descriptionFile, new File(""), new File(""));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        getServer().getPluginManager().registerPlugin(plugin);
        return plugin;
    }

    /**
     * Enable a plugin instance.
     *
     * @param plugin  The plugin to enable.
     */
    public static void enablePlugin(Plugin plugin) {
        getServer().getPluginManager().enablePlugin(plugin);
    }

    /**
     * Disable a plugin instance.
     *
     * @param plugin  The plugin to disable.
     */
    public static void disablePlugin(Plugin plugin) {
        getServer().getPluginManager().disablePlugin(plugin);
    }

    /**
     * Simulate a click on a slot in the players current inventory view.
     *
     * @param player        The player to simulate a click for.
     * @param type          The slot type.
     * @param rawSlotIndex  The raw slot index of the click.
     * @param clickType     The click type.
     * @param action        The inventory action.
     */
    public static void viewClick(Player player, SlotType type, int rawSlotIndex,
                                 ClickType clickType, InventoryAction action) {

        InventoryView view = player.getOpenInventory();
        if (view == null)
            throw new RuntimeException("Cannot click inventory view because the player " +
                    "does not have an open inventory view.");


        if (!(view instanceof MockInventoryView))
            throw new RuntimeException("Cannot click inventory view because its not an " +
                    "instance of MockInventoryView.");

        MockInventoryView mockView = (MockInventoryView)view;

        mockView.click(type, rawSlotIndex, clickType, action);

        pause(1);
    }
}
