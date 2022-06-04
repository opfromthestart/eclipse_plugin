package com.gmail.opfromthestart.culling;

import org.bukkit.Chunk;
import org.bukkit.plugin.java.JavaPlugin;

public class SpeedUnloader extends Unloader {

    public SpeedUnloader(JavaPlugin plug)
    {
        super(plug);
    }

    @Override
    public boolean willSave(Chunk chunk) {
        if (plugin.getConfig().getInt("eclipseplugin.culling.time")==-1)
        {
            return true;
        }
        // plug.getLogger().info(plug.getConfig().getInt("eclipseplugin.culling.time") + ">" + chunk.getInhabitedTime());
        return chunk.getInhabitedTime() > plugin.getConfig().getInt("eclipseplugin.culling.time");
    }
}
