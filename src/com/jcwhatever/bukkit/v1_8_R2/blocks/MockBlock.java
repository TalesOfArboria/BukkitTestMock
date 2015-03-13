package com.jcwhatever.bukkit.v1_8_R2.blocks;

import com.jcwhatever.bukkit.v1_8_R2.MockChunk;
import com.jcwhatever.bukkit.v1_8_R2.MockWorld;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

/**
 * A mock implementation of {@link org.bukkit.block.Block}.
 */
public class MockBlock implements Block {

    MockWorld _world;
    Material _material;
    Biome _biome = Biome.BEACH;
    byte _data;
    int _x;
    int _y;
    int _z;

    MockBlockState _currentState;



    /**
     * Constructor.
     *
     * @param world     The world the block is in.
     * @param material  The block material.
     * @param x         The x coordinates.
     * @param y         The y coordinates.
     * @param z         The z coordinates.
     */
    public MockBlock(World world, Material material, int x, int y, int z) {
        this(world, material, (byte)0, x, y, z);
    }

    /**
     * Constructor.
     *
     * @param world     The world the block is in.
     * @param material  The block material.
     * @param data      The block data.
     * @param x         The x coordinates.
     * @param y         The y coordinates.
     * @param z         The z coordinates.
     */
    public MockBlock(World world, Material material, byte data, int x, int y, int z) {
        _world = (MockWorld)world;
        _material = material;
        _data = data;
        _x = x;
        _y = y;
        _z = z;
        _currentState = MockBlockState.create(this);
    }

    /**
     * Constructor.
     *
     * @param mockBlock  {@link MockBlock} to copy.
     */
    public MockBlock(MockBlock mockBlock) {
        _world = mockBlock._world;
        _material = mockBlock._material;
        _biome = mockBlock._biome;
        _data = mockBlock._data;
        _x = mockBlock._x;
        _y = mockBlock._y;
        _z = mockBlock._z;
        _currentState = new MockBlockState(mockBlock._currentState);
    }

    /**
     * Get the default mock block.
     *
     * @param world   The world the block is in.
     * @param chunkX  The X coordinates of the chunk the block is in.
     * @param chunkZ  The Z coordinates of the chunk the block is in.
     * @param x       The relative X coordinates.
     * @param y       The Y coordinates.
     * @param z       The relative Z coordinates.
     */
    public static MockBlock getNewBlock(MockWorld world, int chunkX, int chunkZ, int x, int y, int z) {
        return new MockBlock(world, y <= 10 ? Material.STONE : Material.AIR, chunkX + x, y, chunkZ + z);
    }

    @Override
    public byte getData() {
        return _data;
    }

    @Override
    public MockBlock getRelative(int x, int y, int z) {
        return _world.getBlockAt(_x + x, _y + y, _z + z);
    }

    @Override
    public MockBlock getRelative(BlockFace blockFace) {
        return _world.getBlockAt(_x + blockFace.getModX(), _y + blockFace.getModY(), _z + blockFace.getModZ());
    }

    @Override
    public MockBlock getRelative(BlockFace blockFace, int i) {
        return _world.getBlockAt(
                _x + (blockFace.getModX() * i),
                _y + (blockFace.getModY() * i),
                _z + (blockFace.getModZ() * i));
    }

    @Override
    public Material getType() {
        return _material;
    }

    @Override
    public int getTypeId() {
        return _material.getId();
    }

    @Override
    public byte getLightLevel() {
        return 15;
    }

    @Override
    public byte getLightFromSky() {
        return 15;
    }

    @Override
    public byte getLightFromBlocks() {
        return 15;
    }

    @Override
    public MockWorld getWorld() {
        return _world;
    }

    @Override
    public int getX() {
        return _x;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public int getZ() {
        return _z;
    }

    @Override
    public Location getLocation() {
        return new Location(_world, _x, _y, _z);
    }

    @Override
    public Location getLocation(Location location) {
        return new Location(_world, _x, _y, _z);
    }

    @Override
    public Chunk getChunk() {
        return new MockChunk(_world, (int)Math.floor(_x / 16.0D), (int)Math.floor(_z / 16.0D));
    }

    @Override
    public void setData(byte b) {
        _data = b;
        _currentState = MockBlockState.create(this);
    }

    @Override
    public void setData(byte b, boolean b1) {
        _data = b;
        _currentState = MockBlockState.create(this);
    }

    @Override
    public void setType(Material material) {
        _material = material;
        _currentState = MockBlockState.create(this);
    }

    @Override
    public boolean setTypeId(int i) {
        Material material = Material.getMaterial(i);
        if (material != null) {
            _material = material;
            _currentState = MockBlockState.create(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean setTypeId(int i, boolean b) {
        Material material = Material.getMaterial(i);
        if (material != null) {
            _material = material;
            _currentState = MockBlockState.create(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean setTypeIdAndData(int i, byte b, boolean b1) {
        Material material = Material.getMaterial(i);
        if (material != null) {
            _material = material;
            _currentState = MockBlockState.create(this);
            return true;
        }
        return false;
    }

    @Override
    public BlockFace getFace(Block block) {
        return BlockFace.NORTH;
    }

    @Override
    public MockBlockState getState() {
        return MockBlockState.create(this);
    }

    @Override
    public Biome getBiome() {
        return _biome;
    }

    @Override
    public void setBiome(Biome biome) {
        _biome = biome;
    }

    @Override
    public boolean isBlockPowered() {
        return false;
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        return false;
    }

    @Override
    public boolean isBlockFacePowered(BlockFace blockFace) {
        return false;
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace blockFace) {
        return false;
    }

    @Override
    public int getBlockPower(BlockFace blockFace) {
        return 0;
    }

    @Override
    public int getBlockPower() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isLiquid() {
        return false;
    }

    @Override
    public double getTemperature() {
        return 0;
    }

    @Override
    public double getHumidity() {
        return 0;
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return PistonMoveReaction.MOVE;
    }

    @Override
    public boolean breakNaturally() {
        return false;
    }

    @Override
    public boolean breakNaturally(ItemStack itemStack) {
        return false;
    }

    @Override
    public Collection<ItemStack> getDrops() {
        return null;
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack itemStack) {
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
}
