package com.dumptruckman.minecraft.matchmaker;

import com.dumptruckman.minecraft.matchmaker.api.Arena;
import com.dumptruckman.minecraft.matchmaker.api.ArenaMap;
import com.dumptruckman.minecraft.matchmaker.api.ArenaMatch;
import com.dumptruckman.minecraft.matchmaker.api.config.ArenaConfig;
import com.dumptruckman.minecraft.matchmaker.api.config.ArenaRecord;
import com.dumptruckman.minecraft.matchmaker.api.config.Config;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;

import java.util.Iterator;

class DefaultArena extends DefaultRegion<ArenaConfig> implements Arena {
    
    private ArenaMap map = null;

    private Config<ArenaRecord> record;

    DefaultArena(Config<ArenaConfig> config, Config<ArenaRecord> record) {
        super(config);
        this.record = record;
    }

    DefaultArena(String name, Region region, Config<ArenaConfig> config, Config<ArenaRecord> record) {
        super(region, config);
        this.record = record;
        config.set(ArenaConfig.NAME, name);
        config.set(ArenaConfig.WORLD, region.getWorld().getName());
        config.save();
    }

    @Override
    public String getName() {
        return config().get(ArenaConfig.NAME);
    }

    @Override
    public void setName(String name) {
        config().set(ArenaConfig.NAME, name);
    }

    @Override
    public ArenaMap getMap() {
        return map;
    }

    @Override
    public void setMap(ArenaMap map) {
        this.map = map;
    }

    public boolean isMapValid(CuboidClipboard clipboard) {
        return getLength() == clipboard.getLength()
                && getWidth() == clipboard.getWidth()
                && getHeight() == clipboard.getHeight();
    }

    @Override
    public ArenaMatch getMatch() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setMatch(ArenaMatch match) throws IllegalStateException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Config<ArenaRecord> record() {
        return record;
    }

    @Override
    public void save() {
        config().save();
        record().save();
    }

    @Override
    public String toString() {
        return getName() + ": " + getMinimumPoint() + " - " + getMaximumPoint();
    }

    @Override
    public String getWorld() {
        return config().get(ArenaConfig.WORLD);
    }

    @Override
    public void setWorld(String world) {
        config().set(ArenaConfig.WORLD, world);
    }

    @Override
    public Iterator<BlockVector> iterator() {
        final Vector min = getMinimumPoint();
        final Vector max = getMaximumPoint();

        return new Iterator<BlockVector>() {

            private int nextX = min.getBlockX();
            private int nextY = min.getBlockY();
            private int nextZ = min.getBlockZ();

            public boolean hasNext() {
                return (nextX != Integer.MIN_VALUE);
            }

            public BlockVector next() {
                if (!hasNext()) throw new java.util.NoSuchElementException();
                BlockVector answer = new BlockVector(nextX, nextY, nextZ);
                if (++nextX > max.getBlockX()) {
                    nextX = min.getBlockX();
                    if (++nextY > max.getBlockY()) {
                        nextY = min.getBlockY();
                        if (++nextZ > max.getBlockZ()) {
                            nextX = Integer.MIN_VALUE;
                        }
                    }
                }
                return answer;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
