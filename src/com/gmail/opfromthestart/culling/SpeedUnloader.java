package com.gmail.opfromthestart.culling;

import org.bukkit.Chunk;
import org.bukkit.plugin.java.JavaPlugin;

public class SpeedUnloader extends Unloader {
    JavaPlugin plug;

    public SpeedUnloader(JavaPlugin plugin)
    {
        plug = plugin;
    }

    @Override
    public boolean willSave(Chunk chunk) {
        if (plug.getConfig().getInt("eclipseplugin.culling.time")==-1)
        {
            return true;
        }
        return chunk.getInhabitedTime() > plug.getConfig().getInt("eclipseplugin.culling.time");
    }
}
