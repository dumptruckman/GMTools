package com.dumptruckman.minecraft.matchmaker.api;

import com.dumptruckman.minecraft.matchmaker.Arenas;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.regions.Region;

import java.io.IOException;

public interface ArenaManager {
    
    Arena getArena(String name);

    Arenas getArenas();

    Arena getIntersectingArena(Region region);

    Arena getArenaAt(String world, BlockVector vector);

    Arena newArena(String name, Region region) throws IllegalArgumentException, IOException;
    
    void loadArenas() throws IOException;

    void loadMap(Arena arena, ArenaMap map, EditSession session) throws IllegalArgumentException, IllegalStateException, DataException, IOException, MaxChangedBlocksException;

    boolean deleteArena(String name);

    void setSelectedArena(String player, Arena arena);

    Arena getSelectedArena(String player);
}
