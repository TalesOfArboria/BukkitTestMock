package com.jcwhatever.bukkit.v1_8_R2;

import com.jcwhatever.bukkit.v1_8_R2.blocks.MockBlock;
import com.jcwhatever.bukkit.v1_8_R2.blocks.MockSign;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A mock implementation of {@link org.bukkit.World}.
 */
public class MockWorld implements World {

    private static Map<String, UUID> _nameIdMap = new HashMap<>(3);

    private final String _name;
    private UUID _uuid;
    private Location _spawnLocation = new Location(this, 0, 0, 0);
    private Map<String, MockChunk> _chunks = new HashMap<>(5);
    private Map<String, MockChunk> _loadedChunks = new HashMap<>(5);
    private long _time;
    private long _fullTime;
    private boolean _hasStorm;
    private int _weatherDuration;
    private boolean _isThundering;
    private int _thunderDuration;
    private Environment _environment = Environment.NORMAL;
    private long _seed;
    private boolean _isPvp;
    private boolean _allowAnimals;
    private boolean _allowMonsters;
    private Map<String, Biome> _biomes = new HashMap<>(35);
    private boolean _keepSpawnInMemory;
    private boolean _isAutoSave = true;
    private Difficulty _difficulty = Difficulty.NORMAL;
    private WorldType _worldType = WorldType.FLAT;
    private boolean _canGenerateStructures;
    private long _ticksPerAnimalSpawn = 20;
    private long _ticksPerMonsterSpawn = 20;
    private int _monsterSpawnLimit = 20;
    private int _animalSpawnLimit = 20;
    private int _waterAnimalSpawnLimit = 20;
    private int _ambientSpawnLimit = 20;

    /**
     * Constructor.
     *
     * @param name  The name of the world.
     */
    MockWorld(String name) {
        _name = name;
        _uuid = _nameIdMap.get(name);
        if (_uuid == null) {
            _uuid = UUID.randomUUID();
            _nameIdMap.put(name, _uuid);
        }
    }

    /**
     * Simulate a player breaking a block in the world.
     *
     * <p>The {@link org.bukkit.event.player.PlayerInteractEvent} is fired first. If the block
     * being broken is not air, the {@link org.bukkit.event.block.BlockBreakEvent} is fired second.</p>
     *
     * @param player  The player.
     * @param x       The X coordinates of the block.
     * @param y       The Y coordinates of the block.
     * @param z       The Z coordinates of the block.
     *
     * @return  True if not cancelled by events.
     */
    public boolean breakBlockNow(Player player, int x, int y, int z) {
        return breakBlockNow(player, getBlockAt(x, y, z));
    }

    /**
     * Simulate a player breaking a block in the world.
     *
     * <p>Acts as if the player is in Creative game mode (event if the
     * player object is not) and only calls the {@link org.bukkit.event.block.BlockBreakEvent}.</p>
     *
     * @param player  The player.
     * @param block   The block to break.
     *
     * @return  True if not cancelled by events.
     */
    public boolean breakBlockNow(Player player, MockBlock block) {

        BlockBreakEvent event = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled())
            return false;

        MockBlock latest = block.getWorld().getBlockAt(block.getX(), block.getY(), block.getZ());

