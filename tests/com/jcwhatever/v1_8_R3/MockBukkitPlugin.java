package com.jcwhatever.v1_8_R3;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

/**
 * Mock plugin for testing loading of a plugin class.
 */
public class MockBukkitPlugin extends JavaPlugin {

    /**
     * Normal Constructor.
     */
    public MockBukkitPlugin() {}

    /**
     * Test Constructor. (Required on plugins that need to be initialized for testing)
     */
    private MockBukkitPlugin(JavaPluginLoader loader,
                             PluginDescriptionFile descriptionFile, File file1, File file2) {
        super(loader, descriptionFile, file1, file2);
    }

}
