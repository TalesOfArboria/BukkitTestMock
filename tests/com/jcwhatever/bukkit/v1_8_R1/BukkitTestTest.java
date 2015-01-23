package com.jcwhatever.bukkit.v1_8_R1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.jcwhatever.bukkit.v1_8_R1.events.BukkitEventTester;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests {@link BukkitTester}
 */
public class BukkitTestTest {

    @BeforeClass
    public static void init() {
        BukkitTester.init();
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

        MockServer server = BukkitTester.getServer();

        assertNotNull(server);
        assertEquals(server, Bukkit.getServer());
    }

    /**
     * Make sure {@code #heartBear} works correctly.
     */
    @Test
    public void testHeartBeat() throws Exception {

        int startTicks = BukkitTester._currentTick;

        long end = System.currentTimeMillis() + (10 * 50); // end in 10 ticks

        while (System.currentTimeMillis() < end) {
            BukkitTester.heartBeat();
        }

        assertTrue(BukkitTester._currentTick > startTicks + 8 &&
                   BukkitTester._currentTick < startTicks + 12 );
    }

    /**
     * Make sure {@code #pause} works correctly.
     */
    @Test
    public void testPause() throws Exception {

        int startTicks = BukkitTester._currentTick;

        BukkitTester.pause(10); // pause 10 ticks

        assertEquals(startTicks + 10, BukkitTester._currentTick);
    }

    /**
     * Make sure {@code #login} works correctly.
     */
    @Test
    public void testLogin() throws Exception {

        BukkitEventTester.reset(PlayerLoginEvent.class);
        BukkitEventTester.reset(PlayerJoinEvent.class);

        assertEquals(null, Bukkit.getPlayer("playerName"));

        BukkitTester.login("playerName");

        assertNotNull(Bukkit.getPlayer("playerName"));

        assertEquals(1, BukkitEventTester.countEvent(PlayerLoginEvent.class));
        assertEquals(1, BukkitEventTester.countEvent(PlayerJoinEvent.class));
    }

    /**
     * Make sure {@code #logout} works correctly.
     */
    @Test
    public void testLogout() throws Exception {

        BukkitEventTester.reset(PlayerQuitEvent.class);

        BukkitTester.login("playerName");

        assertNotNull(Bukkit.getPlayer("playerName"));

        BukkitTester.logout("playerName");

        assertEquals(null, Bukkit.getPlayer("playerName"));

        assertEquals(1, BukkitEventTester.countEvent(PlayerQuitEvent.class));
    }

    /**
     * Make sure {@code #kick} works correctly.
     */
    @Test
    public void testKick() throws Exception {

        BukkitEventTester.reset(PlayerQuitEvent.class);
        BukkitEventTester.reset(PlayerKickEvent.class);

        BukkitTester.login("playerName");

        assertNotNull(Bukkit.getPlayer("playerName"));

        BukkitTester.kick("playerName");

        assertEquals(null, Bukkit.getPlayer("playerName"));

        assertEquals(1, BukkitEventTester.countEvent(PlayerQuitEvent.class));
        assertEquals(1, BukkitEventTester.countEvent(PlayerKickEvent.class));
    }

    /**
     * Make sure {@code #world} works correctly.
     */
    @Test
    public void testWorld() throws Exception {

        BukkitEventTester.reset(WorldLoadEvent.class);

        assertEquals(null, Bukkit.getWorld("testworld"));

        World world = BukkitTester.world("testworld");

        assertTrue(world instanceof MockWorld);

        assertEquals(world, Bukkit.getWorld("testworld"));

        BukkitEventTester.countEvent(WorldLoadEvent.class);
    }

    /**
     * Make sure {@code #mockPlugin} works correctly.
     */
    @Test
    public void testMockPlugin() throws Exception {

        assertEquals(null, Bukkit.getPluginManager().getPlugin("plugin"));

        Plugin plugin = BukkitTester.mockPlugin("plugin");

        assertTrue(plugin instanceof MockPlugin);

        assertEquals(plugin, Bukkit.getPluginManager().getPlugin("plugin"));
    }

