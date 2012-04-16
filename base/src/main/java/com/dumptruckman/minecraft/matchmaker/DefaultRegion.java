package com.dumptruckman.minecraft.matchmaker;

import com.dumptruckman.minecraft.matchmaker.api.MMRegion;
import com.dumptruckman.minecraft.matchmaker.api.config.ArenaConfig;
import com.dumptruckman.minecraft.matchmaker.api.config.Config;
import com.dumptruckman.minecraft.matchmaker.api.config.RegionConfig;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.data.ChunkStore;
import com.sk89q.worldedit.regions.Region;

import java.util.HashSet;
import java.util.Set;

class DefaultRegion<V extends RegionConfig<V>> implements MMRegion<V> {

    private Config<V> config;
    
    DefaultRegion(Config<V> config) {
        this.config = config;
    }

    DefaultRegion(Region region, Config<V> config) {
        this.config = config;
        Vector min = region.getMinimumPoint();
        Vector max = region.getMaximumPoint();
        config.set(ArenaConfig.MIN_X, min.getBlockX());
        config.set(ArenaConfig.MIN_Y, min.getBlockY());
        config.set(ArenaConfig.MIN_Z, min.getBlockZ());
        config.set(ArenaConfig.MAX_X, max.getBlockX());
        config.set(ArenaConfig.MAX_Y, max.getBlockY());
        config.set(ArenaConfig.MAX_Z, max.getBlockZ());
    }

    @Override
    public Config<V> config() {
        return config;
    }

    @Override
    public Vector getMinimumPoint() {
        return new Vector(config().get(ArenaConfig.MIN_X), config().get(ArenaConfig.MIN_Y), config().get(ArenaConfig.MIN_Z));
    }

    @Override
    public Vector getMaximumPoint() {
        return new Vector(config().get(ArenaConfig.MAX_X), config().get(ArenaConfig.MAX_Y), config().get(ArenaConfig.MAX_Z));
    }

    @Override
    public int getArea() {
        Vector min = getMinimumPoint();
        Vector max = getMaximumPoint();
        return (int)((max.getX() - min.getX() + 1) *
                (max.getY() - min.getY() + 1) *
                (max.getZ() - min.getZ() + 1));
    }

    @Override
    public int getWidth() {
        Vector min = getMinimumPoint();
        Vector max = getMaximumPoint();
        return (int) (max.getX() - min.getX() + 1);
    }

    @Override
    public int getHeight() {
        Vector min = getMinimumPoint();
        Vector max = getMaximumPoint();
        return (int) (max.getY() - min.getY() + 1);
    }

    @Override
    public int getLength() {
        Vector min = getMinimumPoint();
        Vector max = getMaximumPoint();
        return (int) (max.getZ() - min.getZ() + 1);
    }

    @Override
    public boolean contains(Vector vector) {
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();

        Vector min = getMinimumPoint();
        Vector max = getMaximumPoint();

        return x >= min.getBlockX() && x <= max.getBlockX()
                && y >= min.getBlockY() && y <= max.getBlockY()
                && z >= min.getBlockZ() && z <= max.getBlockZ();
    }

    @Override
    public Set<Vector2D> getChunks() {
        Set<Vector2D> chunks = new HashSet<Vector2D>();

        Vector min = getMinimumPoint();
        Vector max = getMaximumPoint();

        for (int x = min.getBlockX(); x <= max.getBlockX(); ++x) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); ++y) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); ++z) {
                    Vector pt = new Vector(x, y, z);
                    chunks.add(ChunkStore.toChunk(pt));
                }
            }
        }

        return chunks;
    }
}
