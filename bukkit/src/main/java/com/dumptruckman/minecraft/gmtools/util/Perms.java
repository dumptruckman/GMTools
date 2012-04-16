package com.dumptruckman.minecraft.gmtools.util;

import com.dumptruckman.minecraft.pluginbase.permission.Perm;

public class Perms {

    public static final Perm CMD_TEXT = new Perm.Builder("cmd.text").desc("Gives user access to 'gmtext' command.").usePluginName().commandPermission().build();

    private Perms() {
        throw new AssertionError();
    }
}
