package com.dumptruckman.gmtools.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getchunky.chunky.ChunkyManager;
import org.getchunky.chunky.module.ChunkyCommand;
import org.getchunky.chunky.module.ChunkyCommandExecutor;
import org.getchunky.chunky.object.ChunkyChunk;
import org.getchunky.chunky.object.ChunkyPlayer;

/**
 * @author dumptruckman
 */
public class CmdChunkyChunkSetShop implements ChunkyCommandExecutor {

    public void onCommand(CommandSender sender, ChunkyCommand command, String label, String[] args) {
        ChunkyPlayer cPlayer = ChunkyManager.getChunkyPlayer((Player)sender);
        ChunkyChunk cChunk = cPlayer.getCurrentChunk();

        if (!cChunk.isOwned() || !cChunk.isOwnedBy(cPlayer)) {
            sender.sendMessage(ChatColor.RED + "You do not own this chunk!");
            return;
        }

        if (args.length != 1 || (!args[0].equalsIgnoreCase("true") && !args[0].equalsIgnoreCase("false"))) {
            sender.sendMessage(ChatColor.RED + "Specify true or false!");
            return;
        }

        cChunk.getData().put("BarterChunk", Boolean.valueOf(args[0]));
        sender.sendMessage(ChatColor.GREEN + "Success!");
    }
}
