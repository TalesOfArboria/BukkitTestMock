package com.jcwhatever.v1_8_R2;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * A mock implementation of {@link org.bukkit.plugin.Plugin}
 */
public class MockPlugin extends JavaPlugin {

    /**
     * Constructor.
     *
     * @param name  The name of the plugin.
     */
    public MockPlugin(String name) {
        super(new JavaPluginLoader(BukkitTester.getServer()),
                generateDescription(name, "v1.0"), new File(""), new File(""));
    }

    /**
     * Enable the plugin.
     *
     * @return  Self for chaining.
     */
    public MockPlugin enable() {
        BukkitTester.getServer().getPluginManager().enablePlugin(this);
        return this;
    }

    /**
     * Disable the plugin.
     *
     * @return  Self for chaining.
     */
    public MockPlugin disable() {
        BukkitTester.getServer().getPluginManager().disablePlugin(this);
        return this;
    }

    private static PluginDescriptionFile generateDescription(String name, String version) {
        PluginDescriptionFile descriptionFile = new PluginDescriptionFile(name, version, "");

        Map<String, Object> descriptionMap = new HashMap<>(10);
        descriptionMap.put("version", version);
        descriptionMap.put("name", name);
        descriptionMap.put("main", MockPlugin.class.getName());
        descriptionMap.put("commands", new HashMap(0));

        try {
            Method method = descriptionFile.getClass().getDeclaredMethod("loadMap", Map.class);
            method.setAccessible(true);

            method.invoke(descriptionFile, descriptionMap);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return descriptionFile;
    }
}
