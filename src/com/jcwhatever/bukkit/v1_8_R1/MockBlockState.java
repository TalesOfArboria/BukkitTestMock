package com.jcwhatever.bukkit.v1_8_R1;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Mock implementation of {@link BlockState}.
 */
public class MockBlockState implements BlockState {

    private MockWorld _world;
    private Material _material;
    private Biome _biome = Biome.BEACH;
    private byte _data;
    private int _x;
    private int _y;
    private int _z;

    public MockBlockState(MockBlock mockBlock) {
        _world = mockBlock._world;
        _material = mockBlock._material;
        _biome = mockBlock._biome;
        _data = mockBlock._data;
        _x = mockBlock._x;
        _y = mockBlock._y;
        _z = mockBlock._z;
    }

    @Override
    public Block getBlock() {
        return _world.getBlockAt(_x, _y, _z);
    }

    @Override
    public MaterialData getData() {
        return new MaterialData(_material.getId(), _data);
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
        return 7;
    }

    @Override
    public World getWorld() {
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
        location.setX(_x);
        location.setY(_y);
        location.setZ(_z);
        return location;
    }

    @Override
    public Chunk getChunk() {
        return _world.getChunkAt(getLocation());
    }

    @Override
    public void setData(MaterialData materialData) {
        _material = materialData.getItemType();
        _data = materialData.getData();
    }

    @Override
    public void setType(Material material) {
        _material = material;
    }

    @Override
    public boolean setTypeId(int i) {

        Material material = Material.getMaterial(i);
        if (material == null)
            return false;

        _material = material;
        return true;
    }

    @Override
    public boolean update() {
        return update(false);
    }

    @Override
    public boolean update(boolean force) {
        MockBlock block = _world.getBlockAt(_x, _y, _z);

        if (!force && block._material != _material)
            return false;

        block._material = _material;
        block._data = _data;
        block._biome = _biome;
        return true;
    }

    @Override
    public boolean update(boolean force, boolean b1) {
        return update(force);
    }

    @Override
    public byte getRawData() {
        return _data;
    }

    @Override
    public void setRawData(byte b) {
        _data = b;
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
