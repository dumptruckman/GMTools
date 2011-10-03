package com.dumptruckman.gmtools.commands;

import com.dumptruckman.gmtools.locale.Font;
import com.dumptruckman.gmtools.permissions.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author dumptruckman
 */
public class TextCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Perms.GM_TEXT.has(sender)) return true;
        if (args.length == 0) return false;

        int r = 50;
        int i = 0;
        boolean isGlobal = false;

        if (args[0].startsWith("-r:")) {
            i++;
            try {
                r = Integer.valueOf(args[0].substring(3));
            } catch (NumberFormatException ignore) {
                sender.sendMessage("Enter a number for the radius!");
                return false;
            }
        } else if (args[0].startsWith("-g")) {
            i++;
            isGlobal = true;
        }

        String message = "";
        for (i = i; i < args.length; i++) {
            if (!message.isEmpty()) message += " ";
            message += args[i];
        }

        List<String> lines = Font.splitString(message);
        if (isGlobal) {
            for (String s : lines)
                Bukkit.getServer().broadcastMessage(s);
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be in game to use non-globally.");
            return true;
        }
        Player player = (Player) sender;
        
        List<Entity> entities = player.getNearbyEntities(r,r,r);

        for (Entity entity : entities) {
            if (entity instanceof CommandSender)
                for (String s : lines)
                    ((CommandSender)entity).sendMessage(s);
            for (String s : lines)
                    player.sendMessage(s);
        }
        return true;
    }
    
}
