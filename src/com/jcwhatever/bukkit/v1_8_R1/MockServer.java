package com.jcwhatever.bukkit.v1_8_R1;

import com.avaje.ebean.config.ServerConfig;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.UnsafeValues;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemFactory;
import org.bukkit.craftbukkit.v1_8_R1.scheduler.CraftScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * A mock implementation of {@link org.bukkit.Server}.
 */
public class MockServer implements Server {

    private Thread _homeThread;
    private final SimpleCommandMap _commandMap;
    private final MockPluginManager _pluginManager;
    private final CraftScheduler _scheduler = new CraftScheduler();
    private final Map<String, MockPlayer> _playerByName = new HashMap<>(5);
    private final Map<UUID, MockPlayer> _playersById = new HashMap<>(5);
    private Map<String, World> _worldsByName = new HashMap<>(5);
    private Map<UUID, World> _worldsById = new HashMap<>(5);

    private int _maxPlayers = 100;
    private int _port = 2356;
    private int _viewDistance = 16;
    private String _ip = "127.0.0.1";
    private String _serverName = "Dummy";
    private String _serverId = "Dummy";
    private String _worldType = "DEFAULT";
    private boolean _hasGeneratedStructures = true;
    private GameMode _defaultGameMode = GameMode.SURVIVAL;
    private int _spawnRadius = 5;
    private boolean _onlineMode;
    private boolean _allowFlight;
    private boolean _isHardcore;
    private boolean _useExactLoginLocation;
    private int _monsterSpawnLimit = 20;
    private int _animalSpawnLimit = 20;
    private int _waterAnimalSpawnLimit = 20;
    private int _ambientSpawnLimit = 20;
    private int _idleTimeOut;

    /**
     * Constructor.
     */
    public MockServer() {
        _homeThread = Thread.currentThread();

        _commandMap = new SimpleCommandMap(this);
        _pluginManager = new MockPluginManager(this, _commandMap);
    }

