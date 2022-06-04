package com.gmail.opfromthestart.dura;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DuraCommand implements CommandExecutor {

    private final Plugin plugin;

    public DuraCommand(JavaPlugin plug)
    {
        plugin = plug;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0)
        {
            sender.sendMessage("Dura tools are  " + (plugin.getConfig().getBoolean("eclipseplugin.dura.active") ? "active" : "disabled"));
            return true;
        }
        else if (args.length==1)
        {
            boolean active = Boolean.parseBoolean(args[0]);
            plugin.getConfig().set("eclipseplugin.dura.active", active);
            sender.sendMessage("Dura tools are now " + (plugin.getConfig().getBoolean("eclipseplugin.dura.active") ? "active" : "disabled"));
            return true;
        }
        else
        {
            sender.sendMessage("Incorrect usage, try /dura or /dura [true | false]");
            return false;
        }
    }
}
