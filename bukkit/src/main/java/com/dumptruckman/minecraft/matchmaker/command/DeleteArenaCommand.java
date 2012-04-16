package com.dumptruckman.minecraft.matchmaker.command;

import com.dumptruckman.minecraft.matchmaker.MatchMakerPlugin;
import com.dumptruckman.minecraft.matchmaker.util.Language;
import com.dumptruckman.minecraft.matchmaker.util.Perms;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DeleteArenaCommand extends MMCommand {

    public DeleteArenaCommand(MatchMakerPlugin plugin) {
        super(plugin);
        this.setName(messager.getMessage(Language.CMD_DELETE_ARENA_NAME));
        this.setCommandUsage(messager.getMessage(Language.CMD_DELETE_ARENA_USAGE, plugin.getCommandPrefixes().get(0)));
        this.setArgRange(1, 1);
        this.addPrefixedKey(" delete arena");
        this.addPrefixedKey(" remove arena");
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " delete arena " + ChatColor.GOLD + "Arena1");
        this.setPermission(Perms.CMD_DELETE_ARENA.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        try {
            plugin.getArenaManager().deleteArena(args.get(0));
        } catch (IllegalArgumentException e) {
            messager.sendMessage(sender, e.getMessage());
            return;
        }
        messager.good(Language.CMD_DELETE_ARENA_SUCCESS, sender, args.get(0));
    }
}
