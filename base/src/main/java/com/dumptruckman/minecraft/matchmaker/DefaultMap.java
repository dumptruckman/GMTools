package com.dumptruckman.minecraft.matchmaker;

import com.dumptruckman.minecraft.matchmaker.api.ArenaMap;
import com.dumptruckman.minecraft.matchmaker.api.config.Config;
import com.dumptruckman.minecraft.matchmaker.api.config.MapRecord;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.data.DataException;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

class DefaultMap implements ArenaMap {
    
    private Config<MapRecord> record;
    private WeakReference<CuboidClipboard> clipboardWeakReference;
    private File schematicFile;
    private String name;

    DefaultMap(File schematicFile, Config<MapRecord> record) {
        this.record = record;
        this.schematicFile = schematicFile;
        this.name = schematicFile.getName().substring(0, schematicFile.getName().indexOf('.'));
    }

    DefaultMap(File schematicFile, Config<MapRecord> record, CuboidClipboard clipboard) throws DataException, IOException {
        this(schematicFile, record);
        this.clipboardWeakReference = new WeakReference<CuboidClipboard>(clipboard);
        save();
    }
    
    public String getName() {
        return this.name;
    }

    @Override
    public CuboidClipboard getClipboard() throws DataException, IOException {
        WeakReference<CuboidClipboard> clipRef = this.clipboardWeakReference;
        if (clipRef != null) {
            CuboidClipboard clipboard = this.clipboardWeakReference.get();
            if (clipboard == null) {
                clipboard = CuboidClipboard.loadSchematic(this.schematicFile);
                clipRef = new WeakReference<CuboidClipboard>(clipboard);
            }
        } else {
            clipRef = new WeakReference<CuboidClipboard>(CuboidClipboard.loadSchematic(this.schematicFile));
        }

        return clipRef.get();
    }

    @Override
    public Config<MapRecord> record() {
        return record;
    }

    @Override
    public void save() throws DataException, IOException {
        getClipboard().saveSchematic(schematicFile);
        record().save();
    }
}
