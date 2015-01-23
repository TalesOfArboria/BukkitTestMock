package com.jcwhatever.bukkit.v1_8_R1;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock implementation of a chunk.
 */
public class MockChunk implements Chunk {

    private final Map<String, MockBlock> _blocks = new HashMap<>(10);
    private final MockWorld _world;
    private final int _x;
    private final int _z;

    private boolean _isLoaded;

    /**
     * Constructor.
     *
     * @param world  The world the chunk is in.
     * @param x      The chunk x coordinates.
     * @param z      The chunk z coordinates.
     */
    public MockChunk(MockWorld world, int x, int z) {
        _world = world;
        _x = x;
        _z = z;
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
    public MockWorld getWorld() {
        return _world;
    }

    @Override
    public MockBlock getBlock(int x, int y, int z) {

        MockBlock block = _blocks.get(getBlockKey(x, y, z));
        if (block == null) {
            block = MockBlock.getNewBlock(_world, _x, _z, x, y, z);
            _blocks.put(getBlockKey(x, y, z), block);
        }

        return  block;
    }

    @Override
    public ChunkSnapshot getChunkSnapshot() {
        return null;
    }

    @Override
    public ChunkSnapshot getChunkSnapshot(boolean b, boolean b1, boolean b2) {
        return null;
    }

    @Override
    public Entity[] getEntities() {
        return new Entity[0];
    }

    @Override
    public BlockState[] getTileEntities() {
        return new BlockState[0];
    }

    @Override
    public boolean isLoaded() {
        return _isLoaded;
    }

    @Override
    public boolean load(boolean b) {
        return _isLoaded = true;
    }

    @Override
    public boolean load() {
        return _isLoaded = true;
    }

    @Override
    public boolean unload(boolean b, boolean b1) {
        return !(_isLoaded = false);
    }

    @Override
    public boolean unload(boolean b) {
        return !(_isLoaded = false);
    }

    @Override
    public boolean unload() {
        return !(_isLoaded = false);
    }

    private String getBlockKey(int x, int y, int z) {
        return String.valueOf(x) + '.' + y + '.' + z;
    }
}
