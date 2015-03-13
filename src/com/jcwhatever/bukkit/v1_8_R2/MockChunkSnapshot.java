package com.jcwhatever.bukkit.v1_8_R2;

import com.jcwhatever.bukkit.v1_8_R2.blocks.MockBlock;

import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Mock implementation of {@link ChunkSnapshot}
 */
public class MockChunkSnapshot implements ChunkSnapshot {

    private final Map<String, MockBlock> _blocks = new HashMap<>(10);
    private final MockWorld _world;
    private final String _worldName;
    private final int _x;
    private final int _z;

    /**
     * Constructor.
     * @param world   The world the chunk is int.
     * @param x       The chunk x coordinates.
     * @param z       The chunk z coordinates.
     * @param blocks  The mock blocks in the chunk.
     */
    public MockChunkSnapshot(MockWorld world, int x, int z, Map<String, MockBlock> blocks) {
        _world = world;
        _worldName = world.getName();
        _x = x;
        _z = z;

        for (Entry<String, MockBlock> entry : blocks.entrySet()) {
            _blocks.put(entry.getKey(), new MockBlock(entry.getValue()));
        }
    }

    public MockBlock getBlock(int x, int y, int z) {
        MockBlock block = _blocks.get(getBlockKey(x, y, z));
        if (block == null) {
            block = MockBlock.getNewBlock(_world, _x, _z, x, y, z);
            _blocks.put(getBlockKey(x, y, z), block);
        }

        return  block;
    }

    @Override
    public int getX() {
        return _x;
    }

    @Override
    public int getZ() {
        return _z;
    }

    @Override
    public String getWorldName() {
        return _worldName;
    }

    @Override
    public int getBlockTypeId(int x, int y, int z) {
        MockBlock block = getBlock(x, y, z);
        return block.getType().getId();
    }

    @Override
    public int getBlockData(int x, int y, int z) {
        MockBlock block = getBlock(x, y, z);
        return block.getData();
    }

    @Override
    public int getBlockSkyLight(int x, int y, int z) {
        return 15;
    }

    @Override
    public int getBlockEmittedLight(int x, int y, int z) {
        return 7;
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        return 0;
    }

    @Override
    public Biome getBiome(int x, int z) {
        MockBlock block = getBlock(x, 0, z);
        return block.getBiome();
    }

    @Override
    public double getRawBiomeTemperature(int x, int z) {
        return 0;
    }

    @Override
    public double getRawBiomeRainfall(int x, int z) {
        return 0;
    }

    @Override
    public long getCaptureFullTime() {
        return 0;
    }

    @Override
    public boolean isSectionEmpty(int i) {
        return false;
    }

    private String getBlockKey(int x, int y, int z) {
        return String.valueOf(x) + '.' + y + '.' + z;
    }
}
