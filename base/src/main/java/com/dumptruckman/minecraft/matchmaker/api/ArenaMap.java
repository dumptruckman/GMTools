package com.dumptruckman.minecraft.matchmaker.api;

import com.dumptruckman.minecraft.matchmaker.api.config.Config;
import com.dumptruckman.minecraft.matchmaker.api.config.MapRecord;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.data.DataException;

import java.io.IOException;

public interface ArenaMap {
    
    String getName();

    Config<MapRecord> record();

    CuboidClipboard getClipboard() throws DataException, IOException;

    /**
     * Saves the Map data to database.
     */
    void save() throws DataException, IOException;
}
