package com.dumptruckman.minecraft.matchmaker;

import com.dumptruckman.minecraft.matchmaker.api.ArenaMap;
import com.dumptruckman.minecraft.matchmaker.api.MapManager;
import com.dumptruckman.minecraft.matchmaker.api.config.MapRecord;
import com.dumptruckman.minecraft.matchmaker.util.Language;
import com.dumptruckman.minecraft.matchmaker.util.YamlConfig;
import com.dumptruckman.minecraft.pluginbase.locale.Messager;
import com.dumptruckman.minecraft.pluginbase.util.Logging;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.data.DataException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

class DefaultMapManager implements MapManager {

    private Maps maps = new Maps();
    MatchMakerPlugin matchMaker;
    
    DefaultMapManager(MatchMakerPlugin matchMaker) {
        this.matchMaker = matchMaker;
    }

    @Override
    public ArenaMap getMap(String name) {
        ArenaMap arenaMap = null;
        for (ArenaMap map : getMaps()) {
            if (map.getName().equalsIgnoreCase(name)) {
                arenaMap = map;
                break;
            }
        }
        if (arenaMap == null) {
            arenaMap = loadMap(name);
        }
        return arenaMap;
    }

    public Maps getMaps() {
        return maps;
    }

    public ArenaMap newMap(String name, CuboidClipboard clipboard) throws IllegalArgumentException, IOException, DataException {
        Messager messager = matchMaker.getMessager();
        if (getMap(name) != null) {
            throw new IllegalArgumentException(messager.getMessage(Language.CMD_CREATE_MAP_EXISTING_NAME, name));
        }
        
        File mapRecords = new File(matchMaker.getMapFolder(), "records");
        ArenaMap map = Maps.newMap(new File(matchMaker.getMapFolder(), name + ".schematic"),
                new YamlConfig<MapRecord>(matchMaker, false, new File(
                        mapRecords, name + ".yml"), MapRecord.class), clipboard);
        maps.add(map);
        return map;
    }

    private File getMapFile(String name) {
        return new File(matchMaker.getMapFolder(), name + ".schematic");
    }

    private File getRecordFile(String name) {
        File recordFolder = new File(matchMaker.getMapFolder(), "records");
        recordFolder.mkdirs();
        return new File(recordFolder, name + ".yml");
    }

    private ArenaMap loadMap(String name) {
        File mapFolder = matchMaker.getMapFolder();
        mapFolder.mkdirs();
        File mapFile = getMapFile(name);
        if (!mapFile.exists())  {
            return null;
        }
        try {
            ArenaMap map = Maps.newMap(mapFile,
                    new YamlConfig<MapRecord>(matchMaker, false, getRecordFile(name), MapRecord.class));
            maps.add(map);
            return map;
        } catch (IOException e) {
            Logging.severe("Error loading map!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteMap(String name) throws IllegalArgumentException {
        File mapFile = getMapFile(name);
        if (!mapFile.exists()) {
            throw new IllegalArgumentException(matchMaker.getMessager().getMessage(Language.CMD_DELETE_MAP_NO_MAP, name));
        }
        removeFromMaps(name);
        return mapFile.delete();
    }

    private void removeFromMaps(String name) {
        Iterator<ArenaMap> it = maps.iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(name)) {
                it.remove();
            }
        }
    }
}
