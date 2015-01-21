package com.jcwhatever.bukkit.v1_8_R1;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

/**
 * Mock implementation of a chunk.
 */
public class MockChunk implements Chunk {

    private World _world;
    private int _x;
    private int _z;

    /**
     * Constructor.
     *
     * @param world  The world the chunk is in.
     * @param x      The chunk x coordinates.
     * @param z      The chunk z coordinates.
     */
    public MockChunk(World world, int x, int z) {
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
    public World getWorld() {
        return _world;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return new MockBlock(_world, y <= 10 ? Material.STONE : Material.AIR, _x + x, y, _z + z);
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
        return false;
    }

    @Override
    public boolean load(boolean b) {
        return false;
    }

    @Override
    public boolean load() {
        return false;
    }

    @Override
    public boolean unload(boolean b, boolean b1) {
        return false;
    }

    @Override
    public boolean unload(boolean b) {
        return false;
    }

    @Override
    public boolean unload() {
        return false;
    }
}