    /**
     * Make sure {@code #initPlugin} works correctly.
     */
    @Test
    public void testInitPlugin() throws Exception {

        assertEquals(null, Bukkit.getPluginManager().getPlugin("bukkitPlugin"));

        Plugin plugin = BukkitTester.initPlugin("bukkitPlugin", "1.0", MockBukkitPlugin.class);

        assertTrue(plugin instanceof MockBukkitPlugin);

        assertEquals(plugin, Bukkit.getPluginManager().getPlugin("bukkitPlugin"));
    }

    /**
     * Make sure {@code #enablePlugin} works correctly on mock plugins.
     */
    @Test
    public void testEnableMockPlugin() throws Exception {

        BukkitEventTester.reset(PluginEnableEvent.class);

        Plugin mockPlugin = new MockPlugin("enableMockPluginTest");

        assertEquals(false, mockPlugin.isEnabled());

        BukkitTester.enablePlugin(mockPlugin);

        assertEquals(true, mockPlugin.isEnabled());

        assertEquals(1, BukkitEventTester.countEvent(PluginEnableEvent.class));
    }

    /**
     * Make sure {@code #enablePlugin} works correctly on plugins.
     */
    @Test
    public void testEnablePlugin() throws Exception {

        BukkitEventTester.reset(PluginEnableEvent.class);

        Plugin plugin = BukkitTester.initPlugin("enablePluginTest", "1.0", MockBukkitPlugin.class);

        assertEquals(false, plugin.isEnabled());

        BukkitTester.enablePlugin(plugin);

        assertEquals(true, plugin.isEnabled());

        assertEquals(1, BukkitEventTester.countEvent(PluginEnableEvent.class));
    }

    /**
     * Make sure {@code #disablePlugin} works correctly on mock plugins.
     */
    @Test
    public void testDisableMockPlugin() throws Exception {

        BukkitEventTester.reset(PluginDisableEvent.class);

        Plugin plugin = new MockPlugin("disableMockPluginTest");

        BukkitTester.enablePlugin(plugin);
        assertEquals(true, plugin.isEnabled());

        BukkitTester.disablePlugin(plugin);
        assertEquals(false, plugin.isEnabled());

        assertEquals(1, BukkitEventTester.countEvent(PluginDisableEvent.class));
    }

    /**
     * Make sure {@code #disablePlugin} works correctly on plugins.
     */
    @Test
    public void testDisablePlugin() throws Exception {

        BukkitEventTester.reset(PluginDisableEvent.class);

        Plugin plugin = BukkitTester.initPlugin("disablePluginTest", "1.0", MockBukkitPlugin.class);

        BukkitTester.enablePlugin(plugin);
        assertEquals(true, plugin.isEnabled());

        BukkitTester.disablePlugin(plugin);
        assertEquals(false, plugin.isEnabled());

        assertEquals(1, BukkitEventTester.countEvent(PluginDisableEvent.class));
    }

    /**
     * Make sure {@code #viewClick} works correctly.
     */
    @Test
    public void testViewClick() throws Exception {

        Player player = BukkitTester.login("viewClick");

        try {
            BukkitTester.viewClick(player, SlotType.CONTAINER, 0, ClickType.LEFT, InventoryAction.PICKUP_ALL);
            throw new AssertionError("RuntimeException expected.");
        }
        catch(RuntimeException ignore) {}

        Inventory inventory = Bukkit.createInventory(player, InventoryType.CHEST);
        player.openInventory(inventory);

        BukkitEventTester.reset(InventoryClickEvent.class);

        BukkitTester.viewClick(player, SlotType.CONTAINER, 0, ClickType.LEFT, InventoryAction.PICKUP_ALL);

        assertEquals(1, BukkitEventTester.countEvent(InventoryClickEvent.class));
    }
}