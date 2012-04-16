package com.dumptruckman.minecraft.matchmaker.command;

import com.dumptruckman.minecraft.matchmaker.MatchMakerPlugin;
import com.dumptruckman.minecraft.matchmaker.api.Arena;
import com.dumptruckman.minecraft.matchmaker.api.ArenaMap;
import com.dumptruckman.minecraft.matchmaker.util.Language;
import com.dumptruckman.minecraft.matchmaker.util.Perms;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.data.DataException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class DeleteMapCommand extends MMCommand {

    public DeleteMapCommand(MatchMakerPlugin plugin) {
        super(plugin);
        this.setName(messager.getMessage(Language.CMD_DELETE_MAP_NAME));
        this.setCommandUsage(messager.getMessage(Language.CMD_DELETE_MAP_USAGE, plugin.getCommandPrefixes().get(0)));
        this.setArgRange(1, 1);
        this.addPrefixedKey(" delete map");
        this.addPrefixedKey(" remove map");
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " delete map " + ChatColor.GOLD + "Map1");
        this.setPermission(Perms.CMD_DELETE_MAP.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        try {
            plugin.getMapManager().deleteMap(args.get(0));
        } catch (IllegalArgumentException e) {
            messager.sendMessage(sender, e.getMessage());
            return;
        }
        messager.good(Language.CMD_DELETE_MAP_SUCCESS, sender, args.get(0));
    }
}
