package com.dumptruckman.gmtools.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author dumptruckman
 */
public enum Perms {
    GM_TEXT (new Permission("gmtools.gm_text", PermissionDefault.OP)),
    GM_FIREBALL (new Permission("gmtools.gm_fireball", PermissionDefault.OP)),
    BED_SPAWN (new Permission("gmtools.bed_spawn", PermissionDefault.TRUE)),
    ;

    private Permission perm;
    
    Perms(Permission perm) {
        this.perm = perm;
    }

    private Permission getPerm() {
        return perm;
    }

    public boolean has(CommandSender sender) {
        return sender.hasPermission(perm);
    }

    public static void load(JavaPlugin plugin) {
        PluginManager pm = plugin.getServer().getPluginManager();
        for (Perms perm : Perms.values()) {
            pm.addPermission(perm.getPerm());
        }
    }
}
