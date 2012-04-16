package com.dumptruckman.minecraft.matchmaker.util;

import com.dumptruckman.minecraft.pluginbase.locale.Message;

public class Language {

    public static final Message CMD_IN_GAME_ONLY = new Message("cmd.in_game_only",
            "This command may only be used in game!");

    public static final Message SELECT_ARENA_FIRST = new Message("cmd.select_arena_first",
            "You must first select an arena! (Or use the arena name arg in command)");

    public static final Message CMD_CREATE_ARENA_NAME = new Message("cmd.create_arena.name",
            "Create an arena.");
    public static final Message CMD_CREATE_ARENA_USAGE = new Message("cmd.create_arena.usage",
            "/%1 create arena &6<name>");
    public static final Message CMD_CREATE_ARENA_SUCCESS = new Message("cmd.create_arena.success",
            "You successfully created new arena named: %1");
    public static final Message CMD_CREATE_ARENA_EXISTING_NAME = new Message("cmd.create_arena.existing_name",
            "There is already an arena named: %1");
    public static final Message CMD_CREATE_ARENA_EXISTING_LOCATION = new Message("cmd.create_arena.existing_location",
            "There is already an arena at this location named: %1");
    public static final Message CMD_CREATE_ARENA_FILE_ISSUE = new Message("cmd.create_arena.file_issue",
            "Something went wrong processing the arena files: %1");

    public static final Message CMD_CLEAR_ARENA_NAME = new Message("cmd.clear_arena.name",
            "Clear the blocks out of an arena.");
    public static final Message CMD_CLEAR_ARENA_USAGE = new Message("cmd.clear_arena.usage",
            "/%1 clear arena &6[name]");
    public static final Message CMD_CLEAR_ARENA_SUCCESS = new Message("cmd.clear_arena.success",
            "You successfully created new arena named: %1");

    public static final Message CMD_CREATE_MAP_NAME = new Message("cmd.create_map.name",
            "Creates a map.");
    public static final Message CMD_CREATE_MAP_USAGE = new Message("cmd.create_map.usage",
            "/%1 save map &6<name>");
    public static final Message CMD_CREATE_MAP_SUCCESS = new Message("cmd.create_map.success",
            "You successfully created new map named: %1");
    public static final Message CMD_CREATE_MAP_EXISTING_NAME = new Message("cmd.create_map.existing_name",
            "There is already an map named: %1");
    public static final Message ERROR_PROCESSING_MAP_FILES = new Message("error.processing_map_files",
            "Something went wrong processing the map files: %1");

    public static final Message CMD_DELETE_MAP_NAME = new Message("cmd.delete_map.name",
            "Deletes a map.");
    public static final Message CMD_DELETE_MAP_USAGE = new Message("cmd.delete_map.usage",
            "/%1 delete map &6<name>");
    public static final Message CMD_DELETE_MAP_SUCCESS = new Message("cmd.delete_map.success",
            "You successfully deleted map named: %1");
    public static final Message CMD_DELETE_MAP_NO_MAP = new Message("cmd.delete_map.no_map",
            "There is no map named: %1");

    public static final Message CMD_DELETE_ARENA_NAME = new Message("cmd.delete_arena.name",
            "Deletes an arena.");
    public static final Message CMD_DELETE_ARENA_USAGE = new Message("cmd.delete_arena.usage",
            "/%1 delete arena &6<name>");
    public static final Message CMD_DELETE_ARENA_SUCCESS = new Message("cmd.delete_arena.success",
            "You successfully deleted arena named: %1");
    public static final Message NO_ARENA_NAMED = new Message("cmd.delete_arena.no_arena",
            "There is no arena named: %1");

    public static final Message CMD_SELECT_ARENA_NAME = new Message("cmd.select_arena.name",
            "Selects an arena optionally selects with WorldEdit.");
    public static final Message CMD_SELECT_ARENA_USAGE = new Message("cmd.select_arena.usage",
            "/%1 select arena &6[name] [--we]");
    public static final Message CMD_SELECT_ARENA_SUCCESS = new Message("cmd.select_arena.success",
            "You've selected '%1'.");
    public static final Message CMD_SELECT_ARENA_SUCCESS_WE = new Message("cmd.select_arena.success_we",
            "You've selected '%1' with WorldEdit.");
    public static final Message NO_ARENA_AT_TARGET = new Message("cmd.select_arena.no_target_arena",
            "There is no arena where you are targeting.");
    public static final Message CMD_SELECT_ARENA_WORLD_UNLOADED = new Message("cmd.select_arena.world_unloaded",
            "Cannot load WorldEdit selection for arena because world '%1' is not loaded!");

    public static final Message MUST_TARGET_BLOCK = new Message("general.must_target_block",
            "You must be targeting a block!");

    public static final Message MUST_COMPLETE_SELECTION = new Message("worldedit.selection.must_complete",
            "You must complete your selection first!");
    public static final Message CLIPBOARD_EMTPY = new Message("worldedit.selection.clipboard_empty",
            "You must copy a selection to your clipboard!");
    public static final Message CUBOID_SELECTION_ONLY = new Message("worldedit.selection.cuboid_only",
            "Selection must be cuboid!");
    public static final Message MAX_CHANGED_BLOCKS = new Message("worldedit.max_change_blocks",
            "You are not allowed to change this many blocks!");

    public static final Message CMD_LOAD_MAP_NAME = new Message("cmd.load_map.name",
            "Load a map into targeted arena.");
    public static final Message CMD_LOAD_MAP_USAGE = new Message("cmd.load_map.usage",
            "/%1 load map &6<mapname> [arenaname]");
    public static final Message CMD_LOAD_MAP_NO_MAP = new Message("cmd.load_map.no_map",
            "'%1' is not a valid map name!");
    public static final Message CMD_LOAD_MAP_NO_ARENA = new Message("cmd.load_map.no_arena",
            "You did not specify an existing arena!");
    public static final Message CMD_LOAD_MAP_SUCCESS = new Message("cmd.load_map.success",
            "Map: '%1' loaded into arena: '%2'");

    public static final Message ARENA_LOAD_MAP = new Message("general.arena.load_map",
            "Map: '%1' does not fit into arena: '%2'!");
    public static final Message ARENA_WORLD_UNLOADED = new Message("general.arena.world_unloaded",
            "World: '%1' for arena: '%2' is not loaded!");
    
    public static void init() { }
}
