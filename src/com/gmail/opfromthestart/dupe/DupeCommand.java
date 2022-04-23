package com.gmail.opfromthestart.dupe;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class DupeCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    public DupeCommand(JavaPlugin plug)
    {
        plugin = plug;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2)
        {
            if (Objects.equals(args[0], "itemlimit")) {
                int val;
                try {
                    val = Integer.parseInt(args[1]);
                } catch (NumberFormatException nfe) {
                    sender.sendMessage("Not a valid number");
                    return false;
                }
                plugin.getConfig().set("eclipseplugin.dupe.itemlimit", val);
                return true;
            }
            else if (Objects.equals(args[0], "active"))
            {
                boolean val;
                val = Boolean.parseBoolean(args[1]);

                plugin.getConfig().set("eclipseplugin.dupe.active", val);
                return true;
            }
            else
            {
                sender.sendMessage("Invalid setting, options are itemlimit and active");
                return false;
            }
        }
        else
        {
            sender.sendMessage("Invalid arguments");
            return false;
        }
    }
}