    /**
     * Login a mock player and return the {@code MockPlayer}
     * instance. If the player is already logged in, the current
     * player is returned.
     *
     * @param playerName  The name of the player.
     */
    public MockPlayer login(String playerName) {
        MockPlayer player = _playerByName.get(playerName.toLowerCase());
        if (player == null) {
            player = new MockPlayer(playerName);
            _playerByName.put(playerName.toLowerCase(), player);
            _playersById.put(player.getUniqueId(), player);

            try {
                PlayerLoginEvent event = new PlayerLoginEvent(
                        player, "dummyHost", InetAddress.getLocalHost());
                _pluginManager.callEvent(event);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return player;
    }

    /**
     * Logout a player by name.
     *
     * @param playerName  The name of the player to logout.
     *
     * @return  True if the player was found and removed.
     */
    public boolean logout(String playerName) {
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
    public boolean logout(String playerName, String message) {

        MockPlayer player = _playerByName.remove(playerName.toLowerCase());
        if (player == null)
            return false;

        _playersById.remove(player.getUniqueId());

        _pluginManager.callEvent(new PlayerQuitEvent(player, message));

        return true;
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
    public boolean kick(String playerName, String reason, String message) {

        MockPlayer player = _playerByName.remove(playerName.toLowerCase());
        if (player == null)
            return false;

        _playersById.remove(player.getUniqueId());

        _pluginManager.callEvent(new PlayerKickEvent(player, reason, message));

        _pluginManager.callEvent(new PlayerQuitEvent(player, message));

        return true;
    }

    /**
     * Add a world to the server or get an existing one.
     *
     * @param name  The name of the world.
     *
     * @return  The world.
     */
    public MockWorld world(String name) {

        World world = _worldsByName.get(name.toLowerCase());
        if (world != null)
            return (MockWorld)world;

        MockWorld mockWorld = new MockWorld(name);

        _worldsByName.put(name.toLowerCase(), mockWorld);
        _worldsById.put(mockWorld.getUID(), mockWorld);
        return mockWorld;
    }

    @Override
    public String getName() {
        return "Dummy";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getBukkitVersion() {
        return "v8_1_R1";
    }

    @Override
    public Player[] _INVALID_getOnlinePlayers() {
        return new Player[0];
    }

    @Override
    public Collection<? extends Player> getOnlinePlayers() {
        return new ArrayList<>(_playerByName.values());
    }

    @Override
    public int getMaxPlayers() {
        return _maxPlayers;
    }

    /**
     * Set the max number of players on the server.
     *
     * @param max  The max players.
     */
    public void setMaxPlayers(int max) {
        _maxPlayers = max;
    }

    @Override
    public int getPort() {
        return _port;
    }

    /**
     * Set the server port.
     *
     * @param port  The port.
     */
    public void setPort(int port) {
        _port = port;
    }

    @Override
    public int getViewDistance() {
        return _viewDistance;
    }

    /**
     * Set the server view distance.
     *
     * @param viewDistance  The view distance.
     */
    public void setViewDistance(int viewDistance) {
        _viewDistance = viewDistance;
    }

    @Override
    public String getIp() {
        return _ip;
    }

    public void setIp(String ip) {
        _ip = ip;
    }

    @Override
    public String getServerName() {
        return _serverName;
    }

    /**
     * Set the server name.
     *
     * @param serverName  The name of the server.
     */
    public void setServerName(String serverName) {
        _serverName = serverName;
    }

    @Override
    public String getServerId() {
        return _serverId;
    }

    /**
     * Set the server Id.
     *
     * @param id  The id of the server.
     */
    public void setServerId(String id) {
        _serverId = id;
    }

    @Override
    public String getWorldType() {
        return _worldType;
    }

    /**
     * Set the server world type.
     *
     * @param worldType  The world type.
     */
    public void setWorldType(String worldType) {
        _worldType = worldType;
    }

    @Override
    public boolean getGenerateStructures() {
        return _hasGeneratedStructures;
    }

    /**
     * Set flag to determine if structures are generated.
     *
     * @param isGenerated  True to generate.
     */
    public void setGeneratedStructures(boolean isGenerated) {
        _hasGeneratedStructures = isGenerated;
    }

    private boolean _allowEnd;

    @Override
    public boolean getAllowEnd() {
        return _allowEnd;
    }

    /**
     * Set flag to determine if the End is allowed
     * for the server.
     *
     * @param allow  True to allow
     */
    public void setAllowEnd(boolean allow) {
        _allowEnd = allow;
    }

    private boolean _allowNether;

    @Override
    public boolean getAllowNether() {
        return _allowNether;
    }

    /**
     * Set flag to determine if the Nether is allows
     * for the server.
     *
     * @param allow  True to allow.
     */
    public void setAllowNether(boolean allow) {
        _allowNether = allow;
    }

    @Override
    public boolean hasWhitelist() {
        return false;
    }

    @Override
    public void setWhitelist(boolean b) {

    }

    @Override
    public Set<OfflinePlayer> getWhitelistedPlayers() {
        return new HashSet<>(0);
    }

    @Override
    public void reloadWhitelist() {

    }

    @Override
    public int broadcastMessage(String s) {
        return _playerByName.size();
    }

    @Override
    public String getUpdateFolder() {
        return null;
    }

    @Override
    public File getUpdateFolderFile() {
        return null;
    }

    @Override
    public long getConnectionThrottle() {
        return 0;
    }

    @Override
    public int getTicksPerAnimalSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerMonsterSpawns() {
        return 0;
    }

    @Override
    public Player getPlayer(String s) {
        return _playerByName.get(s.toLowerCase());
    }

    @Override
    public Player getPlayerExact(String s) {
        return _playerByName.get(s);
    }

    @Override
    public List<Player> matchPlayer(String s) {

        List<Player> result = new ArrayList<>(10);

        for (MockPlayer player : _playerByName.values()) {
            if (player.getName().contains(s))
                result.add(player);
        }

        return result;
    }

    @Override
    public Player getPlayer(UUID uuid) {
        return _playersById.get(uuid);
    }

    @Override
    public PluginManager getPluginManager() {
        return _pluginManager;
    }

    @Override
    public BukkitScheduler getScheduler() {
        return _scheduler;
    }

    @Override
    public ServicesManager getServicesManager() {
        return null;
    }

    @Override
    public List<World> getWorlds() {
        return new ArrayList<>(_worldsByName.values());
    }

    @Override
    public World createWorld(WorldCreator worldCreator) {
        return null;
    }

    @Override
    public boolean unloadWorld(String s, boolean b) {
        return false;
    }

    @Override
    public boolean unloadWorld(World world, boolean b) {
        return false;
    }

    @Override
    public World getWorld(String s) {
        return _worldsByName.get(s.toLowerCase());
    }

    @Override
    public World getWorld(UUID uuid) {
        return _worldsById.get(uuid);
    }

    @Override
    public MapView getMap(short i) {
        return null;
    }

    @Override
    public MapView createMap(World world) {
        return null;
    }

    @Override
    public void reload() {

        _pluginManager.disablePlugins();
        for (Plugin plugin : _pluginManager.getPlugins()) {
            _pluginManager.enablePlugin(plugin);
        }
    }

    @Override
    public Logger getLogger() {
        return Logger.global;
    }

    @Override
    public PluginCommand getPluginCommand(String s) {
        return null;
    }

    @Override
    public void savePlayers() {

    }

    @Override
    public boolean dispatchCommand(CommandSender commandSender, String s) throws CommandException {
        return false;
    }

    @Override
    public void configureDbConfig(ServerConfig serverConfig) {

    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        return false;
    }

    @Override
    public List<Recipe> getRecipesFor(ItemStack itemStack) {
        return null;
    }

    @Override
    public Iterator<Recipe> recipeIterator() {
        return null;
    }

    @Override
    public void clearRecipes() {

    }

    @Override
    public void resetRecipes() {

    }

    @Override
    public Map<String, String[]> getCommandAliases() {
        return null;
    }

    @Override
    public int getSpawnRadius() {
        return _spawnRadius;
    }

    @Override
    public void setSpawnRadius(int blocks) {
        _spawnRadius = blocks;
    }

    @Override
    public boolean getOnlineMode() {
        return _onlineMode;
    }

    /**
     * Set the servers online mode.
     *
     * @param isOnline  True for online, false for offline.
     */
    public void setOnlineMode(boolean isOnline) {
        _onlineMode = isOnline;
    }

    @Override
    public boolean getAllowFlight() {
        return _allowFlight;
    }

    /**
     * Set the servers allow flight policy.
     *
     * @param allowFlight  True to allow, false to deny.
     */
    public void setAllowFlight(boolean allowFlight) {
        _allowFlight = allowFlight;
    }

    @Override
    public boolean isHardcore() {
        return _isHardcore;
    }

    /**
     * Set the server hardcore mode.
     *
     * @param isHardcore  True for hardcore, false for normal.
     */
    public void setIsHardcore(boolean isHardcore) {
        _isHardcore = isHardcore;
    }

    @Override
    public boolean useExactLoginLocation() {
        return _useExactLoginLocation;
    }

    /**
     * Set use the exact login location.
     *
     * @param useExact  True to use exact
     */
    public void setUseExactLoginLocation(boolean useExact) {
        _useExactLoginLocation = useExact;
    }

    @Override
    public void shutdown() {
        System.out.println("The server is shutting down.");
    }

    @Override
    public int broadcast(String message, String permission) {
        Collection<? extends Player> players = getOnlinePlayers();
        int count = 0;
        for (Player p : players) {
            if (p.hasPermission(permission)) {
                p.sendRawMessage(message);
                count++;
            }
        }

        return count;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String s) {
        return null;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        return null;
    }

    @Override
    public Set<String> getIPBans() {
        return new HashSet<>(0);
    }

    @Override
    public void banIP(String s) {

    }

    @Override
    public void unbanIP(String s) {

    }

    @Override
    public Set<OfflinePlayer> getBannedPlayers() {
        return new HashSet<>(0);
    }

    @Override
    public BanList getBanList(Type type) {
        return null;
    }

    @Override
    public Set<OfflinePlayer> getOperators() {
        return new HashSet<>(0);
    }

    @Override
    public GameMode getDefaultGameMode() {
        return _defaultGameMode;
    }

    @Override
    public void setDefaultGameMode(GameMode gameMode) {
        _defaultGameMode = gameMode;
    }

    @Override
    public ConsoleCommandSender getConsoleSender() {
        return null;
    }

    @Override
    public File getWorldContainer() {
        return null;
    }

    @Override
    public OfflinePlayer[] getOfflinePlayers() {
        return new OfflinePlayer[0];
    }

    @Override
    public Messenger getMessenger() {
        return null;
    }

    @Override
    public HelpMap getHelpMap() {
        return null;
    }

    @Override
    public Inventory createInventory(InventoryHolder inventoryHolder, InventoryType inventoryType) {
        return new MockInventory((Player)inventoryHolder, inventoryType, inventoryType.getDefaultSize());
    }

    @Override
    public Inventory createInventory(InventoryHolder inventoryHolder, InventoryType inventoryType, String s) {
        return new MockInventory((Player)inventoryHolder, inventoryType, inventoryType.getDefaultSize());
    }

    @Override
    public Inventory createInventory(InventoryHolder inventoryHolder, int i) throws IllegalArgumentException {
        return new MockInventory((Player)inventoryHolder, InventoryType.CHEST, i);
    }

    @Override
    public Inventory createInventory(InventoryHolder inventoryHolder, int i, String s) throws IllegalArgumentException {
        return new MockInventory((Player)inventoryHolder, InventoryType.CHEST, i);
    }

    @Override
    public int getMonsterSpawnLimit() {
        return _monsterSpawnLimit;
    }

    /**
     * Set the servers monster spawn limit.
     *
     * @param limit  The limit.
     */
    public void setMonsterSpawnLimit(int limit) {
        _monsterSpawnLimit = limit;
    }

    @Override
    public int getAnimalSpawnLimit() {
        return _animalSpawnLimit;
    }

    /**
     * Set the servers animal spawn limit.
     *
     * @param limit  The limit.
     */
    public void setAnimalSpawnLimit(int limit) {
        _animalSpawnLimit = limit;
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        return _waterAnimalSpawnLimit;
    }

    /**
     * Set the servers water animal spawn limit.
     *
     * @param limit  The limit.
     */
    public void setWaterAnimalSpawnLimit(int limit) {
        _waterAnimalSpawnLimit = limit;
    }

    @Override
    public int getAmbientSpawnLimit() {
        return _ambientSpawnLimit;
    }

    /**
     * Set the servers ambient spawn limit.
     *
     * @param limit  The limit.
     */
    public void setAmbientSpawnLimit(int limit) {
        _ambientSpawnLimit = limit;
    }

    @Override
    public boolean isPrimaryThread() {
        return Thread.currentThread().equals(_homeThread);
    }

    @Override
    public String getMotd() {
        return "dummy motd";
    }

    @Override
    public String getShutdownMessage() {
        return "dummy shutdown";
    }

    @Override
    public WarningState getWarningState() {
        return WarningState.DEFAULT;
    }

    @Override
    public ItemFactory getItemFactory() {
        return CraftItemFactory.instance();
    }

    @Override
    public ScoreboardManager getScoreboardManager() {
        return null;
    }

    @Override
    public CachedServerIcon getServerIcon() {
        return null;
    }

    @Override
    public CachedServerIcon loadServerIcon(File file) throws IllegalArgumentException, Exception {
        return null;
    }

    @Override
    public CachedServerIcon loadServerIcon(BufferedImage bufferedImage) throws IllegalArgumentException, Exception {
        return null;
    }

    @Override
    public void setIdleTimeout(int minutes) {
        _idleTimeOut = minutes;
    }

    @Override
    public int getIdleTimeout() {
        return _idleTimeOut;
    }

    @Override
    public UnsafeValues getUnsafe() {
        return null;
    }

    @Override
    public Spigot spigot() {
        return null;
    }

    @Override
    public void sendPluginMessage(Plugin plugin, String s, byte[] bytes) {

    }

    @Override
    public Set<String> getListeningPluginChannels() {
        return null;
    }
}
