package com.jcwhatever.bukkit;

import com.jcwhatever.bukkit.v1_8_R1.MockServer;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.scheduler.CraftScheduler;
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

    private static boolean _isInit;
    private static long _nextHeartBeat;
    private static int _currentTick = 0;

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

        try {
            Bukkit.setServer(new MockServer());
        }
        catch (UnsupportedOperationException ignore) {
            return false;
        }

        return true;
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
     * <p>The plugin is initialized without commands. The returned instance is already
     * enabled.</p>
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

        // instantiate plugin
        try {

            Constructor<T> constructor = pluginClass.getDeclaredConstructor(
                    JavaPluginLoader.class, PluginDescriptionFile.class, File.class, File.class);
            constructor.setAccessible(true);

            plugin = constructor.newInstance(new JavaPluginLoader(Bukkit.getServer()),
                    descriptionFile, new File(""), new File(""));

            try {
                plugin = pluginClass.newInstance();
            }
            catch (IllegalStateException ignore) {}

        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        // enable plugin
        try {
            Method method = JavaPlugin.class.getDeclaredMethod("setEnabled", boolean.class);
            method.setAccessible(true);

            method.invoke(plugin, true);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return plugin;
    }
}
