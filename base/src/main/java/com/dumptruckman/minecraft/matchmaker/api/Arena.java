package com.dumptruckman.minecraft.matchmaker.api;

import com.dumptruckman.minecraft.matchmaker.api.config.ArenaConfig;
import com.dumptruckman.minecraft.matchmaker.api.config.ArenaRecord;
import com.dumptruckman.minecraft.matchmaker.api.config.Config;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.CuboidClipboard;

import java.io.IOException;

/**
 * Represents a single Arena location in the world.
 */
public interface Arena extends MMRegion<ArenaConfig>, Iterable<BlockVector> {
    
    String getName();
    
    void setName(String name);
    
    ArenaMap getMap();
    
    void setMap(ArenaMap map);

    boolean isMapValid(CuboidClipboard clipboard);
    
    ArenaMatch getMatch();
    
    void setMatch(ArenaMatch match) throws IllegalStateException;
    
    Config<ArenaConfig> config();
    
    Config<ArenaRecord> record();

    /**
     * Saves the Arena data to database.
     */
    void save();

    String getWorld();

    void setWorld(String localWorld);
}
