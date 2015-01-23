package com.jcwhatever.bukkit.v1_8_R1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests {@link BukkitTest}
 */
public class BukkitTestTest {

    @BeforeClass
    public static void init() {
        BukkitTest.init();
    }

    /**
     * Make sure {@code #init} is working correctly.
     */
    @Test
    public void testInit() throws Exception {
        assertTrue(Bukkit.getServer() instanceof MockServer);
    }

    /**
     * Make sure {@code #getServer} returns the correct value.
     */
    @Test
    public void testGetServer() throws Exception {

        MockServer server = BukkitTest.getServer();

        assertNotNull(server);
        assertEquals(server, Bukkit.getServer());
    }

    /**
     * Make sure {@code #heartBear} works correctly.
     */
    @Test
    public void testHeartBeat() throws Exception {

        int startTicks = BukkitTest._currentTick;

        long end = System.currentTimeMillis() + (10 * 50); // end in 10 ticks

        while (System.currentTimeMillis() < end) {
            BukkitTest.heartBeat();
        }

        assertTrue(BukkitTest._currentTick > startTicks + 8 &&
                   BukkitTest._currentTick < startTicks + 12 );
    }

    /**
     * Make sure {@code #pause} works correctly.
     */
    @Test
    public void testPause() throws Exception {

        int startTicks = BukkitTest._currentTick;

        BukkitTest.pause(10); // pause 10 ticks

        assertEquals(startTicks + 10, BukkitTest._currentTick);
    }

    /**
     * Make sure {@code #login} works correctly.
     */
    @Test
    public void testLogin() throws Exception {

        assertEquals(null, Bukkit.getPlayer("playerName"));

        BukkitTest.login("playerName");

        assertNotNull(Bukkit.getPlayer("playerName"));
    }

    /**
     * Make sure {@code #logout} works correctly.
     */
    @Test
    public void testLogout() throws Exception {

        BukkitTest.login("playerName");

        assertNotNull(Bukkit.getPlayer("playerName"));

        BukkitTest.logout("playerName");

        assertEquals(null, Bukkit.getPlayer("playerName"));
    }

    /**
     * Make sure {@code #kick} works correctly.
     */
    @Test
    public void testKick() throws Exception {

        BukkitTest.login("playerName");

        assertNotNull(Bukkit.getPlayer("playerName"));

        BukkitTest.kick("playerName");

        assertEquals(null, Bukkit.getPlayer("playerName"));
    }

    /**
     * Make sure {@code #world} works correctly.
     */
    @Test
    public void testWorld() throws Exception {

        assertEquals(null, Bukkit.getWorld("testworld"));

        World world = BukkitTest.world("testworld");

        assertTrue(world instanceof MockWorld);

        assertEquals(world, Bukkit.getWorld("testworld"));
    }

    /**
     * Make sure {@code #mockPlugin} works correctly.
     */
    @Test
    public void testMockPlugin() throws Exception {

        assertEquals(null, Bukkit.getPluginManager().getPlugin("plugin"));

        Plugin plugin = BukkitTest.mockPlugin("plugin");

        assertTrue(plugin instanceof MockPlugin);

        assertEquals(plugin, Bukkit.getPluginManager().getPlugin("plugin"));
    }

    /**
     * Make sure {@code #initPlugin} works correctly.
     */
    @Test
    public void testInitPlugin() throws Exception {

        assertEquals(null, Bukkit.getPluginManager().getPlugin("bukkitPlugin"));

        Plugin plugin = BukkitTest.initPlugin("bukkitPlugin", "1.0", MockBukkitPlugin.class);

        assertTrue(plugin instanceof MockBukkitPlugin);

        assertEquals(plugin, Bukkit.getPluginManager().getPlugin("bukkitPlugin"));
    }

    /**
     * Make sure {@code #enablePlugin} works correctly on mock plugins.
     */
    @Test
    public void testEnableMockPlugin() throws Exception {

        Plugin mockPlugin = new MockPlugin("enableMockPluginTest");

        assertEquals(false, mockPlugin.isEnabled());

        BukkitTest.enablePlugin(mockPlugin);

        assertEquals(true, mockPlugin.isEnabled());
    }

    /**
     * Make sure {@code #enablePlugin} works correctly on plugins.
     */
    @Test
    public void testEnablePlugin() throws Exception {

        Plugin plugin = BukkitTest.initPlugin("enablePluginTest", "1.0", MockBukkitPlugin.class);

        assertEquals(false, plugin.isEnabled());

        BukkitTest.enablePlugin(plugin);

        assertEquals(true, plugin.isEnabled());
    }

    /**
     * Make sure {@code #disablePlugin} works correctly on mock plugins.
     */
    @Test
    public void testDisableMockPlugin() throws Exception {

        Plugin plugin = new MockPlugin("disableMockPluginTest");

        BukkitTest.enablePlugin(plugin);
        assertEquals(true, plugin.isEnabled());

        BukkitTest.disablePlugin(plugin);
        assertEquals(false, plugin.isEnabled());
    }

    /**
     * Make sure {@code #disablePlugin} works correctly on plugins.
     */
    @Test
    public void testDisablePlugin() throws Exception {

        Plugin plugin = BukkitTest.initPlugin("disablePluginTest", "1.0", MockBukkitPlugin.class);

        BukkitTest.enablePlugin(plugin);
        assertEquals(true, plugin.isEnabled());

        BukkitTest.disablePlugin(plugin);
        assertEquals(false, plugin.isEnabled());
    }

    /**
     * Make sure {@code #viewClick} works correctly.
     */
    @Test
    public void testViewClick() throws Exception {

        Player player = BukkitTest.login("viewClick");

        try {
            BukkitTest.viewClick(player, SlotType.CONTAINER, 0, ClickType.LEFT, InventoryAction.PICKUP_ALL);
            throw new AssertionError("RuntimeException expected.");
        }
        catch(RuntimeException ignore) {}

        Inventory inventory = Bukkit.createInventory(player, InventoryType.CHEST);
        player.openInventory(inventory);

        BukkitTest.viewClick(player, SlotType.CONTAINER, 0, ClickType.LEFT, InventoryAction.PICKUP_ALL);
    }
}