        latest.setType(Material.AIR);
        latest.setData((byte)0);
        return true;
    }

    /**
     * Simulate a player breaking a block in the world.
     *
     * <p>If the player is in {@link org.bukkit.GameMode#CREATIVE} game mode, the block is
     * broken immediately. Otherwise the {@link org.bukkit.event.player.PlayerInteractEvent} is called
     * first followed by a 10 tick delay before calling the {@link org.bukkit.event.block.BlockBreakEvent}
     * and breaking the block</p>
     *
     * <p>The 10 tick delay is a thread pause so there is no need to schedule a task
     * to handle the result. When the method ends the entire event cycle is finished.
     * The Bukkit scheduler service is still running during the 10 tick pause.</p>
     *
     * @param player  The player.
     * @param x       The X coordinates of the block.
     * @param y       The Y coordinates of the block.
     * @param z       The Z coordinates of the block.
     *
     * @return  True if not cancelled by events.
     */
    public boolean breakBlock(Player player, int x, int y, int z) {
        return breakBlock(player, getBlockAt(x, y, z));
    }

    /**
     * Simulate a player breaking a block in the world.
     *
     * <p>If the player is in {@link org.bukkit.GameMode#CREATIVE} game mode, the block is
     * broken immediately. Otherwise the {@link org.bukkit.event.player.PlayerInteractEvent} is called
     * first followed by a 10 tick delay before calling the {@link org.bukkit.event.block.BlockBreakEvent}
     * and breaking the block</p>
     *
     * <p>The 10 tick delay is a thread pause so there is no need to schedule a task
     * to handle the result. When the method ends the entire event cycle is finished.
     * The Bukkit scheduler service is still running during the 10 tick pause.</p>
     *
     * @param player  The player.
     * @param block   The block to break.
     *
     * @return  True if not cancelled by events.
     */
    public boolean breakBlock(Player player, MockBlock block) {

        if (player.getGameMode() == GameMode.CREATIVE)
            return breakBlockNow(player, block);

        Action action = block.getType() == Material.AIR
                ? Action.LEFT_CLICK_BLOCK
                : Action.RIGHT_CLICK_BLOCK;

        // simulate initial dig
        PlayerInteractEvent interactEvent = new PlayerInteractEvent(
                player, action, player.getItemInHand(), block, BlockFace.SOUTH);
        Bukkit.getPluginManager().callEvent(interactEvent);

        if (interactEvent.isCancelled())
            return false;

        // simulate time to break the block. (compressed)
        BukkitTester.pause(10);

        return breakBlockNow(player, block);
    }

    /**
     * Change the block at the specified coordinates to a sign post and
     * fill with the specified lines.
     *
     * <p>There must be 4 lines.</p>
     *
     * @param player  The player that is placing the sign.
     * @param x       The X coordinates.
     * @param y       The Y coordinates.
     * @param z       The Z coordinates.
     * @param lines   The lines.
     */
    public boolean placeSignPost(Player player, int x, int y, int z, String... lines) {
        return placeSign(player, Material.SIGN_POST, x, y, z, lines);
    }

    /**
     * Change the block at the specified coordinates to a wall sign and
     * fill with the specified lines.
     *
     * <p>There must be 4 lines.</p>
     *
     * @param player  The player that is placing the sign.
     * @param x       The X coordinates.
     * @param y       The Y coordinates.
     * @param z       The Z coordinates.
     * @param lines   The lines.
     */
    public boolean placeWallSign(Player player, int x, int y, int z, String... lines) {
        return placeSign(player, Material.WALL_SIGN, x, y, z, lines);
    }

    /**
     * Change the block at the specified coordinates to a sign and
     * fill with the specified lines.
     *
     * <p>There must be 4 lines.</p>
     *
     * @param player    The player that is placing the sign.
     * @param material  The sign type.  Must be {@link org.bukkit.Material#WALL_SIGN} or
     *                  {@link org.bukkit.Material#SIGN_POST}.
     * @param x         The X coordinates.
     * @param y         The Y coordinates.
     * @param z         The Z coordinates.
     * @param lines     The lines.
     */
    public boolean placeSign(Player player, Material material, int x, int y, int z, String... lines) {

        assert material == Material.WALL_SIGN || material == Material.SIGN_POST;

        MockBlock block = getBlockAt(x, y, z);

        MockBlock clone = new MockBlock(block);
        clone.setData((byte)0x2);

        clone.setType(material);
        MockSign sign = (MockSign)clone.getState();
        sign.setLine(0, "");
        sign.setLine(1, "");
        sign.setLine(2, "");
        sign.setLine(3, "");

        MockBlock clickedBlock = block.getRelative(BlockFace.SOUTH);
        player.setItemInHand(new ItemStack(Material.SIGN));

        // call Player interact event
        PlayerInteractEvent interactEvent = new PlayerInteractEvent(
                player, Action.RIGHT_CLICK_BLOCK, player.getItemInHand(),
                clickedBlock, BlockFace.NORTH);

        Bukkit.getPluginManager().callEvent(interactEvent);
        if (interactEvent.isCancelled())
            return false;

        // call block place event
        BlockPlaceEvent placeEvent = new BlockPlaceEvent(block, clone.getState(),
                clickedBlock, player.getItemInHand(), player, true);

        Bukkit.getPluginManager().callEvent(placeEvent);
        if (placeEvent.isCancelled())
            return false;

        // update block state
        sign.update(true);

        // call sign change event
        SignChangeEvent signEvent = new SignChangeEvent(block, player, lines);

        Bukkit.getPluginManager().callEvent(signEvent);
        if (signEvent.isCancelled())
            return false;

        sign.setLine(0, signEvent.getLine(0));
        sign.setLine(1, signEvent.getLine(1));
        sign.setLine(2, signEvent.getLine(2));
        sign.setLine(3, signEvent.getLine(3));

        sign.update();

        return true;
    }

    @Override
    public MockBlock getBlockAt(int x, int y, int z) {
        MockChunk chunk = getChunkAt(getChunkCoords(x), getChunkCoords(z));
        return chunk.getBlock(getRelativeCoord(x), y, getRelativeCoord(z));
    }

    @Override
    public MockBlock getBlockAt(Location location) {
        return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public int getBlockTypeIdAt(int x, int y, int z) {
        MockBlock block = getBlockAt(x, y, z);
        return block.getType().getId();
    }

    @Override
    public int getBlockTypeIdAt(Location location) {
        return getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        MockBlock block = getHighestBlockAt(x, z);
        if (block == null)
            return -1;

        return block.getY();
    }

    @Override
    public int getHighestBlockYAt(Location location) {
        return getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
    }

    @Override
    public MockBlock getHighestBlockAt(int x, int z) {
        MockChunk chunk = getChunkAt(getChunkCoords(x), getChunkCoords(z));
        x = getRelativeCoord(x);
        z = getRelativeCoord(z);

        for (int i= 254; i >= 0; i--) {
            MockBlock block = chunk.getBlock(x, i, z);
            if (block.getType() != Material.AIR)
                return block;
        }
        return null;
    }

    @Override
    public MockBlock getHighestBlockAt(Location location) {
        return getHighestBlockAt(location.getBlockX(), location.getBlockZ());
    }

    @Override
    public MockChunk getChunkAt(int x, int z) {
        String key = getCoordKey(x, z);
        MockChunk chunk = _chunks.get(key);
        if (chunk == null) {
            chunk = new MockChunk(this, x, z);
            _chunks.put(key, chunk);
        }

        return chunk;
    }

    @Override
    public Chunk getChunkAt(Location location) {

        if (!this.equals(location.getWorld()))
            throw new AssertionError("Location is not from the same world.");

        int x = getChunkCoords(location.getX());
        int z = getChunkCoords(location.getZ());

        return getChunkAt(x, z);
    }

    @Override
    public Chunk getChunkAt(Block block) {

        if (!this.equals(block.getLocation().getWorld()))
            throw new AssertionError("Block is not from the same world.");

        return getChunkAt(block.getLocation());
    }

    @Override
    public boolean isChunkLoaded(Chunk chunk) {

        if (!this.equals(chunk.getWorld()))
            throw new AssertionError("Chunk is not from the same world.");

        return _loadedChunks.containsKey(getCoordKey(chunk.getX(), chunk.getZ()));
    }

    @Override
    public Chunk[] getLoadedChunks() {
        return _loadedChunks.values().toArray(new Chunk[_loadedChunks.size()]);
    }

    @Override
    public void loadChunk(Chunk chunk) {

        if (!this.equals(chunk.getWorld()))
            throw new AssertionError("Chunk is not from the same world.");

        loadChunk(chunk.getX(), chunk.getZ());
    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        return _loadedChunks.containsKey(getCoordKey(x, z));
    }

    @Override
    public boolean isChunkInUse(int x, int z) {
        return false;
    }

    @Override
    public void loadChunk(int x, int z) {
        MockChunk mockChunk = getChunkAt(x, z);
        _loadedChunks.put(getCoordKey(x, z), mockChunk);
    }

    @Override
    public boolean loadChunk(int x, int z, boolean generate) {
        loadChunk(x, z);
        return true;
    }

    @Override
    public boolean unloadChunk(Chunk chunk) {
        return unloadChunk(chunk.getX(), chunk.getZ());
    }

    @Override
    public boolean unloadChunk(int x, int z) {
        return _loadedChunks.remove(getCoordKey(x, z)) != null;
    }

    @Override
    public boolean unloadChunk(int x, int z, boolean safe) {
        return unloadChunk(x, z);
    }

    @Override
    public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
        return unloadChunk(x, z);
    }

    @Override
    public boolean unloadChunkRequest(int x, int z) {
        return unloadChunk(x, z);
    }

    @Override
    public boolean unloadChunkRequest(int x, int z, boolean safe) {
        return unloadChunk(x, z);
    }

    @Override
    public boolean regenerateChunk(int x, int z) {
        MockChunk mockChunk = new MockChunk(this, x, z);
        _loadedChunks.put(getCoordKey(x, z), mockChunk);
        return true;
    }

    @Override
    public boolean refreshChunk(int x, int z) {
        return true;
    }

    @Override
    public Item dropItem(Location location, ItemStack itemStack) {
        return null;
    }

    @Override
    public Item dropItemNaturally(Location location, ItemStack itemStack) {
        return null;
    }

    @Override
    public Arrow spawnArrow(Location location, Vector vector, float v, float v1) {
        return null;
    }

    @Override
    public boolean generateTree(Location location, TreeType treeType) {
        return false;
    }

    @Override
    public boolean generateTree(Location location, TreeType treeType, BlockChangeDelegate blockChangeDelegate) {
        return false;
    }

    @Override
    public Entity spawnEntity(Location location, EntityType entityType) {
        return null;
    }

    @Override
    public LivingEntity spawnCreature(Location location, EntityType entityType) {
        return null;
    }

    @Override
    public LivingEntity spawnCreature(Location location, CreatureType creatureType) {
        return null;
    }

    @Override
    public LightningStrike strikeLightning(Location location) {
        return null;
    }

    @Override
    public LightningStrike strikeLightningEffect(Location location) {
        return null;
    }

    @Override
    public List<Entity> getEntities() {
        return new ArrayList<>(0);
    }

    @Override
    public List<LivingEntity> getLivingEntities() {
        return new ArrayList<>(0);
    }

    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... classes) {

        Set<T> result = new HashSet<>(10);

        for (Class<T> aClass : classes) {
            result.addAll(getEntitiesByClass(aClass));
        }

        return result;
    }

    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> aClass) {

        if (Player.class.isAssignableFrom(aClass)) {

            List<Player> players = getPlayers();
            List<T> result = new ArrayList<>(players.size());
            for (Player p : players) {

                @SuppressWarnings("unchecked")
                T t = (T)p;

                result.add(t);
            }

            return result;
        }

        return new ArrayList<>(0);
    }

    @Override
    public Collection<Entity> getEntitiesByClasses(Class<?>... classes) {

        Set<Entity> result = new HashSet<>(10);

        for (Class<?> aClass : classes) {
            if (Player.class.isAssignableFrom(aClass)) {
                result.addAll(getPlayers());
            }
        }

        return result;
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> result = new ArrayList<>(10);
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player p : players) {
            if (this.equals(p.getWorld()))
                result.add(p);
        }
        return result;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public UUID getUID() {
        return _uuid;
    }

    @Override
    public Location getSpawnLocation() {
        return _spawnLocation;
    }

    @Override
    public boolean setSpawnLocation(int x, int y, int z) {
        _spawnLocation = new Location(this, x, y, z);
        return true;
    }

    @Override
    public long getTime() {
        return _time;
    }

    @Override
    public void setTime(long l) {
        _time = l;
    }

    @Override
    public long getFullTime() {
        return _fullTime;
    }

    @Override
    public void setFullTime(long l) {
        _fullTime = l;
    }

    @Override
    public boolean hasStorm() {
        return _hasStorm;
    }

    @Override
    public void setStorm(boolean hasStorm) {
        _hasStorm = hasStorm;
    }

    @Override
    public int getWeatherDuration() {
        return _weatherDuration;
    }

    @Override
    public void setWeatherDuration(int i) {
        _weatherDuration = i;
    }

    @Override
    public boolean isThundering() {
        return _isThundering;
    }

    @Override
    public void setThundering(boolean isThundering) {
        _isThundering = isThundering;
    }

    @Override
    public int getThunderDuration() {
        return _thunderDuration;
    }

    @Override
    public void setThunderDuration(int duration) {
        _thunderDuration = duration;
    }

    @Override
    public boolean createExplosion(double v, double v1, double v2, float v3) {
        return true;
    }

    @Override
    public boolean createExplosion(double v, double v1, double v2, float v3, boolean b) {
        return true;
    }

    @Override
    public boolean createExplosion(double v, double v1, double v2, float v3, boolean b, boolean b1) {
        return true;
    }

    @Override
    public boolean createExplosion(Location location, float v) {
        return true;
    }

    @Override
    public boolean createExplosion(Location location, float v, boolean b) {
        return true;
    }

    @Override
    public Environment getEnvironment() {
        return _environment;
    }

    /**
     * Set the world environment.
     *
     * @param environment  The environment.
     */
    public void setEnvironment(Environment environment) {
        _environment = environment;
    }

    @Override
    public long getSeed() {
        return _seed;
    }

    /**
     * Set the world seed.
     *
     * @param seed  The seed.
     */
    public void setSeed(long seed) {
        _seed = seed;
    }

    @Override
    public boolean getPVP() {
        return _isPvp;
    }

    @Override
    public void setPVP(boolean b) {
        _isPvp = b;
    }

    @Override
    public ChunkGenerator getGenerator() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public List<BlockPopulator> getPopulators() {
        return new ArrayList<>(0);
    }

    @Override
    public <T extends Entity> T spawn(Location location, Class<T> aClass) throws IllegalArgumentException {
        return null;
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, Material material, byte b) throws IllegalArgumentException {
        return null;
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, int i, byte b) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void playEffect(Location location, Effect effect, int i) {

    }

    @Override
    public void playEffect(Location location, Effect effect, int i, int i1) {

    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T t) {

    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T t, int i) {

    }

    @Override
    public ChunkSnapshot getEmptyChunkSnapshot(int i, int i1, boolean b, boolean b1) {
        return null;
    }

    @Override
    public void setSpawnFlags(boolean b, boolean b1) {

    }

    @Override
    public boolean getAllowAnimals() {
        return _allowAnimals;
    }

    /**
     * Set animals allowed in world.
     *
     * @param allow  True to allow.
     */
    public void setAllowAnimals(boolean allow) {
        _allowAnimals = allow;
    }

    @Override
    public boolean getAllowMonsters() {
        return _allowMonsters;
    }

    /**
     * Set monsters allowed in world.
     *
     * @param allow  True to allow.
     */
    public void setAllowMonsters(boolean allow) {
        _allowMonsters = allow;
    }

    @Override
    public Biome getBiome(int x, int z) {
        Biome biome = _biomes.get(getCoordKey(x, z));
        return biome != null ? biome : Biome.PLAINS;
    }

    @Override
    public void setBiome(int x, int z, Biome biome) {
        _biomes.put(getCoordKey(x, z), biome);
    }

    @Override
    public double getTemperature(int i, int i1) {
        return 0;
    }

    @Override
    public double getHumidity(int i, int i1) {
        return 0;
    }

    @Override
    public int getMaxHeight() {
        return 255;
    }

    @Override
    public int getSeaLevel() {
        return 10;
    }

    @Override
    public boolean getKeepSpawnInMemory() {
        return _keepSpawnInMemory;
    }

    @Override
    public void setKeepSpawnInMemory(boolean b) {
        _keepSpawnInMemory = b;
    }

    @Override
    public boolean isAutoSave() {
        return _isAutoSave;
    }

    @Override
    public void setAutoSave(boolean b) {
        _isAutoSave = b;
    }

    @Override
    public void setDifficulty(Difficulty difficulty) {

        if (difficulty == null)
            throw new AssertionError("Difficulty cannot be null");

        _difficulty = difficulty;
    }

    @Override
    public Difficulty getDifficulty() {
        return _difficulty;
    }

    @Override
    public File getWorldFolder() {
        return null;
    }

    @Override
    public WorldType getWorldType() {
        return _worldType;
    }

    /**
     * Set the world type.
     *
     * @param worldType  The world type.
     */
    public void setWorldType(WorldType worldType) {
        _worldType = worldType;
    }

    @Override
    public boolean canGenerateStructures() {
        return _canGenerateStructures;
    }

    /**
     * Set world structure generation.
     *
     * @param canGenerate  True to allow structure generation.
     */
    public void setCanGenerateStructures(boolean canGenerate) {
        _canGenerateStructures = canGenerate;
    }

    @Override
    public long getTicksPerAnimalSpawns() {
        return _ticksPerAnimalSpawn;
    }

    @Override
    public void setTicksPerAnimalSpawns(int ticks) {
        _ticksPerAnimalSpawn = ticks;
    }

    @Override
    public long getTicksPerMonsterSpawns() {
        return _ticksPerMonsterSpawn;
    }

    @Override
    public void setTicksPerMonsterSpawns(int ticks) {
        _ticksPerMonsterSpawn = ticks;
    }

    @Override
    public int getMonsterSpawnLimit() {
        return _monsterSpawnLimit;
    }

    @Override
    public void setMonsterSpawnLimit(int limit) {
        _monsterSpawnLimit = limit;
    }

    @Override
    public int getAnimalSpawnLimit() {
        return _animalSpawnLimit;
    }

    @Override
    public void setAnimalSpawnLimit(int limit) {
        _animalSpawnLimit = limit;
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        return _waterAnimalSpawnLimit;
    }

    @Override
    public void setWaterAnimalSpawnLimit(int limit) {
        _waterAnimalSpawnLimit = limit;
    }

    @Override
    public int getAmbientSpawnLimit() {
        return _ambientSpawnLimit;
    }

    @Override
    public void setAmbientSpawnLimit(int limit) {
        _ambientSpawnLimit = limit;
    }

    @Override
    public void playSound(Location location, Sound sound, float v, float v1) {

    }

    @Override
    public String[] getGameRules() {
        return new String[0];
    }

    @Override
    public String getGameRuleValue(String s) {
        return null;
    }

    @Override
    public boolean setGameRuleValue(String s, String s1) {
        return false;
    }

    @Override
    public boolean isGameRule(String s) {
        return false;
    }

    @Override
    public Spigot spigot() {
        return null;
    }

    @Override
    public WorldBorder getWorldBorder() {
        return null;
    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {

    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        return null;
    }

    @Override
    public boolean hasMetadata(String s) {
        return false;
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {

    }

    @Override
    public void sendPluginMessage(Plugin plugin, String s, byte[] bytes) {

    }

    @Override
    public Set<String> getListeningPluginChannels() {
        return null;
    }

    @Override
    public int hashCode() {
        return _name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof World && ((World) obj).getName().equals(_name);
    }

    private int getChunkCoords(double worldCoord) {
        return (int)Math.floor(worldCoord / 16.0D);
    }

    private int getRelativeCoord(double worldCoord) {
        return (int)worldCoord - getChunkCoords(worldCoord);
    }

    private String getCoordKey(int x, int z) {
        return String.valueOf(x) + '.' + z;
    }

}
