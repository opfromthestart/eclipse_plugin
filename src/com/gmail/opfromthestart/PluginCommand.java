package com.gmail.opfromthestart;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class PluginCommand implements CommandExecutor {
    protected Plugin plugin;

    public PluginCommand(Plugin plug)
    {
        plugin = plug;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
