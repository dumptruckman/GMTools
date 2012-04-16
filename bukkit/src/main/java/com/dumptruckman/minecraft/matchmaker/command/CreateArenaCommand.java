package com.dumptruckman.minecraft.matchmaker.command;

import com.dumptruckman.minecraft.matchmaker.MatchMakerPlugin;
import com.dumptruckman.minecraft.matchmaker.api.Arena;
import com.dumptruckman.minecraft.matchmaker.util.Language;
import com.dumptruckman.minecraft.matchmaker.util.Perms;
import com.dumptruckman.minecraft.pluginbase.util.Logging;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class CreateArenaCommand extends MMCommand {

    public CreateArenaCommand(MatchMakerPlugin plugin) {
        super(plugin);
        this.setName(messager.getMessage(Language.CMD_CREATE_ARENA_NAME));
        this.setCommandUsage(messager.getMessage(Language.CMD_CREATE_ARENA_USAGE, plugin.getCommandPrefixes().get(0)));
        this.setArgRange(1, 1);
        for (String prefix : plugin.getCommandPrefixes()) {
            this.addKey(prefix + " create arena");
        }
        this.addCommandExample("/" + plugin.getCommandPrefixes().get(0) + " create arena " + ChatColor.GOLD + "Arena1");
        this.setPermission(Perms.CMD_CREATE_ARENA.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player)) {
            messager.bad(Language.CMD_IN_GAME_ONLY, sender);
            return;
        }
        Player player = (Player) sender;
        Selection selection = plugin.getWorldEdit().getSelection(player);
        Logging.fine("Selection: " + selection);
        if (!(selection instanceof CuboidSelection)) {
            messager.normal(Language.CUBOID_SELECTION_ONLY, sender);
            return;
        }
        Logging.fine("Selection is cuboid");
        Region region;
        try {
            region = selection.getRegionSelector().getRegion();
        } catch (IncompleteRegionException e) {
            messager.normal(Language.MUST_COMPLETE_SELECTION, sender);
            return;
        }
        Logging.fine("Region: " + region);
        Arena arena;
        try {
            arena = plugin.getArenaManager().newArena(args.get(0), region);
        } catch (IllegalArgumentException e) {
            messager.sendMessage(sender, e.getMessage());
            return;
        } catch (IOException e) {
            messager.bad(Language.CMD_CREATE_ARENA_FILE_ISSUE, sender, e.getMessage());
            return;
        }
        Logging.fine("Arena: " + arena);
        messager.good(Language.CMD_CREATE_ARENA_SUCCESS, sender, arena.getName());
    }
}
