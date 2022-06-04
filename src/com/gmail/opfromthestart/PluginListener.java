package com.gmail.opfromthestart;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PluginListener implements Listener {
    protected Plugin plugin;

    public PluginListener(Plugin plug)
    {
        plugin = plug;
    }
}
