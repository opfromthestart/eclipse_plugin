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
                sender.sendMessage("Item limit set to " + val);
                plugin.saveConfig();
                return true;
            }
            else if (Objects.equals(args[0], "active"))
            {
                boolean val;
                val = Boolean.parseBoolean(args[1]);

                plugin.getConfig().set("eclipseplugin.dupe.active", val);
                sender.sendMessage(val ? "Dupe is active" : "Dupe is disabled");
                plugin.saveConfig();
                return true;
            }
            else if (Objects.equals(args[0], "timeout"))
            {
                float val;
                val = Float.parseFloat(args[1]);

                plugin.getConfig().set("eclipseplugin.dupe.timeout", (int)(val*1000));
                sender.sendMessage("Timeout set to " + (int)(val*1000) + "ms.");
                plugin.saveConfig();
                return true;
            }
            else
            {
                sender.sendMessage("Invalid setting, options are itemlimit, active, and timeout");
                return false;
            }
        }
        else if (args.length==1)
        {
            if (Objects.equals(args[0], "itemlimit")) {
                sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.dupe.itemlimit")).toString());
                return true;
            }
            else if (Objects.equals(args[0], "active"))
            {
                sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.dupe.active")).toString());
                return true;
            }
            else if (Objects.equals(args[0], "timeout"))
            {
                sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.dupe.timeout")).toString());
                return true;
            }
            else
            {
                sender.sendMessage("Invalid setting, options are itemlimit, active, and timeout");
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