package com.gmail.opfromthestart;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;

public abstract class PluginCommand implements CommandExecutor {
    protected Plugin plugin;

    public PluginCommand(Plugin plug)
    {
        plugin = plug;
    }
}
