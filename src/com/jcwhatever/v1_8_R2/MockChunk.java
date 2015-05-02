package com.jcwhatever.v1_8_R2;

import com.jcwhatever.v1_8_R2.blocks.MockBlock;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        return new MockChunkSnapshot(_world, _x, _z, _blocks);
    }

    @Override
    public ChunkSnapshot getChunkSnapshot(boolean b, boolean b1, boolean b2) {
        return new MockChunkSnapshot(_world, _x, _z, _blocks);
    }

    @Override
    public Entity[] getEntities() {
        return new Entity[0];
    }

    @Override
    public BlockState[] getTileEntities() {

        List<BlockState> blockStates = new ArrayList<>(10);

        for (MockBlock block : _blocks.values()) {
            switch (block.getType()) {
                case WALL_SIGN:
                case SIGN_POST:
                    blockStates.add(block.getState());
                break;
            }
        }

        return blockStates.toArray(new BlockState[blockStates.size()]);
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
