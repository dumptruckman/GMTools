package com.dumptruckman.minecraft.matchmaker.util;

import com.dumptruckman.minecraft.pluginbase.permission.Perm;

public class Perms {

    public static final Perm CMD_CREATE_ARENA = new Perm.Builder("cmd.arena.create").desc("Gives user access to 'create arena' command.").usePluginName().commandPermission().build();

    public static final Perm CMD_DELETE_ARENA = new Perm.Builder("cmd.arena.delete").desc("Gives user access to 'delete arena' command.").usePluginName().commandPermission().build();

    public static final Perm CMD_SELECT_ARENA = new Perm.Builder("cmd.arena.select").desc("Gives user access to 'delete arena' command.").usePluginName().commandPermission().build();

    public static final Perm CMD_CLEAR_ARENA = new Perm.Builder("cmd.arena.clear").desc("Gives user access to 'delete arena' command.").usePluginName().commandPermission().build();

    public static final Perm CMD_CREATE_MAP = new Perm.Builder("cmd.map.create").desc("Gives user access to 'create map' command.").usePluginName().commandPermission().build();

    public static final Perm CMD_DELETE_MAP = new Perm.Builder("cmd.map.delete").desc("Gives user access to 'delete map' command.").usePluginName().commandPermission().build();

    public static final Perm CMD_LOAD_MAP = new Perm.Builder("cmd.map.load").desc("Gives user access to 'create map' command.").usePluginName().commandPermission().build();
    
    private Perms() {
        throw new AssertionError();
    }
}
